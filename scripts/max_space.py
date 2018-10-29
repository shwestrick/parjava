#!/usr/bin/python

import sys
import os

folder = sys.argv[1]
files = os.listdir(folder)
plines = []
procs = ['1', '18', '36', '54', '72']
names = []
header = " & ".join(["Benchmark"] + procs)
print(header)
for f in files:
    pf = open(folder + "/" + f, 'r')
    name = f.split("n=")[0]
    lines = pf.readlines()
    targ1 = lines[22]
    targ2 = lines[58]
    targ3 = lines[58 + 36]
    targ4 = lines[58 + 2*36]
    targ5 = lines[58 + 3*36]
    t1 = str(int((targ1.split(" ")[-1])))
    t18 = str(int((targ2.split(" ")[-1])))
    t36 = str(int((targ3.split(" ")[-1])))
    t54 = str(int((targ4.split(" ")[-1])))
    t72 = str(int((targ5.split(" ")[-1])))
    s = " & ".join([name, t1, t18, t36, t54, t72])
    print(s)
