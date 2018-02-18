#!/bin/sh

javac *.java
for prog in TwoSum TwoSumFast ThreeSum ThreeSumFast; do echo ===== Using $prog.java =====; for txtfile in 1Kints 2Kints 4Kints 8Kints 16Kints 32Kints; do time java -classpath . $prog $txtfile.txt; done; done

