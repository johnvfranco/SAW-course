/***
    vpnFrame.java - make and distribute keys for VPN
    Copyright (C) 2020  John Franco

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
***/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

class DistributeKeys extends Thread {
   vpnFrame vf = null;
   FileInputStream fis = null;
   BufferedReader br = null;
   String str = null;
   boolean running = true;
   String command = null;
   int nkeys;

   public DistributeKeys (vpnFrame v) {
      vf = v;
      try {
         nkeys = Integer.parseInt((String)vf.no_keys.getSelectedItem());
      } catch (Exception e) { nkeys = 50; }
   }

   void appendToClientConfigFile (String ParmsLocation) {
      int keyNo = getVPNKeyNoFromIPAddress(ParmsLocation);
      String str = "";
      String tmp = null;
      try {
         FileInputStream fis = new FileInputStream("../contest/client/client.conf");
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         while ((tmp = br.readLine()) != null) str += tmp + "\n";

         str += "cert keys/client"+(keyNo-100)+".crt\n";
         str += "key keys/client"+(keyNo-100)+".key\n";
         str += "\n#\n# The hostname/IP and port of the vpn server\n";
         String svrloc = vf.cf.scorer.svrlocation.getText();
         if (svrloc == null || svrloc.equals("")) {
            vf.cf.msgs.setText("client.conf not created - OpenVPN server "+
                                       "location field must have an ip address");
            if (fis != null) fis.close();
            try { Thread.sleep(1000); } catch (Exception e) { }
            return;
         }
         str += "remote "+svrloc+" 1194\n";
         
         FileOutputStream fos = new FileOutputStream("../contest/client.conf");
         PrintWriter pw = new PrintWriter(fos, true);
         StringTokenizer t = new StringTokenizer(str, "\n");
         while (t.hasMoreTokens()) pw.println(t.nextToken());
         if (fis != null) fis.close();
         if (fos != null) fos.close();
      } catch (Exception e) {
         vf.cf.msgs.setText("Cannot locate client.conf template");
      }
   }

   void appendToServerConfigFile () {
      try {
         FileInputStream fis = new FileInputStream("../contest/server/server.conf.tmpl");
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         str = "";
         String tmp = null;
         while ((tmp = br.readLine()) != null) str += tmp + "\n";
         String svrloc = vf.cf.scorer.svrlocation.getText();
         if (svrloc == null || svrloc.equals("")) {
            vf.cf.msgs.setText("server.conf not created - OpenVPN server "+
                               "location field must have an ip address");
            if (fis != null) fis.close();
            try { Thread.sleep(1000); } catch (Exception e) { }
            return;
         }
         str += "local " + svrloc + "\n"; 
         
         FileOutputStream fos = new FileOutputStream("../contest/server/server.conf");
         PrintWriter pw = new PrintWriter(fos, true);
         StringTokenizer t = new StringTokenizer(str, "\n");
         while (t.hasMoreTokens()) pw.println(t.nextToken());
         if (fis != null) fis.close();
         if (fos != null) fos.close();
      } catch (Exception e) {
         vf.cf.msgs.setText("Cannot locate server.conf template");
      }
   }

   int getVPNKeyNoFromIPAddress(String ParmsLocation) {
      FileInputStream fin = null;
      String ret = null;
      try {
         fin = new FileInputStream(ParmsLocation);
         BufferedReader br = new BufferedReader(new InputStreamReader(fin));
         while ((str = br.readLine()) != null) {
            if ((str.equals("# Client Location")) ||
                (str.equals("# Client Location editable"))) ret = br.readLine();
         }
         fin.close();
         if (ret == null) return -1;
         StringTokenizer t = new StringTokenizer(ret,".");
         if (t.hasMoreTokens()) {
            t.nextToken();
            if (t.hasMoreTokens()) {
               t.nextToken();
               if (t.hasMoreTokens()) {
                  t.nextToken();
                  if (t.hasMoreTokens()) {
                     return Integer.parseInt(t.nextToken());
                  } else {
                     return -1;
                  }
               } else {
                  return -1;
               }
            } else {
               return -1;
            }
         }
      } catch (Exception e) {
         vf.cf.msgs.setText("Could not determine key number");
      }
      return -1;
   }

   public void run () {
      //yield();
      boolean failed = false;
      running = true;

      vf.start.setEnabled(false);
		vf.cf.tf.keys.setEnabled(false);
      vf.save.setEnabled(false);
      vf.cf.cancel.setEnabled(false);
      vf.cf.done.setEnabled(false);
      
      vf.cf.msgs.setText("Checking conditions for distribution");

      try {
         File fs = new File("../contest/keys/dh2048.pem");
         if (fs == null || !fs.exists() || fs.length() == 0) {
            vf.cf.msgs.setText("Keys appear not to be made - abort");
            running = false;
            vf.start.setEnabled(true);
				vf.cf.tf.keys.setEnabled(true);
            vf.save.setEnabled(true);
            vf.cf.cancel.setEnabled(true);
            vf.cf.done.setEnabled(true);
            return;
         }
      } catch (Exception e) {
         vf.cf.msgs.setText("Abort: keys or key directory do not exist");
         running = false;
         vf.start.setEnabled(true);
			vf.cf.tf.keys.setEnabled(true);
         vf.save.setEnabled(true);
         vf.cf.cancel.setEnabled(true);
         vf.cf.done.setEnabled(true);
         return;
      }
      
      try { sleep(500); } catch (Exception e) { }

      File fs = new File("../Contestants");
      if (fs == null || !fs.exists()) (new File("../Contestants")).mkdir();
      
      try {
         fis = new FileInputStream("../contest/vpnKeyIds.txt");
         br = new BufferedReader(new InputStreamReader(fis));
         while ((str = br.readLine()) != null) {
            String user = str;
            int keyNo = -1;
            command = null;
            vf.cf.msgs.setText("Distributing keys and certs to "+user);
            try { sleep(200); } catch (Exception e) { }

            String svrAddress = "../Contestants/"+str;
            fs = new File(svrAddress);
            if (fs.exists()) {
               fs = new File(svrAddress+"/keys");
               if (!fs.exists()) new File(svrAddress+"/keys").mkdir();
               fs = new File(svrAddress+"/keys");
               if (fs.exists()) {
                  keyNo = getVPNKeyNoFromIPAddress(svrAddress+"/Parms");
                  if (keyNo < 100 || keyNo > 249) {
                     vf.cf.msgs.setText("Cannot get key number from "+user+" Parms file");
                     try { Thread.sleep(500); } catch (Exception e) { }
                     continue;
                  }
                  File tst = new File("../contest/keys/issued/client"+(keyNo-100)+".crt");
                  if (!tst.exists()) {
                     vf.cf.msgs.setText("Keys do not exist for "+user);
                     failed = true;
                     try { Thread.sleep(1000); } catch (Exception e) { }
                     continue;
                  }
                  
                  command = "cp ../contest/keys/private/client"+(keyNo-100)+".key "+
                     svrAddress+"/keys";
                  Runtime.getRuntime().exec(command);
                  command = "cp ../contest/keys/issued/client"+(keyNo-100)+".crt "+
                     svrAddress+"/keys";
                  Runtime.getRuntime().exec(command);
                  command = "cp ../contest/keys/ca.crt "+svrAddress+"/keys";
                  Runtime.getRuntime().exec(command);
                  command = "cp -fr ../contest/sbin "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
						command = "cp ../contest/client/run.player "+svrAddress+"/";
						Runtime.getRuntime().exec(command);
						command = "cp ../contest/client/stop.player "+svrAddress+"/";
						Runtime.getRuntime().exec(command);
                  command = "cp ../contest/client/instructions.pdf "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
                  command = "cp ../contest/client/run.client "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
                  command = "cp ../contest/client/run.vpn "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
                  command = "cp ../contest/client/stop.client "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
                  appendToClientConfigFile(svrAddress+"/Parms");
                  command = "mv ../contest/client.conf "+svrAddress+"/";
                  Runtime.getRuntime().exec(command);
                  command = "chmod -R go-w "+svrAddress;
                  Runtime.getRuntime().exec(command);
                  command = "chmod -R a+r "+svrAddress;
                  Runtime.getRuntime().exec(command);
               } else {
                  vf.cf.msgs.setText("Not creating keys for "+user+": no directory");
                  try { sleep(2000); } catch (Exception e) { }
               }
            } else {
               vf.cf.msgs.setText("Not creating keys for "+user+": no directory");
               try { sleep(2000); } catch (Exception e) { }
            }
         }
         if (fis != null) fis.close();
         try { sleep(1000); } catch (Exception e) { }
         vf.cf.msgs.setText("Making server directory");
         appendToServerConfigFile();
         fs = new File("../contest/server/keys");
         if (!fs.exists()) new File("../contest/server/keys").mkdir();
         command = "cp -fr ../contest/sbin ../contest/server/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/issued/server.crt ../contest/server/keys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/private/server.key ../contest/server/keys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/ca.crt ../contest/server/keys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/dh2048.pem ../contest/server/keys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -fr ../contest/keys/issued ../contest/server/allkeys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -fr ../contest/keys/private ../contest/server/allkeys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/ca.crt ../contest/server/allkeys/";
         Runtime.getRuntime().exec(command);         
         command = "cp -f ../contest/keys/dh2048.pem ../contest/server/allkeys/";
         Runtime.getRuntime().exec(command);
         command = "chmod -R a+r ../contest/server";
         Runtime.getRuntime().exec(command);
         command = "chmod a+x ../contest/server/allkeys/";
         Runtime.getRuntime().exec(command);
         command = "chmod -R go-w ../contest/server";
         Runtime.getRuntime().exec(command);
         try { sleep(1000); } catch (Exception e) { }
         vf.cf.msgs.setText("Keys have been distributed");
         try { Thread.sleep(1000); } catch (Exception e) { }
      } catch (Exception e) {
         vf.cf.msgs.setText("vpnKeyIds.txt file not present or corrupted");
      }
      if (failed) vf.cf.msgs.setText("Failed to create all keys and certificates");
      
      vf.start.setEnabled(true);
		vf.cf.tf.keys.setEnabled(true);
      vf.save.setEnabled(true);
      vf.cf.cancel.setEnabled(true);
      vf.cf.done.setEnabled(true);

      running = false;      
   }
}

class MakeKeys extends Thread {
   vpnFrame vf = null;
   int cnt = 0;
   String command = null;
   FileInputStream fis = null;
   FileOutputStream fos = null;
   BufferedReader br = null;
   PrintWriter pw = null;
   String str = null;
   String tmp = null;
   boolean running = true;
   
   public MakeKeys (int c, vpnFrame v) { cnt = c; vf = v; }
   
   public void run () {
      str = "";
      try {
         fis = new FileInputStream("../contest/vars1");
         br = new BufferedReader(new InputStreamReader(fis));
         while ((tmp = br.readLine()) != null) str += tmp+"\n";
         fis.close();
      } catch (Exception e) {
         vf.cf.msgs.setText("Could not open or read from vars1");
         running = false;
         return;
      }
      str += "set_var EASY_REQ_COUNTRY \""+vf.country.getText()+"\"\n";
      str += "set_var EASY_REQ_PROVINCE \""+vf.state.getText()+"\"\n";
      str += "set_var EASY_REQ_CITY \""+vf.city.getText()+"\"\n";
      str += "set_var EASY_REQ_ORG \""+vf.org.getText()+"\"\n";
      str += "set_var EASY_REQ_EMAIL \""+vf.mail.getText()+"\"\n";
      str += "set_var EASY_REQ_OU \""+vf.unit.getText()+"\"\n";
      try {
         fis = new FileInputStream("../contest/vars2");
         br = new BufferedReader(new InputStreamReader(fis));
         while ((tmp = br.readLine()) != null) str += tmp+"\n";
         fis.close();
      } catch (Exception e) {
         vf.cf.msgs.setText("Could not open or read from vars2");
         running = false;
         return;
      }
      try {
         fos = new FileOutputStream("../contest/vars");
         pw = new PrintWriter(fos,true);
         StringTokenizer st = new StringTokenizer(str,"\n");
         while (st.hasMoreTokens()) pw.println(st.nextToken());
         fos.close();
      } catch (Exception e) {
         vf.cf.msgs.setText("Could not open or write to vars");
         running = false;
         return;
      }
      
      vf.cf.msgs.setText("Wrote vars file - creating keys and certs ...");
      try { Thread.sleep(1000); } catch (Exception e) { }

      try {
         vf.cf.msgs.setText("Making keys and certificates - might want to take a tea or "+
									 "coffee break");
			// easyrsa init-pki
			Runtime.getRuntime().exec("./cmd1", null, new File("../contest"));
			try { Thread.sleep(1000); } catch (Exception e) { }
			if (!running) {
				Runtime.getRuntime().exec("killall cmd1");
				return;
			}
			command = "echo making CA key and certificate >> keys.log";
			Runtime.getRuntime().exec(command, null, new File("../contest"));
			Runtime.getRuntime().exec("./cmd2", null, new File("../contest"));
			try { Thread.sleep(1000); } catch (Exception e) { }
			if (!running) {
				Runtime.getRuntime().exec("killall cmd2");
				return;
			}
			command = "echo making server key and certificate >> keys.log";
			Runtime.getRuntime().exec(command, null, new File("../contest"));
			Runtime.getRuntime().exec("./cmd3", null, new File("../contest"));
			try { Thread.sleep(1000); } catch (Exception e) { }			
			if (!running) {
				Runtime.getRuntime().exec("killall cmd3");
				return;
			}
			Runtime.getRuntime().exec("./cmd4", null, new File("../contest"));
			try { Thread.sleep(1000); } catch (Exception e) { }
			if (!running) {
				Runtime.getRuntime().exec("killall cmd4");
				return;
			}
			Runtime.getRuntime().exec("rm -rf ../contest/server/ccd");
			try { Thread.sleep(1000); } catch (Exception e) { }			
			if (!running) return;
			Runtime.getRuntime().exec("mkdir ../contest/server/ccd");
			try { Thread.sleep(1000); } catch (Exception e) { }			
			if (!running) return;
			Runtime.getRuntime().exec("rm -f ../contest/server/ipp.txt");
			for (int i=0 ; i <= cnt && running ; i++) {
				Runtime.getRuntime().exec("./cmd5 "+i, null, new File("../contest"));
				try { Thread.sleep(2000); } catch (Exception e) { }
				if (!running) {
					Runtime.getRuntime().exec("killall cmd5");
					return;
				}
				Runtime.getRuntime().exec("./cmd6 "+i, null, new File("../contest"));
				try { Thread.sleep(1000); } catch (Exception e) { }				
				if (!running) {
					Runtime.getRuntime().exec("killall cmd6");
					return;
				}
				vf.cf.msgs.setText("Making OpenVPN keys and certificate for client "+i);
				Runtime.getRuntime().exec("./cmd7 "+i, null, new File("../contest"));
				if (!running) {
					Runtime.getRuntime().exec("killall cmd7");
					return;
				}
				Runtime.getRuntime().exec("./cmd8 "+i, null, new File("../contest"));
				try { Thread.sleep(1000); } catch (Exception e) { }
				if (!running) {
					Runtime.getRuntime().exec("killall cmd8");
					return;
				}
			}
			if (!running) return;
         command = "chmod -R go-w ../contest/server/ccd";
			Runtime.getRuntime().exec(command);
			if (!running) return;			
			command = "chmod -R go-w ../contest/server/keys";
			Runtime.getRuntime().exec(command);
			if (!running) return;			
			
			try { Thread.sleep(1000); } catch (Exception e) { }
			if (!running) return;			
			vf.cf.msgs.setText("Server, CA, diffie-hellman OpenVPN keys and certificates being created");
         Runtime.getRuntime().exec("./cmd9", null, new File("../contest"));
      } catch (Exception e) {
         vf.cf.msgs.setText("make-keys failed: "+command);
         running = false;
         return;
      }

      boolean over = false;
      try { sleep(1000); } catch (Exception e) { }
      while (!over) {
         try {
            File fs = new File("../contest/keys/dh2048.pem");
            if (fs.exists() && fs.length() != 0) over = true; else over = false;
            fs = null;
         } catch (Exception e) {
            over = false;
         }
         try { sleep(1000); } catch (Exception e) { }
      }
      vf.cf.msgs.setText("all done - see contest/keys.log for details");
      running = false;
    }
}

class KeySender extends Thread {
   vpnFrame vf = null;

   public KeySender(vpnFrame v) { vf = v; }

   public void run () {
      vf.cf.msgs.setText("Beginning client key, certificate, and scripts distribution");

      try { sleep(500); } catch (Exception e) { }

      vf.start.setEnabled(false);
		vf.cf.tf.keys.setEnabled(false);
      vf.no_keys.setEnabled(false);
      vf.cancel.setEnabled(false);

      DistributeKeys dk = null;
      (dk = new DistributeKeys(vf)).start();

      try { sleep(1000); } catch (Exception e) { }

      if (dk != null) {
         while (dk.running) {
            try { sleep(1000); } catch (Exception e) { }
         }
      }

      vf.start.setEnabled(true);
      vf.cf.tf.keys.setEnabled(true);		
      vf.no_keys.setEnabled(true);
      vf.cancel.setEnabled(true);
   }
}

class KeyMaker extends Thread {
   vpnFrame vf = null;
   MakeKeys mk = null;
   int nkeys;

   public KeyMaker(vpnFrame v) {
      vf = v;
      nkeys = Integer.parseInt((String)vf.no_keys.getSelectedItem());
   }

   public void run () {
      if (vf.country.getText().equals("") || vf.state.getText().equals("") ||
          vf.city.getText().equals("") || vf.org.getText().equals("") ||
          vf.unit.getText().equals("") || vf.mail.getText().equals("")) {
         vf.cf.msgs.setText("Some fields above are empty but all must be filled in");
         return;
      } else {
         vf.cf.msgs.setText("Making server, CA, client, and diffie-hellman keys");
         vf.start.setEnabled(false);
			vf.cf.tf.keys.setEnabled(false);
         vf.no_keys.setEnabled(false);

         try { sleep(1000); } catch (Exception e) { }            
         (mk = new MakeKeys(nkeys, vf)).start();

         if (mk != null) {
            while (mk.running) {
               try { sleep(1000); } catch (Exception e) { }
            }
         }
         
         vf.start.setEnabled(true);
			vf.cf.tf.keys.setEnabled(true);
         vf.no_keys.setEnabled(true);
      }
   }
}

public class vpnFrame extends JPanel implements ActionListener, MouseListener {
   JTextField country, state, city, org, unit, server, mail;
   Color bkgcolor = new Color(255,255,223);
   Font font = new Font("Helvetica", Font.PLAIN, 16);
   Font med = new Font("Helvetica", Font.PLAIN, 16);
   JButton start, cancel, save;
   JLabel label;
   configFrame cf;
   JComboBox <String> no_keys;
	KeyMaker km = null;

   public vpnFrame (configFrame c) {
      cf = c;

      setLayout(new BorderLayout());
      setBackground(bkgcolor);

      add("North", label = new JLabel("OpenVPN Key & Certificate Maker"));
      label.setFont(new Font("Helvetica", Font.BOLD, 16));
      label.setForeground(new Color(0,50,150));

      JPanel nq = new JPanel(new BorderLayout());
      nq.setBackground(bkgcolor);
      
      JPanel np = new JPanel(new GridLayout(5,1));
      np.setBackground(bkgcolor);

      JPanel p = new JPanel(new FlowLayout());
      p.setBackground(bkgcolor);
      
      JPanel q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      
      q.add("North", country = new JTextField(4));
      q.add("Center", new JLabel("country", JLabel.CENTER));
      country.setHorizontalAlignment(JTextField.CENTER);
      country.setFont(font);
      p.add(q);

      p.add(new JLabel(""));

      q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      q.add("North", state = new JTextField(4));
      q.add("Center", new JLabel("state", JLabel.CENTER));
      state.setHorizontalAlignment(JTextField.CENTER);
      state.setFont(font);
      p.add(q);

      p.add(new JLabel(""));
 
      q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      q.add("North", city = new JTextField(20));
      q.add("Center", new JLabel("city (ex: Los Angeles)", JLabel.CENTER));
      city.setHorizontalAlignment(JTextField.CENTER);
      city.setFont(font);
      p.add(q);

      np.add(p);

      p = new JPanel(new FlowLayout());
      p.setBackground(bkgcolor);
      
      q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      
      q.add("North", org = new JTextField(30));
      q.add("Center", new JLabel("organization (ex: UCLA)", JLabel.CENTER));
      org.setHorizontalAlignment(JTextField.CENTER);
      org.setFont(font);
      p.add(q);

      np.add(p); 
      
      p = new JPanel(new FlowLayout());
      p.setBackground(bkgcolor);
      
      q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      
      q.add("North", unit = new JTextField(30));
      q.add("Center", new JLabel("organizational unit (ex: Emergency Room)", JLabel.CENTER));
      unit.setHorizontalAlignment(JTextField.CENTER);
      unit.setFont(font);
      p.add(q);

      np.add(p); 

      p = new JPanel(new FlowLayout());
      p.setBackground(bkgcolor);
      q = new JPanel(new BorderLayout());
      q.setBackground(bkgcolor);
      q.add("North", mail = new JTextField(30));
      q.add("Center", new JLabel("email", JLabel.CENTER));
      mail.setHorizontalAlignment(JTextField.CENTER);
      mail.setFont(font);
      p.add(q);

      np.add(p);

      p = new JPanel(new FlowLayout());
      p.setBackground(bkgcolor);
      
      p.add(new JLabel("   #keys:"));
      p.add(no_keys = new JComboBox <String> ());
      no_keys.addItem("10");		
      no_keys.addItem("50");
      no_keys.addItem("100");
      no_keys.addItem("150");
      no_keys.setSelectedIndex(3);
      p.add(new JLabel("  "));      
      p.add(start = new JButton("Make Keys"));
      p.add(new JLabel("  "));
      p.add(cancel = new JButton("Stop Making Keys"));
      p.add(new JLabel("  "));
      p.add(save = new JButton("Save"));

      np.add(p);

      nq.add("Center", np);
      nq.add("East", new JLabel("     "));
      nq.add("West", new JLabel("     "));      

      add("Center", nq);
      nq.setBorder(BorderFactory.createLineBorder(Color.black));      

      start.addActionListener(this);
      start.addMouseListener(this);
      cancel.addActionListener(this);
      cancel.addMouseListener(this);

      FileInputStream fis;
      
      try {
         String str = "";
         String tmp = null;
         fis = new FileInputStream("../contest/game-id.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         while ((tmp = br.readLine()) != null) str += tmp + "\n";
         StringTokenizer t = new StringTokenizer(str,"\n");
         if (t.hasMoreTokens()) country.setText(t.nextToken().replaceAll("[!$&\"`]","."));
         if (t.hasMoreTokens()) state.setText(t.nextToken().replaceAll("[!$&\"`]","."));
         if (t.hasMoreTokens()) city.setText(t.nextToken().replaceAll("[!$&\"`]","."));
         if (t.hasMoreTokens()) org.setText(t.nextToken().replaceAll("[!$&\"`]","."));
         if (t.hasMoreTokens()) unit.setText(t.nextToken().replaceAll("[!$&\"`]","."));
         if (t.hasMoreTokens()) mail.setText(t.nextToken().replaceAll("[!$&\"`]","."));
      } catch (Exception e) {
         cf.scorer.text.append("Problem reading game id info from file "+
                                "game-id.txt\n");
      }

      country.addActionListener(this);
      state.addActionListener(this);
      city.addActionListener(this);
      org.addActionListener(this);
      unit.addActionListener(this);
      mail.addActionListener(this);
      save.addActionListener(this);
      save.addMouseListener(this);
      no_keys.addMouseListener(this);
      
      setSize(800,440);
      setVisible(true);
   }

   public void mouseEntered (MouseEvent evt) {
      if (!cf.helpstate) return;
      if (evt.getSource() == save) cf.mousehelp.xlate(8);
      if (evt.getSource() == start) cf.mousehelp.xlate(9);
      if (evt.getSource() == cancel) cf.mousehelp.xlate(10);
      if (evt.getSource() == no_keys) cf.mousehelp.xlate(11);
   }
   
   public void mouseExited (MouseEvent evt) { }
   public void mouseClicked (MouseEvent evt) { cf.mousehelp.exit(); }
   public void mousePressed (MouseEvent evt) { }
   public void mouseReleased (MouseEvent evt) { }

   public void saveGameId () {
      try {
         FileOutputStream fos = new FileOutputStream("../contest/game-id.txt");
         PrintWriter pw = new PrintWriter(fos, true);
         pw.println((country.getText()).replaceAll("[!$&\"`]","."));
         pw.println((state.getText()).replaceAll("[!$&\"`]","."));
         pw.println((city.getText()).replaceAll("[!$&\"`]","."));
         pw.println((org.getText()).replaceAll("[!$&\"`]","."));
         pw.println((unit.getText()).replaceAll("[!$&\"`]","."));
         pw.println((mail.getText()).replaceAll("[!$&\"`]","."));
         fos.close();
      } catch (Exception e) {
         cf.scorer.text.append("Problem writing game identification info "+
                                "to file game-id.txt");
      }
   }

   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == country || evt.getSource() == state ||
          evt.getSource() == city || evt.getSource() == org ||
          evt.getSource() == unit || evt.getSource() == mail ||
          evt.getSource() == save) {
         saveGameId();
         return;
      }
      start.setEnabled(false);
		cf.tf.keys.setEnabled(false);		
      no_keys.setEnabled(false);
      cf.msgs.setText(" ");
      if (evt.getSource() == cancel) {
			if (km != null) km.mk.running = false;
         start.setEnabled(true);
			cf.tf.keys.setEnabled(true);
         no_keys.setEnabled(true);
         cancel.setEnabled(true);
         cf.msgs.setText("");
      } else if (evt.getSource() == start) {  
         (km = new KeyMaker(this)).start();
      }
   }
}
