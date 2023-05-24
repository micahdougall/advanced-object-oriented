import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> userList = getIntegerList();
        System.out.printf("\nFibonacci sync stream: %s\n", syncStream(userList));
        System.out.printf("Fibonacci parallel stream: %s\n", parallelStream(userList));
        System.out.printf("\nFibonacci sync stream: %s\n", syncNonRecursive(userList));
        System.out.printf("Fibonacci parallel stream: %s\n", parallelNonRecursive(userList));

        System.out.printf("\n\nTiming functions sync vs parallel...\n\n");
        BenchmarkingNano.timeFibFunctions(Main::syncStream, Main::parallelStream);

        System.out.printf("\n\nTiming non-recursive functions sync vs parallel...\n\n");
        BenchmarkingNano.timeFibFunctions(Main::syncNonRecursive, Main::parallelNonRecursive);
    }

    public static List<Integer> syncStream(List<Integer> arrayList) {
        return arrayList.stream()
                .filter((i) -> (i >= 0))
                .map(Main::fib)
                .toList();
    }

    public static List<Integer> parallelStream(List<Integer> arrayList) {
        return arrayList.stream()
                .filter((i) -> (i >= 0))
                .parallel()
                .map(Main::fib)
                .toList();
    }

    public static List<Integer> syncNonRecursive(List<Integer> arrayList) {
        return arrayList.stream()
                .filter((i) -> (i >= 0))
                .map(getNthFibonacci)
                .toList();
    }

    public static List<Integer> parallelNonRecursive(List<Integer> arrayList) {
        return arrayList.stream()
                .filter((i) -> (i >= 0))
                .parallel()
                .map(getNthFibonacci)
                .toList();
    }

    public static ArrayList<Integer> getIntegerList() {
        Scanner in = new Scanner(System.in);
        System.out.printf("\nEnter a list of integers to calculate the Fibonacci numbers of: ");

        String[] input = in.nextLine().split(" ");
        return (ArrayList<Integer>) Arrays.stream(input)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

//    Function<Integer, Integer> fibonacci = (n) -> (n <= 1) ? n : fibonacci(n - 2) + fib(n - 1)
    public static int fib(int n) {
        return (n <= 1) ? n : fib(n - 2) + fib(n - 1);
    }

    static Function<Integer, Integer> getNthFibonacci = (nth) -> {
        int a = 0;
        int b = 1;
        for (int i = 1; i <= nth; i++) {
            if (i > 2) {
                int next = a + b;
                a = b;
                b = next;
            }
        }
        return (nth == 1) ? 0 : b;
    };
}
