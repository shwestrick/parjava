import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class Map {

    static private int mapper(int elem) {
        return elem - 1;
    }

    static private void map(Integer[] arr) {
        IntStream.range(0, arr.length).forEach(i -> arr[i] = mapper(arr[i]));
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

        Integer[] result = new Integer[n];
        IntStream.range(0, n).forEach(i -> result[i] = i);

        Runner.run((Void v) -> { map(result); return null; }, reps);
    }
}