import java.time.*;
import java.math.*;

public class ContestTimer extends Thread {
	boolean running = true;
	Monitor monitor = null;

	public ContestTimer (Monitor mn) { monitor = mn; }

	public void run () {
		while (running) {
			monitor.current_time =
				new BigInteger(String.valueOf(Instant.now().getEpochSecond()));
			if (monitor.current_time.compareTo(monitor.end_time) >= 0) {
				if (!monitor.ended) {
					monitor.out.ended(true);
					monitor.stopContest();
				}
				monitor.out.time_label("Contest:");
				monitor.out.timeleft("Finished");
				running = false;
			} else if (monitor.current_time.compareTo(monitor.start_time) >= 0) {
				if (!monitor.started) {
					monitor.out.startup(true, false);
					monitor.startContest();
				}
				monitor.out.time_label("Time to end:");
				long diff =
					(monitor.end_time.subtract(monitor.current_time)).longValue();
				monitor.out.timeleft(secsToMinsAndHours(diff));
			} else {
				monitor.out.time_label("Time to start:");
				long diff =
					(monitor.start_time.subtract(monitor.current_time)).longValue();
				monitor.out.timeleft(secsToMinsAndHours(diff));
			}
			try { sleep(1000); } catch (Exception e) { }
		}
	}
	
	String secsToMinsAndHours (long seconds) {
		long days = seconds / 86400;
		long hours = (seconds - (days * 86400)) / 3600;
		long mins = (seconds - (days * 86400) - (hours * 3600)) / 60;
		long secs = seconds % 60;
		return monitor.out.gettime(days, hours, mins, secs);
	}
}
