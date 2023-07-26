import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.nio.file.*;

class linx extends JFrame implements ActionListener {
   JButton b[] = new JButton[24];
   JButton c[] = new JButton[24];
   JButton x[] = new JButton[24];
   JButton exit = null;
   JButton term = null;
   Color bkgd = new Color(255,255,255);
   JLabel lbl, title;
   Font fnt = new Font("Helvetica", Font.PLAIN, 18);
   aFrame parent = null;
   
   public linx (aFrame pnt) {
      super("Linux Primer");
      setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      
      parent = pnt;
      (new PlaySound("dooropen.wav")).start();
      
      for (int i=0 ; i < 24 ; i++) {
         b[i] = null;
         c[i] = null;
         x[i] = null;
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
      rs.add(title = new JLabel("Preliminaries                ", JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      xq.add(rs, BorderLayout.CENTER);

      xq.add(new JLabel(" "), BorderLayout.SOUTH);
      xq.add(new JLabel(" "), BorderLayout.NORTH);

      /******
      JPanel xq = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,20));
      xq.setBackground(bkgd);
      xq.add(title = new JLabel("Linux Primer"));
      title.setFont(new Font("Helvetica", Font.BOLD, 22));
      title.setForeground(new Color(0,0,150));
      ********/

      add("North", xq);

      xq = new JPanel(new FlowLayout(FlowLayout.CENTER,0,20));
      xq.setBackground(bkgd);
      xq.add(term = new JButton(" Terminal "));
      xq.add(new JLabel("              "));
      xq.add(exit = new JButton("   Back   "));
      add("South", xq);

      JPanel xt = new JPanel(new GridLayout(1,1));
      xt.setBackground(bkgd);

      //------ left column ----------

      JPanel xy = new JPanel(new BorderLayout());
      xy.setBackground(bkgd);

      JPanel q = new JPanel(new GridLayout(1,1));
      q.setBackground(bkgd);

      JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT));

      p = new JPanel(new FlowLayout(FlowLayout.RIGHT));
      p.setBackground(bkgd);
      p.add(new JLabel("        "));
      p.add(c[0] = new JButton("Lab"));
      p.add(b[0] = new JButton("Solutions"));
      q.add(p);

      xy.add("West", q);
      
      q = new JPanel(new GridLayout(1,1));
      q.setBackground(bkgd);

      p = new JPanel(new FlowLayout(FlowLayout.LEFT));
      p.setBackground(bkgd);
      p.add(lbl = new JLabel("  Linux Primer"));
      lbl.setFont(fnt);
      q.add(p);
      xy.add("Center", q);

      xt.add(xy);

      add("Center", xt);

      exit.addActionListener(this);
      term.addActionListener(this);      
      for (int i=0 ; i < 24 ; i++) {
         if (b[i] != null) b[i].addActionListener(this);
         if (c[i] != null) c[i].addActionListener(this);
      }
      
      setSize(500,220);
      setVisible(true);
      setResizable(true);
    }
   
   public void actionPerformed (ActionEvent evt) {
      String currentPath = null;
      String fpath = null;
      try {
         currentPath = new java.io.File(".").getCanonicalPath();
         fpath = currentPath+"/src/linx/";
      } catch (Exception e) {
         System.out.println("Exception:"+e.toString());
      }
      if (evt.getSource() == exit) {
         if (parent != null) parent.setVisible(true);
         (new PlaySound("leave-12.wav")).start();
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
      else if (evt.getSource() == b[0]) getDoc(fpath+"/solution.pdf");
      else if (evt.getSource() == c[0]) getDoc(fpath+"/lab.pdf");
   }

   public void getDoc (String name) {
      try {
         Desktop.getDesktop().open(new File(name));
      } catch (Exception e) {
         System.out.println(">>>"+e.toString());
      }
   }
}
