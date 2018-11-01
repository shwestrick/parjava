import subprocess

benches = ['Fib', 'Tabulate', 'MapMut', 'Filter',
'ScanImmut', 'Reduce', 'MatrixMultiply', 'Integrate', 'ConcHash',
'Histogram', 'Sort']
tenm = 10000000
sizes = [42, tenm, tenm, tenm, tenm, tenm, 2048, 1, tenm, tenm, tenm]

for (b, s) in zip(benches, sizes):
    subprocess.check_output(['./time_runner.py', b, str(s), '1and72'])
    subprocess.check_output(['./time_runner.py', b, str(s), 'space'])
    subprocess.check_output(['./time_runner.py', b, str(s), 'speedup'])