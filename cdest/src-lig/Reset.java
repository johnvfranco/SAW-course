public class Reset extends Thread {
	Scorer scorer = null;

	public Reset (Scorer m) { scorer = m; }
	
	public void run () {
      if (scorer.playerDB == null) {
			Messages.no_playerDB(scorer.out);
			scorer.toLog("Attempt to reset has failed - player database does not "+
					"exist", 2);
			return;
      }
		if (scorer.contest == null) {
			Messages.reset_failed_no_contest(scorer.out);			
			scorer.toLog("Attempt to reset failed - contest object is null", 2);
			return;
		}
      for (int i=0 ; i < scorer.playerDB.nplayers ; i++) {
			scorer.playerDB.players[i].reset();
			if (scorer.started && !scorer.ended && !scorer.suspended) {
				Messages.reset_player(scorer.out, scorer.playerDB.players[i].getIdentity());
				scorer.contest.stopAndMaybeStartPlayer(scorer.playerDB.players[i]);
				scorer.toLog("Player "+scorer.playerDB.players[i].getIdentity()+" reset", 2);
			}
      }
		Messages.reset(scorer.out);
      scorer.toLog("Players have been reset", 1);
   }
}
