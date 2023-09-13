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

and then invoke run