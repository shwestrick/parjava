import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;

class ParallelFor {

  private static class Task extends RecursiveTask<Void> {
    int grain;
    int lo;
    int hi;
    IntConsumer func;

    Task(int grain, int lo, int hi, IntConsumer func) {
      this.grain = grain;
      this.lo = lo;
      this.hi = hi;
      this.func = func;
    }

    public Void compute() {
      int length = hi - lo;
      if (length <= grain) {
        for (int i = lo; i < hi; i++) {
          func.accept(i);
        }
        return null;
      }

      int mid = lo + length/2;
      Task right = new Task(grain, mid, hi, func);
      right.fork();
      Task left = new Task(grain, lo, mid, func);
      left.compute();
      right.join();
      return null;
    }
  }

  static void range(int lo, int hi, IntConsumer func) {
    Task t = new Task(10000, lo, hi, func);
    t.compute();
  }

  static void range(int grain, int lo, int hi, IntConsumer func) {
    Task t = new Task(grain, lo, hi, func);
    t.compute();
  }

}
