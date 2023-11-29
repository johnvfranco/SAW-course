import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.math.*;
import java.lang.*;

class Scorer extends JFrame implements ActionListener {
	Output out = new Output(this);
	ContestTimer ct = null;
   PlayerDB playerDB = null;
   Contest contest = null;
   JButton addPlayer, deletePlayer, listPlayers, savePlayers, loadPlayers,
      bumpScore, checkServices, start, stop, reset, httpQuery, hitApache,
      hitWP, exit, help, hit, interrupt, suspend, resume, config;
   JTextArea text;
   JTextField address, addplayer, deleteplayer, checkplayer, apacheplayer,
		bumpplayer, wpplayer, amount, payload, port, phrase, hitplayer,
		intrpplayer, emailaddress, svrlocation, timeleft;
	Color bkgd = new Color(255,255,223);
	Color frgd = new Color(0,0,150);
	Font fnt = new Font("Helvetica", Font.PLAIN, 16);
	Font tfnt = new Font("Helvetica", Font.BOLD, 22);
	Font bfnt = new Font("Helvetica", Font.PLAIN, 15);
	JLabel lbl, title, time_label;
	configFrame cf = null;
	String url = "file:///home/httpd/html";
	String name = "standings.html";
	String fileloc = "/home/httpd/html";
	String ttl = "Contest";
	String logfile = "cdx.log";
	int logopt = 0;
	int recover = 0;
	int dynamic = 0;
	BigInteger start_time = new BigInteger("0");
	BigInteger end_time = new BigInteger("0");
	BigInteger current_time = new BigInteger("0");
	boolean started = false;
	boolean ended = false;
	boolean suspended = false;
	boolean bad = false;
	boolean db_loaded_from_file = false;

   public Scorer () {
      setLayout(new BorderLayout());
		setBackground(bkgd);

		JPanel q = new JPanel(new BorderLayout());
		q.setBackground(bkgd);

		JPanel p = new JPanel(new FlowLayout());
		p.setBackground(bkgd);
		p.add(title = new JLabel("CDX Control Panel"));
		title.setFont(tfnt);
		title.setForeground(frgd);
		q.add("North", p);

		p = new JPanel(new FlowLayout());
		p.setBackground(bkgd);
		
      p.add(start = new JButton("Start"));
		start.setFont(bfnt);
		start.setPreferredSize(new Dimension(100, 25));
		
      p.add(suspend = new JButton("Suspend"));
		suspend.setFont(bfnt);
		suspend.setPreferredSize(new Dimension(100, 25));
		
      p.add(resume = new JButton("Resume"));
		resume.setFont(bfnt);
		resume.setPreferredSize(new Dimension(100, 25));
		
      p.add(stop = new JButton("Stop"));
		stop.setFont(bfnt);
		stop.setPreferredSize(new Dimension(100, 25));
		
      p.add(new JLabel("      "));
      p.add(loadPlayers = new JButton("Load"));
		loadPlayers.setFont(bfnt);
		loadPlayers.setPreferredSize(new Dimension(100, 25));
		
      p.add(savePlayers = new JButton("Save"));      
		savePlayers.setFont(bfnt);
		savePlayers.setPreferredSize(new Dimension(100, 25));
		
      p.add(listPlayers = new JButton("List"));
		listPlayers.setFont(bfnt);
		listPlayers.setPreferredSize(new Dimension(100, 25));
		
      q.add("Center", p);

		p = new JPanel(new FlowLayout());
		p.setBackground(bkgd);
		p.add(new JLabel(" "));
		q.add("South", p);

		add("North", q);

		q = new JPanel(new BorderLayout());
		q.setBackground(bkgd);

      p = new JPanel(new FlowLayout());
      p.setBackground(bkgd);

		p.add(time_label = new JLabel("time label:"));
		time_label.setFont(fnt);
		p.add(timeleft = new JTextField(10));
		timeleft.setFont(fnt);
		p.add(new JLabel("                           "));
		
      p.add(help = new JButton("Help"));
		help.setFont(bfnt);
		help.setPreferredSize(new Dimension(120, 25));
		
      p.add(new JLabel("                "));

		p.add(config = new JButton("Configure"));
		config.setFont(bfnt);
		config.setPreferredSize(new Dimension(120, 25));
		
      p.add(new JLabel("                "));
      p.add(reset = new JButton("Reset"));
		reset.setFont(bfnt);
		reset.setPreferredSize(new Dimension(120, 25));
		
      p.add(new JLabel("                "));
		
      p.add(exit = new JButton("Exit"));
		exit.setFont(bfnt);
		exit.setPreferredSize(new Dimension(120, 25));

		q.add("Center", p);
		
		q.add("North", new JLabel("  "));
		
      add("South", q);

		int topsize = 3;
		
		JPanel r = new JPanel(new GridLayout(1,2));
		r.setBackground(bkgd);

		q = new JPanel(new BorderLayout());  // left: button, center: player, right: ip
      q.setBackground(bkgd);

		JPanel s = new JPanel(new GridLayout(9,1));
		s.setBackground(bkgd);

		s.add(mkButtonPanel(addPlayer=new JButton("Add"), topsize));          // row 1
		s.add(mkButtonPanel(deletePlayer = new JButton("Delete"), topsize));  // row 2
		s.add(mkButtonPanel(checkServices = new JButton("Check"), topsize));  // row 3
		s.add(mkButtonPanel(interrupt = new JButton("Interrupt"), topsize));  // row 4
		s.add(mkButtonPanel(bumpScore = new JButton("Bump"), topsize));       // row 5
		s.add(mkButtonPanel(hitWP = new JButton("WP"), topsize));             // row 6
		s.add(mkButtonPanel(hitApache = new JButton("HTTP"), topsize));       // row 7
		s.add(mkLabelPanel(lbl = new JLabel("  "), topsize));                 // row 8
		s.add(mkButtonPanel(hit = new JButton("Hit"), topsize));              // row 9

		q.add("West", s);

		s = new JPanel(new GridLayout(9,3));
		s.setBackground(bkgd);
		// row 1
		s.add(mkTextPanel(addplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkTextPanel(address = new JTextField(), new JLabel("IP address  "), topsize));
		s.add(mkTextPanel(emailaddress = new JTextField(), new JLabel("email  "), topsize));
		// row 2
		s.add(mkTextPanel(deleteplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 3
		s.add(mkTextPanel(checkplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		s.add(mkTextPanel(svrlocation = new JTextField(),
								new JLabel("OpenVPN server location   "), topsize));
		// row 4
		s.add(mkTextPanel(intrpplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 5
		s.add(mkTextPanel(bumpplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkTextPanel(amount = new JTextField(), new JLabel("Score "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 6
		s.add(mkTextPanel(wpplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 7
		s.add(mkTextPanel(apacheplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkTextPanel(payload = new JTextField(), new JLabel("Payload  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 8
		s.add(mkTextPanel(phrase = new JTextField(), new JLabel("Phrase  "), topsize));
		s.add(mkTextPanel(port = new JTextField(), new JLabel("Port  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		// row 9
		s.add(mkTextPanel(hitplayer = new JTextField(), new JLabel("Player  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		s.add(mkLabelPanel(new JLabel("  "), topsize));
		
		q.add("Center", s);

		r.add(q);

		q = new JPanel(new BorderLayout());
		q.setBackground(bkgd);
		q.add("North", new JLabel("  "));
		q.add("South", new JLabel("  "));
		q.add("East", new JLabel("     "));
      q.add(new JScrollPane(text = new JTextArea()));
		text.setFont(fnt);

		r.add(q);

		add(r);

      start.addActionListener(this);
      interrupt.addActionListener(this);
      config.addActionListener(this);
      stop.addActionListener(this);
      reset.addActionListener(this);
      exit.addActionListener(this);
      loadPlayers.addActionListener(this);
      savePlayers.addActionListener(this);
      listPlayers.addActionListener(this);
      addPlayer.addActionListener(this);
      deletePlayer.addActionListener(this);
      checkServices.addActionListener(this);
      bumpScore.addActionListener(this);
      hitWP.addActionListener(this);
      hitApache.addActionListener(this);
		hit.addActionListener(this);
      help.addActionListener(this);
      address.addActionListener(this);
      amount.addActionListener(this);
      payload.addActionListener(this);
      suspend.addActionListener(this);
      resume.addActionListener(this);

		address.setHorizontalAlignment(JTextField.CENTER);
		deleteplayer.setHorizontalAlignment(JTextField.CENTER);
		addplayer.setHorizontalAlignment(JTextField.CENTER);
		checkplayer.setHorizontalAlignment(JTextField.CENTER);
		apacheplayer.setHorizontalAlignment(JTextField.CENTER);
		bumpplayer.setHorizontalAlignment(JTextField.CENTER);
		wpplayer.setHorizontalAlignment(JTextField.CENTER);
		amount.setHorizontalAlignment(JTextField.CENTER);
		payload.setHorizontalAlignment(JTextField.CENTER);
		port.setHorizontalAlignment(JTextField.CENTER);
		phrase.setHorizontalAlignment(JTextField.CENTER);
		hitplayer.setHorizontalAlignment(JTextField.CENTER);
		intrpplayer.setHorizontalAlignment(JTextField.CENTER);
		emailaddress.setHorizontalAlignment(JTextField.CENTER);
		svrlocation.setHorizontalAlignment(JTextField.CENTER);
		timeleft.setHorizontalAlignment(JTextField.CENTER);

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
			this.out.timeleft("Not running - contest can only be started manually");
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

      DefaultCaret caret = (DefaultCaret)text.getCaret();
      caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		setSize(1200,700);
		setVisible(true);
   }

   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == help) help();
      else if (evt.getSource() == addPlayer ||
					evt.getSource() == address ||
					evt.getSource() == addplayer) addPlayerToDB();
      else if (evt.getSource() == listPlayers) listPlayers();
      else if (evt.getSource() == start) startIt();
      else if (evt.getSource() == exit) leave();
      else if (evt.getSource() == stop) {
			if (!started || ended) {
				Messages.contest_already_stopped(out);
				toLog("Attempt to stop contest failed - contest is already "+
						"stopped", 2);
			} else {
				this.out.print("  Contest will stop at least 10 seconds from "+
									"now...");
				if (ct == null) stopContest();
				else
					end_time =
						new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
				out.time_label("Contest:");
				if (bad) out.timeleft("Stopped"); else out.timeleft("Finished");
				(new sayIt(this)).start();
				toLog("Contest is stopped", 2);
			}
		}
      else if (evt.getSource() == suspend) suspendContest();
      else if (evt.getSource() == resume) resumeContest();
      else if (evt.getSource() == reset) (new Reset(this)).start();
      else if (evt.getSource() == savePlayers) savePlayerDB();
      else if (evt.getSource() == loadPlayers) loadPlayerDB();
      else if (evt.getSource() == checkServices ||
					evt.getSource() == checkplayer ) checkServices();
      else if (evt.getSource() == httpQuery ||
					evt.getSource() == apacheplayer ||
					evt.getSource() == payload ||
					evt.getSource() == port ||
					evt.getSource() == phrase ||
					evt.getSource() == hitApache) httpQuery();
      else if (evt.getSource() == bumpScore ||
					evt.getSource() == bumpplayer ||
					evt.getSource() == amount) bumpScore();
      else if (evt.getSource() == deletePlayer ||
					evt.getSource() == deleteplayer) deletePlayer();
      else if (evt.getSource() == hit ||
					evt.getSource() == hitplayer) hit();
      else if (evt.getSource() == hitWP ||
					evt.getSource() == hitplayer) hitWP();
      else if (evt.getSource() == interrupt ||
					evt.getSource() == intrpplayer) interrupt();
		else if (evt.getSource() == config)
			if (cf == null) cf = new configFrame(this);
   }
	
   void help () {
		Messages.help(out);
      toLog("Help requested", 0);
   }
	
   void interrupt () {
      String plr = intrpplayer.getText();
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
			toLog("Interrupt to "+plr+" failed - no such player",2);
   }
	
   void hit () {
      String plr = hitplayer.getText();
		if (!started || ended) {
			Messages.contest_must_be_running(out);
			toLog("http query to "+plr+" failed - contest is not started", 2);
			return;
		}
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("http query to "+plr+" failed - player database does not exist", 2);
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
	
   void hitWP () {
      String plr = wpplayer.getText();
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
	
   void deletePlayer () {
      String plr = deleteplayer.getText();
		if (playerDB == null) {
			Messages.no_playerDB(out);			
			toLog("Attempt to delete player "+plr+" failed - player database doesn't exist", 2);
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
	
   void bumpScore () {
      long amt;
      String plr = bumpplayer.getText();
      try {
			amt = (long)Integer.parseInt(amount.getText());
      } catch (Exception e) { amt = 0; }
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("Attempt to bump "+plr+" score failed - player database does not exist", 2);
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
	
   void httpQuery () {
      String plr = apacheplayer.getText();
		if (!started || ended) {
			Messages.contest_must_be_running(out);			
			toLog("http query on "+plr+" failed - contest is not running", 2);
			return;
		}
		int prt;
		try {
			prt = Integer.parseInt(port.getText());
		} catch (Exception e) {
			Messages.http_query_fail(out, "a number in the port field");			
			toLog("http query on "+plr+" failed - port argument must be a number", 2);
			return;
		}
      String pld = payload.getText();
		if (pld == null || pld.equals("")) {
			Messages.http_query_fail(out, "an entry in the payload field");			
			toLog("http query on "+plr+" failed - empty payload field", 2);
			return;
		}
      String phs = phrase.getText();
		if (phs == null || phs.equals("")) {
			Messages.http_query_fail(out, "an entry in the phrase field");			
			toLog("http query on "+plr+" failed - empty phrase field", 2);
			return;
		}
		if (playerDB == null) {
			Messages.no_playerDB(out);
			toLog("http query on "+plr+" failed - player database does not exist", 2);
			return;
		}
      Player player = playerDB.lookup(plr);
      if (player != null) {
			HTTPQuery q = new HTTPQuery(player, pld, prt, phs, null);
			q.start();
			Messages.httpQuery(out, plr, pld, prt, phs);			
			toLog("http query to "+plr+" succeeded with payload:"+pld+" port:"+prt+
					" phrase:"+phs, 2);
      } else {
			toLog("http query for player "+plr+" failed - no such player", 2);
		}
   }
	
   void checkServices () {
      String plr = checkplayer.getText();
      Player player = playerDB.lookup(plr);
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
      if (player != null) {
			CheckServices c = new CheckServices(player, this);
			c.start();
			Messages.services_checked(out, plr);			
			toLog("Services of player "+player.getIdentity()+" are checked", 2);
      } else {
			toLog("Attempted service check for player "+plr+"failed - "+
					"no such player", 2);
		}
   }      
	
   void addPlayerToDB () {
      byte[] ip = new byte[4];
      int[] ipalt = new int[4];
      String plr = addplayer.getText();
		if (plr == null || plr.equals("")) {
			Messages.add_player_fail(out, "without player name");
			return;
		}
		String email = emailaddress.getText();
		if (email == null || email.equals("")) {
			Messages.add_player_fail(out, "without email address");
			return;
		}
		if (address.getText().equals("")) {
			Messages.add_player_fail(out, "without IP address");
			return;
		}
      StringTokenizer ipaddr = new StringTokenizer(address.getText(),".");
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
			toLog("Player "+plr+" added at "+ipalt[0]+"."+ipalt[1]+"."+
					ipalt[2]+"."+ipalt[3], 2);
      } catch (Exception e) {
			Messages.add_player_error(out, e.toString());			
			toLog("Addplayer, exception: "+e.toString(), 3);
      }
   }
	
   void savePlayerDB () {
		Messages.saveDB(out);
		if (playerDB == null) playerDB = new PlayerDB(this);
      SaveDB.saveArray(playerDB.players, this);
      toLog("Players saved in database", 0);
   }
	
   boolean loadPlayerDB () {
		if (playerDB == null) playerDB = new PlayerDB(this);
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
	
   void startIt () {
		if (started) {
			Messages.contest_already_started(out);
			toLog("Attempt to start contest failed - contest already is started",
					2);
		} else if (ended) {
			Messages.contest_ended(out);
			toLog("Attempt to start contest failed - contest has ended", 2);
		} else {
			this.out.print("  Wait a bit...");
			if (playerDB == null) playerDB = new PlayerDB(this);
			loadPlayerDB();

			if (ct == null) startContest();  // made this the same as
			                                 // non-GUI version
			else
				start_time =
					new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
			Messages.contest_started(out);
			if (bad) {
				out.time_label("Contest:");
				out.timeleft("Running - use STOP to stop the contest");
			}
			toLog("Contest is started", 2);
		}
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
      toLog("Contest started", 3);
   }

   void stopContest () {
      if (contest != null) contest.stopContest();
      contest = null;
      toLog("Contest stopped", 3);
   }
	
   void suspendContest () {
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
			if (contest != null) {
				if (contest.dl != null) contest.dl.suspend();
				suspended = true;
				contest.suspend();
			}
			Messages.contest_suspended(out);			
			toLog("Contest suspended", 3);
		}
   }
	
   void resumeContest () {
		if ((!started || ended) || !suspended) {
			if (!started || ended) {
				Messages.cannot_be_resumed(out);
				toLog("Attempt to resume contest failed - contest is not running", 2);
			} else {
				Messages.not_suspended(out);
				toLog("Attempt to resume contest failed - contest is not suspended", 2);
			}
		} else {		
			if (contest != null) {
				contest.resume();
				suspended = false;
				if (contest.dl != null) contest.dl.resume();
			}
			Messages.contest_resumed(out);
			toLog("Contest resumed", 3);
		}
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
			fr.write(s);
			fr.close();
      } catch (Exception e) {
			this.out.print("  toLog: "+e.toString());
			this.out.print("------------------------------");
      }
   }

	JPanel mkButtonPanel (JButton jb, int topsize) {
		JPanel p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
		p.add("North", lbl = new JLabel("  "));
		lbl.setFont(new Font("Helvetica", Font.PLAIN, topsize));
		p.add("South", lbl = new JLabel("  "));
		lbl.setFont(new Font("Helvetica", Font.PLAIN, 10));		
		p.add("East", lbl = new JLabel("   "));
		p.add("West", lbl = new JLabel("   "));
		JPanel u = new JPanel(new FlowLayout());
		u.setBackground(bkgd);
		u.add(jb);
		jb.setFont(bfnt);
		jb.setPreferredSize(new Dimension(90, 25));
      p.add("Center", u);
		return p;
	}
	
	JPanel mkLabelPanel (JLabel jb, int topsize) {
		JPanel p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
		p.add("North", lbl = new JLabel("  "));
		lbl.setFont(new Font("Helvetica", Font.PLAIN, topsize));
		p.add("South", lbl = new JLabel("  "));
		lbl.setFont(new Font("Helvetica", Font.PLAIN, 10));		
		p.add("East", lbl = new JLabel("   "));
		p.add("West", lbl = new JLabel("   "));
		JPanel u = new JPanel(new FlowLayout());
		u.setBackground(bkgd);
		u.add(jb);
		jb.setFont(bfnt);
		jb.setPreferredSize(new Dimension(90, 25));
      p.add("Center", u);
		return p;
	}

	JPanel mkTextPanel (JTextField jt, JLabel jl, int topsize) {
		JLabel lbl;
		JPanel p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
		
		JPanel t = new JPanel(new FlowLayout(FlowLayout.CENTER));
		t.setBackground(bkgd);
		t.add(jt);
      p.add("Center", t);
		jt.setFont(fnt);
		jt.setPreferredSize(new Dimension(150,25));
		t = new JPanel(new FlowLayout(FlowLayout.CENTER));
		t.setBackground(bkgd);
		t.add(jl);
		jl.setFont(new Font("Helvetica", Font.BOLD, 10));
      p.add("South", t);
      p.add("North", lbl = new JLabel(" "));
		lbl.setFont(new Font("Helvetica", Font.PLAIN, topsize));
		p.add("East", lbl = new JLabel("   "));
		return p;
	}
}

class sayIt extends Thread {
	Scorer scorer = null;
	String str = "  Contest is stopped\n------------------------------\n";
	int i = 0;

	public sayIt (Scorer m) { scorer = m; }

	public void run () {
		try { sleep(10000); } catch (Exception e) { }		
		while (!scorer.ended && i++ < 20) {
			try { sleep(1000); } catch (Exception e) { }
		}
		scorer.text.append(str);
	}
}
