package edu.swansea.dougall;

import com.beust.jcommander.JCommander;
import edu.swansea.dougall.controller.DeliveryManager;
import edu.swansea.dougall.entities.Coordinate;
import edu.swansea.dougall.artifacts.DeliveryRoute;
import edu.swansea.dougall.entities.Priority;
import edu.swansea.dougall.util.Colors;
import edu.swansea.dougall.util.Printer;
import edu.swansea.dougall.artifacts.DeliveryAssignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;


public class Main {

    /**
     * Whether to print debug messages to the console, assigned from the CLI args.
     */
    public static boolean debug;

    public static void main(String[] argv) {

        // CLI args
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(argv);
        debug = args.debug;

        DeliveryManager manager = new DeliveryManager();

        // Test Assignment equality
        testAssignmentEquality();

        // Load data and de-duplicate
        loadRoutes(manager, args.routes, args.print);
        loadAssignments(manager, args.assignments, args.print);

        // Print 5 random routes from assignment coordinates
        findRoutes(manager, 5);

        // Test path calculations
        routeDeliveryAssignment(manager, 5);

        // Test all paths
        // Exclude slow-running implementation from large files
        if (!args.routes.contains("large")) {
            routeAllDeliveryAssignmentsSlow(manager, args.print);
        }
        routeAllDeliveryAssignments(manager, args.print, args.threads);

        // Print all RoutedDeliveries for smaller files to show priority sorting
        int maxRecords = (args.routes.contains("large") ? args.print : Integer.MAX_VALUE);
        streamRoutedDeliveries(manager, maxRecords);
    }

    public static void testAssignmentEquality() {
        DeliveryAssignment a = new DeliveryAssignment(
                "A", Priority.HIGH, new Coordinate(1, 2), new Coordinate(10, 20)
        );
        DeliveryAssignment b = new DeliveryAssignment(
                "B", Priority.LOW, new Coordinate(1, 2), new Coordinate(10, 20)
        );
        DeliveryAssignment c = new DeliveryAssignment(
                "C", Priority.HIGH, new Coordinate(100, 200), new Coordinate(10, 20)
        );
        try {
            assert (a.equals(b));
            assert (!a.equals(c));
        } catch (AssertionError e) {
            Printer.warning("Assertion error in testAssignmentEquality");
            throw e;
        }
        Printer.info("\nAssignment equality tests passed\n", Colors.ANSI_BOLD_WHITE);
    }



    /**
     * Load delivery routes into the {@code DeliveryManager} controller by parsing a local file
     * provided as a command line argument.
     * <p>
     * The {@code maxRecords} parameter limits the number of records printed to the console.
     *
     * @param manager the {@code DeliveryManager} controller to load the routes into.
     * @param routes filename of the file containing the routes to be loaded.
     * @param maxRecords number of records to print after file has been ingested.
     */
    public static void loadRoutes(DeliveryManager manager, String routes, int maxRecords) {
        String routeFile = String.format("src/main/resources/%s", routes);
        try {
            manager.parseRoutes(routeFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Printer.report(manager.getRoutes(), maxRecords);
    }

    /**
     * Load delivery assignments into the {@code DeliveryManager} controller by parsing a local
     * file provided as a command line argument.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param assignments filename of the file containing the assignments to be loaded.
     * @param maxRecords number of records to print after file has been ingested.
     */
    public static void loadAssignments(
            DeliveryManager manager, String assignments, int maxRecords
    ) {
        String assignmentFile = String.format("src/main/resources/%s", assignments);
        try {
            manager.parseAssignments(new File(assignmentFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Printer.report(manager.getAssignments(), maxRecords);
    }

    /**
     * Test the {@code DeliveryManager} controller's {@code findRoute} method by selecting a
     * random set of coordinates from the {@code DeliveryManager} controller's {@code assignments}.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param count number of random routes to select.
     */
    public static void findRoutes(DeliveryManager manager, int count) {
        Printer.heading(
                "Finding route details from randomly selecting assignment coordinates...");

        for (int i = 0; i < count; i++) {
            int random = new Random().nextInt(manager.getAssignments().size());
            DeliveryRoute randomRoute = manager.getRoutes().get(random);

            Coordinate start = randomRoute.getStart();
            Coordinate end = randomRoute.getEnd();

            Optional<DeliveryRoute> route = manager.findRoute(start, end);
            if (route.isPresent()) {
                String routeString = String.format("%s⤑%s", start, end);
                Printer.info(
                        String.format("Route for coordinates: %21s %s", routeString, route.get()),
                        Colors.ANSI_BOLD_WHITE);
            } else {
                Printer.heading(String.format("Item not found for coordinates: %s⤑%s", start, end));
            }
        }

    }

    /**
     * Test the {@code DeliveryManager} controller's {@code getOptimalPath} method by selecting
     * random {@code DeliveryAssignment} objects from the {@code DeliveryManager}.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param count number of random routes to select.
     */
    public static void routeDeliveryAssignment(DeliveryManager manager, int count) {
        Printer.heading("Finding optimal paths for randomly selecting assignments...");

        for (int i = 0; i < count; i++) {
            int random = new Random().nextInt(manager.getAssignments().size());
            DeliveryAssignment assignment = manager.getAssignments().get(random);

            Optional<Stack<DeliveryRoute>> path = manager.getOptimalPath(assignment);
            if (path.isPresent()) {
                Printer.deliveryPath(
                        path.get(), assignment.getPriority(), assignment.getSource(),
                        assignment.getDestination());
            } else {
                Printer.warning(
                        String.format("No route available for assignment: %s", assignment)
                );
            }
        }
    }

    /**
     * Perform optimal search for all {@code DeliveryAssignment} objects in the {@code
     * DeliveryManager} controller's {@code assignments} list.
     *
     * @implNote This method uses the
     * {@link DeliveryManager#getAssignmentPaths(ArrayList)} method which manually iterates
     * through the assignments list, so the performance is slower for larger files.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param maxRecords number of records to print after file has been ingested.
     */
    public static void routeAllDeliveryAssignmentsSlow(DeliveryManager manager, int maxRecords) {
        Printer.heading("Printing optimal paths without threading...");
        Instant start = Instant.now();

        HashMap<DeliveryAssignment, Stack<DeliveryRoute>> routes = manager
                .getAssignmentPaths(manager.getAssignments());

        Printer.multiplePaths(routes, maxRecords);
        Printer.timeTaken("sequential", Duration.between(start, Instant.now()).toMillis());
    }

    /**
     * Perform optimal search for all {@code DeliveryAssignment} objects in the {@code
     * DeliveryManager} controller's {@code assignments} list. This method uses parallel
     * processing to speed up the search.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param maxRecords number of records to print after file has been ingested.
     *
     * @see edu.swansea.dougall.controller.DeliveryManager#getAssignmentPathsParallel(ArrayList, int)
     */
    public static void routeAllDeliveryAssignments(
            DeliveryManager manager, int maxRecords, int threads
    ) {
        Printer.heading("Printing optimal paths with parallel processing...");
        Instant start = Instant.now();

        try {
            HashMap<DeliveryAssignment, Stack<DeliveryRoute>> routes = manager
                    .getAssignmentPathsParallel(manager.getAssignments(), threads);

            Printer.multiplePaths(routes, maxRecords);
            Printer.timeTaken("parallel", Duration.between(start, Instant.now()).toMillis());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Perform optimal search for all {@code DeliveryAssignment} objects in the {@code
     * DeliveryManager} controller's {@code assignments} list. This method uses Java 8 streams to
     * create BatchDeliveryRoute objects which are then printed to the console.
     *
     * @implNote This method uses the {@link DeliveryManager#batchDeliveryRoutes()}.
     *
     * @param manager the {@code DeliveryManager} controller to load the assignments into.
     * @param maxRecords number of records to print after file has been ingested.
     */
    public static void streamRoutedDeliveries(DeliveryManager manager, int maxRecords) {
        Printer.heading("Printing optimal paths with streaming, in priority order...");
        Instant start = Instant.now();

        manager.batchDeliveryRoutes()
                .limit(maxRecords)
                .forEachOrdered(d ->
                        Printer.deliveryPath(
                                d.getPath(),
                                d.getAssignment().getPriority(),
                                d.getAssignment().getSource(),
                                d.getAssignment().getDestination()
                        )
                );
        if (maxRecords < manager.getAssignments().size()) {
            Printer.omitted(manager.getAssignments().size() - maxRecords);
        }
        Printer.timeTaken("streaming", Duration.between(start, Instant.now()).toMillis());
    }
}



