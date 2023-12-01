import java.net.*;
import java.io.*;
import java.util.*;

public class DynamicListener extends Thread {
	Contest contest = null;
	boolean running = true;
	InputStreamReader isr = null;
	BufferedReader in = null;
	PrintWriter out = null;
	ServerSocket socket = null;
	Socket connect = null;
	byte[] ip = new byte[4];
	int[] ipalt = new int[4];

	public DynamicListener (Contest ct) { contest = ct; }

	public void run () {
		try {
			socket = new ServerSocket(9180);
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (running) {
			try {
				// Wait for a connection from a client then connect
            connect = socket.accept();
            isr = new InputStreamReader(connect.getInputStream());
            in = new BufferedReader(isr);
            out = new PrintWriter (connect.getOutputStream(), true);
				String str = in.readLine();
				if (str == null || str.equals("")) {
					out.println("Command syntax: [add|delete|save] [player] [ipaddress]");
					if (connect != null) connect.close();
					connect = null;
					continue;
				}
				StringTokenizer t = new StringTokenizer(str, " ");
				String command = t.nextToken();
				if (command == null || !(command.toUpperCase().equals("ADD") ||
												 command.toUpperCase().equals("DELETE") ||
												 command.toUpperCase().equals("SAVE"))) {
					out.println("Syntax: [add|delete|save] [player] [ipaddress]");
					if (connect != null) connect.close();
					connect = null;
					continue;
				} else if (command.toUpperCase().equals("ADD")) {
					String player = null, ipaddress = null;
					try {
						player = t.nextToken();
						ipaddress = t.nextToken();
					} catch (Exception e) {
						out.println("Command syntax: add player ipaddress");
						if (connect != null) connect.close();
						connect = null;
						continue;
					}
					StringTokenizer ipaddr = new StringTokenizer(ipaddress,".");
					try {
						for (int i=0 ; i < 4 ; i++) {
							String tk = ipaddr.nextToken();
							ipalt[i] = Integer.parseInt(tk);
							ip[i] = (byte)Integer.parseInt(tk);
						}
						Player plr =
							new Player(player, ip, "bogus@localhost", contest.scorer, 0, 0);
						contest.scorer.playerDB.add(plr);
						out.println("Player "+player+" added to the database");
						contest.scorer.toLog("Player "+player+" added at "+ipalt[0]+"."+
													 ipalt[1]+"."+ipalt[2]+"."+ipalt[3]+
													 " remotely", 2);
					} catch (Exception e) {
						out.println("Add player exception: "+e.toString());
						contest.scorer.toLog("Addplayer, exception: "+e.toString(), 3);
						if (connect != null) connect.close();
						connect = null;
						continue;
					}
				} else if (command.toUpperCase().equals("DELETE")) {
					String player = null;
					try {
						player = t.nextToken();
					} catch (Exception e) {
						out.println("Command syntax: delete player");
						if (connect != null) connect.close();
						connect = null;
						continue;
					}
					if (contest.scorer.playerDB.delete(player)) {
						out.println("Player "+player+" deleted from the database");
						contest.scorer.toLog("Player "+player+" deleted remotely from the "+
													 "database", 2);
					} else {
						out.println("Player "+player+" not in the database");
					}
				} else if (command.toUpperCase().equals("SAVE")) {
					SaveDB.saveArray(contest.scorer.playerDB.players, contest.scorer);
					out.println("Players saved");
					contest.scorer.toLog("Database saved remotely", 2);
				} else {
					out.println("Command syntax: [add|delete|save] [player] [ipaddress]");
				}
				if (connect != null) connect.close();
			} catch (Exception e) {
				contest.scorer.toLog("Dynamic Listener killed", 2);
			}
		}
		try {
			if (in != null) in.close();
			if (out != null) out.close();
			if (isr != null) isr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopListener () {
		running = false;
		try {
			if (connect != null) connect.close();
			if (socket != null) socket.close();
		} catch (Exception e) { }
	}
}
