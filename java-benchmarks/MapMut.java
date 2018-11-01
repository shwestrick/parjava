import java.util.*;
import java.util.concurrent.*;
import java.util.stream.*;

class MapMut {

    static private char[] mapper(char[] elem) {
      int res = StrGen.hash(elem);
      return Integer.toBinaryString(res).toCharArray();
    }

    static private void map(char[][] arr) {
      IntStream.range(0, arr.length).parallel().forEach(i -> arr[i] = mapper(arr[i]));
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
	    System.out.println(e);
            System.out.println("Usage: java Filter size reps sreps");
            return;
        }


	char[][] result = new char[n][0];
	IntStream.range(0, n).parallel().forEach(i -> result[i] = StrGen.generate(i));

        Runner.run((Void v) -> { map(result); return null; }, reps, sreps);
    }
}
