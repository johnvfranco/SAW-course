import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.nio.file.*;
import java.lang.*;

class lab6C extends JFrame implements ActionListener {
   JButton b[] = new JButton[24];
   JButton c[] = new JButton[24];
   JButton x[] = new JButton[24];
   JButton y[] = new JButton[24];
   JButton z[] = new JButton[24];   
   JButton exit = null;
   JButton term = null;
   JButton manual = null;
   JButton tutorial = null;
   Color bkgd = new Color(255,255,255);
   JLabel lbl, title;
   Font fnt = new Font("Helvetica", Font.PLAIN, 18);
   lab6 parent = null;
   JFileChooser fc = null;
   String fpath = null;
   String cpath = null;
	
   public lab6C (lab6 pnt) {
      super("Type Safety");
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      
      parent = pnt;
      (new PlaySound("dooropen.wav")).start();
      
      for (int i=0 ; i < 24 ; i++) {
         b[i] = null;
         c[i] = null;
         x[i] = null;
         y[i] = null;
         z[i] = null;
      }
      try {
         fpath = (new java.io.File(".").getCanonicalPath())+"/src/lab6C/";
         cpath = (new java.io.File(".").getCanonicalPath())+"/src/common/";			
      } catch (Exception e) {
         System.out.println("fpath is not set!!");
      }
      setLayout(new BorderLayout());
      setBackground(bkgd);

      JPanel xq = new JPanel(new BorderLayout());
      xq.setBackground(bkgd);
      JPanel rt = new JPanel(new FlowLayout(FlowLayout.CENTER));
      rt.setBackground(bkgd);
      rt.add(new JLabel("     "));
      JLabel logo = new JLabel("",JLabel.LEFT);
      logo.setIcon(new ImageIcon("src/images/galois-2.png"));
      rt.add(logo);
      xq.add(rt,BorderLayout.WEST);

      JPanel rs = new JPanel(new FlowLayout(FlowLayout.CENTER));
      rs.setBackground(bkgd);      
      rs.add(title = new JLabel("Lesson 6.2                      ", JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      xq.add(rs,BorderLayout.CENTER);

      xq.add(new JLabel(" "), BorderLayout.SOUTH);
      xq.add(new JLabel(" "), BorderLayout.NORTH);      

      add("North", xq);

      xq = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
      xq.setBackground(bkgd);
      xq.add(manual = new JButton("  Manual  "));
      xq.add(new JLabel("   "));
      xq.add(tutorial = new JButton(" Tutorial "));
      xq.add(new JLabel("   "));
      xq.add(term = new JButton(" Terminal "));
      xq.add(new JLabel("              "));
      xq.add(exit = new JButton("   Back   "));
      add("South", xq);

      JPanel xt = new JPanel(new GridLayout(1,1));
      xt.setBackground(bkgd);

      //------ left column ----------

      JPanel xy = new JPanel(new BorderLayout());
      xy.setBackground(bkgd);

      JPanel q = new JPanel(new GridLayout(2,1));
      q.setBackground(bkgd);

      JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);      
      p.add(new JLabel("        "));
      p.add(y[0] = new JButton("Background"));
      p.add(z[0] = new JButton("Cryptol"));
      q.add(p);
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(x[0] = new JButton("Edit"));
      p.add(c[0] = new JButton("Lab"));
      p.add(b[0] = new JButton("Solution"));
      q.add(p);

      z[0].setPreferredSize(new Dimension(110,24));
      y[0].setPreferredSize(new Dimension(135,24));
      x[0].setPreferredSize(new Dimension(70,24));
      c[0].setPreferredSize(new Dimension(70,24));
      b[0].setPreferredSize(new Dimension(100,24));

      xy.add("West", q);
      
      q = new JPanel(new GridLayout(1,1));
      q.setBackground(bkgd);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Type Safety"));
      lbl.setFont(fnt);
      q.add(p);
      xy.add("Center", q);

      xt.add(xy);

      add("Center", xt);

      exit.addActionListener(this);
      term.addActionListener(this);
      manual.addActionListener(this);
      tutorial.addActionListener(this);		
      for (int i=0 ; i < 24 ; i++) {
         if (b[i] != null) b[i].addActionListener(this);
         if (c[i] != null) c[i].addActionListener(this);
         if (x[i] != null) x[i].addActionListener(this);
         if (y[i] != null) y[i].addActionListener(this);
         if (z[i] != null) z[i].addActionListener(this);
      }

		/*
      String command = "cryptol "+fpath;
      try {
         Runtime.getRuntime().exec(command);
      } catch (Exception e) {
         System.out.println("Runtime: "+e.toString());
      }
		*/

      setSize(600,260);
      setVisible(true);
      setResizable(true);
    }
   
   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == exit) {
         if (parent != null) parent.setVisible(true);
         (new PlaySound("leave-12.wav")).start();
         String command = "killall cryptol-bin";
         try {
            Runtime.getRuntime().exec(command);
         } catch (Exception e) {
            System.out.println("Runtime: "+e.toString());
         }
         try { Thread.sleep(500); } catch (Exception e) { }
         dispose();
      }
      else if (evt.getSource() == term) {
         try {
            String command = "runterminal "+fpath;
            Runtime.getRuntime().exec(command);
         } catch (Exception e) {
            System.out.println("Runtime: "+e.toString());
         }
         try { Thread.sleep(500); } catch (Exception e) { }
      }
      else if (evt.getSource() == manual) getDoc(cpath+"/manual.pdf");
      else if (evt.getSource() == tutorial) getDoc(cpath+"/tutorial.pdf");
      else if (evt.getSource() == b[0]) getDoc(fpath+"/solution.pdf");
      else if (evt.getSource() == c[0]) getDoc(fpath+"/lab.pdf");
      else if (evt.getSource() == y[0]) getDoc(fpath+"/background.pdf");
      else if (evt.getSource() == z[0]) {
         String command = "cryptol "+fpath;
         try {
            Runtime.getRuntime().exec(command);
         } catch (Exception e) {
            System.out.println("Runtime: "+e.toString());
         }
		}
		else if (evt.getSource() == x[0]) {
         try {
            fc = new JFileChooser(fpath);
            javax.swing.filechooser.FileFilter filter =
               new FileNameExtensionFilter(
						 "Editable files",
                   new String[] {"txt","cry","tex","saw","c","cc","py","h","Makefile","ll"});
            fc.setFileFilter(filter);
            fc.addChoosableFileFilter(filter);
            fc.showOpenDialog(this);
            File file = fc.getSelectedFile();
            getDoc(fpath+"/"+file.getName());
         } catch (Exception e) {
            System.out.println(fpath+" not available");
         }
      }
   }

   public void getDoc (String name) {
      try {
         Desktop.getDesktop().open(new File(name));
      } catch (Exception e) {
         System.out.println(">>>"+e.toString());
      }
   }
}
