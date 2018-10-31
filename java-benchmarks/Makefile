.PHONY: phony

default: all

all: Tabulate Filter Reduce Map FilterCount Scan Fib Integrate MatrixMultiply MapMut NQueens ScanImmut ConcHash Sort

Tabulate: *.java
	javac Runner.java StrGen.java Tabulate.java
Filter: *.java
	javac Runner.java StrGen.java Filter.java
FilterCount: *.java
	javac Runner.java StrGen.java FilterCount.java
Reduce: *.java
	javac Runner.java StrGen.java Reduce.java
Scan: *.java
	javac Runner.java StrGen.java Scan.java
ScanImmut: *.java
	javac Runner.java StrGen.java ScanImmut.java
ConcHash: *.java
	javac Runner.java StrGen.java ConcHash.java
Sort: *.java
	javac Runner.java StrGen.java Sort.java
Map: *.java
	javac Runner.java StrGen.java Map.java
NQueens: *.java
	javac Runner.java StrGen.java NQueens.java
MapMut: *.java
	javac Runner.java StrGen.java MapMut.java
Fib:
	javac Runner.java StrGen.java Fib.java
Integrate:
	javac Runner.java StrGen.java Integrate.java
MatrixMultiply:
	javac Runner.java StrGen.java MatrixMultiply.java

clean: phony
	rm -f *.class
