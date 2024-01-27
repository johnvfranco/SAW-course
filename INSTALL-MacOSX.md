# To use SAW-course we need to install several tools first

## Install the Glasgow Haskel Compiler System:

https://www.haskell.org/ghcup/

source /home/flanders/.ghcup/env
ghcup tui

## Install Yosys - this will allow us to verify (System)Verilog and VHDL (RTL):

git clone https://github.com/YosysHQ/yosys.git
cd yosys
git submodule update --init

### Install Yosys SBY - this will allow us to verify properties about RTL:

git clone https://github.com/YosysHQ/sby.git
cd sby
git submodule update --init

## Install Galois Cryptol

- This allows us to specify algorithms and later use them as golden
  reference to formally verify whether another implementation in
  e.q. cryptol, C, SystemVerilog, or VHDL is equivalent to the
  reference.

### Get a clone and follow the below installation commands

git clone https://github.com/GaloisInc/cryptol.git
cd cryptol
git submodule update --init

### Best way to build cryptol

- If you do not have a $HOME/bin directory in your path now is a good
  time to create the directory and set up the path so you can execute
  the tools in the bin directory from any other directory.

./cry build
./cry run
./cry test
cabal v2-install --overwrite-policy=always --installdir=$HOME/bin

##  Install Galois SAW:

- Supports advanced FEV between cryptol, (System)Verilog and VHDL
  specifications/implementations, i.e. refinement development and
  formal verification

### Get a clone of SAW and follow the installation instructions:

git clone https://github.com/GaloisInc/saw-script
cd saw-script
git submodule update --init

## Helper tools needed:

- The below tools are used by SAW and SAW-course. The java tools in
  openjdk-17 should ensure that the SAW-course graphics, etc. is
  working properly.

### Install abc

git clone https://github.com/berkeley-abc/abc.git
cd abc
git submodule update --init

- Now follow the installation instructions in the README.md file

### Install cvc4

git clone https://github.com/CVC4/CVC4-archived.git
cd CVC4-archived
git submodule update --init

- Now follow the installation instructions in the README.md file

### Install yices and yices-smt2

brew update
brew install yices2

### Install boolector

brew install boolector

### Install z3

brew install z3

### Install libcanberra-gtk-module

#brew install libcanberra-gtk-module

### Install java tools

brew install openjdk@17

#### Now make openjdk-17 java programs preferred:

- You may have to find out where apt installed the tools. You can use
  this command
  
     brew info openjdk@17
  
  to find out how to solve this issue.

- We should now have all tools needed to run SAW-course. If some are
  still missing they can be installed with apt or installed after
  download.

## Finally, get and install the John Franco SAW-course from:

https://github.com/johnvfranco/SAW-course

cd <work-dir>

- Get a clone in your <work-dir>

git clone https://github.com/johnvfranco/SAW-course
cd SAW-course
git submodule update --init

### Make the SAW-course use the newly installed cryptol:

cd <work-dir>/SAW-course/bin
rm -f cryptol-bin
ln -s $HOME/bin/cryptol ./cryptol-bin

cd <work-dir>/SAW-course

- You should now be able to run SAW-course by following the instructions
