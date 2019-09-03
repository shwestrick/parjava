import java.util.concurrent.*;

public class TransposePoints extends RecursiveAction {
  public final Point[] input;
  public final Point[] output;

  public final int start;
  public final int end;

  public TransposePoints(Point[] input) {
    this.input = input;

    this.start = 0;
    this.end = input.length;

    this.output = new Point[input.length];
  }

  public TransposePoints(Point[] input, Point[] output, int start, int end) {
    this.input = input;
    this.output = output;
    this.start = start;
    this.end = end;
  }

  private void computeSequential() {
    for (int i = start; i < end; i++) {
      Point p = input[i];
      output[i] = new Point(p.y, p.x);
    }
    return;
  }

  protected void compute() {
    if (end - start <= 10000) {
      computeSequential();
      return;
    }

    int mid = start + (end - start)/2;
    invokeAll(new TransposePoints(input, output, start, mid),
              new TransposePoints(input, output, mid, end));
  }

}
