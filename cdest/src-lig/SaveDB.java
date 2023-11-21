import java.util.*;
import java.io.*;

public class SaveDB {
   public static void saveArray (Player[] players, Scorer m) {
      FileOutputStream fos;
      FileWriter fw;
      byte[] addr;
      
      try {
			fos = new FileOutputStream("../config/"+GameParameters.PLAYER_DB_FILE);
			fw  = new FileWriter(fos.getFD());
	 
			int i=0;
			while (players[i] != null) {
				fw.write(players[i].getScore()+" ");
				fw.write(players[i].getPort()+" ");
				addr = players[i].getInetAddress().getAddress();
				fw.write(addr[0]+"."+addr[1]+"."+addr[2]+"."+addr[3]+" ");
				fw.write(players[i].getEmail()+" ");
				fw.write(players[i].getIdentity()+"\n");
				i++;
			}
			fw.close();
      } catch (Exception e) {
			Messages.save_exception(m.out);
			m.toLog("Cannot save to players file", 2);
			e.printStackTrace();
      }      
   }
}
