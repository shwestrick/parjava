import java.util.*;
import java.util.function.*;

class Runner {
    static final long NPS = (1000L * 1000 * 1000);
    public static void run (Function<Void, Void> func, int reps) {
        Void v = null;
        func.apply(v);

        for (int r = 0;r < reps;r++) {
            long start = System.nanoTime();
            func.apply(null);
            long end = System.nanoTime();
            double elapsed = (double) (end - start) / NPS;
            System.out.printf("Time: %7.3f\n", elapsed);
        }
    }

    public static void test() {
        run ((Void v) -> {System.out.println("wei");return null;}, 0);
    }
}