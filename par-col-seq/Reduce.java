import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Reduce {

    private static int generateInt(int idx) {
        return idx;
    }

    private static int[] setup(int len) {
        int[] d = new int[len];
        IntStream.range(0, len).parallel().forEach(i -> d[i] = generateInt(i));
        return d;
    }

    private static int max (int a, int b) {
        if (a > b) {
            return a;
        } 
        return b;
    }
    private static void compute(int[] l) {
        int result = Arrays.stream(l).parallel().reduce((a,b) -> max(a,b)).getAsInt();
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

        int[] l = setup(n);
        Runner.run((Void v) -> { compute(l); return null; }, reps);
    }
}
