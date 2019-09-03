import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Main {
    
  private static Point[] compute(Point[] input) {
    TransposePoints task = new TransposePoints(input);
    task.invoke();
    return task.output;
  }

  public static void main (String args[]) throws Exception {
    int n = 1000000;
    int reps = 1;
    int sreps = 1;
    try {
      if (args.length > 0)
        n = Integer.parseInt(args[0]);
      if (args.length > 1)
        reps = Integer.parseInt(args[1]);
      if (args.length > 2)
        sreps = Integer.parseInt(args[2]);
    } catch (Exception e) {
      System.out.println("Usage: java Main <size> <rounds> <warmup rounds>");
      return;
    }

    Point[] input = new Point[n];
    IntStream.range(0, n).parallel().forEach(i -> {
      input[i] = new Point(2*i, 2*i+1);
    });

    Runner.run(
      (Void v) -> { compute(input); return null; },
         reps, sreps);
  }

}
