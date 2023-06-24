package org.example;

import com.beust.jcommander.JCommander;
import jdk.nashorn.internal.ir.Assignment;
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
import java.util.stream.Stream;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] argv) {

        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
//        exit(0);


//    }
//
//    public static void run(Args args) {

        DeliveryManager manager = new DeliveryManager();


        loadRoutes(manager, args.routes);
        loadAssignments(manager, args.assignments);

        // Print loaded data
        ArtifactPrinter.printReport(manager.getRoutes());
        ArtifactPrinter.printReport(manager.getAssignments());

        // TODO: Show de-duping proof

        // Search for routes
        ArtifactPrinter.findRoutes(manager);

        // Test path calculations
        routeDeliveryAssignment(manager);

        // Test all paths
        routeAllDeliveryAssignmentsSlow(manager);
        routeAllDeliveryAssignments(manager);

        // Streamed Routed Deliveries
        streamRoutedDeliveries(manager);
    }


    public static void loadRoutes(DeliveryManager manager, String routes) {
        String routeFile = String.format("src/main/resources/%s", routes);
        try {
            manager.parseRoutes(routeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public static void routeDeliveryAssignment(DeliveryManager manager) {
        DeliveryAssignment assignment = manager
                .getAssignments()
                .stream()
                .findFirst()
                .get();

        Optional<Stack<Location>> path = manager.getOptimalPath(assignment);
        if (path.isPresent()) {
            ArtifactPrinter.printDeliveryPath(
                    path.get(), assignment.getSource(), assignment.getDestination());
        } else {
            System.out.println("No route available for assignment: " + assignment);
        }
    }

    public static void routeAllDeliveryAssignmentsSlow(DeliveryManager manager) {
        Instant start = Instant.now();

        HashMap<DeliveryAssignment, Stack<Location>> routes = manager
                .getAssignmentPaths(manager.getAssignments());

        for (Map.Entry<DeliveryAssignment, Stack<Location>> route : routes.entrySet()) {
            DeliveryAssignment assignment = route.getKey();
            ArtifactPrinter.printDeliveryPath(
                    route.getValue(), assignment.getSource(), assignment.getDestination());
        }
        System.out.printf(
                "Time taken for slow method: %s milliseconds.\n",
                Duration.between(start, Instant.now()).toMillis()
        );
    }

    public static void routeAllDeliveryAssignments(DeliveryManager manager) {
        Instant start = Instant.now();

        try {
            HashMap<DeliveryAssignment, Stack<Location>> routes = manager
                    .getAssignmentPathsParallel(manager.getAssignments());

            // TODO: Extract to printer
            for (Map.Entry<DeliveryAssignment, Stack<Location>> route : routes.entrySet()) {
                DeliveryAssignment assignment = route.getKey();
                ArtifactPrinter.printDeliveryPath(
                        route.getValue(), assignment.getSource(), assignment.getDestination());
            }
            System.out.printf(
                    "Time taken for parallel method: %s milliseconds.\n",
                    Duration.between(start, Instant.now()).toMillis()
            );
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void streamRoutedDeliveries(DeliveryManager manager) {
        Instant start = Instant.now();

        // TODO: getAssugments is silly
        manager.batchDeliveryRoutes(manager.getAssignments())
                .forEachOrdered(d ->
                        ArtifactPrinter.printDeliveryPath(
                                d.getPath(),
                                d.getAssignment().getSource(),
                                d.getAssignment().getDestination()
                        )
                );
        System.out.printf(
                "Time taken for streaming routed deliveries: %s milliseconds.\n",
                Duration.between(start, Instant.now()).toMillis()
        );
    }
}



