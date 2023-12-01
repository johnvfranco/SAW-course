import java.nio.file.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CheckServices extends Thread implements Serializable {
   Scorer scorer = null;
   String ipaddr, phrase;
   Player player;
   InetAddress ipaddress;
   boolean[] services = new boolean[20]; // max of 20 services
   String[] srvports = new String[20];
   String[] entry = new String[20];
   String cmd = null;
   BufferedReader br = null;
   InputStream is = null;
   int nservices;
   public boolean clear = true;
   
   CheckServices (Player plr, Scorer m) {
      scorer = m;
      player = plr;
      ipaddress = player.getInetAddress();
      ipaddr = ipaddress.toString().substring(1);
      nservices = 5;
      for (int i=0 ; i < 20 ; i++) services[i] = false;
      srvports[0] = "13/tcp";
      srvports[1] = "21/tcp";
      srvports[2] = "80/tcp";
      srvports[3] = "631/tcp";
      srvports[4] = "3306/tcp";
      for (int i=5 ; i < 20 ; i++) srvports[i] = "__________";
      entry[0] = "daytime";
      entry[1] = "ftp";
      entry[2] = "apache";
      entry[3] = "printer";
      entry[4] = "mysql";
      for (int i=5 ; i < 20 ; i++) entry[i] = "----------";
      clear = true;
   }

   synchronized void startSection (Player player) {
      String plr = player.getIdentity();
      scorer.toLog("---- "+plr+" starting ----", 3);
   }

   synchronized void endSection (Player player) {
      String plr = player.getIdentity();
      scorer.toLog("---- "+plr+" ending ----", 3);
   }

   // Uses nmap to determine if services are up.  One of the lines returned
   // by nmap will be '631/tcp open ipp' which will cause
   // str.contains(srvports[3]) to evaluate to true.  services[3],
   // which starts out false and becomes true, is used to make sure only one
   // occurrence of the service is counted on each block of nmap queries.
   // each time a service is counted, the player's score is bumped.  A 
   // maximum of 20 services may be counted.  
   public void run () {	probe();	}

   BufferedReader getCommandResponse (String cmd) {
      br = null;
      try {
         is = Runtime.getRuntime().exec(cmd).getInputStream(); 
      } catch (Exception e) {
			Messages.checkservices_exception(scorer.out, cmd, e.toString());
         scorer.toLog("Command exec failed ("+cmd+"): "+e.toString(), 3);
      }
      if (is != null) br = new BufferedReader(new InputStreamReader(is));
      return br;
   }

   // This is where services are probed.  Runtime.getRuntime is used to 
   // execute the nmap command on an IP address belonging to a player.
   // The returned lines are parsed to find the port number and where the
   // port is open. For each service that matches a service query the player's
   // score is bumped by 1 point and this fact is logged.
   void probe () {
      is = null;
      br = null;
      clear = false;

      cmd ="nmap "+ipaddr;
      br = getCommandResponse(cmd);

      try {
         startSection(player);  //------------------ start section
         String str;
         for (int i=0 ; i < 20 ; i++) services[i] = false;
         while ((str = br.readLine()) != null) {
            for (int j=0 ; j < nservices ; j++) {
               StringTokenizer t = new StringTokenizer(str," ");
               String prt = null, state = null;
               if (t.hasMoreTokens()) prt = t.nextToken();
               if (t.hasMoreTokens()) state = t.nextToken();
               if (prt != null && prt.equals(srvports[j]) &&
                   state != null && state.equals("open") && !services[j]) {
                  services[j] = true;
                  player.bumpScore(1);
                  scorer.toLog("\""+player.getIdentity()+"\": service "+
                                entry[j]+" at "+srvports[j]+" is up", 3);
                  break;
               }
            }
         }
         br.close();
      } catch (Exception e) {
			Messages.probe_exception(scorer.out, cmd, e.toString());
         scorer.toLog("Command exec failed ("+cmd+"): "+e, 3);
      }

      try { if (is != null) is.close(); } catch (Exception e) {
			Messages.close_nmap_exception(scorer.out, e.toString());
         scorer.toLog("Unable to close IS due to nmap: "+e, 3);
      }
      
      is = null;
      br = null;
      cmd ="ping -c 1 -W 2 "+ipaddr;
      br = getCommandResponse(cmd);
      
      try { 
         String str;
         String result = "";
         while ((str = br.readLine()) != null) result += str;
         if (!result.contains("100% packet loss")) {
            player.bumpScore(1);
            scorer.toLog("\""+player.getIdentity()+"\": service ping is up", 3);
         }
         br.close();
      } catch (Exception e) { }
      
      try { if (is != null) is.close(); } catch (Exception e) {
			Messages.close_ping_exception(scorer.out, e.toString());
         scorer.toLog("Unable to close IS due to ping:"+e, 3);
      }
      
      (new HTTPQuery(player, "wordpress", 80, "wordpress", scorer)).run();

      endSection(player);   //---------------- end Section
      clear = true;
   }

   public void finalize () {
      clear = true;
   }
}
