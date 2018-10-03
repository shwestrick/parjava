import java.util.concurrent.*;
import java.util.*;

class Scan {

  static final long NPS = (1000L * 1000 * 1000);

  public static void main(String[] args) throws Exception {
    int procs = 1;
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
      System.out.println("Usage: java Tabulate size reps sreps");
      return;
    }

    final int size = n;

    System.out.printf("size  %d\n", size);
    System.out.printf("sreps %d\n", sreps);
    System.out.printf("reps  %d\n", reps);
    System.out.println("");

    ArraySequence<Long> input =
      new ArraySequence<Long>(10000, size, i -> new Long(1));

    for (int r = 0; r < sreps; r++) {
      long start = System.nanoTime();

      int serialGrain = size; // reuse length as grain to force serial
      ArraySequence<Long> result =
        input.scan(serialGrain, (x,y) -> x+y, new Long(0));

      long stop = System.nanoTime();
      double elapsed = (double)(stop - start) / NPS;
      System.out.printf("serial time:  %7.3f\n", elapsed);
    }

    for (int r = 0; r < reps; r++) {
      long start = System.nanoTime();

      int grain = 4096;
      ArraySequence<Long> result =
        input.scan(grain, (x,y) -> x+y, new Long(0));

      long stop = System.nanoTime();
      double elapsed = (double)(stop - start) / NPS;
      System.out.printf("parallel time:  %7.3f\n", elapsed);
      if (size < 100) {
        System.out.println(Arrays.toString(result.data));
      }
    }
  }

}
