.PHONY: phony

default: all

all: Tabulate Filter Reduce Map FilterCount Scan Fib Integrate MatrixMultiply

Tabulate: *.java
	javac Runner.java Tabulate.java
Filter: *.java
	javac Runner.java Filter.java
FilterCount: *.java
	javac Runner.java FilterCount.java
Reduce: *.java
	javac Runner.java Reduce.java
Scan: *.java
	javac Runner.java Scan.java
Map: *.java
	javac Runner.java Map.java
Fib:
	javac Runner.java Fib.java
Integrate:
	javac Runner.java Integrate.java
MatrixMultiply:
	javac Runner.java MatrixMultiply.java

clean: phony
	rm -f *.class
