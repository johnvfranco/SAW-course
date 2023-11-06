import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPQuery extends Thread {
   Monitor monitor = null;
   String payload, phrase;
   Player player;
   int port;

   HTTPQuery (Player plr, String pyld, int prt, String phs, Monitor m) {
      player = plr;
      payload = pyld;
      port = prt;
      phrase = phs;
      monitor = m;
   }

   public void run () {
      String line;
      String result = "";
      try {
         URL url=new URL("http:/"+player.getInetAddress()+":"+port+"/"+payload);
         InputStream is = (InputStream)url.getContent();
         InputStreamReader isr = new InputStreamReader(is);
         BufferedReader bis = new BufferedReader(isr);
         Date date = new Date();
         String d = date.toString();
         String plr = player.getIdentity();

         while ((line = bis.readLine()) != null) result += line;
         if (result.contains(phrase)) {
            player.bumpScore(1);
            if (monitor != null) monitor.toLog("\""+plr+"\": bump score (WP)", 2);
         }
         monitor.toLog("\""+plr+"\" result="+result, 3);
      } catch (Exception e) {  }
   }
}
