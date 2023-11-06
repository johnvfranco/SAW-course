public class Reset extends Thread {
	Monitor monitor = null;

	public Reset (Monitor m) { monitor = m; }
	
	public void run () {
      if (monitor.playerDB == null) {
			Messages.no_playerDB(monitor.out);
			monitor.toLog("Attempt to reset has failed - player database does not "+
					"exist", 2);
			return;
      }
		if (monitor.contest == null) {
			Messages.reset_failed_no_contest(monitor.out);			
			monitor.toLog("Attempt to reset failed - contest object is null", 2);
			return;
		}
      for (int i=0 ; i < monitor.playerDB.nplayers ; i++) {
			monitor.playerDB.players[i].reset();
			if (monitor.started && !monitor.ended && !monitor.suspended) {
				Messages.reset_player(monitor.out, monitor.playerDB.players[i].getIdentity());
				monitor.contest.stopAndMaybeStartPlayer(monitor.playerDB.players[i]);
				monitor.toLog("Player "+monitor.playerDB.players[i].getIdentity()+" reset", 2);
			}
      }
		Messages.reset(monitor.out);
      monitor.toLog("Players have been reset", 1);
   }
}
