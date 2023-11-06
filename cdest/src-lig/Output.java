public class Output {
	Monitor monitor = null;

	public Output (Monitor m) { monitor = m; }
	
	void print(String str) {
		monitor.text.append(str+"\n");
		monitor.text.setCaretPosition(monitor.text.getDocument().getLength());
	}

	void time_label (String str) {
		monitor.time_label.setText(str);
	}

	void timeleft (String str) {
		monitor.timeleft.setText(str);
	}

	void ended (boolean e) {
		monitor.ended = e;
	}

	void startup(boolean a) {
		monitor.started = a;
	}

	String gettime (long days, long hours, long mins, long secs) {
		return days+":"+hours+":"+mins+":"+secs;
	}
}
