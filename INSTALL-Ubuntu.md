install the VM - detailed instructions to follow
Enter the home directory: cd ~/
Note: no changes to any of the files in the virgin install (yet)

sudo apt update
sudo apt upgrade
sudo apt install git # get git to install packages from github.com

# get course content, in SAW-course directory
git clone https://github.com/johnvfranco/SAW-course.git 

# some needed OS tools to install
sudo apt install xterm tcsh emacs-nox curl libffi-dev libgmp-dev
libncurses-dev libncurses5 zlib1g-dev clang

# but make sure the following are already there
-> evince, gnome-terminal, gedit, build-essential, libgmp10, libtinfo5, libffi8ubuntu1 
# use sudo apt install ... for any of these that might be missing

# Install the Haskel compiler
curl --proto '=https' --tlsv1.2 -sSf https://get-ghcup.haskell.org | sh 
  -  Enter to proceed
  -  N (do not add the required path variable)
  -  N (do not install haskell-language-server) 
  -  Y (better integration)
  -  Enter (all the dependencies have been added above)
# Observe ghc-9.4.8 is being installed

# At the long-awaited prompt:
source ~/.ghcup/env
ghcup tui
# Use up,down arrows to move cursor to a line in the table
move the line cursor to recommended HLS and hit Enter
Hit enter to continue
# make sure the recommended application from each section of the table is installed
# (install as above)
# test: run ghci to get prompt then hit control-D

# Install Yosys - for hardware verification - VHDL and RTL
git clone https://github.com/YosysHQ/yosys.git
cd yosys
git submodule update --init
cd ..

# Install SBY to verify properties of RTL
git clone https://github.com/YosysHQ/sby.git
cd sby
git submodule update --init
cd ..

# Install Galois Cryptol
# This allows us to specify algorithms and later use them as golden
# reference to formally verify whether another implementation in
# e.q. cryptol, C, SystemVerilog, or VHDL is equivalent to the
# reference.
git clone https://github.com/GaloisInc/cryptol.git
cd cryptol
git submodule update --init
./cry build

# Install z3
sudo apt install z3

# Run cryptol
./cry run

# Test cryptol - but this may hang and anyway takes a long time
# so you may want to skip this step
./cry test

# Create ~/.bin directory for executables
mkdir ~/.bin

# Install cryptol into ~/.bin directory (current directory is still the cryptol directory)
cabal v2-install --overwrite-policy=always --installdir=$HOME/.bin

# Test cryptol in $HOME/.bin
~/.bin/cryptol

# Build Galois SAW - - Supports advanced FEV between cryptol,
# (System)Verilog and VHDL specifications/implementations, i.e.
# refinement development and formal verification.
# First cd to parent of the cryptol directory
cd ..
# Then prepare the directory containing SAW
git clone https://github.com/GaloisInc/saw-script
cd saw-script
git submodule update --init

# Now the build
./build.sh
cp bin/saw ~/.bin

# Install cvc4
sudo apt install cvc4

# Install yices
sudo add-apt-repository ppa:sri-csl/formal-methods
sudo apt-get update
sudo apt-get install yices2

# Install abc
sudo apt install berkely-abc
pushd
cd /usr/bin
sudo ln -s berkeley-abc abc
popd

# Install boolector
sudo apt install boolector

# Install libcanberra-gtk-module
sudo apt-get install libcanberra-gtk-module

# Install Java
sudo apt install openjdk-17-jre
sudo apt install openjdk-17-jdk

# Add some scripts to the home directory
cd
cp SAW-course/.cshrc .
cp SAW-course/.bashrc .
cp SAW-course/.dir_colors .
