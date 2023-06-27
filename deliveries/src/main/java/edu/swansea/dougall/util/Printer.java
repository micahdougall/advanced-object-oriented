package edu.swansea.dougall.util;

import edu.swansea.dougall.Main;
import edu.swansea.dougall.entities.Location;
import edu.swansea.dougall.entities.Coordinate;
import edu.swansea.dougall.artifacts.DeliveryAssignment;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to control printing delivery and routing data to the console.
 * <p>
 * Some methods include a {@code maxRecords} parameter to limit the number of records printed.
 *
 * @see edu.swansea.dougall.util.Colors
 */
public class Printer {

    /**
     * Prints a report of artifacts from the {@code DeliveryManager}.
     *
     * @param artifacts the Collection of elements to be printed in the report.
     * @param maxRecords the maximum number of records ot be printed, following which a count of
     *                   the unprinted records will be displayed.
     * @param <E> type of object to be printed as part of the Collection. Must implement its own
     * {@code toString()} method.
     */
    public static <E> void report(Collection<E> artifacts, int maxRecords) {
        int records = artifacts.size();
        int printRecords = Math.min(records, maxRecords);
        Iterator<E> iter = artifacts.iterator();

        heading(String.format("Printing report for %d of %d artifacts...", printRecords, records));

        for (int i = 0; i < printRecords; i++) {
            System.out.println(iter.next());
        }
        if (printRecords < records) {
            omitted(records - printRecords);
        }
    }

    /**
     * Prints a set of complete delivery paths from the {@code DeliveryManager}.
     *
     * @param routes the HashMap of {@code DeliveryAssignment} objects to be printed.
     * @param maxRecords the maximum number of records ot be printed, following which a count of
     *                   the remaining unprinted records will be displayed.
     * @see #deliveryPath(Stack, Coordinate, Coordinate)
     */
    public static void multiplePaths(
            HashMap<DeliveryAssignment, Stack<Location>> routes, int maxRecords
    ) {
        int records = routes.size();
        int printRecords = Math.min(records, maxRecords);

        List<Map.Entry<DeliveryAssignment, Stack<Location>>> samples = routes.entrySet()
                .stream()
                .limit(printRecords)
                .collect(Collectors.toList());

        for (Map.Entry<DeliveryAssignment, Stack<Location>> route : samples) {
            DeliveryAssignment assignment = route.getKey();
            deliveryPath(
                    route.getValue(), assignment.getSource(), assignment.getDestination());
        }
        if (printRecords < records) {
            omitted(records - printRecords);
        }
    }

    /**
     * Prints a single delivery path from the {@code DeliveryManager} to the console.
     *
     * @param path the Stack of {@code Location} objects to be printed which constitute the path.
     * @param start the {@code Coordinate} of the start of the path.
     * @param end the {@code Coordinate} of the end of the path.
     */
    public static void deliveryPath(Stack<Location> path, Coordinate start, Coordinate end) {
        DecimalFormat formatter = new DecimalFormat("###,###.00");
        String currency = String.format("£%S", formatter.format(path.peek().getCost()));
        System.out.printf(
                Colors.ANSI_COLOR_CYAN_BOLD + "%-12s" + Colors.ANSI_COLOR_PURPLE + " ➫ " +
                        Colors.ANSI_COLOR_CYAN_BOLD + "%-12s" +
                        Colors.ANSI_COLOR_YELLOW + "%10s: ",
                start, end, currency);
        for (Location location : path) {
            System.out.printf(Colors.ANSI_BOLD_WHITE + "⤑%s", location.getPoint());
        }
        System.out.println(Colors.ANSI_COLOR_RESET);
    }

    /**
     * Prints the time taken for a method to complete.
     *
     * @param methodType the name of the method being timed.
     * @param millis the time taken for the method to complete in milliseconds.
     */
    public static void timeTaken(String methodType, long millis) {
        System.out.printf(
                Colors.ANSI_BOLD_WHITE + "Time taken for " +
                        Colors.ANSI_COLOR_GREEN_BOLD + "%s" +
                        Colors.ANSI_BOLD_WHITE + " method:" +
                        Colors.ANSI_COLOR_BLUE + Colors.ANSI_BACKGROUND + " %s milliseconds." +
                        Colors.ANSI_COLOR_RESET + "\n",
                methodType, millis
        );
    }

    /**
     * Prints a heading to the console.
     *
     * @param text the text to be printed as a heading.
     */
    public static void heading(String text) {
        System.out.printf(Colors.ANSI_COLOR_GREEN_BOLD + "\n%s\n" + Colors.ANSI_COLOR_RESET, text);
    }

    /**
     * Prints a message to the console.
     *
     * @param text the text to be printed.
     * @param color the color to be used to print the text.
     * @param args the arguments to be passed to the {@code String.format()} method.
     */
    public static void info(String text, String color) {
        System.out.printf(color + "\n%s\n" + Colors.ANSI_COLOR_RESET, text);
    }

    /**
     * Prints a message to the console.
     * @param text the text to be printed.
     * @param entity the entity to be printed.
     */
    public static void warning(String text) {
        System.out.printf(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n%s\n" + Colors.ANSI_COLOR_RESET, text
        );
    }

    /**
     * Prints a message to the console detailing the number of records which have been omitted
     * from a print report.
     *
     * @param records the number of records omitted.
     */
    public static void omitted(int records) {
        System.out.printf(
                Colors.ANSI_COLOR_MAGENTA +
                        "\n\t\t\t\t------%d records omitted------\n\n" + Colors.ANSI_COLOR_RESET,
                records
        );
    }

    /**
     * Prints a message to the console only in the event that the {@code Main.debug} flag is set
     * to true.
     *
     * @param text the text to be printed.
     * @param args the arguments to be passed to the {@code String.format()} method.
     */
    public static void debug(String text) {
        if (Main.debug) {
            System.out.printf("%s\n", text);
        }
    }
}
