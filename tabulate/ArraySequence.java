import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;
import java.lang.reflect.*;

class ArraySequence<T> {

  Object[] data;
  int start;
  int length;

  ArraySequence() {
    this.data = null;
    this.start = 0;
    this.length = 0;
  }

  ArraySequence(int n) {
    this.data = new Object[n];
    this.start = 0;
    this.length = n;
  }

  public void tabulate(Function<Integer,T> func) {
    ParallelFor.range(0, length, i -> data[start+i] = func.apply(i));
  }

  public void tabulate(int grain, Function<Integer,T> func) {
    ParallelFor.range(grain, 0, length, i -> data[start+i] = func.apply(i));
  }

}
