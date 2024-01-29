# .bashrc

# User specific aliases and functions

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

#
ulimit -c 0
#
alias ok='okular'
alias ooffice='libreoffice'
alias jed='env TERM=xterm-256color emacs -nw'
alias sawit='saw -j /usr/java/jdk1.8.0_121/jre/lib/rt.jar'
alias ev='evince'
alias xpdf='evince'
alias win='startx'
#alias jed='jed -n -l /home/franco/.jedrc.old'
alias muttuc='mutt -F /home/franco/.muttrcuc'
alias muttgm='mutt -F /home/franco/.muttrcgm'
alias acroread='acroread -geometry 1200x768+50+0 -openInNewInstance'
alias ls='ls --color=yes -F -X'
alias mv='mv -i'
alias cp='cp -i'
alias df='df -k -T --type=ext3 --type=ext4 --type=iso9660 --type=vfat --type=fuseblk'
alias rm='rm -i'
alias lpr='lpr -h'
alias lpq='lpq -a'
alias ghci='ghci -XParallelListComp -XTypeSynonymInstances'
alias ghc='ghc -XParallelListComp -XTypeSynonymInstances'
alias nvcc='nvcc -lglut -lGL -lpthread'
alias iwconfig='sudo iwconfig'
alias javac='javac -Xlint:unchecked'
alias kuickshow='kuickshow /home/franco'
#
alias more='less'
alias ssh='ssh -AXY'
alias pstree='pstree -G'
alias cvs='cvs -q'
alias gv='gv --scale=2 --media=letter'
alias xanim='xanim +Av80'
alias mplayer='mplayer -ao alsa'
alias midi='/usr/bin/timidity'
alias timidity='timidity -i g'
alias octave='octave -q'
#
export PLATFORM=lin
export XILINX=/home/franco/.Xilinx/10.1/ISE
#
eval `dircolors /home/franco/.dir_colors`
export LS_COLORS="no=00:fi=00:di=00;34:ln=04;35:pi=40;33:so=00;35:bd=40;33;01:cd=40;33;01:or=01;05;33;41:mi=01;05;33;41:ex=00;31:*.aux=00;35:*.AUX=00;35:*.cmd=00;31:*.exe=00;31:*.EXE=00;31:*.com=00;31:*.COM=00;31:*.btm=00;31:*.bat=00;31:*.BAT=00;31:*.tar=00;35:*.TAR=00;35:*.tgz=00;35:*.arj=00;35:*.taz=00;35:*.lzh=00;35:*.pdf=00;31:*.PDF=00;31:*.zip=00;35:*.ZIP=00;35:*.z=00;35:*.Z=00;35:*.gz=00;35:*.GZ=00;35:*.bz2=00;35:*.BZ2=00;35:*.jpg=00;35:*.JPG=00;35:*.jpeg=00;35:*.JPEG=00;35:*.gif=00;35:*.GIF=00;35:*.bmp=00;35:*.BMP=00;35:*.xbm=00;35:*.xpm=00;35:*.ps=00;32:*.PS=00;32:*.dvi=00;32:*.DVI=00;32:*.eps=00;32:*.EPS=00;32:*.txt=00;33:*.TXT=00;33:*.log=00;32:*.LOG=00;32:*.tex=00;36:*.TEX=00;36:*.mid=00;36:*.MID=00;36:*.midi=00;36:*.MIDI=00;36:*.wav=00;36:*.WAV=00;36:*.au=00;36:*.AU=00;36:*.mp3=00;36:*.MP3=00;36:*.email=00;35:*.ema=00;35:*.doc=00;36:*.xls=00;36:*.html=00;33:*.htm=00;33:*.HTM=00;33:*.c=00;36:*.cc=00;36:*.cpp=00;36:*.h=00;32:*.o=00;35:*.java=00;36:*.class=00;35:*.ods=00;32:*.cry=0;35:"
#
export PS1='[\u@\h \W]\$ '
## The following worked after editing /var/lib/locales/supported.d/local
## to include the line: en_US.ISO-8859-1 ISO-8859-1
export CVS_RSH='ssh'
export ACRO_CRASHLOG=1  # idiotic need to prevent a crash - hope temporary
#
export PATH=".:/usr/local/bin:/usr/java/jdk-13.0.2/bin:/usr/local/yices/bin:/usr/local/saw-script/bin:/home/franco/.cabal/bin:/usr/local/z3/bin:/usr/local/clang+llvm-9.0.0/bin:/usr/local/zeek/bin:/home/Backup/home:/home/Backup/html:/home/Backup/mail:/home/Backup/external:/usr/local/java/bin:~/.android-sdk-linux/Sdk/platform-tools:~/.android-sdk-linux/Sdk/tools:~/.android-studio/bin:/usr/local/z3-4.5.1/bin:/usr/local/racket/bin:/usr/local/saw/bin:/usr/local/bro/bin:/home/franco/.bin:/opt/ghc/7.8.4/bin:/usr/NX/bin:/usr/local/xaralx/bin:/usr/local/ant/bin:/home/franco/.Xilinx/10.1/ISE/bin/lin:/usr/local/smv/bin:/usr/local/cuda/bin:/usr/local/java/bin:/sbin:/usr/local/sbin:$PATH"
export LD_LIBRARY_PATH="/usr/lib/aplus-fsf/4.22.4:/usr/lib/atlas:/usr/lib/mysql:/usr/lib/qt-3.3/lib:/usr/lib/R/lib:/usr/lib/root:/usr/lib/wine:/usr/lib/xulrunner-1.9.2:/lib:/usr/lib:/home/franco/.Xilinx/10.1/ISE/lib/lin"
export LD_LIBRARY_PATH="/usr/lib/aplus-fsf/4.22.4:/usr/lib/atlas:/usr/lib/mysql:/usr/lib/qt-3.3/lib:/usr/lib/R/lib:/usr/lib/root:/usr/lib/wine:/usr/lib/xulrunner-1.9.2:/lib:/usr/lib:/home/franco/.Xilinx/10.1/ISE/lib/lin"
#
#if [ ! -e /home/franco/.stoppedem ]; then
#	/usr/local/bin/stopem.sh
#fi
#touch .stoppedem

[ -f "/home/franco/.ghcup/env" ] && source "/home/franco/.ghcup/env" # ghcup-env
