// GameParameters.java  - 
//   All the constants for Game Parameters

class GameParameters {
   public static String GAME_DIRECTORY = "./";
   
   // PLAYER_DB_FILE
   //   File where statistics are stored
   static final String PLAYER_DB_FILE = "players.db";

   // In class Contest probes to IP addresses are made in round robin
   // fashion.  To spread out traffic the below, in milliseconds, is the 
   // time to wait until the next probe sequence is executed
   public static int TIME_BETWEEN_PLAYER_PROBES = 10000;
}
