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
