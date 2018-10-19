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

    static private void compute(ArrayList<Integer> l) {
        l.parallelStream().filter(i -> i % 2 == 0).collect(Collectors.toList());
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
        Runner.run((Void v) -> {compute(l);return null;}, reps);
    }
}