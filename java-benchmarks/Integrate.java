import java.util.*;
import java.util.concurrent.*;

public class Integrate {
    public static void main(String[] args) {
        double start = -47;
        double end = 48;
        int exp = 5;
        int reps = 1;
        int sreps = 1;
        try {
            if (args.length > 1)
              reps = Integer.parseInt(args[1]);
            if (args.length > 2)
              sreps = Integer.parseInt(args[2]);
        }
        catch (Exception e) {
            // TODO: update this note
            System.out.println("Usage: java Integrate <lower bound> <upper bound> \n (for example 2 1 48 5).");
            return;
        }

        Function f = new SampleFunction(exp);
        Integrator integrator = new Integrator(f, 0.001);


        Runner.run((Void v) -> {integrator.integral(start, end); return null;}, reps);
    }

    static interface Function {
      double compute(double x);
    }
    static class SampleFunction implements Function {
      final int n;
      SampleFunction(int n) { this.n = n; }

      public double compute(double x)  {
        double power = x;
        double xsq = x * x;
        double val = power;
        double di = 1.0;
        for (int i = n - 1; i > 0; --i) {
          di += 2.0;
          power *= xsq;
          val += di * power;
        }
        return val;
      }
    }

    static class Integrator {
      final Function f;      // The function to integrate
      final double errorTolerance;

        Integrator(Function f, double errorTolerance) {
          this.f = f;
          this.errorTolerance = errorTolerance;
        }

        double integral(double lowerBound, double upperBound) {
          double f_lower = f.compute(lowerBound);
          double f_upper = f.compute(upperBound);
          double initialArea = 0.5 * (upperBound-lowerBound) * (f_upper + f_lower);
          Quad q = new Quad(lowerBound, upperBound,
                            f_lower, f_upper,
                            initialArea);
          ForkJoinPool.commonPool().invoke(q);
	  // System.out.println(q.area);
          return q.area;
        }

        class Quad extends RecursiveTask<Double> {
            final double left;
            final double right;
            final double f_left;
            final double f_right;

            // probably will be removed?
            volatile double area;

            Quad(double left, double right, double f_left, double f_right, double area) {
                this.left = left;
                this.right = right;
                this.f_left = f_left;
                this.f_right = f_right;
                this.area = area;
            }

            @Override
            public Double compute () {
                double center = 0.5 * (left + right);
                double f_center = f.compute(center); 

                double leftArea  = 0.5 * (center - left)  * (f_left + f_center); 
                double rightArea = 0.5 * (right - center) * (f_center + f_right);
                double sum = leftArea + rightArea;

                double diff = sum - area;
                if (diff < 0) diff = -diff;

                if (diff >= errorTolerance) { 
                    Quad q1 = new Quad(left,   center, f_left,   f_center, leftArea);
                    q1.fork();
                    Quad q2 = new Quad(center, right,  f_center, f_right,  rightArea);
                    sum = q2.compute() + q1.join();
                }
                area = sum;
                return sum;
            }
        }
    }
}
