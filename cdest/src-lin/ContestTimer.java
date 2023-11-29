import java.time.*;
import java.math.*;

public class ContestTimer extends Thread {
	boolean running = true;
	Scorer scorer = null;

	public ContestTimer (Scorer mn) { scorer = mn; }

	public void run () {
		while (running) {
			scorer.current_time =
				new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
			if (scorer.current_time.compareTo(scorer.end_time) >= 0) {
				if (!scorer.ended) {
					scorer.out.ended(true);
					scorer.stopContest();
				}
				scorer.out.time_label("Contest:");
				scorer.out.timeleft("Finished");
				running = false;
			} else if (scorer.current_time.compareTo(scorer.start_time) >= 0) {
				if (!scorer.started) {
					scorer.out.startup(true, false);
					scorer.startContest();
				}
				scorer.out.time_label("Time to end:");
				long diff =
					(scorer.end_time.subtract(scorer.current_time)).longValue();
				scorer.out.timeleft(secsToMinsAndHours(diff));
			} else {
				scorer.out.time_label("Time to start:");
				long diff =
					(scorer.start_time.subtract(scorer.current_time)).longValue();
				scorer.out.timeleft(secsToMinsAndHours(diff));
			}
			try { sleep(1000); } catch (Exception e) { }
		}
	}
	
	String secsToMinsAndHours (long seconds) {
		long days = seconds / 86400;
		long hours = (seconds - (days * 86400)) / 3600;
		long mins = (seconds - (days * 86400) - (hours * 3600)) / 60;
		long secs = seconds % 60;
		return scorer.out.gettime(days, hours, mins, secs);
	}
}
