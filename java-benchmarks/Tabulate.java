import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Tabulate {

    static private void run (int len) {
      char[][] result = new char[len][0];
      IntStream.range(0, len).parallel().forEach(i -> result[i] = StrGen.generate(i));
    }

    public static void main(String[] args) throws Exception {
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
            System.out.println("Usage: java Filter size reps sreps");
            return;
        }
        final int n2 = n;
        Runner.run((Void v) -> { run(n2); return null; }, reps, sreps);
    }
}
