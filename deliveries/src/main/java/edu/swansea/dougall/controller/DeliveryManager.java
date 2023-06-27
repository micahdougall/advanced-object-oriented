package edu.swansea.dougall.controller;

import edu.swansea.dougall.artifacts.DeliveryAssignment;
import edu.swansea.dougall.artifacts.DeliveryRoute;
import edu.swansea.dougall.entities.*;
import edu.swansea.dougall.model.DeliveryNetwork;
import edu.swansea.dougall.artifacts.RoutedDelivery;
import edu.swansea.dougall.util.Colors;
import edu.swansea.dougall.Main;
import edu.swansea.dougall.util.Printer;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Stream;


/**
 * Controller class to manage the delivery network and assign {@link DeliveryAssignment} objects.
 *
 * @implNote delegates Getter creation to Lombok.
 */
@Getter
public class DeliveryManager {

    private ArrayList<DeliveryRoute> routes;
    private ArrayList<DeliveryAssignment> assignments;


    /**
     * Batches each {@link DeliveryAssignment} with its optimal route according to the lowest
     * cost through the network from the assignment's source to destination. Uses Java 8 Streams
     * and returns the {@link RoutedDelivery} objects in sorted order, according to their priority.
     *
     * @return Stream of {@link RoutedDelivery} objects.
     */
    public Stream<RoutedDelivery> batchDeliveryRoutes() {

        return getAssignmentPaths(assignments)
                .entrySet().stream()
                .map(e -> new RoutedDelivery(
                        e.getKey(),
                        e.getValue(),
                        e.getValue().peek().getCost()))
                .sorted();
    }

    /**
     * Creates a map of {@link DeliveryAssignment} objects to their optimal route through the
     * network. Uses #getOptimalPath(DeliveryAssignment) to find the optimal route for each
     * assignment.
     *
     * @param assignments List of {@link DeliveryAssignment} objects to be routed.
     * @return Map of {@link DeliveryAssignment} objects to their optimal route.
     */
    public HashMap<DeliveryAssignment, Stack<Location>> getAssignmentPaths(
            ArrayList<DeliveryAssignment> assignments) {

        HashMap<DeliveryAssignment, Stack<Location>> routes = new HashMap<>();

        for (DeliveryAssignment assignment : assignments) {
            Optional<Stack<Location>> route = getOptimalPath(assignment);
            route.ifPresent(locations -> routes.put(assignment, locations));
        }
        return routes;
    }

    /**
     * Creates a map of {@link DeliveryAssignment} objects to their optimal route through the
     * network. Uses #getOptimalPath(DeliveryAssignment) to find the optimal route for each route.
     *
     * @implNote Uses parallel processing to execute each call to
     * {@link #getOptimalPath(DeliveryAssignment)} on a separate thread.
     *
     * @param assignments List of {@link DeliveryAssignment} objects to be routed.
     * @param threadCount Number of threads to use for parallel processing.
     * @return Map of {@link DeliveryAssignment} objects to their optimal route.
     *
     * @throws InterruptedException in case of thread interruption.
     * @throws ExecutionException in case of thread execution error.
     */
    public HashMap<DeliveryAssignment, Stack<Location>> getAssignmentPathsParallel(
            ArrayList<DeliveryAssignment> assignments, int threadCount
    ) throws InterruptedException, ExecutionException {

        HashMap<DeliveryAssignment, Stack<Location>> routes = new HashMap<>();

        // Parallel processing
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        HashMap<DeliveryAssignment, Future<Optional<Stack<Location>>>> futures = new HashMap<>();
        for (DeliveryAssignment assignment : assignments) {
            futures.put(assignment, service.submit(() ->  getOptimalPath(assignment)));
        };
        service.shutdown();
        while (!service.awaitTermination(500, TimeUnit.MILLISECONDS));

        // Get futures as map
        for (Map.Entry<DeliveryAssignment, Future<Optional<Stack<Location>>>> future : futures.entrySet()) {
            Optional<Stack<Location>> path = future.getValue().get();
            path.ifPresent(locations -> routes.put(future.getKey(), locations));
        }
        return routes;
    }

    // TODO: Cost of list vs Stack
    // TODO: This should take coordinates, not assignment? And return an Optional

    /**
     * Finds the optimal route through the network from the source to destination of the
     * {@link DeliveryAssignment}. Uses a breadth first search to find the shortest path, as
     * generated in {@link DeliveryNetwork#breadthFirstQueue(Coordinate)}. The cost of each node
     * is updated for each node in the queue in order and the final path determined by traversing
     * the network from the destination node to the source node using each {@link Location}'s
     * parent pointer.
     *
     * @param assignment {@link DeliveryAssignment} object to be routed.
     * @return Optional of Stack of {@link Location} objects representing the optimal route.
     */
    public Optional<Stack<Location>> getOptimalPath(DeliveryAssignment assignment) {

        DeliveryNetwork network = new DeliveryNetwork(routes);

        // Breadth first order from starting node
        Coordinate currentNode = assignment.getSource();
        LinkedList<Coordinate> queue = network.breadthFirstQueue(currentNode);

        // Update start node to having zero distance
        network.updateCost(currentNode, 0.0, null);

        HashSet<Coordinate> neighbours;

        Location currentLocation;
        while (!queue.isEmpty()) {
            currentNode = queue.poll();
            currentLocation = network.getNode(currentNode);

            double currentNodeCost = currentLocation.getCost();

            neighbours = currentLocation.getChildren();

            if (neighbours != null) {
                for (Coordinate node : neighbours) {
                    double routeCost = findRoute(currentNode, node).get().getCost();
                    network.updateCost(node, currentNodeCost + routeCost, currentNode);
                }
            }
        }

        Stack<Location> path = new Stack<>();

        Location targetLocation = network.getNode(assignment.getDestination());
        Location startLocation = network.getNode(assignment.getSource());

        while (!path.contains(startLocation) && targetLocation != null) {
            path.push(targetLocation);
            targetLocation = network.getNode(targetLocation.getParent());
        }
        if (!path.contains(startLocation)) {
            return Optional.empty();
        }
        Collections.reverse(path);
        return Optional.of(path);
    }

    /**
     * Parses the delivery routes from the given file path and stores them in the {@link #routes}
     * field. De-duplicates routes with the same start and end coordinates by keeping the route
     * with the lowest cost. Route names are ignored for comparison.
     *
     * @implNote prints all discards and replacements only in debug mode as defined in
     * {@link Main}, else prints a summary.
     *
     * @param filePath Path to the file containing the delivery routes.
     * @throws IOException in case of file reading error.
     */
    public void parseRoutes(String filePath) throws IOException {
        routes = new ArrayList<>();
        Scanner in = new Scanner(new File(filePath));
        int discards = 0;
        int replacements = 0;

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

            String routeName = line[2];
            double cost = Double.parseDouble(line[3]);
            Coordinate start = new Coordinate(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1])
            );
            Coordinate end = new Coordinate(
                    Integer.parseInt(line[4]),
                    Integer.parseInt(line[5])
            );

            // Optimise for routes with a lower cost
            Optional<DeliveryRoute> existing = findRoute(start, end);
            if (existing.isPresent()) {
                DeliveryRoute oldRoute = existing.get();
                if (oldRoute.getCost() <= cost) {
                    Printer.debug(
                        String.format(
                            "Discarding %s (cost=%.2f) in favour of existing %s (cost=%.2f)",
                            routeName, cost, oldRoute.getName(), oldRoute.getCost())
                    );
                    discards++;
                    continue;
                } else {
                    Printer.debug(
                        String.format("Replacing %s (cost=%.2f) with new %s (cost=%.2f)",
                               oldRoute.getName(), oldRoute.getCost(), routeName, cost)
                    );
                    replacements++;
                    routes.remove(oldRoute);
                }
            };
            routes.add(new DeliveryRoute(routeName, start, end, cost));
        }
        if (discards + replacements > 0) {
            System.out.printf(
                    Colors.ANSI_BOLD_WHITE + "Duplicates routes found!\n" +
                    Colors.ANSI_COLOR_CYAN_BOLD +
                            "Discarded %d routes and replaced %d in favour of lower costs routes.\n" +
                            Colors.ANSI_COLOR_RESET,
                    discards, replacements
            );
        }
        in.close();

    }

    /**
     * Parses the delivery assignments from the given file path and stores them in the
     * {@link #assignments} field.
     *
     * @param inFile Path to the file containing the delivery assignments.
     * @throws FileNotFoundException in case of file reading error.
     */
    public void parseAssignments(File inFile) throws FileNotFoundException {
        Scanner in = new Scanner(inFile);
        assignments = new ArrayList<>();
        int discards = 0;
        int replacements = 0;

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

            String description = line[3];
            Priority priority = Priority.valueOf(line[2]);
            Coordinate source = new Coordinate(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1])
            );
            Coordinate destination = new Coordinate(
                    Integer.parseInt(line[4]),
                    Integer.parseInt(line[5])
            );

            // Retain assignments with the highest priority
            Optional<DeliveryAssignment> existing = findAssignment(source, destination);
            if (existing.isPresent()) {
                DeliveryAssignment oldAssignment = existing.get();
                if (oldAssignment.getPriority().compareTo(priority) > 0) {
                    Printer.debug(
                        String.format(
                            "Discarding %s (priority=%s) in favour of existing %s (priority=%s)",
                                description, priority, oldAssignment.getDescription(),
                                oldAssignment.getPriority())
                    );
                    discards++;
                    continue;
                } else {
                    Printer.debug(
                            String.format(
                                    "Replacing %s (priority=%s) with new %s (priority=%s)",
                                    description, priority, oldAssignment.getDescription(),
                                    oldAssignment.getPriority())
                    );
                    replacements++;
                    assignments.remove(oldAssignment);
                }
            };
            assignments.add(new DeliveryAssignment(description, priority, source, destination));
        }
        if (discards + replacements > 0) {
            System.out.printf(
                    Colors.ANSI_BOLD_WHITE + "\n\nDuplicates routes found!\n" +
                            Colors.ANSI_COLOR_CYAN_BOLD +
                            "Discarded %d assignments and replaced %d in favour of higher " +
                            "prioritisation in the data.\n\n" +
                            Colors.ANSI_COLOR_RESET,
                    discards, replacements
            );
        }
        in.close();
    }

    /**
     * Finds the {@link DeliveryRoute} with the given start and end coordinates. Returns an
     * {@link Optional} in case the route is not found.
     *
     * @param start Start coordinate of the route.
     * @param end End coordinate of the route.
     * @return Optional of the {@link DeliveryRoute} object.
     */
    public Optional<DeliveryRoute> findRoute(Coordinate start, Coordinate end) {
        for (DeliveryRoute route : routes) {
            if (route.getStart().equals(start) && route.getEnd().equals(end)) {
                return Optional.of(route);
            }
        }
        return Optional.empty();
    }


    /**
     * Finds the {@link DeliveryAssignment} with the given source and destination coordinates. Returns
     * an {@link Optional} in case the assignment is not found.
     *
     * @param source Source coordinate of the assignment.
     * @param destination Destination coordinate of the assignment.
     * @return Optional of the {@link DeliveryAssignment} object.
     */
    public Optional<DeliveryAssignment> findAssignment(Coordinate source, Coordinate destination) {
        for (DeliveryAssignment assignment : assignments) {
            if (assignment.getSource().equals(source)
                    && assignment.getDestination().equals(destination)) {
                return Optional.of(assignment);
            }
        }
        return Optional.empty();
    }
}
