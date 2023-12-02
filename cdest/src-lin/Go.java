public class Go {
   public void go () {
		Scorer scorer = new Scorer();
		FileOp.copy("../config/"+scorer.logfile, "../config/"+scorer.logfile+".old",
						scorer);
		FileOp.create("../config/"+scorer.logfile, scorer);
		FileOp.copy("../config/"+GameParameters.PLAYER_DB_FILE,
						"../config/"+GameParameters.PLAYER_DB_FILE+".bak",
						scorer);
   }
}
