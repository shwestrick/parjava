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

  ArraySequence(T x) {
    this.data = new Object[] {x};
    this.start = 0;
    this.length = 1;
  }

  ArraySequence(T[] data) {
    this.data = data;
    this.start = 0;
    this.length = data.length;
  }

  ArraySequence(int n, Function<Integer,T> func) {
    this.data = new Object[n];
    this.start = 0;
    this.length = n;
    ParallelFor.range(0, n, i -> data[i] = func.apply(i));
  }

  ArraySequence(int grain, int n, Function<Integer,T> func) {
    this.data = new Object[n];
    this.start = 0;
    this.length = n;
    ParallelFor.range(grain, 0, n, i -> data[i] = func.apply(i));
  }

  public T get(int i) {
    return (T) data[start + i];
  }

  public void set(int i, T x) {
    data[start + i] = x;
  }

  public ArraySequence<T> scan(int chunkSize, BinaryOperator<T> op, T id) {
    if (length == 0)
      return new ArraySequence<T>(id);
    if (length == 1) {
      T[] result = (T[]) new Object[] {id, this.get(0)};
      return new ArraySequence<T>(result);
    }

    int numChunks = 1 + ((length - 1) / chunkSize);

    ArraySequence<T> chunkSums = new ArraySequence<T>(1, numChunks, i -> {
      int lo = i*chunkSize;
      int hi = Math.min((i+1)*chunkSize, length);
      T sum = id;
      for (int j = lo; j < hi; j++) sum = op.apply(sum, this.get(j));
      return sum;
    });

    ArraySequence<T> partials = chunkSums.scan(chunkSize, op, id);

    Object[] result = new Object[length+1];
    /* copy the final result */
    result[length] = partials.get(numChunks);
    /* compute the intermediate prefix sums */
    ParallelFor.range(1, 0, numChunks, i -> {
      int lo = i*chunkSize;
      int hi = Math.min((i+1)*chunkSize, length);
      T sum = partials.get(i);
      for (int j = lo; j < hi; j++) {
        result[j] = sum;
        sum = op.apply(sum, this.get(j));
      }
    });

    return new ArraySequence<T>((T[]) result);
  }

}
