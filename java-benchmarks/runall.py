import subprocess
import sys

benches = ['Fib', 'Tabulate', 'MapMut', 'Filter',
'ScanImmut', 'Reduce', 'MatrixMultiply', 'Integrate', 'ConcHash',
'Histogram', 'Sort']
tenm = 10000000
sizes = [42, tenm, tenm, tenm, tenm, tenm, 2048, 1, tenm, tenm, tenm]

# benches = [sys.argv[1]]
# sizes = [int(sys.argv[2])]
# benches = ['MatrixMultiply', 'Integrate', 'ConcHash', 'Histogram', 'Sort']
# sizes = [2048, 1, tenm, tenm, tenm]
# benches = ['Tabulate', 'MapMut']
# sizes = [tenm, tenm]
benches = ['ScanImmut']
sizes = [tenm]

for (b, s) in zip(benches, sizes):
    subprocess.check_output(['./time_runner.py', b, str(s), '1and72'])
    subprocess.check_output(['./time_runner.py', b, str(s), 'space'])
    # subprocess.check_output(['./time_runner.py', b, str(s), 'speedup'])
