public class Messages {
	public static void run_configure (Output out) {
		out.print("  Notice: you may want to run the Configurator");
		out.print("    Invoke CONFIGURE in the GUI version of the program "+
					 "to run");
		out.print("      the Configurator which allows setting contest start "+
					 "and end");
		out.print("      times plus logging, recovery, scoreboard and other "+
					 "parameters");
		out.print("    Parameters set by the Configurator are saved in file "+
					 "Parameters.txt");
		out.print("    Parameters.txt is read by this program on startup: if "+
					 "the file");
		out.print("      does not exist in the directory from which this "+
					 "program is run,");
		out.print("      or if you are unsure whether the file exists there, "+
					 "create one");
		out.print("      with the Configurator, copy Parameters.txt to this "+
					 "directory");
		out.print("      (which likely is on a remote machine necessitating a "+
					 "transfer");
		out.print("      via the Internet), and rerun this program");
		out.print("------------------------------");
	}

	public static void no_db_memory (Output out) {
		out.print("  Notice: no player database exists in memory");
		out.print("    A player database should be created before the contest is started,");
		out.print("      either automatically via the timer or manually via START.");
		out.print("    To create a player database in memory invoke ADD commands");
		out.print("      or load a database from the '"+
					 GameParameters.PLAYER_DB_FILE+"' file, if it exists, using the");
		out.print("      LOAD command.");
		out.print("    To save the player database to file '"+
					 GameParameters.PLAYER_DB_FILE+"' invoke the SAVE");
		out.print("      command.");
		out.print("    To list players that are in the database invoke the "+
					 "LIST command.");
		out.print("------------------------------");
	}

	public static void contest_ended (Output out) {
		out.print("  Cannot start a contest that is finished - invoke EXIT "+
					 "and rerun this program");
		out.print("    with start and end times set in the future via the "+
					 "Configurator");
		out.print("  Caution: set parameters to keep or reset scores as "+
					 "desired on program rerun");
		out.print("  If the 'Recover database' option in the Configurator is "+
					 "set to 'No' then");
		out.print("    reset occurs when the contest is restarted after this "+
					 "program is rerun");
		out.print("  Alternatively, edit the value of RECOVERY_STATE in "+
					 "Parameters.txt to 1");
		out.print("    to reset scores on contest restart");
		out.print("  Reset can occur immediately any time by invoking the "+
					 "RESET command");
		out.print("------------------------------");
	}

	public static void no_parameters (Output out) {
		out.print("    A contest may only be run manually using START and STOP");
		out.print("    commands.  To set up a contest for automatic starting and");
		out.print("    stopping run the Configurator by clicking 'Configure' below, then");
		out.print("    set dates, options regarding the scoreboard locations and title, and");
		out.print("    create OpenVPN keys if necessary or desired.  Exit the Configurator");
		out.print("    Configurator to create file Parameters.txt and, if necessary, the");
		out.print("    competitor credentials that will be sent to competitors.  Exit the");
		out.print("    Control Panel then re-run this program, this time to use the Control");
		out.print("    Panel to control the competition with updated parameters.  See the");
		out.print("    manual, Appendices A and C, for details and if there is a problem.");
		out.print("------------------------------");
	}

	public static void no_db_file (Output out) {
		out.print("  No player file '"+GameParameters.PLAYER_DB_FILE+"' exists");
		out.print("  Empty player database is created in memory");
		out.print("  Use ADD to build a player database");
		out.print("  then save it to '"+GameParameters.PLAYER_DB_FILE+"' by "+
					 "invoking SAVE");
		out.print("------------------------------");
	}
	
	public static void contest_already_started (Output out) {
		out.print("  Contest has already been started");
		out.print("------------------------------");
	}

	public static void contest_started (Output out) {
		out.print("  Contest is started");
		out.print("------------------------------");
	}

	public static void contest_already_stopped (Output out) {
		out.print("  Contest is already stopped");
		out.print("------------------------------");
	}

	public static void contest_stopped (Output out) {
		out.print("  Contest is stopped");
		out.print("------------------------------");
	}

	public static void load_failed (Output out) {
		out.print("  Load of player database from file failed");
		out.print("------------------------------");
	}		

	public static void cannot_be_suspended (Output out) {
		out.print("  Contest is not running so cannot be suspended");
		out.print("------------------------------");
	}

	public static void contest_already_suspended (Output out) {
		out.print("  Contest is already suspended");
		out.print("------------------------------");
	}

	public static void cannot_be_resumed (Output out) {
		out.print("  Contest is not running so cannot be resumed");
		out.print("------------------------------");
	}

	public static void not_suspended (Output out) {
		out.print("  Contest is not suspended so cannot be resumed");
		out.print("------------------------------");
	}

	public static void exiting (Output out) {
		out.print("  Leaving");
		out.print("------------------------------");
	}

	public static void print_vars (Output out, Scorer scorer) {
		out.print("");
		out.print("suspended: "+scorer.suspended);
		out.print("started:   "+scorer.started);
		out.print("ended:     "+scorer.ended);
		out.print("------------------------------");
	}

	public static void unrecognized (Output out, String cmd) {
		out.print("  Unrecognized command: "+cmd);
		out.print("------------------------------");
	}

	public static void list_players (Output out) {
		out.print("  Listing players");
		out.print("------------------------------");
	}

	public static void saveDB (Output out) {
		out.print("  Saving players to database file '"+
					 GameParameters.PLAYER_DB_FILE+"'");
		out.print("------------------------------");
	}

	public static void add_help (Output out) {
		out.print("  Usage: ADD <player-name> <email> <ip-address>");
		out.print("------------------------------");
	}

	public static void check_help (Output out) {
		out.print("  Usage: check <player>");
		out.print("------------------------------");
	}

	public static void http_help (Output out) {
		out.print("  Usage: http <player> <payload> <port> <phrase>");
		out.print("------------------------------");
	}

	public static void bump_help (Output out) {
		out.print("  Usage: bump <player-name> <points>");
		out.print("------------------------------");
	}

	public static void delete_help (Output out) {
		out.print("  Usage: delete <player-name>");
		out.print("------------------------------");
	}

	public static void wp_help (Output out) {
		out.print("  Usage: WP <player-name>");
		out.print("------------------------------");
	}

	public static void hit_help (Output out) {
		out.print("  Usage: hit <player-name>");
		out.print("------------------------------");
	}

	public static void interrupt_help (Output out) {
		out.print("  Usage: interrupt <player-name>");
		out.print("------------------------------");
	}

	public static void show_time (Output out, Scorer scorer) {
		out.print("  "+scorer.time_label+" "+scorer.timeleft);
		out.print("------------------------------");
	}

	public static void bad_start_greater (Output out) {
		out.print("----------------------------------");
		out.print("  Warning: start time is greater than end time");
	}

	public static void bad_start_or_end_0 (Output out) {
		out.print("----------------------------------");
		out.print("  Warning: start and/or end time is 0");
	}

	public static void bad_start_equals_end (Output out) {
		out.print("----------------------------------");
		out.print("  Warning: start time is equal to end time");
	}

	public static void log_error (Output out, String err) {
		out.print("  toLog: "+err);
		out.print("------------------------------");
	}

	public static void contest_resumed (Output out) {
      out.print("  Contest resumed");
		out.print("------------------------------");
	}

	public static void contest_suspended (Output out) {
      out.print("  Contest suspended");
		out.print("------------------------------");
	}

	public static void contest_null (Output out) {
		out.print("  Attempt to start contest failed - contest object is null");
		out.print("------------------------------");
	}

	public static void reset (Output out) {
      out.print("  Player database in memory is reset");
		out.print("  Invoke the SAVE command to copy the database to file, "+
					 "if desired");
		out.print("------------------------------");
	}

	public static void reset_failed_no_contest (Output out) {
		out.print("  Reset not possible until contest object is not null");
		out.print("------------------------------");
	}

	public static void reset_player (Output out, String player) {
		out.print("  Resetting player "+player);
	}

	public static void no_playerDB (Output out) {
		out.print("  Player database does not exist");
		out.print("------------------------------");
	}

	public static void players_loaded (Output out) {
		out.print("  Players loaded from '"+GameParameters.PLAYER_DB_FILE+
					 "' - invoke LIST to see them");
		out.print("------------------------------");
	}

	public static void added_player(Output out, String plr, int a1, int a2, int a3, int a4) {
		out.print("  Added player "+plr+" at "+a1+"."+a2+"."+a3+"."+a4);
		out.print("------------------------------");
	}

	public static void add_player_error (Output out, String e) {
		out.print("  AddPlayer exception: "+e);
		out.print("------------------------------");
	}

	public static void add_player_fail (Output out, String reason) {
		out.print("  Can't add player "+reason);
		out.print("------------------------------");
	}

	public static void http_query_fail (Output out, String reason) {
		out.print("  Must have "+reason);
		out.print("------------------------------");
	}

	public static void services_checked (Output out, String player) {
		out.print("  Services checked for player "+player);
		out.print("------------------------------");
	}

	public static void contest_must_be_running (Output out) {
		out.print("  Contest must be running for this to work");
		out.print("------------------------------");
	}

	public static void check_failed_no_db (Output out) {
		out.print("  Services cannot be checked: no player database exists");
		out.print("------------------------------");
	}

	public static void httpQuery (Output out, String plr, String pld, int prt, String phs) {
		out.print("  HTTP query to "+plr+" payload:"+pld+" port:"+prt+
					 " phrase:"+phs);
		out.print("------------------------------");
	}

	public static void bump (Output out, String player, long amount) {
		out.print("  Bump score of "+player+" by "+amount);
		out.print("------------------------------");
	}

	public static void delete (Output out, String player) {
		out.print("  Deleted player "+player);
		out.print("------------------------------");
	}

	public static void wp (Output out, String player) {
		out.print("  Wordpress query to "+player);
		out.print("------------------------------");
	}

	public static void hit (Output out, String player) {
		out.print("  HTTP query to "+player+": index.html, port 80, <head>");
		out.print("------------------------------");
	}

	public static void interrupt (Output out, String player) {
		out.print("  Player "+player+" interrupted");
		out.print("------------------------------");
	}

	public static void help (Output out) {
      out.print("  Commands:");
      out.print("    Start - \tBegin probing the services of each player");
      out.print("    Stop  - \tKill the check services threads");
      out.print("    Load - \tLoad players from database");
      out.print("    Save - \tSave players to database");
		out.print("    Time - \tShow days, hours, minutes, seconds to start "+
					 "or end");
      out.print("    List - \tList players");
      out.print("    Add -\tAdd named player with email and named ip "+
					 "addresses");
      out.print("    Delete -\tDelete named player");
      out.print("    Check - \tRun check services thread on named player");
      out.print("    Interrupt - Find and interrupt a named player");
      out.print("    Bump - \tIncrement score of named player");
      out.print("    WP - \tHit WordPress of named player");
      out.print("    HTTP - \tQuery named player w/ payload, port, phrase");
      out.print("    Hit - \tHit apache server of named player");
      out.print("    Reset - \tZero all scores");
      out.print("    Exit - \tLeave");
		out.print("------------------------------");
	}

	public static void no_player (Output out, String player) {
      out.print("  Player ["+player+"] is not in the database");
      out.print("------------------------------");
	}

	public static void unknown_host (Output out) {
		out.print("  Player class: Unknown host exception");
      out.print("------------------------------");
	}

	public static void save_exception (Output out) {
		out.print("  Cannot save to players file");
		out.print("------------------------------");
	}

	public static void checkservices_exception (Output out,
															  String cmd,
															  String reason) {
		out.print("  cannot execute "+cmd+" cause:"+reason);
      out.print("------------------------------");
	}

	public static void probe_exception (Output out,
													String cmd,
													String reason) {
		out.print("  cannot complete "+cmd+" cause:"+reason);
      out.print("------------------------------");
	}

	public static void close_nmap_exception (Output out,
														  String reason) {
		out.print("  cannot close is socket (nmap) - cause:"+reason);
      out.print("------------------------------");
	}

	public static void close_ping_exception (Output out,
														  String reason) {
		out.print("  cannot close is socket (ping) - cause:"+reason);
		out.print("------------------------------");
	}		
}

