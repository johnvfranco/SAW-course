public class Output {
	Scorer scorer = null;

	public Output (Scorer m) { scorer = m; }
	
	void print(String str) {
		scorer.text.append(str+"\n");
		scorer.text.setCaretPosition(scorer.text.getDocument().getLength());
	}

	void time_label (String str) {
		scorer.time_label.setText(str);
	}

	void timeleft (String str) {
		scorer.timeleft.setText(str);
	}

	void ended (boolean e) {
		scorer.ended = e;
	}

	void startup(boolean a) {
		scorer.started = a;
	}

	String gettime (long days, long hours, long mins, long secs) {
		return days+":"+hours+":"+mins+":"+secs;
	}
}
