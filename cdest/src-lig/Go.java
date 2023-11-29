import java.io.*;

public class Go {
   public void go () {
		Scorer scorer = new Scorer();
		try {
			File fs = new File("../config/"+scorer.logfile);
			if (fs.exists()) {
				FileOp.copy("../config/"+scorer.logfile,
								"../config/"+scorer.logfile+".old",
								scorer);
			}
		} catch (Exception e) { }
		FileOp.create("../config/"+scorer.logfile, scorer);
		try {
			File fs = new File("../config/"+GameParameters.PLAYER_DB_FILE);
			if (fs.exists()) {
				FileOp.copy("../config/"+GameParameters.PLAYER_DB_FILE,
						"../config/"+GameParameters.PLAYER_DB_FILE+".bak",
						scorer);
			}
		} catch (Exception e) { }
   }
}

