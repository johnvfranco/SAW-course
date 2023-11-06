public class Output {
	Monitor monitor;

	public Output (Monitor m) { monitor = m; }
	
	void print(String text) {
		System.out.println(text);
	}

	void time_label (String str) {
		monitor.time_label = str;
	}

	void timeleft (String str) {
		monitor.timeleft = str;
	}

	void ended (boolean e) {
		monitor.ended = e;
	}

	void startup(boolean a, boolean b) {
		monitor.started = a;
	}

	String gettime (long days, long hours, long mins, long secs) {
		String dys = (days == 1) ? "day" : "days";
		String hrs = (hours == 1) ? "hour" : "hours";
		String mns = (mins == 1) ? "minute" : "minutes";
		String sec = (secs == 1) ? "second" : "seconds";
		return days+" "+dys+", "+hours+" "+hrs+", "+mins+" "+mns+", "+
			secs+" "+sec;
	}
}
