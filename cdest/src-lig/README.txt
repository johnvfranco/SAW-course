This is the source directory of the GUI version of the Scorer for Linux.
This version includes the CDX Control Panel and the Configurator.

To compile sources to a jar archive type 'make' and hit return.
To run the Scorer from this directory use './run' and return.
You can also change directory to 'cdest' and run './cdx-lig.run'

The script 'tar-server' is used by the Configurator to conveniently tar the
contents of the cdest/contest/server directory which is to be sent to the
OpenVPN server maintainer, if OpenVPN is used.

The script 'make-keys' is used by the Configurator to conveniently make
the credentials using 'easyrsa' which is located in the cdest/contest
directory.

Running the Configurator assuming OpenVPN is used
-------------------------------------------------
  1. Start the application
     From directory cdest 

        ./cdx-lig.run

     or from this directory

        ./run
     
  2. In the CDX Control Panel enter the IP address or qualified host
     name of the OpenVPN server in the 'OpenVPN server location' field.
     This will be the address that is known behind a firewall in case
     the server is inside an organization's security perimeter.  The
     IP address of a host as seen from inside the perimeter is
     different from the IP address that the world outside the perimeter
     sees.  The address to enter is the inside address.

     example:  IP address of gauss.ececs.uc.edu inside is 10.52.10.252
               IP address of gauss.ececs.uc.edu outside is 129.137.4.132

     Note: this step is unnecessary if OpenVPN is not used.
     
  3. Create a list of players and monitor with information for joining
     For each Player click the 'Add' button.
     Enter the player name (a directory with that name will be created
     and subsequent files for Player will be put in that directory and
     sent to that Player).  Enter the Player's email address, and IP
     address that will be assigned to the Player.
        
     Note: spaces are allowed in the Player field.
      
     Note: the file 'players.db' is created only if 'Save' is clicked.

  4. Run the Configurator
       Make sure the player database is created as above.
     Click the 'Configure' button in the 'CDX Control Panel'.
     The directory in which the scoreboard and market value card are to be
     made public via a web server can be edited as can the title of the
     scoreboard.  Click 'Show Help' and hover the mouse over a menu to
     get information about that menu.  Set contest start and end dates
     and times using the menus and click 'Save Dates & Times' to record.
     Be sure to choose the appropriate.  Daylight savings time is
     automatically taken care of.  Consider making keys - this is not
     necessarily done for every contest and takes a long time.
     If keys are to be made, choose a number of keys that is greater
     than the number of Players.  You can select up to 150 keys to
     be made.  If 'game-id.txt' does not exist, all the textfields
     of the 'OpenVPN Key & Certificate Maker' section will be blank.
     They must be filled in if keys are to be made.  Edit those fields
     accordingly.  To make keys click 'Make Keys'.
     
     In the 'Prepare Files' section click one of the buttons depending
     on whether OpenVPN is used or not.  Assuming no errors, click
     the button that distributes files (actually creates an archive of
     the files to be sent to Players and the OpenVPN server maintainer
     and sends via mutt in the case of Linux).

     Remember: DO NOT FORGET TO SET THE TIMEZONE!!

     The result of the above operations is the following:
     1. A directory named for every player in the player list is created
        with the following files:
        a. Parms
           this file contains information that should be used by the player
           to make sure the player's Client can connect to the Monitor.
        b. email.txt
           this file contains the email address of the player that is to
           get this directory
        c. in case OpenVPN is used, more files will be added as stated
           below in point 5.
     2. a file 'vpnKeyIds.txt' is created which lists the names of all
        player and monitor directories.  This list is used to distribute
        OpenVPN keys, if OpenVPN is used.

     Note: contest start and end time depend solely on the Contest
     Administrator to choose the proper time and timezone,  Thus,
     the Contest Administrator must be deliberate in choosing times,
     dates, and the timezone.
     
  5. Archive and send needed files to Players and the OpenVPN server maintainer
     Still in the Configurator, click 'Send Files and Quit' or 'Tar
     Files and Quit' button.  The following are added to each Player's
     Contestants directory:
       If OpenVPN is used:
         a. a keys directory containing ca.crt, clientX.crt, clientX.key 
            credentials for connecting to the OpenVPN network
         b. client.conf which is used by OpenVPN to start a connection
         c. run.client which runs OpenVPN on client.conf
         d. stop.client which stops the OpenVPN connection
         e. run.vpn which actually runs the openvpn utility and saves
            process number
         f. README.txt which supplies more information such as how to use a
            socks proxy, if necessary.
       In every case:
         g. Parms which provides start and end dates and times and IP address
         h. email.txt which contains the email address of the Player
     These directories are tarred and the tar files can be sent to the Players
     and OpenVPN maintainer.
      

Information for the recipients of the tar files - what to do with them.
-----------------------------------------------------------------------
  1. untar the file
  2. use the Parms file to know when the Scoring begins and ends and
     to set your computer's static IP address to that specified in
     the Parms file.

Additional Notes
----------------
  1. There is no information in a Parms file that should be edited by
     a Player.

  2. In the Configurator - when clicking a button in the 'Prepare Files'
     section - watch for errors in the message box - a common error
     is that the OpenVPN server location is not set.  This needs to
     be set in the CDX Control Panel.

  3. For a Dynamic competition, say for practice, set start date and
     time to a time before current time and the end time to some
     date and time significantly into the future and start the scoring.
     Then manually stop the scoring when appropriate.

  5. To compile the java source code enter directory src-lig or
     src-lin and run 'make' or directory src-wig or src-win
     and run compile.bat.  The result is 'cdx-contest.jar' in the
     source directory.
  
  6. The following applications are used in the Configurator: mutt, tar,
     openssl, and openvpn.  Linux versions of these that are statically
     linked are used.  Directories containing these are: cdest/contest/bin,
     and cdest/contest/sbin.  Source code that was used to create these
     applications is included in directory cdest/contrib.
  
  7. See players.db.example in directory cdest/contest/examples for
     a sample players.db file
     See Parameters.txt.example in directory cdest/contest/examples
     for a sample Parameters.txt file
     See vpnKeyIds.txt.example in directory cdest/contest/examples
     for a sample vpnKeyIds.txt file
     See Parms.example in directory cdest/contest/examples for a sample
     Parms file
     See game-id.txt.example example in directory cdest/contest/examples
     for a sample game-id.txt file
