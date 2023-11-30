import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.math.*;
import java.lang.*;

class Scorer extends Thread {
	Output out = new Output(this);
	ContestTimer ct = null;
   PlayerDB playerDB = null;
   Contest contest = null;
	String url = "file:///home/httpd/html";
	String name = "standings.html";
	String fileloc = "/home/httpd/html";
	String ttl = "Contest";
	String logfile = "cdx.log";
	int logopt = 0;
	int recover = 0;
	int dynamic = 0;
	boolean bkgnd = false;
	BigInteger start_time = new BigInteger("0");
	BigInteger end_time = new BigInteger("0");
	BigInteger current_time = new BigInteger("0");
	boolean started = false;
	boolean ended = false;
	boolean suspended = false;
	String time_label = "";
	String timeleft = "";
	boolean bad = false;
	boolean db_loaded_from_file = false;

   public Scorer () {
		String temp = ReadParm.getSetup("PLAYER_DATABASE_STATE", this).trim();
		if (!temp.equals(""))
			try {
				dynamic = Integer.parseInt(temp);
			} catch (Exception e) { dynamic = 0; }
		temp = ReadParm.getSetup("LOGGING_OPTION", this).trim();
		if (!temp.equals(""))
			try {
				logopt = Integer.parseInt(temp);
			} catch (Exception e) { logopt = 0; }
		temp = ReadParm.getSetup("RECOVERY_STATE", this).trim();
		if (!temp.equals(""))
			try {
				recover = Integer.parseInt(temp);
			} catch (Exception e) { recover = 0; }
		temp = ReadParm.getSetup("WEB_URL", this).trim();
		if (!temp.equals("")) url = temp;
		temp = ReadParm.getSetup("SCORECARD_FILE", this).trim();
		if (!temp.equals("")) name = temp;
		temp = ReadParm.getSetup("WEB_DIRECTORY", this).trim();
		if (!temp.equals("")) fileloc = temp;
		temp = ReadParm.getSetup("SCORECARD_TITLE", this).trim(); 
		if (!temp.equals("")) ttl = temp;
		temp = ReadParm.getSetup("LOG_FILE", this).trim();
		if (!temp.equals("")) logfile = temp;
		temp = ReadParm.getSetup("START_TIME_UNIX", this).trim();
		if (!temp.equals("")) start_time = new BigInteger(temp);
		temp = ReadParm.getSetup("END_TIME_UNIX", this).trim();
		if (!temp.equals("")) end_time = new BigInteger(temp);
		current_time =
			new BigInteger(String.valueOf(Instant.now().getEpochSecond()));

		if (start_time.compareTo(end_time) > 0) {
			bad = true;
			Messages.bad_start_greater(out);
			
		} else if ((start_time.compareTo(new BigInteger("0")) == 0) ||
					  (end_time.compareTo(new BigInteger("0")) == 0)) {
			bad = true;
			Messages.bad_start_or_end_0(out);
			
		} else if (start_time.compareTo(end_time) == 0) {
			bad = true;
			Messages.bad_start_equals_end(out);

		} else {
			if (start_time.compareTo(current_time) > 0)
				this.out.time_label("Time to start:");
			else if (current_time.compareTo(end_time) >= 0)
				this.out.time_label("Contest:");
			else {   // contest is already underway
				if (playerDB == null) {
					playerDB = new PlayerDB(this);
					db_loaded_from_file = loadPlayerDB();
				}
				this.out.time_label("Time to end:");
			}
		}
		
		if (bad) {
			this.out.time_label("Contest:");
			this.out.timeleft("Not running - contest may only be started manually");
			Messages.no_parameters(out);
		}

		if (!db_loaded_from_file) {
			Messages.no_db_memory(out);
			if (!ReadParm.parm_file_marker) Messages.run_configure(out);
		}

		if (!bad) {
			contest = new Contest(this);
			ct = new ContestTimer(this);
			ct.start();
		}
		start();
   }

	public void run () {
		String command = null;
		Get get = new Get();
		while (true) {
			System.out.print("command> ");
			command = get.command();
			if (command.equals("")) { command = null; }			
			if (command != null) {
				String args[] = new String[4];
				args[0] = args[1] = args[2] = args[3] = null;
				StringTokenizer tokens = new StringTokenizer(command, " ");
				String cmd = tokens.nextToken().trim();
				int i = 0;
				while (tokens.hasMoreTokens() && i < 4) args[i++] = tokens.nextToken().trim();

				if (cmd.toUpperCase().equals("TIME")) {
					Messages.show_time(out, this);
				} else if (cmd.toUpperCase().equals("HELP")) {
					help();
				} else if (cmd.toUpperCase().equals("INTERRUPT")) {
					if (args[0] != null) interrupt(args[0]);
					else 
						Messages.interrupt_help(out);

				} else if (cmd.toUpperCase().equals("HIT")) {
					if (args[0] != null) hit(args[0]);
					else 
						Messages.hit_help(out);

				} else if (cmd.toUpperCase().equals("WP")) {
					if (args[0] != null) hitWP(args[0]);
					else
						Messages.wp_help(out);

				} else if (cmd.toUpperCase().equals("DELETE")) {
					if (args[0] != null) deletePlayer(args[0]);
					else 
						Messages.delete_help(out);
					
				} else if (cmd.toUpperCase().equals("BUMP")) {
					if (args[0] != null && args[1] != null)
						bumpScore(args[0], args[1]);
					else
						Messages.bump_help(out);
					
				} else if (cmd.toUpperCase().equals("HTTP")) {
					if (args[0] != null && args[1] != null && args[2] != null &&
						 args[3] != null)
						httpQuery(args[0], args[1], args[2], args[3]);
					else 
						Messages.http_help(out);
					
				} else if (cmd.toUpperCase().equals("CHECK")) {
					if (args[0] != null)
						checkServices(args[0]);
					else
						Messages.check_help(out);
					
				} else if (cmd.toUpperCase().equals("ADD")) {
					if (args[0] != null && args[1] != null && args[2] != null)
						addPlayerToDB(args[0], args[1], args[2]);
					else 
						Messages.add_help(out);
					
				} else if (cmd.toUpperCase().equals("SAVE")) {
					Messages.saveDB(out);
					savePlayerDB();
				} else if (cmd.toUpperCase().equals("LOAD")) {
					if (playerDB == null) playerDB = new PlayerDB(this);
					loadPlayerDB();
				} else if (cmd.toUpperCase().equals("LIST")) {
					if (playerDB != null) {
						Messages.list_players(out);
						listPlayers();
					} else {
						Messages.no_db_memory(out);
						toLog("Attempt to list players failed - player database "+
								"doesn't exist", 2);
					}
				} else if (cmd.toUpperCase().equals("RESET")) {
					resetPlayers();
				} else if (cmd.toUpperCase().equals("START")) {
					if (ended) {
						Messages.contest_ended(out);
						toLog("Attempt to start contest failed - contest has ended",
								2);
					} else if (started) {
						Messages.contest_already_started(out);
						toLog("Attempt to start contest failed - contest already "+
								"has been started", 2);
					} else {
						this.out.print("  Wait a bit...");
						if (playerDB == null) playerDB = new PlayerDB(this);
						loadPlayerDB();

						if (ct == null) startContest();
						else
							start_time =
								new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
						if (contest != null) contest.goAhead();
						Messages.contest_started(out);
						if (bad) {
							out.time_label("Contest:");
							out.timeleft("Running - use STOP to stop the contest");
						}
						toLog("Contest is started", 2);
					}
				} else if (cmd.toUpperCase().equals("STOP")) {
					if (!started || ended) {
						Messages.contest_already_stopped(out);
						toLog("Attempt to stop contest failed - contest is already "+
								"stopped", 2);
					} else { // started must be true and ended must be false
						this.out.print("  Wait at least 10 seconds...");
						if (ct == null) stopContest();
						else
							end_time =
								new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
						if (contest != null) contest.goAhead();
						Messages.contest_stopped(out);
						if (bad) {
							out.time_label("Contest:");
							out.timeleft("Stopped");
						}
						toLog("Contest is stopped", 2);
					}
				} else if (cmd.toUpperCase().equals("SUSPEND")) {
					if ((!started || ended) || suspended) {
						if (!started || ended) {
							Messages.cannot_be_suspended(out);
							toLog("Attempt to suspend contest failed - contest is not "+
									"running", 2);
						} else {
							Messages.contest_already_suspended(out);
							toLog("Attempt to suspend contest failed - contest is already "+
									"suspended", 2);
						}
					} else {
						suspendContest();
						toLog("Contest suspended", 2);
					}
				} else if (cmd.toUpperCase().equals("RESUME")) {
					if ((!started || ended) || !suspended) {
						if (!started || ended) {
							Messages.cannot_be_resumed(out);
							toLog("Attempt to resume contest failed - contest is not "+
									"running", 2);
						} else {
							Messages.not_suspended(out);
							toLog("Attempt to resume contest failed - contest is not "+
									"suspended", 2);
						}
					} else {		
						resumeContest();
						toLog("Contest resumed", 2);
					}
				} else if (cmd.toUpperCase().equals("EXIT")) {
					Messages.exiting(out);
					toLog("Exiting", 2);
					leave();
				} else if (cmd.toUpperCase().equals("VARS")) {
					Messages.print_vars(out, this);
				} else {
					Messages.unrecognized(out, cmd.toUpperCase());
				}
			}
		}
   }

   void help () {
		Messages.help(out);
      toLog("Help requested", 0);
   }
	
   void interrupt (String plr) {
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("Interrupt to "+plr+" failed - contest is not started", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null && contest != null) {
			contest.findAndInterruptPlayer(plr);
			Messages.interrupt(out, plr);
			toLog("Player "+plr+" interrupted", 2);
      } else
			toLog("Interrupt to "+plr+" failed - no such player", 2);
   }
	
   void hit (String plr) {
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("http query to "+plr+" failed - contest is not started", 2);
			return;
		}
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("http query to "+plr+" failed - player database does not "+
					"exist", 2);
			return;
		}		
      Player player = playerDB.lookup(plr);
      if (player != null) {
			HTTPQuery q = new HTTPQuery(player,"index.html",80,"<head>", null);
			q.start();
			Messages.hit(out, plr);
			toLog("http query to "+plr+": index.html, port 80, <head>", 2);
      } else {
			toLog("http query to "+plr+" failed - no such player", 2);
		}
   }
	
   void hitWP (String plr) {
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("Wordpress query to "+plr+" failed - contest not started", 2);
			return;
		}
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("Wordpress query to "+plr+" failed - no player database", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			String phrase = "wordpress";
			HTTPQuery q = new HTTPQuery(player, "wordpress/?p=4", 80, phrase, null);
			q.start();
			Messages.wp(out, plr);
			toLog("Wordpress query to "+plr+" succeeded", 2);
      } else {
			toLog("Wordpress query to "+plr+" failed - no such player", 2);
		}
   }      
	
   void deletePlayer (String plr) {
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("Attempt to delete player "+plr+" failed - player "+
					"database doesn't exist", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			playerDB.delete(plr);
			Messages.delete(out, plr);
			toLog("Deleted player "+plr, 2);
      } else {
			toLog("Attempt to delete player "+plr+" failed - no such player", 2);
		}
   }      
	
   void bumpScore (String plr, String amount) {
      long amt;
      try {
			amt = (long)Integer.parseInt(amount);
      } catch (Exception e) { amt = 0; }
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("Attempt to bump "+plr+" score failed - player database "+
					"doesn't exist", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			player.bumpScore(amt);
			Messages.bump(out, plr, amt);
			toLog("Bump score of "+plr+" by "+amt, 2);
      } else {
			toLog("Attempt to bump "+plr+" score failed - no such player", 2);
		}
   }
	
   void httpQuery (String plr, String pld, String port, String phs) {
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("http query on "+plr+" failed - contest is not running", 2);
			return;
		}
		int prt;
		try {
			prt = Integer.parseInt(port);
		} catch (Exception e) {
			Messages.http_query_fail(out, "a number in the port field");
			toLog("http query on "+plr+" failed - port argument must be a "+
					"number", 2);
			return;
		}
		if (pld == null || pld.equals("")) {
			Messages.http_query_fail(out, "an entry in the payload field");
			toLog("http query on "+plr+" failed - empty payload field", 2);
			return;
		}
		if (phs == null || phs.equals("")) {
			Messages.http_query_fail(out, "an entry in the phrase field");
			toLog("http query on "+plr+" failed - empty phrase field", 2);
			return;
		}
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("http query on "+plr+" failed - player database does not "+
					"exist", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			HTTPQuery q = new HTTPQuery(player, pld, prt, phs, null);
			q.start();
			Messages.httpQuery(out, plr, pld, prt, phs);
			toLog("http query on "+plr+" succeeded with payload:"+pld+" port:"+
					prt+" phrase:"+phs, 2);
      } else {
			toLog("http query for player "+plr+" failed - no such player", 2);
		}
   }
	
   void checkServices (String plr) {
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("Checkservices on "+plr+" failed - contest is not running", 2);
			return;
		}
		if (playerDB == null) {
			Messages.check_failed_no_db(out);
			toLog("Checkservices on "+plr+" failed - player database does not exist", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			CheckServices c = new CheckServices(player, this);
			c.start();
			Messages.services_checked(out, plr);
			toLog("Services of player "+player.getIdentity()+" are checked", 2);
      } else {
			toLog("Attempt to check services of player "+plr+" failed - "+
					"no such player", 2);
		}
   }      
	
   void addPlayerToDB (String plr, String email, String address) {
      byte[] ip = new byte[4];
      int[] ipalt = new int[4];
		if (plr == null || plr.equals("")) {
			Messages.add_player_fail(out, "without player name");
			return;
		}
		if (email == null || email.equals("")) {
			Messages.add_player_fail(out, "without email address");
			return;
		}
		if (address.equals("")) {
			Messages.add_player_fail(out, "without IP address");
			return;
		}
      StringTokenizer ipaddr = new StringTokenizer(address,".");
      try {
			for (int i=0 ; i < 4 ; i++) {
				String tk = ipaddr.nextToken();
				ipalt[i] = Integer.parseInt(tk);
				ip[i] = (byte)Integer.parseInt(tk);
			}
			Player player = new Player(plr, ip, email, this);
			if (playerDB == null) playerDB = new PlayerDB(this);
			playerDB.add(player);
			Messages.added_player(out, plr, ipalt[0], ipalt[1], ipalt[2], ipalt[3]);
			toLog("Player "+plr+" added at "+
					ipalt[0]+"."+ipalt[1]+"."+ipalt[2]+"."+ipalt[3], 2);
      } catch (Exception e) {
			Messages.add_player_error(out, e.toString());
			toLog("Addplayer, exception: "+e.toString(), 3);
      }
   }
	
   void savePlayerDB () {
		if (playerDB == null) playerDB = new PlayerDB(this);
		SaveDB.saveArray(playerDB.players, this);
		toLog("Players saved in database", 0);
	}
	
   boolean loadPlayerDB () {
      Player[] p = ReadDB.readPlayerArray(this);
      if (p != null) {
			playerDB.players = p;
			playerDB.setNPlayers();
			Messages.players_loaded(out);
			toLog("Players loaded from the player database", 0);
			return true;
		} else {
			Messages.load_failed(out);
			toLog("Load of player database from file failed", 0);
			return false;
		}
   }
	
   void listPlayers () {
		if (playerDB != null) {		
			for (int i=0 ; i < playerDB.nplayers ; i++) {
				Player player = playerDB.players[i];
				String ident = player.getIdentity();
				int len = ident.length();
				for (int j=len ; j < 25 ; j++) ident += " ";
				this.out.print("  "+ident+" \t"+
									player.getInetAddress().toString().substring(1)+
									" \t"+player.getScore());
			}
			this.out.print("------------------------------");
			toLog("Players are listed", 0);
		} else {
			Messages.no_db_memory(out);
			toLog("Attempt to list players failed - player database doesn't exist", 2);			
		}
	}
	
   void resetPlayers () {
      if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("Attempt to reset has failed - player database does not "+
					"exist", 2);
			return;
      }
		if (contest == null) {
			Messages.reset_failed_no_contest(out);
			toLog("Attempt to reset has failed - contest object does not "+
					"exist", 2);
			return;
		}
      for (int i=0 ; i < playerDB.nplayers ; i++) {
			playerDB.players[i].reset();
			if (started && !ended && !suspended) {
				Messages.reset_player(out, playerDB.players[i].getIdentity());
				contest.stopAndMaybeStartPlayer(playerDB.players[i]);
			}
      }
		out.print(" -  -  -  -  -  -  -  -  -  -");
		Messages.reset(out);
      toLog("Players have been reset", 1);
   }
	
   void startContest () {
		if (contest == null) contest = new Contest(this);
      if (contest != null) {
			contest.start();
			suspended = false;
			toLog("Contest started", 3);
		} else {
			Messages.contest_null(out);
			toLog("Attempt to start contest failed - contest object is null", 3);
			return;
		}
   }
	
   void stopContest () {
      if (contest != null) contest.stopContest();
      contest = null;
      toLog("Contest stopped", 3);
   }
	
   void suspendContest () {
      if (contest != null) {
			if (contest.dl != null) contest.dl.suspend();
			suspended = true;
			contest.suspend();
		}
		Messages.contest_suspended(out);
      toLog("Contest suspended", 3);
   }
	
   void resumeContest () {
      if (contest != null) {
			contest.resume();
			suspended = false;
			if (contest.dl != null) contest.dl.resume();
		}
		Messages.contest_resumed(out);
      toLog("Contest resumed", 3);
   }

   void leave () {
      toLog("Exiting", 3);
      System.exit(0);
   }

   synchronized void toLog (String str, int level) {
      Date date = new Date();
      String d = date.toString();
      String s = d+": "+str+"\n";

		if (level > logopt) return;
      
      try {
			File file = new File("../config/cdx.log");
			FileWriter fr = new FileWriter(file, true);
			fr.write(str+"\n");
			fr.close();
      } catch (Exception e) {
			Messages.log_error(out, e.toString());
      }
   }
}

class Get {
	static public String command () {
		String cmd = "";
		try {
			InputStreamReader sr = new InputStreamReader(System.in);
			char ch = (char)sr.read();
			cmd += String.valueOf(ch);
			while (ch != '\n') {
				System.out.print(ch);
				ch = (char)sr.read();
				cmd += String.valueOf(ch);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
		return cmd;
	}
}
