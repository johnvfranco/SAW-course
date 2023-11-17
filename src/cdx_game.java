import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

class cdx_game extends JFrame implements ActionListener {
   JButton c[] = new JButton[24];
   JButton exit = null;
   Color bkgd = new Color(255,255,255);
   JLabel lbl, title;
   Font fnt = new Font("Helvetica", Font.PLAIN, 18);
   aFrame parent = null;
   String fpath = null;
   
   public cdx_game (aFrame pnt) {
      super("Cyber Defense Exercise ST");
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      
      parent = pnt;
      (new PlaySound("dooropen.wav")).start();
      
      for (int i=0 ; i < 24 ; i++) c[i] = null;

      try {
         fpath = (new java.io.File(".").getCanonicalPath())+"/src/cdest";
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
      rs.add(title = new JLabel("Competition                      ", JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      xq.add(rs,BorderLayout.CENTER);

      xq.add(new JLabel(" "), BorderLayout.SOUTH);
      xq.add(new JLabel(" "), BorderLayout.NORTH);      

      add("North", xq);

      xq = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
      xq.setBackground(bkgd);
      xq.add(exit = new JButton("   Back   "));
      add("South", xq);

      JPanel xt = new JPanel(new GridLayout(1,1));
      xt.setBackground(bkgd);

      //------ left column ----------

      JPanel xy = new JPanel(new BorderLayout());
      xy.setBackground(bkgd);

      JPanel q = new JPanel(new GridLayout(4,1));
      q.setBackground(bkgd);

      JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(c[3] = new JButton("Doc"));
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(c[0] = new JButton("Doc"));
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(c[1] = new JButton("GUI"));
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(c[2] = new JButton("Text"));
      q.add(p);

      xy.add("West", q);

      c[0].setPreferredSize(new Dimension(100,24));
      c[1].setPreferredSize(new Dimension(100,24));
      c[2].setPreferredSize(new Dimension(100,24));
      c[3].setPreferredSize(new Dimension(100,24));		
      
      q = new JPanel(new GridLayout(4,1));
      q.setBackground(bkgd);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Outline"));
      lbl.setFont(fnt);
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Manual"));
      lbl.setFont(fnt);
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Control Panel and Scorer"));
      lbl.setFont(fnt);
      q.add(p);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Command Line Scorer Only"));
      lbl.setFont(fnt);
      q.add(p);

      xy.add("Center", q);

      xt.add(xy);

      add("Center", xt);

      exit.addActionListener(this);
      for (int i=0 ; i < 24 ; i++) {
         if (c[i] != null) c[i].addActionListener(this);
      }
      
      setSize(450,310);
      setVisible(true);
      setResizable(true);
   }
   
   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == exit) {
         if (parent != null) parent.setVisible(true);
         (new PlaySound("leave-12.wav")).start();
         try { Thread.sleep(500); } catch (Exception e) { }
         dispose();
      }
      else if (evt.getSource() == c[0]) getDoc(fpath+"/cdest_manual.pdf");
      else if (evt.getSource() == c[3]) getDoc(fpath+"/contest.pdf");
      else if (evt.getSource() == c[1]) {
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec("./run-gui");
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
      else if (evt.getSource() == c[2]) {
			try {
				Runtime rt = Runtime.getRuntime();
				rt.exec("./run-txt");
			} catch (Exception e) {
				System.out.println(e.toString());				
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
