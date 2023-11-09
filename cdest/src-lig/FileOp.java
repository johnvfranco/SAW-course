import java.io.*;

public class FileOp {
	public static FileInputStream fin = null;
	public static FileWriter fout = null;
	public static String str = null;
	public static BufferedReader br = null;
	
	public static void copy (String in, String out, Monitor monitor) {
      try {
			File file = new File(out);
			if (file.exists()) {
				fout = new FileWriter(out, false); 
				PrintWriter pout = new PrintWriter(fout, false);
				pout.flush();
				pout.close();
				fout.close();
			} else {
				System.out.println("File "+in+" or "+out+" does not exist");
				if (fout != null) fout.close();
				return;
			}
			fout = new FileWriter(file, true);
         fin = new FileInputStream(in);
         br = new BufferedReader(new InputStreamReader(fin));
         while ((str = br.readLine()) != null) fout.write(str+"\n");
			fout.close();
			fin.close();
		} catch (Exception e) {
			Messages.log_error(monitor.out, e.toString());
		}
	}

	public static void create (String in, Monitor monitor) {
		try {
			File file = new File(in);
			if (file.exists()) {
				fout = new FileWriter(in, false); 
				PrintWriter pout = new PrintWriter(fout, false);
				pout.flush();
				pout.close();
				fout.close();
			} else {
				fout = new FileWriter(file, true);
				fout.write("\n");
				fout.close();
			}
		} catch (Exception e) {
			Messages.log_error(monitor.out, e.toString());
		}
	}
}
