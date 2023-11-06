import java.io.*;
import java.util.*;

public class ReadParm {
	static boolean parm_file_marker = true;
	
	static public String getSetup (String key, Monitor monitor) {
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
				monitor.out.print("  Warning: Parameters.txt file cannot be found - "+
										"defaults are used:");
				monitor.out.print("    Player database state defaults to dynamic");
				monitor.out.print("    Logging option defaults to strict");
				monitor.out.print("    Recovery option defaults to recover saved database");
				monitor.out.print("    Scoreboard URL defaults to "+monitor.url);
				monitor.out.print("    Scoreboard file name defaults to "+monitor.name);
				monitor.out.print("    Scoreboard title defaults to "+monitor.ttl);
				monitor.out.print("    Scoreboard directory defaults to "+monitor.fileloc);
				monitor.out.print("    Name of log file defaults to "+monitor.logfile);
				monitor.out.print("    Contest start time is set to 0");
				monitor.out.print("    Contest end time is set to 0");
				parm_file_marker = false;
			}
		}
		return "";
	}
}
