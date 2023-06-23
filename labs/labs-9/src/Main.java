import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        naivePrimes();
        executePrimes();
//        threadPrimes();
    }

    public static void naivePrimes() {
        Instant start = Instant.now();
        for (int i = 100000; i < 200000; i += 20000) {
            System.out.println(TwinPrimes.slowCountTwinPrimes(i));
        }
        System.out.printf(
                "Time taken for naive method: %s milliseconds.",
                Duration.between(start, Instant.now()).toMillis()
        );
    }

    public static void executePrimes() throws InterruptedException, ExecutionException {
        Instant start = Instant.now();

        ExecutorService service = Executors.newFixedThreadPool(6);
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 100000; i < 200000; i += 20000) {
            int finalI = i;
            futures.add(service.submit(() -> TwinPrimes.slowCountTwinPrimes(finalI)));
        };
        service.shutdown();
        while (!service.awaitTermination(500, TimeUnit.MILLISECONDS));

        for (Future<Integer> i : futures) {
            System.out.println(i.get());
        }
        System.out.printf(
                "Time taken for thread method: %s milliseconds.",
                Duration.between(start, Instant.now()).toMillis()
        );
    }



    //    public static void threadPrimes() throws InterruptedException, ExecutionException {
//        Instant start = Instant.now();
//
//        Thread one =
//
//        ExecutorService service = Executors.newFixedThreadPool(6);
//        List<Future<Integer>> futures = new ArrayList<>();
//        for (int i = 100000; i < 200000; i += 20000) {
//            int finalI = i;
//            futures.add(service.submit(() -> TwinPrimes.slowCountTwinPrimes(finalI)));
//        };
//        service.shutdown();
//        while (!service.awaitTermination(500, TimeUnit.MILLISECONDS));
//
//        for (Future<Integer> i : futures) {
//            System.out.println(i.get());
//        }
//        System.out.printf(
//                "Time taken for thread method: %s milliseconds.",
//                Duration.between(start, Instant.now()).toMillis()
//        );
//    }
}