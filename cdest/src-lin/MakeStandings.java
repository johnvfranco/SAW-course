import java.io.*;
import java.util.*;
import java.net.*;

public class MakeStandings extends Thread {
   Player[] players = new Player[1500];
	Scorer mon = null;
	boolean running = true;
	int time_between_probes = GameParameters.TIME_BETWEEN_PLAYER_PROBES;		

   MakeStandings (Scorer m) { mon = m; }

	void readPlayers() {
		for (int i=0 ; i < 1500 ; i++) players[i] = null;
		
      try {
         FileInputStream fis = new FileInputStream("../config/"+
																	GameParameters.PLAYER_DB_FILE);
         BufferedReader br = new BufferedReader(new FileReader(fis.getFD()));

         int i=0;
         String line;
         while ((line = br.readLine()) != null) {
            try {
					StringTokenizer t = new StringTokenizer(line," ");
					long score = (long)Integer.parseInt(t.nextToken());
					int port = Integer.parseInt(t.nextToken());
					String inet = t.nextToken();
					StringTokenizer q = new StringTokenizer(inet,".");
					byte[] ip = new byte[4];
					ip[0] = (byte)Integer.parseInt(q.nextToken());
					ip[1] = (byte)Integer.parseInt(q.nextToken());
					ip[2] = (byte)Integer.parseInt(q.nextToken());
					ip[3] = (byte)Integer.parseInt(q.nextToken());
					String email = t.nextToken();
					String identity = t.nextToken();
					while (t.hasMoreTokens()) identity += " "+t.nextToken();
					players[i] = new Player(identity, ip, email, null, port, score);
					i++;
            } catch (Exception e) {	}
         }
         br.close();
         fis.close();
      } catch (Exception e) {
         mon.out.print("MakeStandings: can't read player database " + e);
			mon.out.print("------------------------------");
      }
   }
        
   void processPlayers() {
      Player p[] = new Player[1500];
      long w[] = new long[1500];
      FileWriter os = null;
      
      try {
         FileOutputStream fos = new FileOutputStream(mon.fileloc+"/"+mon.name);
         os = new FileWriter(fos.getFD());

         Date date = new Date();
         String ds = date.toString();
         
         // Print the file header
         os.write(
						"<html>\n"+
						"<head>\n"+
						"<META http-equiv=\"Refresh\" CONTENT=\"20;url="+
						mon.url+"/"+mon.name+"\">\n"+
						"<title>"+mon.ttl+"</title>\n"+
						"</head>\n"+
						"<body bgcolor=\"#ffffdf\">\n"+
						"<center>\n"+
						"<table cellspacing=10>\n"+
						"<tr>\n"+
						"<td align=\"CENTER\">&nbsp;</td>\n"+
						"<th><b><font size=+2 color=#BB0000>"+mon.ttl+"</font></b></th>\n"+
						"<td align=\"CENTER\">&nbsp;</td>\n"+
						"</tr>\n"+
						"</table><p>\n"+
						"<font size=+2 color=#0000BB><b>"+
						"<nobr>Scoreboard</nobr></b></font><p>\n"+
						"<p><font size=-1 color=\"#cc0000\">"+ds+"</font><p>\n"+
						//  "</center>\n"+
						"<p>\n"+
						"<table>\n"+
						"<tr><th colspan=7 align=center><font size=+1>"+
						"<b><u>Points for Working Services</u></b></font></th></tr><p>\n"+
						"<tr><th><b><u>Player</u></b></th><th>&nbsp;&nbsp;&nbsp;</th>"+
						"<th><b><u>IP Address</u></b></th><th>&nbsp;&nbsp;&nbsp;</th>"+
						"<th><u><b>Rank</u></b></th><th>&nbsp;&nbsp;&nbsp;</th>"+
						"<th><u><b>Points</u></b></th></tr>\n");

         int index = 0;
         while (players[index] != null) {
            long points = players[index].getScore();
            
            int i = index;
            for ( ; i > 0 && points > w[i-1] ; i--) {
               w[i] = w[i-1];
               p[i] = p[i-1];
            }
            w[i] = points;
            p[i] = players[index];
            index++;
         } 
         
         for (int i=0 ; i < index ; i++) {
            os.write(
							"<tr><td>"+p[i].getIdentity()+"</td><td></td>"+
							"<td>"+p[i].getInetAddress().toString().substring(1)+
							"</td><td></td>"+
							"<td align=CENTER>"+(i+1)+"</td><td></td>"+
							"<td align=CENTER>"+p[i].getScore()+"</td></tr>\n");
         }
         os.write("</table><p></center>\n");

         os.write("</body>\n</html>\n");
         os.close();
      } catch (Exception e) {
			mon.out.print(e.toString());
			mon.out.print("------------------------------");			
		}
   }

	public void run () {
		// Update of scoreboard is synced with a round of checking services in class
		// Contest.  Start of scoreboard update is delayed by 15 seconds.
		try { sleep(15000); } catch (Exception e) { }
		
		while (running) {
			readPlayers();
			processPlayers();
			try { sleep(time_between_probes); } catch (Exception e) { }
		}
	}
}
