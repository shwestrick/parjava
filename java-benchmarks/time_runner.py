#!/usr/bin/python

import sys
import subprocess

test = sys.argv[1]
n = int(sys.argv[2])

test_type = "1and72"
if len(sys.argv) > 3:
    test_type = sys.argv[3]

if test_type == "speedup":
    f = open(test +'n=' + str(n) +'self.txt', 'w')
    for p in ['1', '18', '36', '54', '72']:
        res = subprocess.check_output(['./run', '-b', test, '-t', p, '-n', str(n)])
        f.write(res)
    f.close()
elif test_type == "space":
    f = open(test +'n=' + str(n) +'space.txt', 'w')
    for p in ['1', '18', '36', '54', '72']:
        res = subprocess.check_output(['time', '-v', './run', '-b', test, '-t', p, '-n', str(n), '-r', '1'], stderr=subprocess.STDOUT)
        f.write(res)
    f.close()
else:
    # run on 1 proc, 72 procs
    f = open(test +'n=' + str(n) +'at1and72.txt', 'w')
    res1 = subprocess.check_output(['./run', '-b', test, '-t', '1', '-n', str(n)])
    res2 = subprocess.check_output(['./run', '-b', test, '-t', '72', '-n', str(n)])
    f.write(res1 + res2)
    f.close()
