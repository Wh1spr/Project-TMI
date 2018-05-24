#!/usr/bin/env bash

# UITLEG
# Dit bestand maakt 15 files aan in tests/
#   inXY.txt, outXY.txt en consoleOut.txt
# Hierbij is X het nummer van het algoritme, Y het aantal dimensies, en
# consoleOut.txt alle output van de "normale" uitvoeringen
#
# De bestanden voor de verschillende dimensies gebruiken dezelfde punten.
# Dit betekent dat, omdat het simpele N^2 algoritme met zekerheid juist is,
# we out1X.txt kunnen gebruiken om de correctheid van de anderen (manueel) na te kijken.


# Lower dimensions have more points to make it a bit more "fair"
# Running this should take no more than 73 seconds (3 for random point generation and
# before/after routines, 7 * 10 seconds for running the algorithms)

rm -rf tests/
mkdir tests

# path to jar
PATH_JAR="toepMeetk.jar"

# Making random files with 3000 points DIMENSION 2
java -jar $PATH_JAR RANDOM tests/in12.txt norun 1 2 3000
cp tests/in12.txt tests/in22.txt
sed -i.bak "1s/.*/2/" tests/in22.txt # changes alg
cp tests/in12.txt tests/in32.txt
sed -i.bak "1s/.*/3/" tests/in32.txt # changes alg

# Making random files with 3000 points DIMENSION 3
java -jar $PATH_JAR RANDOM tests/in13.txt norun 1 3 3000
cp tests/in13.txt tests/in23.txt
sed -i.bak "1s/.*/2/" tests/in23.txt # changes alg

# Making random files with 3000 points DIMENSION 4
java -jar $PATH_JAR RANDOM tests/in14.txt norun 1 4 3000
cp tests/in14.txt tests/in24.txt
sed -i.bak "1s/.*/2/" tests/in24.txt # changes alg

rm tests/*.bak

ALGS="1 2"
DIMS="2 3 4"
SECS=35

echo " "
echo "Executing all programs, this should take $SECS seconds."
echo "You can find console output @ tests/consoleOut.txt"
echo " "

for NUM in $ALGS
do
  for NUM2 in $DIMS
  do
    java -jar $PATH_JAR tests/in$NUM$NUM2.txt tests/out$NUM$NUM2.txt >> tests/consoleOut.txt
    SECS=$(expr $SECS - 5)
    echo "$SECS seconds left."
    echo "---" >> tests/consoleOut.txt
  done
done

java -jar $PATH_JAR tests/in32.txt tests/out32.txt >> tests/consoleOut.txt

echo " "
echo "Done executing."
