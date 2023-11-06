import java.util.*;
import java.io.*;

class Pair {
   CheckServices[] cs;
   Player[] pl;
   int np;

   public Pair (CheckServices[] c, Player[] p, int n) { cs=c; pl=p; np=n; }
}

public class Contest extends Thread implements Serializable {
   PrintWriter pw = null;
   Monitor monitor = null;
   boolean running = true;
	MakeStandings ms = null;
	DynamicListener dl = null;

   /* This class has the responsibility of logging everything from the
      CheckServices threads.                                           */
   Contest (Monitor m) {  monitor = m;  }

   // Attempt to stop a thread - does not appear to have worked
   synchronized void findAndInterruptPlayer (String plr) {
      int nplayers = monitor.playerDB.nplayers;
      Player[] player = monitor.playerDB.players;
      CheckServices[] services = monitor.playerDB.services;
      for (int i=0 ; i < nplayers ; i++) {
			if (player[i].getIdentity().equals(plr) && services[i] != null) {
				services[i].interrupt();
				break;
			}
      }
   }

	// added so that the command prompt follows output from start here
	synchronized void notifyPrompt() {
		notify();
	}

	// command prompt waits here
	synchronized void goAhead () {
		try { wait(); } catch (Exception e) { }
	}	

   // Each iteration of the while loop in run() takes a long time.  It is 
   // possible that deletePlayer, addPlayer, or stopAndMaybeStartPlayer
   // are invoked in the meantime thereby changing the database that the
   // for loop executes on.  This function takes a snapshot of the player
   // database and hands it to the while loop in run() at the start of
   // each iteration so execution of the loop always executes on a stable
   // player database.
   synchronized Pair setitup (int n) {
      CheckServices[] c = new CheckServices[n];
      Player[] p = new Player[n];
      for (int i=0 ; i < n ; i++) {
			c[i] = monitor.playerDB.services[i];
			p[i] = monitor.playerDB.players[i];
      }
      return new Pair(c,p,n);
   }

   // A CheckServices thread is launched for each player and a loop 
   // probes services of all players and then saves the updated player
   // database
   public void run () {
		// If setup is dynamic then start a listener that will add players as
		// they request to be added
		if (monitor.dynamic != 0) {
			(dl = new DynamicListener(this)).start(); // kill dl on stop!!!
		}
		// Make score = 0 for all players and save as a new player database file
		if (monitor.recover != 0) {
			for (int i=0 ; i < monitor.playerDB.nplayers ; i++) {
				monitor.playerDB.players[i].reset();
				SaveDB.saveArray(monitor.playerDB.players, monitor);
			}
		}
		try {
			for (int i=0 ; i < monitor.playerDB.nplayers ; i++) {
				monitor.playerDB.services[i] =
					new CheckServices(monitor.playerDB.players[i], monitor);
				if (monitor.playerDB.services[i] != null)
					monitor.playerDB.services[i].start();
			}
		} catch (Exception e) { e.printStackTrace(); }
      monitor.started = true;

		ms = new MakeStandings(monitor);
		ms.start();

		notifyPrompt();
      
      while (running) {
			int time_between_probes = GameParameters.TIME_BETWEEN_PLAYER_PROBES;

			// Gather the current set of players which will remain constant
			// even if one or more of deletePlayer, addPlayer, or
         // stopAndMaybeStartPlayer are invoked while executing the for
			// loop below
			Pair pair = setitup(monitor.playerDB.nplayers);
			CheckServices[] cs = pair.cs;
			Player[] pl = pair.pl;
			int np = pair.np;
			
			// For all players as above kill a current running CheckServices
			// thread, and create and start a new CheckServices thread.
			// It is hoped that killing the existing CheckServices thread will 
			// release a stuck thread.  In 2017 stuck threads occurred when
			// probing WordPress - this caused some players to stop getting
			// points.  After each CheckServices probe is spawned the next
			// probe waits TIME_BETWEEN_PLAYER_PROBES milliseconds before
			// starting.
			//
			// Note: several attempts have been made to make Interrupt work
			// One try had findAndInterruptPlayer above null the thread reference
			// after interrupting and stopping.  That caused the second operand
			// in the || below to become true.  The result was to create a
			// new thread (OK, the stuck thread stayed stuck).  The next time
			// through clear should have been false and cs[i] should have been
			// not null but a new thread was created anyway.
			for (int i=0 ; i < np ; i++) {
				if ((cs[i] != null && cs[i].clear) ||
					 (cs[i] == null && pl[i] != null)) {
					Thread t = cs[i];                 // all this to stop a thread
					cs[i] = null;                     // 1st eliminate the ref
					if (t != null) t.interrupt();     // then interrupt it
					t = null;                         // eliminate the temp ref
					cs[i] = new CheckServices(pl[i], monitor);
					cs[i].start();
				}
				try { sleep(time_between_probes); } catch (Exception e) { }
			}
			SaveDB.saveArray(monitor.playerDB.players, monitor);
      }
   }
	
   synchronized void deletePlayer (Player plr) {
      monitor.playerDB.delete(plr.getIdentity());
   }
	
   synchronized void stopAndMaybeStartPlayer (Player plr) {
      for (int i=0 ; i < monitor.playerDB.nplayers ; i++) {
			if (monitor.playerDB.players[i] == plr) {
				Thread t = monitor.playerDB.services[i];
				monitor.playerDB.services[i] = null;
				if (t != null) t.interrupt();
				t = null;
				try { Thread.sleep(500); } catch (Exception e) {}
				if (monitor.started) {
					monitor.playerDB.services[i] = new CheckServices(plr, monitor);
					if (monitor.playerDB.services[i] != null)
						monitor.playerDB.services[i].start();
				}
				return;
			}
      }
   }
	
   synchronized void stopContest () {
		// stop making standings
		if (ms != null) ms.running = false;
		if (dl != null) dl.stopListener();
		dl = null;

		if (monitor == null) {
			System.out.println("stopContest: monitor is null");
			System.out.println("------------------------------");			
			return;
		} else if (monitor.playerDB == null) {
			System.out.println("  Contest is already stopped");
			System.out.println("------------------------------");			
			return;
		}

      for (int i=0 ; i < monitor.playerDB.nplayers ; i++) {
			Thread t = monitor.playerDB.services[i];
			monitor.playerDB.services[i] = null;
			if (t != null) t.interrupt();
			t = null;
      }
      try { sleep(10000); } catch (Exception e) { }
		running = false;
      monitor.ended = true;
		monitor.suspended = false;
		notifyPrompt();
   }
	
   synchronized void suspendContest () {
      running = false;
   }
}

