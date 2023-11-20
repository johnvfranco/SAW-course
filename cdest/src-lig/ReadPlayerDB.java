// ReadPlayerDB.java - self explanatory - read DB and print to console
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.net.*;

class ReadPlayerDB {
   public static void readPlayerDB() {
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
					byte[] b = new byte[4];
					b[0] = (byte)Integer.parseInt(q.nextToken());
					b[1] = (byte)Integer.parseInt(q.nextToken());
					b[2] = (byte)Integer.parseInt(q.nextToken());
					b[3] = (byte)Integer.parseInt(q.nextToken());
					InetAddress ipadd = InetAddress.getByAddress(b);
					String identity = t.nextToken();
					while (t.hasMoreTokens()) identity += " "+t.nextToken();
					System.out.println(identity+"\t"+
											 ipadd.toString().substring(1)+"\t"+
											 port+"\t"+score);
					i++;
				} catch (Exception e) {
					System.out.println("ReadPlayerDB: "+e.toString());
				}
			}
      } catch (Exception e) {
			System.out.println("ReadPlayerDB: "+e.toString());
      }
   }

	public static void main(String argv[]) {
		ReadPlayerDB rpd = new ReadPlayerDB();
		rpd.readPlayerDB();
   }
}
