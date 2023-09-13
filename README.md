jdk-lin/lib/modules.zip is used instead of jdk-lin/lib/modules because
jdk-lin/lib/modules is too large for the git repository.  The "run" script
and the "compile" script checks to see if jdk-lin/lib/modules exists and,
if not, unzips jdk-lin/lib/modules.zip.  Once that is done, modules exists
and the unzip is no longer done.

If you are running this application on a high definition monitor and the
windows and fonts are uncomfortably small, edit the run script and change
the line

   jdk-lin/bin/java -Dsun.java2d.uiScale=1.0 -jar game.jar

to

   jdk-lin/bin/java -Dsun.java2d.uiScale=2.0 -jar game.jar

or

   jdk-lin/bin/java -Dsun.java2d.uiScale=3.0 -jar game.jar (for really hi dpi)

and then invoke run.  Doing this will enlarge the Java windows and Dialogs
proportionately, including the File Chooser.  This will not affect the size
of the Cryptol application, the Text Editor, the Terminal, or the pdf viewer
(evince) windows.  To enlarge the Cryptol window press the Contol key
simultaneouly with the Right mouse button to get the VT Fonts menu and scroll
down with the mouse wheel to select 'Large', 'Huge', or 'Enormous'.  Do the
same for the terminal although the font selection there is not as good as
for the Cryptol window (not known why at the moment).  If the text editor
is the default (gnome-terminal running emacs), drop the view menu and select
'Zoom In' or 'Zoom Out' as needed.  If the text editor is gedit you have to
install the text size plugin like this:

   sudo apt-get install -y gedit-plugin-text-size

then click the 3 horizontal bars atop each other icon and select preferences,
then select plugins then scroll down and select the text size plugin.  To
change content size hold the control key while scrolling with the mouse
thumbwheel.  Other text editors have their own ways to control content
size.  To control the size of the pdf viewer content first adjust the size
of the pdf viewer window by grabbing a corner with the mouse and dragging
it until you have the size you want and second, drop the size menu (last
on the left in the rightmost group of widgets) and select a number repeatedly
until you are happy with the content size.  You can set that size as the
default when the viewer is showing something by clicking the menu icon that
looks like three horizontal bars atop each other and choosing 'Save Current
Settings as Default'.
