import java.util.*;
import java.util.function.*;
import java.lang.management.*;

class Runner {
    static final long NPS = (1000L * 1000 * 1000);
    public static void run (Function<Void, Void> func, int reps, int preRun) {
	for (int i = 0;i < preRun;i++) {
	  func.apply(null);
	}

        ArrayList<Double> times = new ArrayList<Double>();
	ArrayList<Double> gc_times = new ArrayList<Double>();
        for (int r = 0;r < reps;r++) {
	    double prevgc = getGarbageCollectionTime();
            long start = System.nanoTime();
            func.apply(null);
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
	    double postgc = getGarbageCollectionTime();
            times.add(elapsed);
	    gc_times.add(postgc - prevgc);
            System.out.printf("Time: %7.3f %7.3f\n", elapsed, postgc-prevgc);
        }
        stats(times, gc_times);
    }

    public static void run_with_setup(Function<Void, Void> setup,
                                      Function<Void, Void> func, int reps, int preRun) {
	for (int i = 0;i < preRun;i++) {
	  setup.apply(null);
	  func.apply(null);
	}

        ArrayList<Double> times = new ArrayList<Double>();
	ArrayList<Double> gc_times = new ArrayList<Double>();
        for (int r = 0;r < reps;r++) {
            setup.apply(null);
	    double prevgc = getGarbageCollectionTime();
            long start = System.nanoTime();
            func.apply(null);
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
	    double postgc = getGarbageCollectionTime();
	    gc_times.add(postgc - prevgc);
            times.add(elapsed);
            System.out.printf("Time: %7.3f %7.3f\n", elapsed, postgc-prevgc);
        }
        stats(times, gc_times);
    }

    public static void stats(ArrayList<Double> times, ArrayList<Double> gctimes) {
        double min = Collections.min(times);
        double max = Collections.max(times);
        double median = 0;
	double medgc = 0;
        Collections.sort(times);
	Collections.sort(gctimes);
        double pos1 = Math.floor((times.size() - 1.0) / 2.0);
        double pos2 = Math.ceil((times.size() - 1.0) / 2.0);
        if (pos1 == pos2 ) {
           median = times.get((int)pos1);
	   medgc = gctimes.get((int)pos1);
        } else {
           median = (times.get((int)pos1) + times.get((int)pos2)) / 2.0 ;
           medgc = (gctimes.get((int)pos1) + gctimes.get((int)pos2)) / 2.0 ;
        }

        System.out.println("Min: " + min);
        System.out.println("Med: " + median + " " + medgc);
        System.out.println("Max: " + max);
    }

    static double getGarbageCollectionTime() {
      long collectionTime = 0;
      for (GarbageCollectorMXBean garbageCollectorMXBean : ManagementFactory.getGarbageCollectorMXBeans()) {
	collectionTime += garbageCollectorMXBean.getCollectionTime();
      }
      return ((double)collectionTime) / 1000.0;
    }
}
