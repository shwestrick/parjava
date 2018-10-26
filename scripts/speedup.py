#!/usr/bin/python

import sys
import os

import matplotlib
matplotlib.use('Agg')
import matplotlib.pyplot as plt

folder = sys.argv[1]
files = os.listdir(folder)
plines = []
procs = [1, 18, 36, 54, 72]
names = []
markers = ['o', 'v', '^', '<', '>', 's', '*', 'd', 'D', '+', 'x']
for (f, m) in zip(files, markers):
    pf = open(folder + "/" + f, 'r')
    name = f.split("n=")[0]
    lines = pf.readlines()
    targ1 = lines[11]
    targ2 = lines[24]
    targ3 = lines[37]
    targ4 = lines[50]
    targ5 = lines[63]

    t1 = float(targ1.split(" ")[1])
    t18 = float(targ2.split(" ")[1])
    t36 = float(targ3.split(" ")[1])
    t54 = float(targ4.split(" ")[1])
    t72 = float(targ5.split(" ")[1])

    s1 = t1
    s18 = t1 / t18
    s36 = t1 / t36
    s54 = t1 / t54
    s72 = t1 / t72
    
    names.append(name)
    speedups = [s1, s18, s36, s54, s72]
    plines.append(plt.plot(procs, speedups, marker=m, linewidth=1))

plt.xlabel('processors')
plt.ylabel('self-speedup')
plt.yticks(procs)
plt.xticks(procs)
plt.gca().grid(axis='both', linestyle='--')
plt.gca().set_axisbelow(True)
print([b[0] for b in plines])
print(names)
plt.legend([b[0] for b in plines], names, loc=2, prop={'size':12})
plt.savefig('self-speedups-java.pdf', bbox_inches='tight')
