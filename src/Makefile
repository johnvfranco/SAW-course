all:
	rm -f *.class
	rm -f game.jar
	../jdk-lin/bin/javac a.java
	../jdk-lin/bin/jar cfm game.jar .perms *.class sounds images \
		lab1 lab2 lab2A lab2B lab2C lab2D lab2E lab3 lab3A lab3B lab3C \
		lab3D lab3E lab4 lab5 lab6 linx
	find . -name "*.class" -exec rm {} \; 
	mv game.jar ..
