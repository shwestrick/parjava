import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Filter {

    static private int generateInt(int i) {
        return i + 10 * 50;
    }

    // Lets start with just an array list of ints
    static private ArrayList<Integer> setup(int len) {
        ArrayList<Integer> l = new ArrayList<Integer>(len);
        for (int i = 0;i < len;i++) {
            l.add(i, generateInt(i));
        }
        return l;
    }

    static final long NPS = (1000L * 1000 * 1000);

    static private void run (ArrayList<Integer> l, int reps) {
        for (int r = 0;r < reps;r++) {
            // Is this an appropriate way to convert it back to a list?
            long start = System.nanoTime();
            l.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList());
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
            System.out.printf("Time: %7.3f\n", elapsed);
        }
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

        ArrayList<Integer> l = setup(n);

        run(l, reps);

        System.out.println("we out here");
    }
}