import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Benchmarking {
    /* THIS METHOD IS NOT INTENDED FOR PROPPER BENCHMARKING! IT IS ONLY A CRUDE
     * INDICATION OF RUNTIME FOR THE GIVEN FUNCTIONS!
     *
     * This method is created to perform a simple time benchmark on the two
     * implementations of a Fibonachi function, or any such function.
     *
     * The expected parameters are two Functions, each taking a List<Integer> as an
     * argument, and returning a List<Integer>
     *
     * The first argument should be the parallel implementation and the second the
     * sequential implementation. Getting them the wrong way round will simply print
     * results backwards :-)
     */
    public static void timeFibFunctions(Function<List<Integer>, List<Integer>> parallel,
                                        Function<List<Integer>, List<Integer>> sequential) {
        final int inputSize = 1000; // How many numbers to test the functions against (1000 should be good.)
        final int trialCount = 20; // Number of trials per method (More is better, but slower)
        List<Integer> largeInput = Collections.unmodifiableList(
                ThreadLocalRandom.current()
                        .ints(inputSize, 0, 32)
                        .boxed()
                        .collect(Collectors.toList()));

        //First some warmup of the JVM
        for (int i = 0; i < trialCount / 2; i++) {
            parallel.apply(largeInput);
            sequential.apply(largeInput);
        }

        // Time the parallel function
        long totalTime = 0;
        for (int i = 0; i < trialCount; i++) {
            long start = System.currentTimeMillis();
            parallel.apply(largeInput);
            totalTime += (System.currentTimeMillis() - start);
        }

        // And then the sequential...
        final long avgParallel = totalTime / trialCount;
        totalTime = 0;
        for (int i = 0; i < trialCount; i++) {
            long start = System.currentTimeMillis();
            sequential.apply(largeInput);
            totalTime += (System.currentTimeMillis() - start);
        }
        totalTime /= trialCount;

        System.out.println("Benchmarking completed.");
        System.out.println(String.format("Parallel took %dms (average) per call, and sequential took %dms.",
                avgParallel, totalTime));
    }
}
