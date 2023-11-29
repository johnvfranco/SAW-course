import java.util.*;
import java.io.*;
import java.net.*;

public class ReadDB {
   public static Player[] readPlayerArray (Scorer scorer) {
      Player [] players = new Player[100];
      for (int i=0 ; i < 100 ; i++) players[i] = null;
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
					players[i] = new Player(identity, ip, email, scorer, port, score);
					i++;
				} catch (Exception e) { }
			}
			br.close();
			fis.close();
			return players;
      }
      catch (Exception e) {
			Messages.no_db_file(scorer.out);
      }
      return null;
   }
}
