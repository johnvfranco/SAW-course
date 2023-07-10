jdk-lin/lib/modules.zip is used instead of jdk-lin/lib/modules because
jdk-lin/lib/modules is too large for the git repository.  The "run" script
and the "compile" script checks to see if jdk-lin/lib/modules exists and,
if not, unzips jdk-lin/lib/modules.zip.  Once that is done, modules exists
and the unzip is no longer done.