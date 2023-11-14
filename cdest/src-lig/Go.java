import java.io.*;

public class Go {
   public void go () {
		Monitor monitor = new Monitor();
		try {
			File fs = new File("../config/"+monitor.logfile);
			if (fs.exists()) {
				FileOp.copy("../config/"+monitor.logfile,
								"../config/"+monitor.logfile+".old",
								monitor);
			}
		} catch (Exception e) { }
		FileOp.create("../config/"+monitor.logfile, monitor);
		try {
			File fs = new File("../config/"+GameParameters.PLAYER_DB_FILE);
			if (fs.exists()) {
				FileOp.copy("../config/"+GameParameters.PLAYER_DB_FILE,
						"../config/"+GameParameters.PLAYER_DB_FILE+".bak",
						monitor);
			}
		} catch (Exception e) { }
   }
}

