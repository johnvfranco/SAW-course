import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

class lab6 extends JFrame implements ActionListener {
   JButton b[] = new JButton[24];
   JButton d[] = new JButton[24];   
   JButton exit = null;
   Color bkgd = new Color(255,255,255);
   JLabel lbl, title;
   Font fnt = new Font("Helvetica", Font.PLAIN, 18);
   lab6A vx = null;
   lab6B kx = null;
   lab6C ox = null;
   lab6D re = null;
   lab6E ar = null;
   aFrame parent = null;
   String fpath = null;
   
   public lab6 (aFrame pnt) {
      super("Code Safety");
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

      parent = pnt;
      (new PlaySound("dooropen.wav")).start();

      try {
         fpath = (new java.io.File(".").getCanonicalPath())+"/src/";
      } catch (Exception e) {
         System.out.println("fpath is not set!!");
      }
      
      setLayout(new BorderLayout());
      setBackground(bkgd);

      for (int i=0 ; i < 24 ; i++) {  b[i] = null;  d[i] = null; }

      //---------- Title of Dialog between these two comments ---------
      
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
      rs.add(title = new JLabel("Code Safety             ", JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      xq.add(rs,BorderLayout.CENTER);

      xq.add(new JLabel(" "), BorderLayout.SOUTH);
      xq.add(new JLabel(" "), BorderLayout.NORTH);      

      //------------------------------------------------
      
      add("North", xq);

      xq = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
      xq.setBackground(bkgd);
      xq.add(exit = new JButton("   Back   "));
      add("South", xq);

      JPanel xy = new JPanel(new BorderLayout());
      xy.setBackground(bkgd);

      JPanel q = new JPanel(new GridLayout(2,1));
      q.setBackground(bkgd);

      // Lab 1
      JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[1] = new JButton("Synopsis"));
      p.add(b[1] = new JButton("Lesson 6.1"));
      q.add(p);

      // Lab 2
      //p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      //p.setBackground(bkgd);
      //p.add(new JLabel("      "));
      //p.add(d[2] = new JButton("Synopsis"));      
      //p.add(b[2] = new JButton("Lesson 6.2"));
      //q.add(p);

      // Lab 3
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[2] = new JButton("Synopsis"));      
      p.add(b[2] = new JButton("Lesson 6.2"));
      q.add(p);

      // Lab 4
      //p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      //p.setBackground(bkgd);
      //p.add(new JLabel("      "));
      //p.add(d[3] = new JButton("Synopsis"));      
      //p.add(b[3] = new JButton("Lesson 6.3"));
		//d[3].setEnabled(false);
		//b[3].setEnabled(false);
      //q.add(p);

      // Lab 5
      //p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      //p.setBackground(bkgd);
      //p.add(new JLabel("      "));
      //p.add(d[4] = new JButton("Synopsis"));      
      //p.add(b[4] = new JButton("Lesson 6.4"));
		//d[4].setEnabled(false);
		//b[4].setEnabled(false);
      //q.add(p);

      xy.add("West", q);
      
      q = new JPanel(new GridLayout(2,1));
      q.setBackground(bkgd);

      for (int i=1 ; i < 5 ; i++) {
         if (d[i] != null) d[i].setPreferredSize(new Dimension(110,24));
         if (b[i] != null) b[i].setPreferredSize(new Dimension(110,24));
      }

      // Lab 1
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Memory Safety", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 2
      //p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //p.setBackground(bkgd);
      //p.add(lbl = new JLabel("  Thread Safety", JLabel.LEFT));
      //lbl.setFont(fnt);
      //q.add(p);

      // Lab 3
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Type Safety", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 4
      //p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //p.setBackground(bkgd);
      //p.add(lbl = new JLabel("  Cryptol and SAW Safety Checks", JLabel.LEFT));
      //lbl.setFont(fnt);
      //q.add(p);

      // Lab 5
      //p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //p.setBackground(bkgd);
      //p.add(lbl = new JLabel("  Conclusions", JLabel.LEFT));
      //lbl.setFont(fnt);
      //q.add(p);

      xy.add("Center", q);
      
      add("Center", xy);

      exit.addActionListener(this);
      
      for (int i=0 ; i < 24 ; i++) {
         if (b[i] != null) b[i].addActionListener(this);
         if (d[i] != null) d[i].addActionListener(this);
      }

      setSize(500,250);      
      setVisible(true);
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
      } else if (evt.getSource() == b[1]) {
         vx = new lab6A(this);
         setVisible(false);
		   // else if (evt.getSource() == b[2]) {
		   //   kx = new lab6B(this);
         //   setVisible(false);
			//
	   }  else if (evt.getSource() == b[2]) {
         ox = new lab6C(this);
         setVisible(false);
         // else if (evt.getSource() == b[3]) {
         //   re = new lab6D(this); 
         //   setVisible(false);
			//
         //} else if (evt.getSource() == b[4]) {
         //    ar = new lab6E(this);
         //    setVisible(false);
      }
      else if (evt.getSource() == d[1]) getDoc(fpath+"/lab6A/synopsis.pdf");
      else if (evt.getSource() == d[2]) getDoc(fpath+"/lab6C/synopsis.pdf");
      //else if (evt.getSource() == d[3]) getDoc(fpath+"/lab6D/synopsis.pdf");
      //else if (evt.getSource() == d[4]) getDoc(fpath+"/lab6E/synopsis.pdf");
      //else if (evt.getSource() == d[5]) getDoc(fpath+"/lab6E/synopsis.pdf");
   }

   public void getDoc (String name) {
      try {
         Desktop.getDesktop().open(new File(name));
      } catch (Exception e) {
         System.out.println(">>>"+e.toString());
      }
   }
}
