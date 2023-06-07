import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Triple topTriple = getTriples()
                    .stream()
                    .sorted()
                    .findFirst()
                    .orElseThrow();
            System.out.printf("\n%s", topTriple);
        } catch (NumberFormatException e) {
            System.out.printf(
                    "Input not valid, please enter 3 numbers separated by spaces: %s\n", e
            );
        }
    }

    public static ArrayList<Triple> getTriples() {
        Scanner in = new Scanner(System.in);
        ArrayList<Triple> triples = new ArrayList<>();
        boolean more = true;

        System.out.printf("\nEnter data for a Triple as [a b c] where a, b, and c are all numeric.");
        do {
            System.out.printf("\nTriple data: ");
            triples.add(getTripleData());
            System.out.print("Enter another triple? (y/n): ");
            String option = in.nextLine();
            if (!(option.contains("y"))) more = false;
        } while (more);
        return triples;
    }


    public static Triple getTripleData() {
        Scanner in = new Scanner(System.in);
        String[] input = in.nextLine().split(" ");

        return new Triple(
            Double.parseDouble(input[0]),
            Double.parseDouble(input[1]),
            Double.parseDouble(input[2])
        );
    }
}
