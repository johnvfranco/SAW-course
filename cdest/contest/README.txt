This directory contains files used to make and save Player, Scorer, and OpenVPN
credentials if OpenVPN is used.

Files in this directory include:
  1. bash scripts: easyrsa, and workaround
  2. openvpn/easy-rsa files: vars, vars1, vars2, openssl-easyrsa.cnf
  3. openvpn/easy-rsa directories: x509-types, sbin (with statically compiled
     openvpn)
  4. client directory: contains files to be distributed to contestants if
     openvpn is used
  5. server directory: contains files needed to start the openvpn server in
     such a way as to map openvpn keys to contest ipaddresses.  This mapping
     is accomplished by files in subdirectory 'ccd' and by file 'ipp.txt'
     which are created by using the Configurator.
  6. bin directory: contains statically linked versions of mutt, tar, openssl
     as well as scripts that email files, and archive files to be sent to
     Players
  7. Configuration files that are created while running the Configurator and
     may persist across multiple uses including:
     a. Parameters.txt: parameters for the monitor.  This is created using
          the Configurator but a sample is included as
        cdest/contest/examples/Parameters.txt.example
     b. game-id.txt: information file used to fill fields of the dialog box
        that are needed to create openvpn keys.  This is created using the
        Configurator but samples are included in this package as
        cdest/contest/game-id.txt and
        cdest/contest/example/game-id.txt.examples
     c. vpnKeyIds.txt: list of player identities for which openvpn keys will
        be distributed.  This is created using the Configurator.  A sample
        is included as cdest/contest/examples/vpnKeyIds.txt.example.
     d. players.db: the player database is normally created in a source
        directory but an example is provided here as
        cdest/contest/examples/players.db for reference.
     e. Parms: the configuration file that is given to Players is created in
        the Configurator and these files appear in the Contestants directory.
        An example is given here as cdest/contest/examples/Parms.example.
