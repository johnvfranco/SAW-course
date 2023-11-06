public class Go {
   public void go () {
		Monitor monitor = new Monitor();
		FileOp.copy("../config/"+monitor.logfile, "../config/"+monitor.logfile+".old",
						monitor);
		FileOp.create("../config/"+monitor.logfile, monitor);
		FileOp.copy("../config/"+GameParameters.PLAYER_DB_FILE,
						"../config/"+GameParameters.PLAYER_DB_FILE+".bak",
						monitor);
   }
}

/**
      try {
			String cmd = "mv "+monitor.logfile+" "+monitor.logfile+".old";
			Runtime.getRuntime().exec(cmd).getInputStream(); 
      } catch (Exception e) {
			monitor.out.print("  Cannot backup "+monitor.logfile);
			monitor.out.print("---------------------------");
      }
      try {
			String cmd = "touch "+monitor.logfile;
			Runtime.getRuntime().exec(cmd).getInputStream(); 
      } catch (Exception e) {
			monitor.out.print("  Cannot create "+monitor.logfile);
			monitor.out.print("---------------------------");
      }
      try {
			String cmd = "cp "+GameParameters.PLAYER_DB_FILE+" "+
				GameParameters.PLAYER_DB_FILE+".bak";
			Runtime.getRuntime().exec(cmd).getInputStream(); 
      } catch (Exception e) {
			monitor.out.print("  Cannot copy "+GameParameters.PLAYER_DB_FILE);
			monitor.out.print("---------------------------");
      }
**/
