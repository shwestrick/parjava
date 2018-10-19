import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Reduce {

    private static int generateInt(int idx) {
        return idx;
    }

    private static ArrayList<Integer> setup(int len) {
        Integer[] d = new Integer[len];
        IntStream.range(0, len).parallel().forEach(i -> d[i] = generateInt(i));

        // for (int i = 0;i < len;i++) {
        //     d[i] = generateInt(i);
        // }
        return new ArrayList<Integer>(Arrays.asList(d));
    }

    private static Integer max (Integer a, Integer b) {
        if (a > b) {
            return a;
        } 
        return b;
    }
    private static void compute(ArrayList<Integer> l) {
        Integer result = l.parallelStream()
                         .reduce((a,b) -> max(a, b)).get();
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

        ArrayList<Integer> l = setup (n);
        Runner.run((Void v) -> { compute(l); return null; }, reps);
    }
}