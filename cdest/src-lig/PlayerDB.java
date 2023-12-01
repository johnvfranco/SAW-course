// PlayerDB.java                                       
import java.util.*;
import java.security.*;
import java.io.*;

class PlayerDB implements Serializable {
   public Scorer scorer;
   public int nplayers;
   public Player[] players;
   public CheckServices[] services;
   
   /* The database is just a hashtable keyed on the player's name */
   PlayerDB (Scorer m) {
      scorer = m;
      nplayers = 0;
      players = new Player[100];
      services = new CheckServices[100];      
      for (int i=0 ; i < 100 ; i++) {
			players[i] = null;
			services[i] = null;
      }
   }

   synchronized void setNPlayers () {
      int i=0;
      for ( ; i < 100 ; i++) if (players[i] == null) break;
      nplayers = i;
   }
   
   /* add a player to the database */
   synchronized void add(Player p) {
      players[nplayers] = p;
      services[nplayers] = new CheckServices(p, scorer);
      nplayers++;
   }

   /* Remove a player from the database - if duplicate names, 1st is deleted */
   synchronized boolean delete(String player) {
      for (int i=0 ; i < nplayers ; i++) {
			if (players[i].getIdentity().equals(player)) {
				if (i == nplayers-1) {
					nplayers--;
					players[nplayers] = null;
					services[nplayers] = null;
					return true;
				} else {
					players[i] = players[nplayers-1];
					services[i] = services[nplayers-1];
					nplayers--;
					players[nplayers] = null;
					services[nplayers] = null;
					return true;
				}
			}
      }
		Messages.no_player(scorer.out, player);
      scorer.toLog("Player ["+player+"] is not in the database", 2);
		return false;
   }

   /* lookup looks up the Player with name = identity.
      Returns that player, or null if no player is found */
   Player lookup(String player) {
      for (int i=0 ; i < nplayers ; i++) {
			if (players[i].getIdentity().equals(player)) return players[i];
      }
		Messages.no_player(scorer.out, player);
      scorer.toLog("Player ["+player+"] is not in the player database", 2);
      return null;
   }
}
