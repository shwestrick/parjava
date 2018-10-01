import java.util.concurrent.*;
import java.util.*;

class Tabulate {

  static private char[] generate(int i) {
    int len = i & 0x7f;
    char[] buffer = new char[len];
    for (int j = 0; j < len; j++) buffer[j] = 'a';
    return buffer;
  }

  static private char[][] tabSerial(int n) {
    char[][] arr = new char[n][];
    for (int i = 0; i < n; i++) {
      arr[i] = generate(i);
    }
    return arr;
  }

  static private char[][] tabulate(int n) {
    char[][] arr = new char[n][];
    ParallelFor.range(0, n, i -> arr[i] = generate(i));
    return arr;
  }

  static final long NPS = (1000L * 1000 * 1000);

  public static void main(String[] args) throws Exception {
    int n = 1000000000;
    int reps = 1;
    int sreps = 0;
    try {
      if (args.length > 0)
        n = Integer.parseInt(args[0]);
      if (args.length > 1)
        reps = Integer.parseInt(args[1]);
      if (args.length > 2)
        sreps = Integer.parseInt(args[2]);
    }
    catch (Exception e) {
      System.out.println("Usage: java Tabulate n reps sreps");
      return;
    }

    ForkJoinPool pool = ForkJoinPool.commonPool();

    for (int r = 0; r < sreps; r++) {
      long start = System.nanoTime();
      char[][] result = tabSerial(n);
      long stop = System.nanoTime();
      double elapsed = (double)(stop - start) / NPS;
      System.out.printf("serial time:  %7.3f\n", elapsed);
    }

    for (int r = 0; r < reps; r++) {
      long start = System.nanoTime();
      char[][] result = tabulate(n);
      long stop = System.nanoTime();
      double elapsed = (double)(stop - start) / NPS;
      System.out.printf("parallel time:  %7.3f\n", elapsed);
    }
  }

}
