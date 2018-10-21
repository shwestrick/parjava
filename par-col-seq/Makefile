.PHONY: phony

default: all

all: Tabulate Filter Reduce Map FilterCount

Tabulate: *.java
	javac Runner.java Tabulate.java
Filter: *.java
	javac Runner.java Filter.java
FilterCount: *.java
	javac Runner.java FilterCount.java
Reduce: *.java
	javac Runner.java Reduce.java
Map: *.java
	javac Runner.java Map.java


clean: phony
	rm -f *.class
