package org.example.util;

import org.example.Colors;
import org.example.controller.DeliveryManager;
import org.example.entities.Coordinate;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Location;
import org.example.entities.DeliveryRoute;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;


public class ArtifactPrinter {

    public static <E> void printReport(Collection<E> artifacts, int maxRecords) {
        int records = artifacts.size();
        int printRecords = Math.min(records, maxRecords);
        Iterator<E> iter = artifacts.iterator();
        System.out.printf(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n\nPrinting report for %d of %d artifacts...\n\n" + Colors.ANSI_COLOR_RESET,
                printRecords, records);

        for (int i = 0; i < printRecords; i++) {
            System.out.println(iter.next());
        }
        if (printRecords < records) {
            System.out.printf(
                    Colors.ANSI_COLOR_CYAN_BOLD + "\n\t\t\t\t------%d records omitted------\n\n" + Colors.ANSI_COLOR_RESET,
                    records - printRecords
            );
        }
    }

    public static void findRoutes(DeliveryManager manager) {

        System.out.println(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n\nSelecting random coordinates from routes.." + Colors.ANSI_COLOR_RESET);
        int random = new Random().nextInt(manager.getAssignments().size());
        DeliveryRoute randomRoute = manager.getRoutes().get(random);

        Coordinate start = randomRoute.getStart();
        Coordinate end = randomRoute.getEnd();

        Optional<DeliveryRoute> route = manager.findRoute(start, end);
         if (route.isPresent()) {
             System.out.printf(
                     Colors.ANSI_BOLD_WHITE + "Route for coordinates: %s⤑%s %s\n\n"
                            + Colors.ANSI_COLOR_RESET,
                     start, end, route.get());
         } else {
             System.out.printf(
                     Colors.ANSI_COLOR_GREEN_BOLD + "Item not found for coordinates: %s⤑%s\n\n"
                            + Colors.ANSI_COLOR_RESET,
                     start, end);
         }
    }

    public static void printOrderedCoordinates(Collection<Location> coordinates) {
        System.out.println("Printing ordered coordinates...");
        for (Location coordinate : coordinates) {
            System.out.printf("\t->%s", coordinate);
        }
    }

    public static void printDeliveryPath(Stack<Location> path, Coordinate start, Coordinate end) {
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

    public static void printMultiplePaths(HashMap<DeliveryAssignment, Stack<Location>> routes, int maxRecords) {
        int records = routes.size();
        int printRecords = Math.min(records, maxRecords);

        List<Map.Entry<DeliveryAssignment, Stack<Location>>> samples = routes.entrySet()
                .stream()
                .limit(printRecords)
                .collect(Collectors.toList());

        for (Map.Entry<DeliveryAssignment, Stack<Location>> route : samples) {
            DeliveryAssignment assignment = route.getKey();
            printDeliveryPath(
                    route.getValue(), assignment.getSource(), assignment.getDestination());
        }
        if (printRecords < records) {
            System.out.printf(
                    Colors.ANSI_COLOR_CYAN_BOLD + "\n\t\t\t\t------%d records omitted------\n\n" + Colors.ANSI_COLOR_RESET,
                    records - printRecords
            );
        }
    }

    public static void printTimeTaken(String methodType, long millis) {
        System.out.printf(
                Colors.ANSI_BOLD_WHITE + "Time taken for " +
                        Colors.ANSI_COLOR_GREEN_BOLD + "%s" +
                        Colors.ANSI_BOLD_WHITE + " method: " +
                        Colors.ANSI_COLOR_BLUE + Colors.ANSI_BACKGROUND + "%s milliseconds" +
                        Colors.ANSI_COLOR_RESET + ".\n",
                methodType, millis
        );
    }
}
