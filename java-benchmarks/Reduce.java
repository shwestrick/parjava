import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Reduce {

    private static long generateLong(long idx) {
        return idx;
    }

    private static long[] setup(long len) {
        long[] d = new long[(int)len];
        IntStream.range(0, (int)len).parallel().forEach(i -> d[i] = generateLong(i));
        return d;
    }

    private static int max (int a, int b) {
        if (a > b) {
            return a;
        } 
        return b;
    }
    private static long add(long a, long b) {
        return a + b;
    }
    private static void compute(long[] l) {
        long result = Arrays.stream(l).parallel().reduce((a,b) -> a + b).getAsLong();
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

        long[] l = setup(n);
        Runner.run((Void v) -> { compute(l); return null; }, reps);
    }
}
