import java.util.*;
import java.util.function.*;
import java.util.concurrent.*;
import java.util.stream.*;

public class OurTabulateBenchmark {

    public static class ParFor extends RecursiveTask<Void> {
	int hi;
	int lo;
	int grain = 10000;
	Function<Integer, Void> f;
	
	ParFor(int l, int h, Function<Integer, Void> func) {
	  this.lo = l;
	  this.hi = h;
	  this.f = func;
	}

	@Override
	public Void compute() {
	  int length = hi - lo;
	  if (length <= grain) {
	    for(int i = lo;i < hi;i++) {
	      f.apply(i);
	    }
	    return null;
	  }
	  int mid = lo + length/2;
	  ParFor left = new ParFor(lo, mid, f);
	  left.fork();
	  ParFor right = new ParFor(mid, hi, f);
	  right.compute();
	  left.join();
	  return null;
	}
    }

    public static void OurTabulate() {
	int n = 10000000;
	char[][] result = new char[n][0];
	ParFor x = new ParFor(0, n, i -> {result[i] = StrGen.generate(i); return null; });
	ForkJoinPool.commonPool().invoke(x);
    }

    public static void main (String args[]) {
	Runner.run((Void v) -> { OurTabulate(); return null; }, 1, 0);
    }

}
