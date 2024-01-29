install the VM - detailed instructions to follow
Enter the home directory: cd ~/
Note: no changes to any of the files in the virgin install

sudo apt update
sudo apt upgrade
sudo apt install git # get git to install packages
# get course content, in SAW-course directory
git clone https://github.com/johnvfranco/SAW-course.git 

# some OS tools
sudo apt install xterm tcsh emacs-nox curl libffi-dev libgmp-dev
libncurses-dev libncurses5 zlib1g-dev clang

# but make sure the following are already there
- evince, gnome-terminal, gedit, build-essential, libgmp10, libtinfo5, libffi8ubuntu1 
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
move cursor to recommended HLS and hit Enter
Hit enter to continue
# make sure the recommended from each section of the table is installed
# (install as above)
# test: run ghci to get promot then hit control-D

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
git clone https://github.com/GaloisInc/cryptol.git
cd cryptol
git submodule update --init
./cry build

# Install z3
sudo apt install z3

# Run cryptol
./cry run

# Test cryptol
./cry test

# Create ~/.bin directory for executables
mkdir ~/.bin

# Install cryptol into ~/.bin directory (still in cryptol directory)
cabal v2-install --overwrite-policy=always --installdir=$HOME/bin

# Test cryptol in ~/.bin
~/.bin/cryptol

# Build Galois SAW - first cd to parent of cryptol directory
cd ..
# Then prepare the directory containing SAW
git clone https://github.com/GaloisInc/saw-script
cd saw-script
git submodule update --init
# Now the build
./build.sh
sudo cp bin/saw /usr/bin

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

# Find java files
# Note: pushd / changes directory to / so find can search all directories
# What find returns will look like ./usr/lib/... so the actual location
# of the searched for directory is /usr/lib/...
pushd /
sudo find . -iname "java-17-openjdk*" | grep -v proc | grep -v snap
popd

# Example: it may be in /usr/lib/jvm/java-17-openjdk-amd64
# Set links to the java executables - use the above example location
pushd ~/.bin
ln -s /usr/lib/jvm/java-17-openjdk-amd64/bin/j* .
popd

# Add some scripts to the home directory
cd
cp SAW-course/.cshrc .
cp SAW-course/.bashrc .
cp SAW-course/.dir_colors .
