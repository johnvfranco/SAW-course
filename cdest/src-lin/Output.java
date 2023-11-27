public class Output {
	Scorer scorer;

	public Output (Scorer m) { scorer = m; }
	
	void print(String text) {
		System.out.println(text);
	}

	void time_label (String str) {
		scorer.time_label = str;
	}

	void timeleft (String str) {
		scorer.timeleft = str;
	}

	void ended (boolean e) {
		scorer.ended = e;
	}

	void startup(boolean a, boolean b) {
		scorer.started = a;
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
