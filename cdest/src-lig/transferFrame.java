/***
    transferFrame.java - set contest parameters
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
import java.util.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.security.*;
import java.lang.*;
import java.math.*;

public class transferFrame extends JPanel implements ActionListener, MouseListener {
   configFrame cnt;
   JButton keys, nokeys;
   JLabel lbl;
   mouse mousehelp = null;
   Color bkgcolor = new Color(255,255,223);
   boolean willSend = true;

   public transferFrame (configFrame cf) {
      cnt = cf;
      mousehelp = new mouse();

      setLayout(new BorderLayout());
      setBackground(bkgcolor);

      JPanel np = new JPanel(new BorderLayout());
      np.setBackground(bkgcolor);

      JPanel p = new JPanel(new GridLayout(2,1,10,10));
      p.setBackground(new Color(255,255,223));

      JPanel q = new JPanel(new FlowLayout());
      q.setBackground(new Color(255,255,223));      
      q.add(keys = new JButton("Prepare with OpenVPN keys"));
      keys.setPreferredSize(new Dimension(240,23));      
      p.add(q);
      
      q = new JPanel(new FlowLayout());
      q.setBackground(new Color(255,255,223));
      q.add(nokeys = new JButton("Prepare without OpenVPN keys"));
      nokeys.setPreferredSize(new Dimension(240,23));
      p.add(q);

      np.add("Center", p);
      np.setBorder(BorderFactory.createLineBorder(Color.black));

      np.add("South", lbl = new JLabel("  "));
      lbl.setFont(new Font("Helvetica", Font.PLAIN, 8));

      np.add("West", new JLabel("     "));
      np.add("East", new JLabel("     "));
      np.add("North", lbl = new JLabel("  "));
      lbl.setFont(new Font("Helvetica", Font.PLAIN, 8));
      add("Center", np);

      add("North", lbl = new JLabel("Prepare Files", JLabel.LEFT));
      lbl.setFont(new Font("Helvetica", Font.BOLD, 16));
      lbl.setForeground(new Color(0,0,190));

      add("South", lbl = new JLabel(" "));

      keys.addActionListener(this);
      keys.addMouseListener(this);
      nokeys.addActionListener(this);
      nokeys.addMouseListener(this);

      setSize(450,230);
      setVisible(true);
   }

   public void mouseEntered (MouseEvent evt) {
      if (!cnt.helpstate) return;
      if (evt.getSource() == keys) cnt.mousehelp.xlate(14);
      if (evt.getSource() == nokeys) cnt.mousehelp.xlate(15);
   }
   
   public void mouseExited (MouseEvent evt) { }
   public void mouseClicked (MouseEvent evt) { cnt.mousehelp.exit(); }
   public void mousePressed (MouseEvent evt) { }
   public void mouseReleased (MouseEvent evt) { }

   boolean tarAndSendFiles (boolean usingOpenVPN) {
      FileInputStream fis;
      FileInputStream mail;
      String address, command;
      try {
         File fs = new File("../Contestants/");
         if (!fs.exists()) {
            cnt.msgs.setText("Abort sending - 'Contestants' doesn't exist "+
                                    "- use a prepare button");
            return false;
         }
			command = "chmod go-w ../Contestants";
			Runtime.getRuntime().exec(command);
         fs = new File("../contest/vpnKeyIds.txt");
         if (!fs.exists()) {
            cnt.msgs.setText("Abort sending - 'vpnKeyIds' does not exist "+
                                    "- use a prepare button\n");
            return false;
         }
         fis = new FileInputStream("../contest/vpnKeyIds.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         String player;
         while ((player = br.readLine()) != null) {
            if (usingOpenVPN) 
					command = "tar cf "+player+".tar "+player+"/keys "+player+"/sbin "+player+"/Parms "+player+"/run.client "+player+"/run.vpn "+player+"/stop.client "+player+"/client.conf "+player+"/JAVA.txt "+player+"/README.txt";
            else
					command = "tar cf "+player+".tar "+player;
				try {
					Runtime.getRuntime().exec(command, null, new File("../Contestants"));
				} catch (Exception e) {
					cnt.scorer.text.append("  Unable to tar files for "+player+"\n");
					continue;
				}

				try {
               mail = new FileInputStream("../Contestants/"+player+"/email.txt");
               BufferedReader em = new BufferedReader(new InputStreamReader(mail));
               address = em.readLine();
					command = "../contest/bin/mutt -s \"Contest Credentials\" "+address+" -a "+player+".tar < ../contest/contest-setup.txt";
               try {
                  Runtime.getRuntime().exec(command, null, new File("../Contestants"));
                  cnt.scorer.text.append("Sent "+player+"'s files to "+address+"\n");
               } catch (Exception g) {
                  cnt.scorer.text.append("  Unable to send files to "+address+"\n");
                  mail.close();
                  continue;
               }
               mail.close();
            } catch (Exception f) {
               cnt.scorer.text.append("  Unable to find email.txt for "+player+"\n");
               continue;
            }
         }
         
         fis.close();
      } catch (FileNotFoundException e) {
         cnt.msgs.setText("Click 'Prepare with...' and try again");
         return false;
      } catch (IOException h) {
         cnt.msgs.setText("Unable to read player IDs - click a prepare button");
         return false;
      }
      try {	   
			command = "chmod go-w ../Contestants";
			Runtime.getRuntime().exec(command);
      }  catch (Exception e) { e.printStackTrace(); }
	   
      boolean done = false;
      if (usingOpenVPN) {
         try {
            File file = new File("../contest/server");
            if (file == null || !file.exists()) {
               cnt.scorer.text.append("  Directory 'server' appears not to exist - run 'Make Keys' in Configurator\n");
               cnt.scorer.text.append("------------------------------\n");               
               done = true;
            } 
	    
            file = new File("../contest/server/ccd");
            if (file == null || !file.exists()) {
               cnt.scorer.text.append("  Directory 'server/ccd' appears not to exist - run 'Make Keys' in Configurator\n");
               cnt.scorer.text.append("------------------------------\n");
               done = true;
            }
            file = new File("../contest/server/keys");
            if (file == null || !file.exists()) {
               cnt.scorer.text.append("  Directory 'server/keys' appears not to exist - run 'Make Keys' in Configurator\n");
               cnt.scorer.text.append("------------------------------\n");
               done = true;
            }

            if (!done) {
               command = "tar cf server.tar server/server.conf server/run.server server/stop.server server/ipp.txt server/keys server/ccd server/allkeys server/run.vpn server/sbin server/run.proxy server/stop.proxy server/README.txt";
               try {
                  Runtime.getRuntime().exec(command, null, new File("../contest"));
               } catch (Exception e) {
                  cnt.msgs.setText("Can't tar server files: "+e.toString());
                  return false;
               }
               (new File("../VPNServer")).mkdir();
					try { Thread.sleep(1000); } catch (Exception e) { }
               command = "mv ../contest/server.tar ../VPNServer";
               try {
                  Runtime.getRuntime().exec(command);
               } catch (Exception e) {
                  cnt.msgs.setText("Can't move server.tar to VPNServer");
                  return false;
               }
               command = "chmod -R go-w ../VPNServer";	       
               try {
                  Runtime.getRuntime().exec(command);
               } catch (Exception e) {
                  cnt.msgs.setText("Unable to create VPNServer directory");
						e.printStackTrace();
               }
            }
         } catch (Exception x) {
            cnt.scorer.text.append("  Problem: "+x.toString()+"\n");
            cnt.scorer.text.append("-------------------------------\n");
         }
      }
      cnt.scorer.text.append("  Finished tar and send files to players\n");
      cnt.scorer.text.append("-------------------------------\n");
      
      return true;
   }
   
   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == keys) {
         try {
            File fs = new File("../Contestants");
            if (fs == null || !fs.exists() || fs.length() == 0) fs.mkdir();
         } catch (Exception e) {
            cnt.msgs.setText("Can't create Contestants directory");
            return;
         }
         try {
            File fs = new File("../config/"+GameParameters.PLAYER_DB_FILE);
            if (fs == null || !fs.exists() || fs.length() == 0) {
               cnt.msgs.setText("File "+GameParameters.PLAYER_DB_FILE+" appears not "+
                                "to exist");
               return;
            }
         } catch (Exception e) {
            cnt.msgs.setText("File "+GameParameters.PLAYER_DB_FILE+" appears not to exist");
            return;
         }
         try {
            File fs = new File("../contest/keys/dh2048.pem");
            if (fs == null || !fs.exists() || fs.length() == 0) {
               cnt.msgs.setText("File keys/dh2048.pem appears not to exist");
               return;
            }
         } catch (Exception e) {
            cnt.msgs.setText("File keys/dh2048.pem appears not to exist");
            return;
         }
         String svrloc = cnt.scorer.svrlocation.getText();
         if (svrloc == null || svrloc.equals("")) {
            cnt.msgs.setText("OpenVPN server location field needs an ip address");
            return;
         }
         
         int i=0;
         while (cnt.vf.start.isEnabled() == false) {
            try { Thread.sleep(1000); } catch (Exception e) { }
            cnt.msgs.setText("Waiting to distribute keys while they are being made - "+i++);
         }
         (new KeySender(cnt.vf)).start();
         cnt.runIt();
         willSend = true;
      } else if (evt.getSource() == nokeys) {
         cnt.runIt();
         willSend = false;
      }
   }
}

