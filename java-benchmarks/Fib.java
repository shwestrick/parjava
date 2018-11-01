import java.util.*;
import java.util.concurrent.*;

public class Fib extends RecursiveTask<Integer> {
    static int sequentialThreshold = 20;
    int number;

    public static void main(String[] args) {
        int reps = 1;
        int sreps = 1;
        int num = 40;
        try {
            if (args.length > 0)
              num = Integer.parseInt(args[0]);
            if (args.length > 1)
              reps = Integer.parseInt(args[1]);
            if (args.length > 2)
              sreps = Integer.parseInt(args[2]);
        } catch (Exception e) {
            System.out.println("Usage: java Reduce size reps sreps");
            return;
        }
        final int fnum = num;
        Runner.run((Void v) -> { job(fnum); return null; }, reps, sreps);
    }

    public Fib (int n) {
        this.number = n;
    }

    public static void job (int num) {
	    ForkJoinPool.commonPool().invoke(new Fib(num));
    }

    static int seqFib (int n) {
        if (n <= 1) 
          return n;
        else 
          return seqFib(n-1) + seqFib(n-2);
    }

    @Override
    public Integer compute() {
        int n = number;
        if (n <= 1) {
            return n;
        } else if (n <= sequentialThreshold) {
            return seqFib(n);
        } else {
            Fib f1 = new Fib(n - 1);
            f1.fork();
            Fib f2 = new Fib(n - 2);
	        return f2.compute () + f1.join();
        }
    }
}
