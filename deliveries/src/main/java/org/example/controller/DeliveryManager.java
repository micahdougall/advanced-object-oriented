package org.example.controller;

import org.example.entities.*;
import org.example.model.DeliveryNetwork;
import org.example.response.RoutedDelivery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DeliveryManager {

    // TODO: Either these are used or not
    private ArrayList<DeliveryRoute> routes;
    private HashSet<DeliveryAssignment> assignments;

    // TODO: Accetp routes as arg for funcitonal
    public Stream<RoutedDelivery> batchDeliveryRoutes(
            HashSet<DeliveryAssignment> assignments) {

        return getAssignmentPaths(assignments)
                .entrySet().stream()
                .map(e -> new RoutedDelivery(
                        e.getKey(),
                        e.getValue(),
                        e.getValue().peek().getCost()))
                .sorted();
    }

    public HashMap<DeliveryAssignment, Stack<Location>> getAssignmentPaths(
            HashSet<DeliveryAssignment> assignments) {

        HashMap<DeliveryAssignment, Stack<Location>> routes = new HashMap<>();
        for (DeliveryAssignment assignment : assignments) {
            Optional<Stack<Location>> route = getOptimalPath(assignment);
            if (route.isPresent()) {
                routes.put(assignment, route.get());
            }
        }
        return routes;
    }

    // TODO: Check concurrency risks
    public HashMap<DeliveryAssignment, Stack<Location>> getAssignmentPathsParallel(
            HashSet<DeliveryAssignment> assignments) throws InterruptedException, ExecutionException {

        HashMap<DeliveryAssignment, Stack<Location>> routes = new HashMap<>();

        // Parallel processing
        ExecutorService service = Executors.newFixedThreadPool(6);
        HashMap<DeliveryAssignment, Future<Optional<Stack<Location>>>> futures = new HashMap<>();
        for (DeliveryAssignment assignment : assignments) {
            futures.put(assignment, service.submit(() ->  getOptimalPath(assignment)));
        };
        service.shutdown();
        while (!service.awaitTermination(500, TimeUnit.MILLISECONDS));

        // Get futures as map
        for (Map.Entry<DeliveryAssignment, Future<Optional<Stack<Location>>>> future : futures.entrySet()) {
            Optional<Stack<Location>> path = future.getValue().get();
            if (path.isPresent()) {
                routes.put(future.getKey(), path.get());
            }
        }
        return routes;
    }

    // TODO: Cost of list vs Stack
    // TODO: This should take coordinates, not assignment? And return an Optional
    public Optional<Stack<Location>> getOptimalPath(DeliveryAssignment assignment) {

//        System.out.println("Getting optimal path for assignment: " + assignment);

        DeliveryNetwork network = new DeliveryNetwork(routes);

//        System.out.println("Network built...");
//        network.printNetwork();


        // Breadth first order from starting node
        Coordinate currentNode = assignment.getSource();
        LinkedList<Coordinate> queue = network.breadthFirstQueue(currentNode);
//        System.out.println("Queue is.. " + queue);

        // Update start node to having zero distance
        network.updateCost(currentNode, 0.0, null);

        HashSet<Coordinate> neighbours;

//        Location currentLocation = network.getNode(currentNode);
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

//        network.printNetwork();

        Stack<Location> path = new Stack<>();
//        Queue<Location> path = new LinkedList<>();

        Location targetLocation = network.getNode(assignment.getDestination());
        Location startLocation = network.getNode(assignment.getSource());

        while (!path.contains(startLocation) && targetLocation != null) {
//        while (!path.contains(startLocation)) {
            path.push(targetLocation);
            targetLocation = network.getNode(targetLocation.getParent());

//            try {
//                targetLocation = network.getNode(targetLocation.getParent());
//            } catch (Exception e) {
//                System.out.println("attempted assignment: " + assignment);
//                System.out.println("No route found for target...??");
//                System.out.println(targetLocation);
//                System.out.println("Path so far: " + path);
//                throw e;
//            }
        }
        if (!path.contains(startLocation)) {
            return Optional.empty();
        }
        Collections.reverse(path);
        return Optional.of(path);
    }

    public void parseRoutes(String filePath) throws IOException {
        // TODO: Can you optimise size?
//        int lines = ArtifactReader.lineCount(filePath);
//        routes = new ArrayList<>(lines, 1);
        routes = new ArrayList<>();

        Scanner in = new Scanner(new File(filePath));

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
                    System.out.printf(
                            "Discarding %s (cost=%.2f) in favour of existing %s (cost=%.2f)\n",
                            routeName, cost, oldRoute.getName(), oldRoute.getCost()
                    );
                    continue;
                } else {
                    System.out.printf(
                            "Removing %s (cost=%.2f) in favour of new %s (cost=%.2f)\n",
                            oldRoute.getName(), oldRoute.getCost(), routeName, cost
                            );
                    routes.remove(oldRoute);
                }
            };
            routes.add(new DeliveryRoute(routeName, start, end, cost));
        }
        in.close();
    }

    public void parseAssignments(File inFile) throws FileNotFoundException {
        Scanner in = new Scanner(inFile);
        assignments = new HashSet<>();

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

            DeliveryAssignment newAssignments = new DeliveryAssignment(
                    line[3],
                    Priority.valueOf(line[2]),
                    new Coordinate(
                            Integer.parseInt(line[0]),
                            Integer.parseInt(line[1])
                    ),
                    new Coordinate(
                            Integer.parseInt(line[4]),
                            Integer.parseInt(line[5])
                    )
            );
            assignments.add(newAssignments);
        }
        in.close();
    }

    public Optional<DeliveryRoute> findRoute(Coordinate start, Coordinate end) {
        return routes.stream()
                .filter(r -> r.getStart().equals(start) && r.getEnd().equals(end))
                .findFirst();
    }


    public HashSet<DeliveryAssignment> getAssignments() {
        return assignments;
    }

    public ArrayList<DeliveryRoute> getRoutes() {
        return routes;
    }
}
