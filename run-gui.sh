#!/bin/bash
export PATH=`pwd`:$HOME/.bin:`pwd`/src:`pwd`/bin:$PATH
export CD=`pwd`
cd cdest/src-lig
java -jar cdx-contest.jar 
