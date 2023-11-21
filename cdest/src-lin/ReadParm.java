import java.io.*;
import java.util.*;

public class ReadParm {
	static boolean parm_file_marker = true;
	
	static public String getSetup (String key, Scorer scorer) {
      String str, ret = "";
		FileInputStream fin = null;
      
      try {
         // Read the setup parameter
         fin = new FileInputStream("../config/Parameters.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(fin));
         while ((str = br.readLine()) != null) {
				if (!str.equals("")) {
					StringTokenizer t = new StringTokenizer(str," ");
					String token = t.nextToken();
					if (token.equals(key)) {
						while (t.hasMoreTokens()) {
							ret += t.nextToken() + " ";
						}
					}
				}
			}
         fin.close();
         
			return ret;
      } catch (Exception e) {
			if (parm_file_marker) {
				scorer.out.print("  Warning: Parameters.txt file cannot be found - "+
										"defaults are used:");
				scorer.out.print("    Player database state defaults to dynamic");
				scorer.out.print("    Logging option defaults to strict");
				scorer.out.print("    Recovery option defaults to recover saved database");
				scorer.out.print("    Scoreboard URL defaults to "+scorer.url);
				scorer.out.print("    Scoreboard file name defaults to "+scorer.name);
				scorer.out.print("    Scoreboard title defaults to "+scorer.ttl);
				scorer.out.print("    Scoreboard directory defaults to "+scorer.fileloc);
				scorer.out.print("    Name of log file defaults to "+scorer.logfile);
				scorer.out.print("    Contest start time is set to 0");
				scorer.out.print("    Contest end time is set to 0");
				parm_file_marker = false;
			}
		}
		return "";
	}
}
