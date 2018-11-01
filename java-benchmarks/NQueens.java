import java.util.*;
import java.util.concurrent.*;

class NQueens extends RecursiveTask {
    static int boardSize;

    public static void main(String[] args) {
        int n = 4;
        int reps = 1;
        int sreps = 1;
        try {
            if (args.length > 1)
              n = Integer.parseInt(args[0]);
            if (args.length > 2)
              reps = Integer.parseInt(args[1]);
            if (args.length > 3)
              sreps = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            // TODO: update this note
            System.out.println("Usage: java Integrate <lower bound> <upper bound> \n (for example 2 1 48 5).");
            return;
        }
        try {
            boardSize = n;
            ForkJoinPool.commonPool().invoke(new NQueens(new int[0]));
            int[] board = result.await();

            for (int i = 0; i < board.length; ++i) {
              System.out.print(" " + board[i]);
            }
            System.out.println();
        }
        catch (InterruptedException ex) {}

	Runner.run_with_setup(
	  (Void v) -> { result.reset(); return null; },
	  (Void v) -> { NQueens f = new NQueens(new int[0]);
	  	        ForkJoinPool.commonPool().invoke(f);
			try{
	  		  int[] board = result.await();
			} catch (InterruptedException ex) {
			  System.out.println("wei");
			}
	   		return null;},
	  reps, sreps
	);

        // Runner.run((Void v) -> { run(); return null; }, reps);
    }

    static void run() {
        System.out.println("hello");
        try {
            System.out.println("wtf?");
            result.reset();
            NQueens f = new NQueens(new int[0]);
            ForkJoinPool.commonPool().invoke(f);
            int [] board = result.await();
            System.out.println("after");
            for (int i = 0; i < board.length; ++i) {
              System.out.print(" " + board[i]);
            }
        }
        catch (InterruptedException ex) {
            System.out.println("wei");
        }
    }

    static final class Result {
        private int[] board = null;

        synchronized int[] get() { return board; }

        synchronized void set(int[] b) {
          if (board == null) { board = b; notifyAll(); }
        }

        synchronized int[] await() throws InterruptedException {
          while (board == null) { wait(); }
          return board;
        }
        synchronized void reset() {
            board = null;
        }
    }

    static final Result result = new Result();

    final int[] sofar;
    NQueens(int[] a) { this.sofar = a; }

    @Override
    public Object compute () {
        if (result.get() == null) {

            int row = sofar.length;

            if (row >= boardSize) // done
                result.set(sofar);
            else {
                for (int q = 0; q < boardSize; ++q) {

                  // Check if can place queen in column q of next row
                    boolean attacked = false;
                    for (int i = 0; i < row; i++) {
                        int p = sofar[i];
                        if (q == p || q == p - (row - i) || q == p + (row - i)) {
                            attacked = true;
                            break;
                        }
                    }

                    // Fork to explore moves from new configuration
                    if (!attacked) { 
                        int[] next = new int[row+1];
                        for (int k = 0; k < row; ++k) next[k] = sofar[k];
                        next[row] = q;
                        new NQueens(next).fork();
                    }
                }
            }
        }
        return null;
    }
}
