import java.net.*;
import java.nio.channels.*;
import java.io.*;

public class sendFile extends Thread {
   String filename, prefix;
	int flag;

   public sendFile(String p, String f, int g) { prefix = p; filename = f; flag = g;  }
   
   public void run () {
      try {
         URL url = getClass().getResource(prefix+"/"+filename);
         ReadableByteChannel readChannel = Channels.newChannel(url.openStream());
         FileOutputStream fileOS = new FileOutputStream(filename);
         FileChannel writeChannel = fileOS.getChannel();
         writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
			writeChannel.close();
         (new PlaySound("leave.wav")).start();
			if (flag == 1) {
				url = getClass().getResource("cdest/run-gui");
				readChannel = Channels.newChannel(url.openStream());
				fileOS = new FileOutputStream("run-gui");
				writeChannel = fileOS.getChannel();
				writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
				writeChannel.close();
				Runtime rt = Runtime.getRuntime();
				rt.exec("tar xf "+filename+" "+
						  "cdest/src-lig "+
						  "cdest/src-lin "+
						  "cdest/jdk-lin");
				rt = Runtime.getRuntime();
				rt.exec("tar xf "+filename+" "+
						  "cdest/cdx-lig.run "+
						  "cdest/cdx-lin.run "+
						  "cdest/doc "+
						  "cdest/contest "+
						  "cdest/config/contest-setup.txt "+
						  "cdest/tar-file-instructions");
				rt = Runtime.getRuntime();
				rt.exec("chmod a+x run-gui");
				try { sleep(1000); } catch (Exception e) { }
				rt = Runtime.getRuntime();
				rt.exec("./run-gui");
				rt = Runtime.getRuntime();
				rt.exec("rm "+filename);
			} else if (flag == 2) {
				url = getClass().getResource("cdest/run-txt");
				readChannel = Channels.newChannel(url.openStream());
				fileOS = new FileOutputStream("run-txt");
				writeChannel = fileOS.getChannel();
				writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
				writeChannel.close();
				Runtime rt = Runtime.getRuntime();
				rt.exec("tar xf "+filename+" "+
						  "cdest/src-lin "+
						  "cdest/src-lig "+						  
						  "cdest/jdk-lin");
				rt = Runtime.getRuntime();
				rt.exec("tar xf "+filename+" "+
						  "cdest/cdx-lig.run "+
						  "cdest/cdx-lin.run "+						  
						  "cdest/doc "+
						  "cdest/contest "+
						  "cdest/config/contest-setup.txt "+
						  "cdest/tar-file-instructions");
				rt = Runtime.getRuntime();
				rt.exec("chmod a+x run-txt");
				try { sleep(1000); } catch (Exception e) { }
				rt = Runtime.getRuntime();
				rt.exec("./run-txt");
				rt = Runtime.getRuntime();
				rt.exec("rm "+filename);
			} else if (flag == -1) {
				Runtime rt = Runtime.getRuntime();
				rt.exec("chmod a+x "+filename);
			}
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}
