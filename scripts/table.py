#!/usr/bin/python

import sys
import os

folder = sys.argv[1]
files = os.listdir(folder)

for f in files:
    pf = open(folder + "/" + f, 'r')
    lines = pf.readlines()
    name = f.split('n=')[0]
    targ1 = lines[11]
    targ2 = lines[24]
    time1 = targ1.split(" ")[1]
    gctime1 = targ1.split(" ")[2]
    time72 = targ2.split(" ")[1]
    gctime72 = targ2.split(" ")[2]
    gcperc1 = float(gctime1) / float(time1)
    gcperc72 = float(gctime72) / float(time72)
    speedup = float(time1) / float(time72)
    s = " & ".join([name, time1, str(gcperc1), time72, str(gcperc72), str(speedup)])
    print(s)
