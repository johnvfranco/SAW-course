# .bashrc

# User specific aliases and functions

# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

#
ulimit -c 0
#
alias ooffice='libreoffice'
alias jed='env TERM=xterm-256color emacs -nw'
alias ev='evince'
alias ls='ls --color=yes -F -X'
alias mv='mv -i'
alias cp='cp -i'
alias df='df -k -T --type=ext3 --type=ext4 --type=iso9660 --type=vfat --type=fuseblk'
alias rm='rm -i'
alias ghci='ghci -XParallelListComp -XTypeSynonymInstances'
alias ghc='ghc -XParallelListComp -XTypeSynonymInstances'
#
alias more='less'
#
export PLATFORM=lin
#
eval `dircolors /home/franco/.dir_colors`
export LS_COLORS="no=00:fi=00:di=00;34:ln=04;35:pi=40;33:so=00;35:bd=40;33;01:cd=40;33;01:or=01;05;33;41:mi=01;05;33;41:ex=00;31:*.aux=00;35:*.AUX=00;35:*.cmd=00;31:*.exe=00;31:*.EXE=00;31:*.com=00;31:*.COM=00;31:*.btm=00;31:*.bat=00;31:*.BAT=00;31:*.tar=00;35:*.TAR=00;35:*.tgz=00;35:*.arj=00;35:*.taz=00;35:*.lzh=00;35:*.pdf=00;31:*.PDF=00;31:*.zip=00;35:*.ZIP=00;35:*.z=00;35:*.Z=00;35:*.gz=00;35:*.GZ=00;35:*.bz2=00;35:*.BZ2=00;35:*.jpg=00;35:*.JPG=00;35:*.jpeg=00;35:*.JPEG=00;35:*.gif=00;35:*.GIF=00;35:*.bmp=00;35:*.BMP=00;35:*.xbm=00;35:*.xpm=00;35:*.ps=00;32:*.PS=00;32:*.dvi=00;32:*.DVI=00;32:*.eps=00;32:*.EPS=00;32:*.txt=00;33:*.TXT=00;33:*.log=00;32:*.LOG=00;32:*.tex=00;36:*.TEX=00;36:*.mid=00;36:*.MID=00;36:*.midi=00;36:*.MIDI=00;36:*.wav=00;36:*.WAV=00;36:*.au=00;36:*.AU=00;36:*.mp3=00;36:*.MP3=00;36:*.email=00;35:*.ema=00;35:*.doc=00;36:*.xls=00;36:*.html=00;33:*.htm=00;33:*.HTM=00;33:*.c=00;36:*.cc=00;36:*.cpp=00;36:*.h=00;32:*.o=00;35:*.java=00;36:*.class=00;35:*.ods=00;32:*.cry=0;35:"
#
export PS1='[\u@\h \W]\$ '
## The following worked after editing /var/lib/locales/supported.d/local
## to include the line: en_US.ISO-8859-1 ISO-8859-1
export CVS_RSH='ssh'
#
export PATH=".:~/.cabal/bin:~/.bin:~/.ghcup/bin:/sbin:/usr/bin:/bin:/usr/sbin:$PATH"
