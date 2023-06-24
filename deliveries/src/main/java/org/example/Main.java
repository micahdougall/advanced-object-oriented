package org.example;

import com.beust.jcommander.JCommander;
import org.example.controller.DeliveryManager;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Location;
import org.example.util.ArtifactPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;


// TODO: SIGINT error for large file
public class Main {

    // Shortcut, just to save creating a Logger class, etc.
    public static boolean debug;

    public static void main(String[] argv) {

        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        debug = args.debug;

        DeliveryManager manager = new DeliveryManager();

        loadRoutes(manager, args.routes);
        loadAssignments(manager, args.assignments);

        // Print loaded data
        ArtifactPrinter.printReport(manager.getRoutes(), args.print);
        ArtifactPrinter.printReport(manager.getAssignments(), args.print);

        // Search for routes
        ArtifactPrinter.findRoutes(manager);

        // Test path calculations
        routeDeliveryAssignment(manager);

        // Test all paths
        routeAllDeliveryAssignmentsSlow(manager, args.print);
        routeAllDeliveryAssignments(manager, args.print);

        // Streamed Routed Deliveries
        streamRoutedDeliveries(manager, args.print);
    }


    public static void loadRoutes(DeliveryManager manager, String routes) {
        String routeFile = String.format("src/main/resources/%s", routes);
        try {
            manager.parseRoutes(routeFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAssignments(DeliveryManager manager, String assignments) {
        String assignmentFile = String.format("src/main/resources/%s", assignments);
        try {
            manager.parseAssignments(new File(assignmentFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // TODO: Should be random route?
    public static void routeDeliveryAssignment(DeliveryManager manager) {
        DeliveryAssignment assignment = manager
                .getAssignments()
                .stream()
                .findFirst()
                .get();

        Optional<Stack<Location>> path = manager.getOptimalPath(assignment);
        if (path.isPresent()) {
            System.out.println(
                    Colors.ANSI_COLOR_GREEN_BOLD + "Printing optimal path for random assignment..." +
                            Colors.ANSI_COLOR_RESET
            );
            ArtifactPrinter.printDeliveryPath(
                    path.get(), assignment.getSource(), assignment.getDestination());
        } else {
            System.out.println(
                    Colors.ANSI_COLOR_RED +
                            "No route available for assignment: " +
                            Colors.ANSI_COLOR_RESET + assignment);
        }
    }

    public static void routeAllDeliveryAssignmentsSlow(DeliveryManager manager, int maxRecords) {
        System.out.println(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n\nPrinting optimal paths without threading..." +
                        Colors.ANSI_COLOR_RESET
        );
        Instant start = Instant.now();

        HashMap<DeliveryAssignment, Stack<Location>> routes = manager
                .getAssignmentPaths(manager.getAssignments());

        ArtifactPrinter.printMultiplePaths(routes, maxRecords);
        ArtifactPrinter.printTimeTaken("slow", Duration.between(start, Instant.now()).toMillis());
    }

    public static void routeAllDeliveryAssignments(DeliveryManager manager, int maxRecords) {
        System.out.println(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n\nPrinting optimal paths with parallel processing..." +
                        Colors.ANSI_COLOR_RESET
        );
        Instant start = Instant.now();

        try {
            HashMap<DeliveryAssignment, Stack<Location>> routes = manager
                    .getAssignmentPathsParallel(manager.getAssignments());

            ArtifactPrinter.printMultiplePaths(routes, maxRecords);
            ArtifactPrinter.printTimeTaken("parallel", Duration.between(start, Instant.now()).toMillis());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void streamRoutedDeliveries(DeliveryManager manager, int maxRecords) {
        System.out.println(
                Colors.ANSI_COLOR_GREEN_BOLD + "\n\nPrinting optimal paths with streaming..." +
                        Colors.ANSI_COLOR_RESET
        );
        Instant start = Instant.now();

        // TODO: getAssugments is silly
        manager.batchDeliveryRoutes(manager.getAssignments())
                .limit(maxRecords)
                .forEachOrdered(d ->
                        ArtifactPrinter.printDeliveryPath(
                                d.getPath(),
                                d.getAssignment().getSource(),
                                d.getAssignment().getDestination()
                        )
                );
        if (maxRecords < manager.getAssignments().size()) {
            System.out.printf(
                    Colors.ANSI_COLOR_CYAN_BOLD + "\n\t\t\t\t------%d records omitted------\n\n" + Colors.ANSI_COLOR_RESET,
                    manager.getAssignments().size() - maxRecords
            );
        }
        ArtifactPrinter.printTimeTaken("streaming", Duration.between(start, Instant.now()).toMillis());
    }
}



