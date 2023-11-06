// Player.java - so simple it is self explanatory hence no comments 
import java.net.*;
import java.util.*;
import java.math.*;
import java.security.*;
import java.io.Serializable;

public class Player implements Serializable {
   String identity, email;
   InetAddress ipaddress;
   int port;
   long score;
   Monitor monitor;
   
   Player (String who, byte[] addr, String em, Monitor m) {
      identity = who;
      monitor = m;
		email = em;
      try {
         ipaddress = InetAddress.getByAddress(addr);
      } catch (Exception e) {
			Messages.unknown_host(monitor.out);
      }
      score = 0;
   }

   Player (String who, byte[] iaddr, String em, Monitor m, int p, long s) {
      identity = who;
      monitor = m;
		email = em;
      score = s;
      port = p;
		try {
			ipaddress = InetAddress.getByAddress(iaddr);
		} catch (Exception e) {
			Messages.unknown_host(monitor.out);
		}
   }

   String getIdentity() {  return new String(identity);  }

   String getHostName() {
      return (ipaddress == null ?  null : new String(ipaddress.getHostName()));
   }

   InetAddress getInetAddress() {  return ipaddress;  }

   int getPort() { return port;  }

   void bumpScore (long bump) { score += bump; }

   long getScore () { return score; }

   void reset () { score = 0; }

	/**
   Player copyPlayer () {
      return new Player(identity, ipaddress, email, monitor, port, score);
   }
	**/

	String getEmail () { return email; }
}
