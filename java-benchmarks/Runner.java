import java.util.*;
import java.util.function.*;

class Runner {
    static final long NPS = (1000L * 1000 * 1000);
    public static void run (Function<Void, Void> func, int reps) {
        ArrayList<Double> times = new ArrayList<Double>();
        for (int r = 0;r < reps;r++) {
            long start = System.nanoTime();
            func.apply(null);
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
            times.add(elapsed);
            System.out.printf("Time: %7.3f\n", elapsed);
        }
        stats(times);
    }

    public static void run_with_setup(Function<Void, Void> setup,
                                      Function<Void, Void> func, int reps) {
        ArrayList<Double> times = new ArrayList<Double>();
        for (int r = 0;r < reps;r++) {
            setup.apply(null);
            long start = System.nanoTime();
            func.apply(null);
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
            times.add(elapsed);
            System.out.printf("Time: %7.3f\n", elapsed);
        }
        stats(times);
    }

    public static void stats(ArrayList<Double> times) {
        double min = Collections.min(times);
        double max = Collections.max(times);
        double median = 0;
        Collections.sort(times);
        double pos1 = Math.floor((times.size() - 1.0) / 2.0);
        double pos2 = Math.ceil((times.size() - 1.0) / 2.0);
        if (pos1 == pos2 ) {
           median = times.get((int)pos1);
        } else {
           median = (times.get((int)pos1) + times.get((int)pos2)) / 2.0 ;
        }

        System.out.println("Min: " + min);
        System.out.println("Med: " + median);
        System.out.println("Max: " + max);
    }

    public static void test() {
        run ((Void v) -> {System.out.println("wei");return null;}, 0);
    }
}