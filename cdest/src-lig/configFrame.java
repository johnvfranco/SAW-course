/***
    configFrame.java - set contest parameters
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
import java.nio.file.*;
import java.net.*;

class DateChoose extends JPanel implements ActionListener, MouseListener {
   JComboBox <String> start_month, start_day, start_year, start_hour, start_minute;
   JComboBox <String> end_month, end_day, end_year, end_hour, end_minute;
   JComboBox <String> timezone;
   Color bkgd = new Color(255,255,223), ttlcolor = new Color(0,0,150);
   JButton setdates;
   JLabel label;
   String starting, timez, ending;
   private DefaultListCellRenderer listRenderer;
   LocalDateTime date;
   configFrame cf;
   mouse mousehelp = null;

   public DateChoose (configFrame c) {
      cf = c;
      JPanel whole = new JPanel(new BorderLayout());
      whole.setBackground(bkgd);

      JPanel t = new JPanel(new BorderLayout());
      t.setBackground(bkgd);
      t.add("North", label = new JLabel(" "));
      label.setFont(new Font("Helvetica", Font.BOLD, 12));
      t.add("Center", label = new JLabel("Competition Dates and Times", JLabel.LEFT));
      label.setFont(new Font("Helvetica", Font.BOLD, 16));
      label.setForeground(ttlcolor);
      
      whole.add("North", t);
      whole.add("East", new JLabel("     "));
      whole.add("South", new JLabel("     "));      

      JPanel s = new JPanel(new BorderLayout());
      s.setBackground(bkgd);

      s.add("North", new JLabel(" "));

      JPanel q = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 25));
      q.setBackground(bkgd);
      
      JPanel p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      
      p.add("South", new JLabel("timezone",JLabel.CENTER));      
      p.add("Center", timezone = new JComboBox <String> ());
      timezone.setBackground(new Color(120,255,255));
      timezone.addItem("Eastern");
      timezone.addItem("Central");
      timezone.addItem("Mountain");
      timezone.addItem("Pacific");
      q.add(p);

      q.add(new JLabel("  "));      

      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("  ",JLabel.CENTER));      
      p.add("Center", setdates = new JButton("Set Dates"));
      setdates.addActionListener(this);
      q.add(p);

      s.add("Center", q);

      JPanel nr = new JPanel(new BorderLayout());
      nr.setBackground(bkgd);

      nr.add("East", new JLabel("      "));
      nr.add("North", label = new JLabel("  "));
      label.setFont(new Font("Helvetica", Font.PLAIN, 17));
      nr.add("South", s);

      q = new JPanel(new GridLayout(3,1));
      q.setBackground(bkgd);
      q.add(label = new JLabel("   Start:  ",JLabel.RIGHT));
      label.setFont(new Font("Helvetica",Font.BOLD, 16));
      label.setForeground(new Color(0,50,150));
      q.add(label = new JLabel(" "));
      label.setFont(new Font("Helvetica",Font.BOLD, 16));
      label.setForeground(new Color(0,50,150));
      q.add(label = new JLabel("End:  ",JLabel.RIGHT));
      label.setFont(new Font("Helvetica",Font.BOLD, 16));
      label.setForeground(new Color(0,50,150));
      nr.add("West", q);

      q = new JPanel(new GridLayout(3,5));
      q.setBackground(bkgd);

      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("month",JLabel.CENTER));
      p.add("Center", start_month = new JComboBox <String> ());
      start_month.addItem("January");
      start_month.addItem("February");
      start_month.addItem("March");
      start_month.addItem("April");
      start_month.addItem("May");
      start_month.addItem("June");
      start_month.addItem("July");
      start_month.addItem("August");
      start_month.addItem("September");
      start_month.addItem("October");
      start_month.addItem("November");
      start_month.addItem("December");
      q.add(p);                                                     // 1

      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("day",JLabel.CENTER));      
      p.add("Center", start_day = new JComboBox <String> ());
      start_day.addItem("1");
      start_day.addItem("2");
      start_day.addItem("3");
      start_day.addItem("4");
      start_day.addItem("5");
      start_day.addItem("6");
      start_day.addItem("7");
      start_day.addItem("8");
      start_day.addItem("9");
      start_day.addItem("10");
      start_day.addItem("11");
      start_day.addItem("12");
      start_day.addItem("13");
      start_day.addItem("14");
      start_day.addItem("15");
      start_day.addItem("16");
      start_day.addItem("17");
      start_day.addItem("18");
      start_day.addItem("19");
      start_day.addItem("20");
      start_day.addItem("21");
      start_day.addItem("22");
      start_day.addItem("23");
      start_day.addItem("24");
      start_day.addItem("25");
      start_day.addItem("26");
      start_day.addItem("27");
      start_day.addItem("28");
      start_day.addItem("29");
      start_day.addItem("30");
      start_day.addItem("31");
      q.add(p);                                                     // 2
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("year",JLabel.CENTER));
      p.add("Center", start_year = new JComboBox <String> ());
      q.add(p);                                                     // 3

      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("hour",JLabel.CENTER));
      p.add("Center", start_hour = new JComboBox <String> ());
      start_hour.addItem("0");
      start_hour.addItem("1");
      start_hour.addItem("2");
      start_hour.addItem("3");
      start_hour.addItem("4");
      start_hour.addItem("5");
      start_hour.addItem("6");
      start_hour.addItem("7");
      start_hour.addItem("8");
      start_hour.addItem("9");
      start_hour.addItem("10");
      start_hour.addItem("11");
      start_hour.addItem("12");
      start_hour.addItem("13");
      start_hour.addItem("14");
      start_hour.addItem("15");
      start_hour.addItem("16");
      start_hour.addItem("17");
      start_hour.addItem("18");
      start_hour.addItem("19");
      start_hour.addItem("20");
      start_hour.addItem("21");
      start_hour.addItem("22");
      start_hour.addItem("23");
      q.add(p);                                                     // 4
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("minute",JLabel.CENTER));
      p.add("Center", start_minute = new JComboBox <String> ());
      start_minute.addItem("00");
      start_minute.addItem("10");
      start_minute.addItem("15");
      start_minute.addItem("30");
      start_minute.addItem("45");
      q.add(p);                                                     // 5

      q.add(new JLabel(""));                                        // 1
      q.add(new JLabel(""));                                        // 2
      q.add(new JLabel(""));                                        // 3
      q.add(new JLabel(""));                                        // 4
      q.add(new JLabel(""));                                        // 5

      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("month",JLabel.CENTER));      
      p.add("Center", end_month = new JComboBox <String> ());
      end_month.addItem("January");
      end_month.addItem("February");
      end_month.addItem("March");
      end_month.addItem("April");
      end_month.addItem("May");
      end_month.addItem("June");
      end_month.addItem("July");
      end_month.addItem("August");
      end_month.addItem("September");
      end_month.addItem("October");
      end_month.addItem("November");
      end_month.addItem("December");
      q.add(p);                                                     // 1
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("day",JLabel.CENTER));
      p.add("Center", end_day = new JComboBox <String> ());
      end_day.addItem("1");
      end_day.addItem("2");
      end_day.addItem("3");
      end_day.addItem("4");
      end_day.addItem("5");
      end_day.addItem("6");
      end_day.addItem("7");
      end_day.addItem("8");
      end_day.addItem("9");
      end_day.addItem("10");
      end_day.addItem("11");
      end_day.addItem("12");
      end_day.addItem("13");
      end_day.addItem("14");
      end_day.addItem("15");
      end_day.addItem("16");
      end_day.addItem("17");
      end_day.addItem("18");
      end_day.addItem("19");
      end_day.addItem("20");
      end_day.addItem("21");
      end_day.addItem("22");
      end_day.addItem("23");
      end_day.addItem("24");
      end_day.addItem("25");
      end_day.addItem("26");
      end_day.addItem("27");
      end_day.addItem("28");
      end_day.addItem("29");
      end_day.addItem("30");
      end_day.addItem("31");
      q.add(p);                                                     // 2
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("year",JLabel.CENTER));
      p.add("Center", end_year = new JComboBox <String> ());
      q.add(p);                                                     // 3
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("hour",JLabel.CENTER));
      p.add("Center", end_hour = new JComboBox <String> ());
      end_hour.addItem("0");
      end_hour.addItem("1");
      end_hour.addItem("2");
      end_hour.addItem("3");
      end_hour.addItem("4");
      end_hour.addItem("5");
      end_hour.addItem("6");
      end_hour.addItem("7");
      end_hour.addItem("8");
      end_hour.addItem("9");
      end_hour.addItem("10");
      end_hour.addItem("11");
      end_hour.addItem("12");
      end_hour.addItem("13");
      end_hour.addItem("14");
      end_hour.addItem("15");
      end_hour.addItem("16");
      end_hour.addItem("17");
      end_hour.addItem("18");
      end_hour.addItem("19");
      end_hour.addItem("20");
      end_hour.addItem("21");
      end_hour.addItem("22");
      end_hour.addItem("23");
      q.add(p);                                                     // 4
      
      p = new JPanel(new BorderLayout());
      p.setBackground(bkgd);
      p.add("South", new JLabel("minute",JLabel.CENTER));
      p.add("Center", end_minute = new JComboBox <String> ());
      end_minute.addItem("00");
      end_minute.addItem("10");
      end_minute.addItem("15");
      end_minute.addItem("30");
      end_minute.addItem("45");
      q.add(p);

      nr.add("Center", q);
      nr.setBorder(BorderFactory.createLineBorder(Color.black));

      whole.add("Center", nr);

      add(whole);
      setBackground(bkgd);

      listRenderer = new DefaultListCellRenderer();
      listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
      start_month.setRenderer(listRenderer);
      start_day.setRenderer(listRenderer);
      start_year.setRenderer(listRenderer);
      start_hour.setRenderer(listRenderer);
      start_minute.setRenderer(listRenderer);
      end_month.setRenderer(listRenderer);
      end_day.setRenderer(listRenderer);
      end_year.setRenderer(listRenderer);
      end_hour.setRenderer(listRenderer);
      end_minute.setRenderer(listRenderer);
      timezone.setRenderer(listRenderer);

      start_month.addMouseListener(this);
      start_day.addMouseListener(this);
      start_year.addMouseListener(this);
      start_hour.addMouseListener(this);
      start_minute.addMouseListener(this);
      end_month.addMouseListener(this);
      end_day.addMouseListener(this);
      end_year.addMouseListener(this);
      end_hour.addMouseListener(this);
      end_minute.addMouseListener(this);
      timezone.addMouseListener(this);
      setdates.addMouseListener(this);
      
      date = LocalDateTime.now();
      
      int minute = date.getMinute();
      int minidx = 0;
      if (minute >= 0 && minute < 10) minidx = 1;
      else if (minute >= 10 && minute < 15) minidx = 2;
      else if (minute >= 15 && minute < 30) minidx = 3;
      else if (minute >= 30 && minute < 45) minidx = 4;
      else minidx = 0;
      if (minidx == 0) {
         end_hour.setSelectedIndex(date.getHour()+1);
         start_hour.setSelectedIndex(date.getHour()+1);
      } else {
         end_hour.setSelectedIndex(date.getHour());
         start_hour.setSelectedIndex(date.getHour());
      }
      start_minute.setSelectedIndex(minidx);
      end_minute.setSelectedIndex(minidx);
      
      int year = date.getYear();
      start_year.addItem(String.valueOf(year));
      end_year.addItem(String.valueOf(year));
      start_year.addItem(String.valueOf(year+1));
      end_year.addItem(String.valueOf(year+1));
      start_year.addItem(String.valueOf(year+2));
      end_year.addItem(String.valueOf(year+2));
      int month = date.getMonthValue();
      start_month.setSelectedIndex(month-1);
      end_month.setSelectedIndex(month-1);
      int day = date.getDayOfMonth();
      start_day.setSelectedIndex(day-1);
      end_day.setSelectedIndex(day-1);
      
      setSize(520,270);
      setVisible(true);
   }

   synchronized public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == setdates) {
         cf.setDates();
      }
   }

   public void mouseEntered (MouseEvent evt) {
      if (!cf.helpstate) return;
      if (evt.getSource() == start_year ||
          evt.getSource() == start_month ||
          evt.getSource() == start_day ||
          evt.getSource() == start_hour ||
          evt.getSource() == start_minute) cf.mousehelp.xlate(16);
      if (evt.getSource() == end_year ||
          evt.getSource() == end_month ||
          evt.getSource() == end_day ||
          evt.getSource() == end_hour ||
          evt.getSource() == end_minute) cf.mousehelp.xlate(17);
      if (evt.getSource() == setdates) cf.mousehelp.xlate(18);
      if (evt.getSource() == timezone) cf.mousehelp.xlate(19);
   }
   
   public void mouseExited (MouseEvent evt) { }
   public void mouseClicked (MouseEvent evt) { cf.mousehelp.exit(); }
   public void mousePressed (MouseEvent evt) { }
   public void mouseReleased (MouseEvent evt) { }

   public String xx (String x) {
      if (x.length() == 1) return "0"+x; else return x;
   }

   public String tz (String x) {
      switch (x) {
      case "EST": return "GMT-5";
      case "EDT": return "GMT-4";
      case "CST": return "GMT-6";
      case "CDT": return "GMT-5";
      case "MST": return "GMT-7";
      case "MDT": return "GMT-6";
      case "PST": return "GMT-8";
      case "PDT": return "GMT-7";
      case "GMT": return "GMT";
      }
      return null;
   }

   public String value () {
      return ((String)start_month.getSelectedItem()).substring(0,3)+" "+
         (String)start_day.getSelectedItem()+" "+
         (String)start_hour.getSelectedItem()+":"+
         (String)start_minute.getSelectedItem()+":00 "+
         (String)timezone.getSelectedItem()+" "+
         (String)start_year.getSelectedItem()+" to "+
         ((String)end_month.getSelectedItem()).substring(0,3)+" "+
         (String)end_day.getSelectedItem()+" "+
         (String)end_hour.getSelectedItem()+":"+
         (String)end_minute.getSelectedItem()+":00 "+
         (String)timezone.getSelectedItem()+" "+         
         (String)end_year.getSelectedItem();
   }

   public long start_time () {
      String m = xx(String.valueOf(start_month.getSelectedIndex()+1));
      String d = xx((String)start_day.getSelectedItem());
      String h = xx((String)start_hour.getSelectedItem());
      starting = d+"."+m+"."+
         start_year.getSelectedItem()+"  "+h+":"+start_minute.getSelectedItem()+":00";
      LocalDateTime time =
         LocalDateTime.parse(starting, DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));

      timez = (String)timezone.getSelectedItem();
      ZoneId zid;
      if (timez.equals("Eastern")) zid = ZoneId.of("America/New_York");
      else if (timez.equals("Central")) zid = ZoneId.of("America/Chicago");
      else if (timez.equals("Mountain")) zid = ZoneId.of("America/Denver");
      else if (timez.equals("Pacific")) zid = ZoneId.of("America/Los_Angeles");
      else zid = ZoneId.of("America/New_York");
      long epoch = time.atZone(zid).toEpochSecond();
      
      return epoch;
   }
   
   public long end_time () {
      String m = xx(String.valueOf(end_month.getSelectedIndex()+1));
      String d = xx((String)end_day.getSelectedItem());
      String h = xx((String)end_hour.getSelectedItem());
      ending = d+"."+m+"."+
         end_year.getSelectedItem()+"  "+h+":"+end_minute.getSelectedItem()+":00";
      LocalDateTime time =
         LocalDateTime.parse(ending, DateTimeFormatter.ofPattern("dd.MM.yyyy  HH:mm:ss"));
      
      String timez = (String)timezone.getSelectedItem();
      ZoneId zid;
      if (timez.equals("Eastern")) zid = ZoneId.of("America/New_York");
      else if (timez.equals("Central")) zid = ZoneId.of("America/Chicago");
      else if (timez.equals("Mountain")) zid = ZoneId.of("America/Denver");
      else if (timez.equals("Pacific")) zid = ZoneId.of("America/Los_Angeles");
      else zid = ZoneId.of("America/New_York");
      long epoch = time.atZone(zid).toEpochSecond();
      
      return epoch;
   }
}

class help extends JFrame implements ActionListener {
   JTextArea log = new JTextArea(24,60);
   JLabel title;
   boolean frame_location;
   Color bkgd = new Color(255,255,223);
   JButton kill;

   public help (String ttl, boolean fl) {
      super.setTitle("contest help");
      setLayout(new BorderLayout());
      setBackground(bkgd);
      setUndecorated(true);
      
      add("Center", log);
      log.setEditable(false);
      log.setFont(new Font("Helvetica", Font.PLAIN, 16));
      log.setBorder(BorderFactory.createLineBorder(Color.black));

      JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
      p.setBackground(new Color(220,220,160));
      p.add(title = new JLabel("   ", JLabel.CENTER));
      p.setBorder(BorderFactory.createLineBorder(Color.black));
      add("North", p);

      title.setText(ttl);
      title.setFont(new Font("Helvetica", Font.BOLD, 18));

      frame_location = fl;
      if (frame_location) setLocationRelativeTo(null);
   }

   public help (String ttl, boolean fl, boolean button) {
      super.setTitle("contest help");
      setLayout(new BorderLayout());
      add("Center", log);
      log.setEditable(false);
      log.setBorder(BorderFactory.createLineBorder(Color.black));
      add("North", title = new JLabel("   ", JLabel.CENTER));
      log.setFont(new Font("Helvetica", Font.PLAIN, 16));
      frame_location = fl;
      setUndecorated(true);
      if (frame_location) setLocationRelativeTo(null);
      log.setText("");
      title.setText(ttl);
      title.setFont(new Font("Helvetica", Font.BOLD, 18));      
      title.setBorder(BorderFactory.createLineBorder(Color.black));      
      title.setBackground(new Color(255,255,0));
      JPanel p = new JPanel(new FlowLayout());
      p.add(kill = new JButton("Close"));
      add("South", p);
      kill.addActionListener(this);
   }

   public void erase () { log.setText(""); title.setText(""); }

   public void append (String str) {
      log.append(str);
   }

   public void fontUp () {
      log.setFont(new Font("Helvetica", Font.PLAIN, 20));
   }

   public void fontDn () {
      log.setFont(new Font("Helvetica", Font.PLAIN, 14));
   }
   
   public void flush () {
      log.append("\n");
   }

   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == kill) {
         dispose();
      }
   }   
}

class mouse {
   private help a1 = null;
   
   public mouse () { }

   public void xlate (int i) {
      switch (i) {
      case 0: player_database_state(); break;
      case 1: scorer_log_file(); break;
      case 2: logging_option(); break;
      case 3: scoreboard_file(); break;
      case 4: scoreboard_url(); break;
      case 5: scoreboard_title(); break;
      case 6: web_directory(); break;
      case 7: recover_database(); break;
      case 8: save_game_id(); break;
      case 9: make_keys(); break;
      case 10: stop_making_keys(); break;
      case 11: number_keys(); break;
      case 12: leave_and_send(); break;
      case 13: leave_no_send(); break;
      case 14: prepare_with_VPN_keys(); break;
      case 15: prepare_with_no_VPN_keys(); break;
      case 16: start_date(); break;
      case 17: end_date(); break;
      case 18: set_dates(); break;
      case 19: time_zone(); break;
      }
   }

   public void start_date() {
      exit();
      a1 = new help("Set the start and end dates and times", false); 
      a1.append(
"\n          Choose the month, day, year, and time the contest will"+
"\n          start.  Times are with respect to the chosen time zone.");
      a1.setSize(480,110);
      a1.setVisible(true);
   }
   
   public void end_date() {
      exit();
      a1 = new help("Set the start and end dates and times", false); 
      a1.append(
"\n          Choose the month, day, year, and time the contest will"+
"\n          end.  Times are with respect to the chosen time zone.");
      a1.setSize(480,110);
      a1.setVisible(true);
   }
   
   public void set_dates() {
      exit();
      a1 = new help("Set the start and end dates and times", false); 
      a1.append(
"\n          Set the month, day, year, time contest is to start and"+
"\n          time contest is to end.  This information shows up in"+
"\n          the 'Parms' file of each Player both as a Unix universal"+
"\n          time and in human readable form.  This way a Player may"+
"\n          code something that automatically causes the Player's"+
"\n          OS to begin serving at the moment the contest begins.");
      a1.setSize(490,200);
      a1.setVisible(true);
   }
   
   public void time_zone() {
      exit();
      a1 = new help("Set the timezone in which the times apply", false); 
      a1.append(
"\n          Choose the official timezone in which the contest start"+
"\n          and end times apply.  The time zones are labeled Eastern"+
"\n          Pacific, etc. instead of EDT, EST, PDT, PST, etc. because"+
"\n          Daylight Savings Time is automatically taken into account.");
      a1.setSize(500,160);
      a1.setVisible(true);
   }
   
   public void prepare_with_VPN_keys() {
      exit();
      a1 = new help("Get all files ready including VPN keys", false); 
      a1.append(
"\n          A directory is created for each Player.  The following are"+
"\n          placed in the directory: a client.conf file for use by"+
"\n          openvpn to connect to a contest VPN; scripts run.client,"+
"\n          run.vpn, stop.client to control joining and leaving the VPN;"+
"\n          a Parms file containing start, stop dates and IP address"+
"\n          of the Player's OS; a directory 'sbin' containing a statically"+
"\n          compiled 'openvpn' ELF executable; a 'keys' directory"+
"\n          containing OpenVPN keys and CA certificate for joining"+
"\n          the contest VPN; and a file 'email.txt' which is used by"+
"\n          'mutt' to send a tarred archive of all these files to a Player."+
"\n          There are also a couple of 'readme' files.");
      a1.setSize(490,290);
      a1.setVisible(true);
   }
   
   public void prepare_with_no_VPN_keys() {
      exit();
      a1 = new help("Get all files ready except those for VPN", false); 
      a1.append(
"\n          A directory is created for each Player.  The following are"+
"\n          placed in the directory: a Parms file containing start, stop"+
"\n          dates and IP address of the Player's OS; and a file 'email.txt'"+
"\n          which is used by 'mutt' to send a tarred archive of all these"+
"\n          files to a Player.  There are also a couple of 'readme' files.");
      a1.setSize(500,180);
      a1.setVisible(true);
   }
   
   public void leave_and_send() {
      exit();
      a1 = new help("Send all files to players", false); 
      a1.append(
"\n          Tar all player directories and send the tar archive to the"+
"\n          email address specified in file 'email.txt' of the Player's"+
"\n          directory.");
      a1.setSize(480,140);
      a1.setVisible(true);
   }
   
   public void leave_no_send() {
      exit();
      a1 = new help("Leave configurator and do not send files", false); 
      a1.append(
"\n          Leave the configurator without making any further changes.");
      a1.setSize(500,100);
      a1.setVisible(true);
   }
   
   public void number_keys() {
      exit();
      a1 = new help("How many OpenVPN keys to make", false); 
      a1.append(
"\n          How many OpenVPN keys should be made.  There is no need"+
"\n          to make keys for every contest hence the default is 150.");
      a1.setSize(510,110);
      a1.setVisible(true);
   }
   
   public void stop_making_keys() {
      exit();
      a1 = new help("Abort making the OpenVPN keys", false); 
      a1.append(
"\n          Stop making OpenVPN keys.  Keys and certificates that have"+
"\n          been made to this point remain.");
      a1.setSize(510,120);
      a1.setVisible(true);
   }
   
   public void make_keys() {
      exit();
      a1 = new help("Make the OpenVPN keys", false); 
      a1.append(
"\n          Initiate making OpenVPN keys.  Some operations are"+
"\n          disabled while the keys are being made.");
      a1.setSize(470,120);
      a1.setVisible(true);
   }
   
   public void save_game_id() {
      exit();
      a1 = new help("Save the game id", false); 
      a1.append(
"\n          Save the information filled in above.  Nothing else"+
"\n          is saved.");
      a1.setSize(430,120);
      a1.setVisible(true);
   }
   
   public void recover_database() {
      exit();
      a1 = new help("Allow database to be recovered on scorer restart?", false); 
      a1.append(
"\n          YES if database should be reloaded on restart of scorer\n"+
"\n          NO, if the database files should be cleared before restarting"+
"\n          the scorer.");
      a1.setSize(510,160);
      a1.setVisible(true);
   }
   
   public void web_directory() {
      exit();
      a1 = new help("Web directory", true); 
      a1.append(
"\n          The directory in which the scoreboard is placed."+
"\n          Edit to where the web server normally serves files.");
      a1.setSize(425,120);
      a1.setVisible(true);
   }

   public void player_database_state () {
      exit();
      a1 = new help("Player Database State", false); 
      a1.append(
"\n          Static means that only Players in the player database"+
"\n          are checked for running services.\n"+
"\n          Dynamic means that Guests can also be checked for"+
"\n          running services (and appear on the scoreboard).");
      a1.setSize(450,180);
      a1.setVisible(true);
   }

   public void scorer_log_file () {
      exit();
      a1 = new help("Scorer Log File", false); 
      a1.append(
"\n          The name of the log file.  The Contest Administrator"+
"\n          may decide to share this with Contestants, especially"+
"\n          in an elementary or intermediate contest.");
      a1.setSize(460,130);
      a1.setVisible(true);
   }

   public void logging_option () {
      exit();
      a1 = new help("Logging Option", false); 
      a1.append(
"\n          1: Every event is logged\n"+
"\n          2: Configuration events plus contest commands and services"+
"\n              are logged\n"+
"\n          3: Checking service results plus other commands and results"+
"\n              during a contest are logged\n"+
"\n          4: Only the results of checking services are logged");
      a1.setSize(520,250);
      a1.setVisible(true);
   }

   public void scoreboard_file () {
      exit();
      a1 = new help("Scoreboard File", true); 
      a1.append(
"\n          The name of the scoreboard file.");
      a1.setSize(500,150);
      a1.setVisible(true);
   }

   public void scoreboard_url () {
      exit();
      a1 = new help("URL to reach scoreboard", true);
      a1.append(
"\n          The URL to use in a browser to reach the scoreboard.  The"+
"\n          field is to be edited to a URL where the scoreboard can be"+
"\n          reached on the web.");
      a1.setSize(480,140);
      a1.setVisible(true);
   }

   public void scoreboard_title () {
      exit();
      a1 = new help("Scoreboard Title", true); 
      a1.append(
"\n          The title that appears in the scoreboard.  The field"+
"\n          should be edited to reflect the theme, organization"+
"\n          and such of the competition.");
      a1.setSize(440,130);
      a1.setVisible(true);
   }

   public void exit () {
      if (a1 != null) a1.dispose();
      a1 = null;
   }
}

public class configFrame extends JFrame implements ActionListener, MouseListener {
   @SuppressWarnings("unchecked")
   FileOutputStream vpnfos = null;
   PrintWriter vpnKeyIds = null;
   JComboBox <String> b[] = new JComboBox [100];
   String c[] = new String [100];
   JTextField msgs = null;
   JPanel p, g, q, r, s;
   JButton go, help, dateAndTime, cancel, done, leave;
   JLabel title, t, label;
   GridBagConstraints gc;
   mouse mousehelp = null;
   boolean helpstate = false;
   private DefaultListCellRenderer listRenderer;
   Color bkgd = new Color(255,255,223);
   Color ttlcolor = new Color(0,0,150);
   DateChoose dc = null;
   long contest_start = 0;
   long contest_end = 0;
   Scorer scorer;
   boolean shouldQuit = false;
   vpnFrame vf = null;
   transferFrame tf = null;

   public configFrame (Scorer cf) {
      this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
      super.setTitle("contest configurator");
      scorer = cf;

      setLayout(new BorderLayout());
      setBackground(bkgd);

      JPanel np = new JPanel(new GridBagLayout());
      np.setBackground(bkgd);
      
      mousehelp = new mouse();
      gc = new GridBagConstraints();
      gc.anchor = GridBagConstraints.WEST;

      //--------- begin row 1 -----------
      r = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
      r.setBackground(bkgd);
      
      //------ start of Scoreboard Parameter Class ---
      q = new JPanel(new BorderLayout());                       // Scoreboard Param Class
      q.setBackground(bkgd);                                    // | parameter 1  ...
      q.add("North", t = new JLabel("Web Server"));             // | +----------+
      t.setFont(new Font("Helvetica", Font.BOLD, 16));          // | |          |
      t.setForeground(ttlcolor);                      // ----------------------
      p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,5));
      p.setBackground(bkgd);
      p.setBorder(BorderFactory.createLineBorder(Color.black));
                                                  // \_____________ border for above
      //-------- Scoreboard Parameters ---------
      b[3] = new JComboBox <String> ();

      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[4] = new JComboBox <String> ());
      g.add("North", parm("Web server", "URL"));
      p.add(g);
      
      JTextField b4editor = (JTextField)b[4].getEditor().getEditorComponent();
      b4editor.setHorizontalAlignment(JTextField.CENTER);

      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[5] = new JComboBox <String> ());
      g.add("North", parm("Scoreboard", "title"));
      p.add(g);

      JTextField b5editor = (JTextField)b[5].getEditor().getEditorComponent();
      b5editor.setHorizontalAlignment(JTextField.CENTER);

      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[6] = new JComboBox <String> ());
      g.add("North", parm("Web server", "directory"));
      p.add(g);

      JTextField b6editor = (JTextField)b[6].getEditor().getEditorComponent();
      b6editor.setHorizontalAlignment(JTextField.CENTER);      

      q.add("Center", p);
      r.add(q);
      //----------- end Scoreboard Parameters -------

      gc.gridx = 0;
      gc.gridy = 0;
      np.add(r, gc);

      //----------- Database Parameters Class ---------
      q = new JPanel(new BorderLayout());
      q.setBackground(bkgd);
      q.add("North", t = new JLabel("Database"));
      t.setFont(new Font("Helvetica", Font.BOLD, 16));
      t.setForeground(ttlcolor);
      p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,5));
      p.setBackground(bkgd);
      p.setBorder(BorderFactory.createLineBorder(Color.black));

      //----------- begin Database Parameters ----------
      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[0] = new JComboBox <String> ());
      g.add("North", parm("Player DB", "state"));
      p.add(g);

      //----------- begin Recover Parameters ----------
      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[7] = new JComboBox <String> ());
      g.add("North", parm("Recover", "database"));
      p.add(g);
      //------------- end Recover Parameters ---------       

      q.add("Center", p);
      r.add(q);
      //------------- end Database Parameters ---------
      gc = new GridBagConstraints();
      gc.gridx = 4;
      gc.gridy = 0;
      np.add(r, gc);

      //---------- Log parameters class ---------
      q = new JPanel(new BorderLayout());                       // Log Parameter Class
      q.setBackground(bkgd);                                    // | parameter 1  ...
      q.add("North", t = new JLabel("Log"));                    // | +----------+
      t.setFont(new Font("Helvetica", Font.BOLD, 16));          // | |          |
      t.setForeground(ttlcolor);                      // ----------------------
      p = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,5));
      p.setBackground(bkgd);
      p.setBorder(BorderFactory.createLineBorder(Color.black)); //
                                                  // \_____________ border for above
      //-------- begin Log Parameters ------------
      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[1] = new JComboBox <String> ());
      g.add("North", parm("Scorer", "log file"));
      p.add(g);

      g = new JPanel(new BorderLayout());
      g.setBackground(bkgd);
      g.add("Center", b[2] = new JComboBox <String> ());
      g.add("North", parm("Logging", "option"));
      p.add(g);

      q.add("Center", p);
      r.add(q);
      //----------- end Log Parameters -------------
      gc = new GridBagConstraints();
      gc.gridx = 6;
      gc.gridy = 0;
      gc.gridwidth = GridBagConstraints.REMAINDER;      
      np.add(r, gc);
      //----------- end row 1 -----------------------

      //---------- begin the title ----------
      JPanel line = new JPanel(new GridLayout(1,1));
      line.setBackground(bkgd);
      
      line.add(title = new JLabel("The Configurator",JLabel.CENTER));
      title.setFont(new Font("Helvetica", Font.BOLD, 24));
      title.setForeground(ttlcolor);
      //---------- end the title ------------

      JPanel nq = new JPanel(new BorderLayout());
      nq.setBackground(bkgd);

      JPanel t = new JPanel(new FlowLayout());
      t.setBackground(bkgd);
      
      t.add(dc = new DateChoose(this));
      t.add(new JLabel(" "));
      
      t.add(vf = new vpnFrame(this));

      JPanel u = new JPanel(new FlowLayout());
      u.setBackground(bkgd);

      u.add(tf = new transferFrame(this));

      u.add(new JLabel("        "));

      //--------- begin controls ----------
      JPanel z = new JPanel(new BorderLayout());
      z.setBackground(bkgd);

      JPanel y = new JPanel(new BorderLayout());
      y.setBackground(bkgd);

      JPanel w = new JPanel(new BorderLayout());
      w.setBackground(bkgd);
      
      JPanel x = new JPanel(new FlowLayout());
      x.setBackground(bkgd);

      x.add(new JLabel("     msgs: ",JLabel.RIGHT));
      x.add(msgs = new JTextField(43));
      msgs.setFont(new Font("Helvetica", Font.PLAIN, 18));
      msgs.setEditable(false);
      x.add(new JLabel("     "));
      w.add("Center", x);
      w.add("South", label = new JLabel("  "));
      label.setFont(new Font("Helvetica", Font.PLAIN, 8));
      w.add("North", label = new JLabel("  "));
      label.setFont(new Font("Helvetica", Font.PLAIN, 8));
      
      y.add("North", w);

      x = new JPanel(new FlowLayout());
      x.setBackground(bkgd);

      x.add(done = new JButton("Send Files and Quit"));
      done.addActionListener(this);
      done.addMouseListener(this);
      x.add(new JLabel("     "));      
      x.add(cancel = new JButton("Quit, Do Not Send Files"));
      cancel.addActionListener(this);
      cancel.addMouseListener(this);
      x.add(new JLabel("     "));
      x.add(leave = new JButton("Cancel"));
      leave.addActionListener(this);
      leave.addMouseListener(this);
      x.add(new JLabel("     "));
      x.add(help = new JButton("Show Help"));
      help.addActionListener(this);
      y.add("Center", x);
      y.add("South", label = new JLabel("  "));
      label.setFont(new Font("Helvetica", Font.PLAIN, 8));
      y.setBorder(BorderFactory.createLineBorder(Color.black));      

      z.add("Center", y);
      z.add("North", label = new JLabel("Controls",JLabel.LEFT));
      label.setFont(new Font("Helvetica", Font.BOLD, 16));
      label.setForeground(new Color(0,0,150));
      z.add("South", new JLabel("  "));

      u.add(z);
      
      //----------- end controls ------------

      nq.add("North", np);
      nq.add("Center", t);
      nq.add("South", u);
      
      add("North", line);
      add("Center", r);
      add("South", nq);

      // game parameters
      b[0].addItem("Static");
      b[0].addItem("Dynamic");
      b[0].setSelectedIndex(scorer.dynamic);
      b[1].addItem(scorer.logfile);
      b[1].setSelectedIndex(0);
      b[2].addItem("1");
      b[2].addItem("2");
      b[2].addItem("3");
      b[2].addItem("4");
      b[2].setSelectedIndex(scorer.logopt);
      b[3].addItem(scorer.name);
      b[3].setSelectedIndex(0);
      b[4].addItem(scorer.url);
      b[4].setSelectedIndex(0);
      b[4].setEditable(true);
      b[5].addItem(scorer.ttl);
      b[5].setSelectedIndex(0);
      b[5].setEditable(true);
      b[6].addItem(scorer.fileloc);
      b[6].setSelectedIndex(0);
      b[6].setEditable(true);
      b[7].addItem("Yes");
      b[7].addItem("No");
      b[7].setSelectedIndex(scorer.recover);

      listRenderer = new DefaultListCellRenderer();
      listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
      for (int i=0 ; i < 51 ; i++) {
         if (b[i] != null) {
            b[i].addMouseListener(this);         
            b[i].setRenderer(listRenderer);
         }
      }
      
      c[0] = "PLAYER_DATABASE_STATE";
      c[1] = "LOG_FILE";
      c[2] = "LOGGING_OPTION";
      c[3] = "SCORECARD_FILE";
      c[4] = "WEB_URL";
      c[5] = "SCORECARD_TITLE";
      c[6] = "WEB_DIRECTORY";
      c[7] = "RECOVERY_STATE";

      setSize(1200,600);
      setVisible(true);
      //      setResizable(false);
   }

   public void setDates () {
      if (dc != null) {
         msgs.setText(dc.value());
         contest_start = dc.start_time();
         contest_end = dc.end_time();
      }
   }

   public void actionPerformed (ActionEvent evt) {
      if (evt.getSource() == leave) {
         mousehelp.exit();
         scorer.cf = null;
         dispose();
      } else if (evt.getSource() == cancel) {
         mousehelp.exit();
         scorer.url = (String)b[4].getSelectedItem();
         scorer.name = (String)b[3].getSelectedItem();
         scorer.fileloc = (String)b[6].getSelectedItem();
         scorer.ttl = (String)b[5].getSelectedItem();
         scorer.logopt = (int)b[2].getSelectedIndex();
         scorer.recover = (int)b[7].getSelectedIndex();
         scorer.dynamic = (int)b[0].getSelectedIndex();
         scorer.cf = null;
         createParms();
         dispose();
      } else if (evt.getSource() == done) {
         mousehelp.exit();
         scorer.url = (String)b[4].getSelectedItem();
         scorer.name = (String)b[3].getSelectedItem();
         scorer.fileloc = (String)b[6].getSelectedItem();
         scorer.ttl = (String)b[5].getSelectedItem();
         scorer.logopt = (int)b[2].getSelectedIndex();
         scorer.recover = (int)b[7].getSelectedIndex();
         scorer.dynamic = (int)b[0].getSelectedIndex();
         scorer.cf = null;
         createParms();
         if (tf.tarAndSendFiles(tf.willSend)) dispose();
      } else if (evt.getSource() == help) {
         if (helpstate == false) {
            b[5].setEditable(false);
            b[4].setEditable(false);
            b[6].setEditable(false);
            helpstate = true;
            help.setText(" Hide Help ");
         } else {
            mousehelp.exit();
            b[5].setEditable(true);
            b[4].setEditable(true);
            b[6].setEditable(true);
            helpstate = false;
            help.setText(" Show Help ");
         }
      } 
   }

   public String yesNoConvert (Object parm) {
      String in = ((String)parm).toUpperCase();
      if (in.trim().toUpperCase().equals("YES")) return "1";
      else if (in.trim().toUpperCase().equals("NO")) return "0";
      else if (in.trim().toUpperCase().equals("STATIC")) return "0";
      else if (in.trim().toUpperCase().equals("DYNAMIC")) return "1";
      else if (in.trim().toUpperCase().equals("NEVER")) return "0";
      else if (in.trim().toUpperCase().equals("ALLOWED")) return "1";
      else return in.toUpperCase();
   }

   public void createParms () {
      msgs.setText("");
       FileOutputStream fos = null;
      try {
         fos = new FileOutputStream("../config/Parameters.txt");
         PrintWriter pw = new PrintWriter(fos, true);
         pw.println("//Database Parameters");
         pw.println(c[0]+" "+yesNoConvert(b[0].getSelectedItem()));
         pw.println("");         
         pw.println("//Recover Parameters");
         pw.println(c[7]+" "+yesNoConvert(b[7].getSelectedItem()));
         pw.println("");
         pw.println("//Logging Parameters");
         pw.println(c[1]+" "+b[1].getSelectedItem());
         pw.println(c[2]+" "+b[2].getSelectedIndex());
         pw.println("");
         pw.println("//Scorecard Parameters");
         pw.println(c[3]+" "+b[3].getSelectedItem());
         pw.println(c[4]+" "+addSlash((String)b[4].getSelectedItem()));
         pw.println(c[5]+" "+b[5].getSelectedItem());
         pw.println(c[6]+" "+addSlash((String)b[6].getSelectedItem()));
         pw.println("");
         pw.println("//Extra");
         pw.println("START_TIME_UNIX "+contest_start);
         pw.println("START_TIME_CONV "+dc.starting+" "+dc.timez);
         pw.println("END_TIME_UNIX "+contest_end);
         pw.println("END_IIME_CONV "+dc.ending+" "+dc.timez);
         fos.close();
         
         scorer.toLog("Parameters.txt created", 0);  
      } catch (Exception e) { }
   }

   public void runIt () {
      msgs.setText("");
      FileOutputStream fos = null;
      FileOutputStream fps = null;
      try {
         new File("../Parameters").mkdir();
         new File("../Contestants").mkdir();
         scorer.toLog("Created Parameters and Contestants directories", 0);
         fos = new FileOutputStream("../Parameters/Parameters.txt");
         PrintWriter pw = new PrintWriter(fos, true);
         String command = "chmod -R go-w ../Parameters ../Contestants";
         try {
            Runtime.getRuntime().exec(command);
         } catch (Exception e) { e.printStackTrace(); }
         
         pw.println("//Database Parameters");
         pw.println(c[0]+" "+yesNoConvert(b[0].getSelectedItem()));
         pw.println("");         
         pw.println("//Recover Parameters");
         pw.println(c[7]+" "+yesNoConvert(b[7].getSelectedItem()));
         pw.println("");
         pw.println("//Logging Parameters");
         pw.println(c[1]+" "+b[1].getSelectedItem());
         pw.println(c[2]+" "+b[2].getSelectedIndex());
         pw.println("");
         pw.println("//Scorecard Parameters");
         pw.println(c[3]+" "+b[3].getSelectedItem());
         pw.println(c[4]+" "+addSlash((String)b[4].getSelectedItem()));
         pw.println(c[5]+" "+b[5].getSelectedItem());
         pw.println(c[6]+" "+addSlash((String)b[6].getSelectedItem()));
         pw.println("");
         pw.println("//Extra");
         pw.println("START_TIME_UNIX "+contest_start);
         pw.println("START_TIME_CONV "+dc.starting+" "+dc.timez);
         pw.println("END_TIME_UNIX "+contest_end);
         pw.println("END_IIME_CONV "+dc.ending+" "+dc.timez);
         fos.close();
         scorer.toLog("Parameters.txt created", 0);
      } catch (Exception e) { }

      FileInputStream fis = null;
      FileOutputStream foa = null;
      String str;
      int serial = 0;
      
      vf.start.setEnabled(false);
      vf.save.setEnabled(false);
      cancel.setEnabled(false);
      done.setEnabled(false);
      
      scorer.toLog("Reading "+GameParameters.PLAYER_DB_FILE+" to make and populate"+
                    "player directories", 0);
      try {
         fis = new FileInputStream("../config/"+GameParameters.PLAYER_DB_FILE); 
         BufferedReader br = new BufferedReader(new InputStreamReader(fis));
         vpnfos = new FileOutputStream("../contest/vpnKeyIds.txt");
         vpnKeyIds = new PrintWriter(vpnfos, true);
         int ip_count = 100;
         int key_count = 0;
         int usr_count = 0;
         // Note: assume port is always a number > 1024 and server is always either
         // a number < 10 or an ip address like 10.8.0.56.  This assurance is due
         // to sanity check code in the playerFrame class.   <--- JVF

         while ((str = br.readLine()) != null) {
            StringTokenizer t = new StringTokenizer(str," \t");
            long score = (long)Integer.parseInt(t.nextToken());
            int port = Integer.parseInt(t.nextToken());
            String inet = t.nextToken();
            String email = t.nextToken();
            StringTokenizer q = new StringTokenizer(inet,".");
            byte[]  ipb = new byte[4];
            ipb[0] = (byte)Integer.parseInt(q.nextToken());
            ipb[1] = (byte)Integer.parseInt(q.nextToken());
            ipb[2] = (byte)Integer.parseInt(q.nextToken());
            ipb[3] = (byte)Integer.parseInt(q.nextToken());
            InetAddress ipadd = InetAddress.getByAddress(ipb);
            String server = ipadd.toString().substring(1);
            String user = t.nextToken();
            while (t.hasMoreTokens()) user += "_"+t.nextToken();

            try {
               key_count = Integer.parseInt(server);
            } catch (NumberFormatException e) {
               key_count = 0;  // server is an ip address
            }
            if (key_count == 0) {
               (new PlantKeys(this, user, server, email)).buildIt();
            } else {
               for (int i=0 ; i < key_count ; i++) {
                  String ip = "10.8.0."+(ip_count++);
                  (new PlantKeys(this, user, ip, email)).buildIt();
               }
            }
         }
         fis.close();
	 try {
	    String command = "chmod -R go-w ../Contestants";
	    Runtime.getRuntime().exec(command);
	 } catch (Exception e) { e.printStackTrace(); }
	      
         scorer.toLog("Finished reading "+GameParameters.PLAYER_DB_FILE+" successfully", 0);
      } catch (Exception e) {
         msgs.setText("Could not read "+GameParameters.PLAYER_DB_FILE);
         scorer.toLog("Reading "+GameParameters.PLAYER_DB_FILE+" is unsuccessful", 0);
      }
      
      vf.start.setEnabled(true);
      vf.save.setEnabled(true);
      cancel.setEnabled(true);
      done.setEnabled(true);

      try {
         if (vpnfos != null) vpnfos.close();
      } catch (Exception e) { }
      try {
         if (vpnKeyIds != null) vpnKeyIds.close();
      } catch (Exception e) { }
      vpnfos = null;
      vpnKeyIds = null;
   }

   public void mouseEntered (MouseEvent evt) {
      if (!helpstate) return;
      for (int i=0 ; i < 51 ; i++) if (evt.getSource() == b[i]) mousehelp.xlate(i);
      if (evt.getSource() == done) mousehelp.xlate(12);
      if (evt.getSource() == cancel) mousehelp.xlate(13);
   }
   
   public void mouseExited (MouseEvent evt) { }
   public void mouseClicked (MouseEvent evt) { mousehelp.exit(); }
   public void mousePressed (MouseEvent evt) { }
   public void mouseReleased (MouseEvent evt) { }

   String addSlash (String path) {
      if (path.endsWith("/")) return path;
      else return path +"/";
   }

   JPanel parm (String arg1, String arg2) {
      s = new JPanel(new GridLayout(2,1));
      s.setBackground(bkgd);
      s.add(new JLabel(arg1));
      s.add(new JLabel(arg2));
      return s;
   }
}

class PlantKeys {
   configFrame config = null;
   String contestant, server, email;
   
   public PlantKeys (configFrame cf, String con, String svr, String em) {
      config = cf;
      contestant = con;
      server = svr;
      email = em;
   }
   
   public void buildIt () {
      BigInteger cert = null;
      String contdir = "../Contestants/"+contestant;

      long cur_time = Instant.now().getEpochSecond();

      config.scorer.toLog("Making files for "+contestant, 0);

      try {
         new File(contdir).mkdir();
         config.vpnKeyIds.println(contestant);
         FileOutputStream fos = new FileOutputStream(contdir+"/Parms");
         PrintWriter out = new PrintWriter(fos, true);
         out.println("# Start Time");
         out.println(String.valueOf(config.contest_start));  // admin supplies start_time
         if (config.dc != null) out.println(config.dc.starting+" "+config.dc.timez);
         out.println(" ");
         out.println("# End Time");
         out.println(String.valueOf(config.contest_end));    // admin supplies end_time
         if (config.dc != null) out.println(config.dc.ending+" "+config.dc.timez);
         out.println(" ");         
         if (((String)config.b[0].getSelectedItem()).equals("Static")) {
            if (server.toUpperCase().equals("EDITABLE"))
               out.println("# Client Location editable");
            else
               out.println("# Client Location");
            out.println(server);
         } else {
            out.println("# Client Location editable");
            out.println(server);
         }
         out.flush();
         out.close();
      } catch (Exception e) {
         config.msgs.setText("Can't make keys file");
      }
      
      config.scorer.toLog("Files for "+contestant+" completed except for email.txt", 0);
      
      try {
         FileOutputStream fos = new FileOutputStream(contdir+"/email.txt");
         PrintWriter out = new PrintWriter(fos, true);
         out.println(email);
         out.flush();
         out.close();
         config.scorer.toLog("File email.txt for "+contestant+" completed", 0);
      } catch (Exception e) {
         config.msgs.setText("Can't make email file");
         config.scorer.toLog("Making email.txt for "+contestant+" failed", 0);
      }
   }
}
