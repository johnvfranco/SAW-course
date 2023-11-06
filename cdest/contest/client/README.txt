If OpenVPN is used, each team Player gets a copy of client.conf,
a copy of ca.crt, a copy of run.client and a copy of stop.client,
plus one each of unique clientX.key, and clientX.crt files.  For
all Players regardless of whether OpenVPN is used an email.txt file,
which is used by the Contest Administrator to distribute these files,
plus a Parms file, which contains start and stop times and dates,
and IP address which the Player must set statically, are included.

Files ca.crt, clientX.key, clientX.crt are in a subdirectory named 'keys'.
Files client.conf, run.client, stop.client, run.player, stop.player,
email.txt, and this README.txt and the keys subdirectory are in a
directory named <player-name> (ex: "Peter_Smith") where <player-name>
is taken from the player property list which is developed .

Some modifications to these files may be necessary as stated below.

Three things to (maybe) change in client.conf
---------------------------------------------
1. currently: remote <some-domain-name> 1194  may need to be changed to

         remote <ip-address-of-server> 1194

     that is, the ip address of the host where 'sudo openvpn server.conf'
     is run.  If the Contest Administrator correctly entered this address
     in the configuration tool the correct <ip-address-of-server> is
     already in client.conf.  The address of the host behind a firewall
     must be used if the host address inside an organization's perimeter
     is different from the host address as seen from outside the
     perimeter.

2. currently: ;socks-proxy 127.0.0.1 8080 may need to be changed to
     socks-proxy 127.0.0.1 8080 if a socks proxy is used.  If this is
     done make sure the line
     
         remote <ip-address-of-openvpn-server> 1194

     is used.  Making this change requires a change in run.client as
     below.  A socks proxy is likely needed when a Client tries to
     join a contest VPN from outside an organization's security
     perimeter while the OpenVPN server is inside the perimeter.

3. currently: there is a section that looks like this:

     ca keys/ca.crt
     cert keys/clientX.crt
     key keys/clientX.key

   where X is a number from 100 to 249.  Check that clientX.crt and
   clientX.key match the names in the keys subdirectory.  If not,
   the Contest Administrator needs to be contacted.

Two things to consider in run.client
------------------------------------
1. The line

     ssh -N -f -T -D 8080 visitor@helios.ececs.uc.edu

   makes a socks proxy connection to be used from outside the UC
   perimeter.  This line is likely commented (it is assumed no proxy
   is needed).  If a socks proxy is needed and is not forbidden then
   just uncomment that line by removing #.  Also uncomment the
   following 'sleep 1'.  If a change is made here then a corresponding
   change in run.client, as noted above, must be made.  You will
   have to find a socks proxy to allow entry to your organization's
   server and replace visitor@helios.ececs.uc.edu with that.

2. A running openvpn process may interfere with the openvpn process that
   run.client will spawn.  If that happens, and it is not detrimental to do
   so, uncomment the 'sudo killall openvpn' line and the following 'sleep 1'
   line to kill those running openvpn processes before running your openvpn
   process.

About the 'Parms' file
----------------------
The Parms file contains all the information that is needed by a Client to
access a contest.  This includes contest start time and end time in Unix
seconds and in human readable form, plus the ip address that the Player
will statically set. 

To Join a contest
---------------------
A contest can be joined at any time that the contest network is available.
This is usually done well before the actual contest scoring happens.

