Installing SAW-course:
----------------------

Installation instructions for the course:

- in an Ubuntu VM are found in INSTALL-Ubuntu.md
- in a Mac OS X VM are found in INSTALL-MacOSX.md
- in Windows ... TBD?

Following these or similar instructions should make it possible to run
SAW-course on any Linux and MacOSX machine.

Installation Overview:
---------------------
Independent of your machine (X86, ARM, ...) and OS (Linux, MacOSX,
Windows, ...) you will need to install at least Haskell, Cryptol, SAW,
openjdk-17, abc, cvc4, yices, yices-smt2, boolector, z3, potentialy
libcanberra-gtk-module, clang and some supporting applications and
libraries.

Yosys from YosysHQ is needed if you want to FEV whether a cryptol
specification is equivalent to a (System) Verilog circuit as yosys is
used to compile the circuit Verilog into a JSON format that can be
loaded into SAW (examples are forthcoming). 

If you are working with VHDL implementations and are using the open
source version of yosys, you can install and use GHDL to translate the
VHDL to Verilog and then use yosys again to generate the JSON file.
The licensed version of yosys supports both full SystemVerilog and
VHDL.

SBY is only needed if you want to formally verify (temporal logic)
properties (assertions) required to be satisfied by your Verilog
implementation.

Orientation:
-----------
Read intro.pdf

Running:
-------
To run the application enter directory SAW-course and issue command './run.sh'
from, say, xterm (or gnome-terminal). Each lesson has documentation suitable
for that lesson.

The following should be installed on the host: gnome-terminal, xterm,
emacs, evince.  Applications evince and gnome-terminal are likely already
installed.  Evince is the pdf viewer and gnome-terminal is the terminal
and also supports running 'emacs -nw'.  If not, do this in a terminal
running on the host:

   sudo apt install gnome-terminal
	sudo apt install evince

Emacs is the default text editor and is not already installed in most cases.
Do this to install emacs:

   sudo apt install emacs

It may be that xterm is not installed.  Xterm is used to run Cryptol.
To install do this:

   sudo apt install xterm

An alternative to emacs is gedit which surely is already installed on
the host.

Compiling:
---------
To compile the application issue command './compile.sh' from, say, gnome-terminal
in directory SAW-course.  You should not have to compile anything unless you
make a change to some Java code.  Compiling or running gives this output:

  rm -f *.class
  rm -f game.jar
  javac a.java
  jar cfm game.jar .perms *.class sounds images \
          lab1 lab2 lab2A lab2B lab2C lab2D lab2E lab3 lab3A lab3B lab3C \
          lab3D lab3E lab4 lab5 lab5A lab5B lab5C lab5D lab6 cdest intr \
          lab6A lab6B lab6C lab6D lab6E lab7 lab7A lab7B linx common
  find . -name "*.class" -exec rm {} \;
  chmod a+x game.jar
  mv game.jar ..

HiDPI:
-----
If you are running this application on a high definition monitor and the
windows and fonts are uncomfortably small, edit the run 'script' and change
the line

   java -Dsun.java2d.uiScale=1.0 -jar game.jar

to

   java -Dsun.java2d.uiScale=2.0 -jar game.jar

or

   java -Dsun.java2d.uiScale=3.0 -jar game.jar (for really hi dpi)

and then invoke ./run.sh.  Doing this will enlarge the Java windows and Dialogs,
including the File Chooser, proportionally.  This will not affect the size
of the Cryptol application, the Text Editor, the Terminal, or the pdf viewer
(evince) windows.  The following shows how to change sizes for those.

  Cryptol:
  -------
    To enlarge the Cryptol window press the Contol key simultaneouly with the
    Right mouse button to get the VT Fonts menu and scroll down with the mouse
    thumb-wheel to select 'Large', 'Huge', or 'Enormous'.

  Terminal:
  --------
    The terminal is now gnome-terminal.  To enlarge the terminal, and get
	 some good looking fonts as well, drop the terminal's 'View' menu and
	 select 'Zoom In' or 'Zoom Out' as needed.  To keep the large fonts
	 for future invocations drop the terminal's 'Edit' menu, select
	 Preferences, click Custom font, click the font name, which is likely
	 Monospace, and click '+' or '-' until you are happy with the size.
  
  Text Editor:
  -----------
    If the text editor is the default (gnome-terminal running emacs),
	 drop the view menu and select 'Zoom In' or 'Zoom Out' as needed.
	 Remember the font size as for the case of the terminal above.  If
	 the text editor is gedit you have to install the text size plugin
	 like this:
  
       sudo apt-get install -y gedit-plugin-text-size

    then click the 3 horizontal bars atop each other icon and select
	 preferences, then select plugins then scroll down and select the
	 text size plugin.  To change content size hold the control key
	 while scrolling with the mouse thumb-wheel.  Other text editors
	 have their own ways to control content size.

  PDF Viewer (evince):
  -------------------
    To control the size of the pdf viewer content first adjust the
	 size of the pdf viewer window by grabbing a corner with the mouse
	 and dragging it until you have the size you want and second, drop
	 the size menu (last on the left in the rightmost group of widgets)
	 and select a number repeatedly until you are happy with the content
	 size.  You can set that size as the default when the viewer is
	 showing something by clicking the menu icon that looks like three
	 horizontal bars atop each other and choosing 'Save Current Settings
    as Default'.
