all:
	rm -f *.class
	rm -f game.jar
	javac a.java
	jar cfm game.jar .perms *.class sounds images \
		lab1 lab2 lab2A lab2B lab2C lab2D lab2E lab3 lab3A lab3B lab3C \
		lab3D lab3E lab4 lab5 lab5A lab5B lab5C lab5D lab6 cdest intr \
		lab6A lab6B lab6C lab6D lab6E lab7 lab7A lab7B linx common
	find . -name "*.class" -exec rm {} \; 
	mv game.jar ..
