import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

class aFrame extends JFrame implements ActionListener {
   JButton b[] = new JButton[24];
   JButton d[] = new JButton[24];   
   JButton exit = null;
   String fpath;
   Color bkgd = new Color(255,255,255);
   JLabel lbl, title;
   Font fnt = new Font("Helvetica", Font.PLAIN, 18);
	//	intr it = null;
   linx lx = null;
   lab1 vx = null;
   lab2 kx = null;
   lab3 ox = null;
   lab4 re = null;
	lab5 tt = null;
   lab6 ar = null;
   lab7 ds = null;
   cdx_game cg = null;
   
   public aFrame () {
      super("Cryptol/SAW for Verification");
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      setLayout(new BorderLayout());
      setBackground(bkgd);

      for (int i=0 ; i < 24 ; i++) {  b[i] = null;  d[i] = null; }

      try {
         fpath = (new java.io.File(".").getCanonicalPath())+"/src";
      } catch (Exception e) {
         System.out.println("fpath is not set!!");
      }
      
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
      rs.add(title = new JLabel("Cryptol/SAW for Verification                ", JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      xq.add(rs,BorderLayout.CENTER);

      xq.add(new JLabel(" "), BorderLayout.SOUTH);
      xq.add(new JLabel(" "), BorderLayout.NORTH);      

      //------------------------------------------------
      
      add("North", xq);

      xq = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
      xq.setBackground(bkgd);
      xq.add(exit = new JButton("   Exit   "));
      add("South", xq);

      JPanel xy = new JPanel(new BorderLayout());
      xy.setBackground(bkgd);

      JPanel q = new JPanel(new GridLayout(9,1));
      q.setBackground(bkgd);

		// Introduction
      //JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      //p.setBackground(bkgd);
      //p.add(new JLabel("      "));
      //p.add(new JLabel("      "));
      //p.add(b[9] = new JButton("Intro"));
      //q.add(p);

      // Linux tutorial
      JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[0] = new JButton("Synopsis"));
      p.add(b[0] = new JButton("Linux"));
      q.add(p);

      // Lab 1
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[1] = new JButton("Synopsis"));
      p.add(b[1] = new JButton("Lesson 1"));
      q.add(p);

      // Lab 2
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[2] = new JButton("Synopsis"));      
      p.add(b[2] = new JButton("Lesson 2"));
      q.add(p);

      // Lab 3
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[3] = new JButton("Synopsis"));      
      p.add(b[3] = new JButton("Lesson 3"));
      d[3].setEnabled(true);
      b[3].setEnabled(true);
      q.add(p);

      // Lab 4
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[4] = new JButton("Synopsis"));      
      p.add(b[4] = new JButton("Lesson 4"));
      d[4].setEnabled(true);
      b[4].setEnabled(true);
      q.add(p);

      // Lab 5
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[5] = new JButton("Synopsis"));      
      p.add(b[5] = new JButton("Lesson 5"));
      d[5].setEnabled(true);
      b[5].setEnabled(true);
      q.add(p);

      // Lab 6
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[6] = new JButton("Synopsis"));      
      p.add(b[6] = new JButton("Lesson 6"));
      d[6].setEnabled(true);
      b[6].setEnabled(true);
      q.add(p);

      // Lab 7
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[7] = new JButton("Synopsis"));      
      p.add(b[7] = new JButton("Lesson 7"));
      d[7].setEnabled(true);
      b[7].setEnabled(true);
      q.add(p);

      // Contest
      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("      "));
      p.add(d[8] = new JButton("Synopsis"));      
      p.add(b[8] = new JButton("Contest"));
      d[8].setEnabled(true);
      b[8].setEnabled(true);
      q.add(p);
      
      xy.add("West", q);
      
      q = new JPanel(new GridLayout(9,1));
      q.setBackground(bkgd);

      for (int i=0 ; i < 9 ; i++) {
         if (d[i] != null) d[i].setPreferredSize(new Dimension(100,24));
         if (b[i] != null) b[i].setPreferredSize(new Dimension(100,24));
      }

      // Introduction
      //p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //p.setBackground(bkgd);
      //p.add(lbl = new JLabel("  Introduction to Course", JLabel.LEFT));
      //lbl.setFont(fnt);
      //q.add(p);
		
      // Linux Totorial
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Linux Primer", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 1
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  What is Math Logic, why is it important?", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 2
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Cryptol control, syntax and semantics", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 3
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Writing a specification", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 4
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Writing and proving theorems", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 5
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Software Analysis Workbench", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 6
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Code safety", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Lab 7
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Proving functional correctness", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      // Contest
      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Formal Verification Contest", JLabel.LEFT));
      lbl.setFont(fnt);
      q.add(p);

      xy.add("Center", q);
      
      add("Center", xy);

      exit.addActionListener(this);
      
      for (int i=0 ; i < 24 ; i++) {
         if (d[i] != null) d[i].addActionListener(this);
         if (b[i] != null) b[i].addActionListener(this);
      }
   }
   
   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == exit) {
         (new PlaySound("leave-12.wav")).start();
         try { Thread.sleep(500); } catch (Exception e) { }
         System.exit(1);
      } //else if (evt.getSource() == b[9]) {
        // it = new intr(this);
        // setVisible(false);
        //}
	   else if (evt.getSource() == b[0]) {
         lx = new linx(this);
         setVisible(false);
      } else if (evt.getSource() == b[1]) {
         vx = new lab1(this);
         setVisible(false);
      } else if (evt.getSource() == b[2]) {
         kx = new lab2(this);
         setVisible(false);
      } else if (evt.getSource() == b[3]) {
         ox = new lab3(this);
         setVisible(false);
      } else if (evt.getSource() == b[4]) {
         re = new lab4(this);
         setVisible(false);
      } else if (evt.getSource() == b[5]) {
         tt = new lab5(this);
         setVisible(false);
      } else if (evt.getSource() == b[6]) {
         ar = new lab6(this);
         setVisible(false);
      } else if (evt.getSource() == b[7]) {
         ds = new lab7(this);
         setVisible(false);
      } else if (evt.getSource() == b[8]) {
         cg = new cdx_game(this);
         setVisible(false);
      }
      else if (evt.getSource() == d[0]) getDoc(fpath+"/linx/synopsis.pdf");
      else if (evt.getSource() == d[1]) getDoc(fpath+"/lab1/synopsis.pdf");
      else if (evt.getSource() == d[2]) getDoc(fpath+"/lab2/synopsis.pdf");
      else if (evt.getSource() == d[3]) getDoc(fpath+"/lab3/synopsis.pdf");
      else if (evt.getSource() == d[4]) getDoc(fpath+"/lab4/synopsis.pdf");
      else if (evt.getSource() == d[5]) getDoc(fpath+"/lab5/synopsis.pdf");
      else if (evt.getSource() == d[6]) getDoc(fpath+"/lab6/synopsis.pdf");
      else if (evt.getSource() == d[7]) getDoc(fpath+"/lab7/synopsis.pdf");
      else if (evt.getSource() == d[8]) getDoc(fpath+"/cdest/synopsis.pdf");
   }

   public void getDoc (String name) {
      try {
         Desktop.getDesktop().open(new File(name));
      } catch (Exception e) {
         System.out.println(">>>"+e.toString());
      }
   }
}

public class a {
   public static void main (String args[]) {
      aFrame af = new aFrame();
      af.setSize(700,520);
      af.setVisible(true);
   }
}
