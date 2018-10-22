import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Filter {

    static private int generateInt(int i) {
        return i;
    }


    static private int[] setup (int len) {
        int[] l = new int[len];
        IntStream.range(0, len).parallel().forEach(i -> l[i] = generateInt(i));
        return l;
    }

    static private void compute(int[] l) {
        int[] res = Arrays.stream(l).parallel().filter(i -> i % 2 == 0).toArray();
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

        int[] l = setup(n);
        Runner.run((Void v) -> {compute(l);return null;}, reps);
    }
}