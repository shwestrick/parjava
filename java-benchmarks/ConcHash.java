import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class ConcHash {

    static class HashObject {
      HashObject(char[] x) {
        x = x;
      }
      public int hashcode () {
	return StrGen.hash(x);
      }
      char[] x;
    }

    private static void compute(char[][] l) {
      ConcurrentHashMap<HashObject, Integer> x = new ConcurrentHashMap<HashObject, Integer>(l.length);
      IntStream.range(0, l.length).parallel()
	      .forEach(i -> x.put(new HashObject(l[i]), 1));
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
            System.out.println("Usage: java Reduce size reps sreps");
            return;
        }

	char[][] l = new char[n][0];
	IntStream.range(0, n).parallel().forEach(i -> l[i] = StrGen.generate(i));
        Runner.run(
          (Void v) -> { compute(l); return null; },
   	  reps);
    }
}
