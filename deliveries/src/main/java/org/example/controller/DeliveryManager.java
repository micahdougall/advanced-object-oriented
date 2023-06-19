package org.example.controller;

import org.example.entities.Coordinate;
import org.example.entities.DeliveryAssignment;
import org.example.entities.DeliveryRoute;
import org.example.entities.Priority;
import org.example.model.DeliveryNetwork;
import org.example.response.RoutedDelivery;
import org.example.util.ArtifactReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

public class DeliveryManager {

//    public DeliveryRoute parseRoute() {
////        <Start_X>,<Start_Y>,<Route_Name>,<Cost>,<End_X>,<End_Y>
//    }

    private ArrayList<DeliveryRoute> rawRoutes;

//    TODO: initialize with capacity and load factor!
//    Not thread-safe https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
    private HashSet<DeliveryRoute> routes;
    private ArrayList<DeliveryAssignment> rawAssignments;
    private HashSet<DeliveryAssignment> assignments;

    public ArrayList<DeliveryRoute> getRoutes() {
        return rawRoutes;
    }

    public HashSet<DeliveryRoute> getDistinctRoutes() {
        return routes;
    }

    public ArrayList<DeliveryAssignment> getAssignments() {
        return rawAssignments;
    }

    /**
     * Part E, F, G - Reads in a data file and calls separate methods depending
     * on the type the data.
     * @param inFile the file to read in
     * @throws FileNotFoundException in case of file not found
     */
//    public ArrayList<DeliveryRoute> parseRoutes(File inFile) throws FileNotFoundException {
    public void parseRoutes(File inFile) throws FileNotFoundException {
        Scanner in = new Scanner(inFile);
        rawRoutes = new ArrayList<>();

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

            DeliveryRoute newRoute = new DeliveryRoute(
                line[2],
                new Coordinate(
                    Integer.parseInt(line[0]),
                    Integer.parseInt(line[1])
                ),
                new Coordinate(
                    Integer.parseInt(line[4]),
                    Integer.parseInt(line[5])
                ),
                Double.parseDouble(line[3])
            );
            rawRoutes.add(newRoute);
        }
        in.close();

//        TODO: Is this efficient with lists
//        TODO: Error handling
//        return routes;
    }

//    public DeliveryAssignment parseAssignments() {
////        <Source_X>,<Source_Y>,<Priority>,<Description>,<Destination_X>,<Destination_Y>
//    }
    //public ArrayList<DeliveryAssignment> parseAssignments(File inFile) throws FileNotFoundException {
    public void parseAssignments(File inFile) throws FileNotFoundException {
        Scanner in = new Scanner(inFile);
        rawAssignments = new ArrayList<>();

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
            rawAssignments.add(newAssignments);
        }
        in.close();

//        TODO: Is this efficient with lists
//        TODO: Error handling
//        return rawAssignments;
    }
    public void parseRoutesPerformant(String filePath) throws IOException {
//        rawRoutes = new ArrayList<>();

//        TODO: Try finding line count and initialising set, comparing value before object creation
//        Could warep with sync set
        int lines = ArtifactReader.lineCount(filePath);
        routes = new HashSet<>(lines, 1);


        Scanner in = new Scanner(new File(filePath));

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

            DeliveryRoute newRoute = new DeliveryRoute(
                    line[2],
                    new Coordinate(
                            Integer.parseInt(line[0]),
                            Integer.parseInt(line[1])
                    ),
                    new Coordinate(
                            Integer.parseInt(line[4]),
                            Integer.parseInt(line[5])
                    ),
                    Double.parseDouble(line[3])
            );
//            if routes.contains()
//            TODO - Use a map?
            routes.add(newRoute);
        }
        in.close();

//        TODO: Is this efficient with lists
//        TODO: Error handling
//        return routes;
    }

    //    public DeliveryAssignment parseAssignments() {
////        <Source_X>,<Source_Y>,<Priority>,<Description>,<Destination_X>,<Destination_Y>
//    }
    //public ArrayList<DeliveryAssignment> parseAssignments(File inFile) throws FileNotFoundException {
    public void parseAssignmentsPerformant(File inFile) throws FileNotFoundException {
        Scanner in = new Scanner(inFile);
        rawAssignments = new ArrayList<>();

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
            rawAssignments.add(newAssignments);
        }
        in.close();

//        TODO: Is this efficient with lists
//        TODO: Error handling
//        return rawAssignments;
    }


//    public Set<DeliveryAssignment> deDuplicateAssignments(List<DeliveryAssignment> assignments) {
//public HashSet<DeliveryAssignment> deDuplicateAssignments() {
public void deDuplicateAssignments() {
//        keep highest priority

//        Options: HashSet, set, check on addition of objs, sort at the end
//        Set<DeliveryAssignment> deDuped = new HashSet<>();
//
//
//        deDuped.addAll(assignments);
//        System.out.printf("Assignments size after de-duplication: %s", deDuped.size());
//        return deDuped;

//        TODO: This is terribly slow - https://stackoverflow.com/questions/44144187/what-is-the-time-complexity-of-initializing-an-hashset-using-a-populated-arrayli
//        return new HashSet<>(this.rawAssignments);
        routes = new HashSet<>(this.rawRoutes);

//        TODO: Or stream:
//        return (HashSet<DeliveryAssignment>) assignments.stream()
//                .collect(Collectors.toSet());
    }

    public Optional<DeliveryRoute> findRoute(Coordinate start, Coordinate end) {
//        TODO: Needed for efficiency?
//        for (DeliveryRoute route : rawRoutes) {
//            if (route.getStart().equals(start) && route.getEnd().equals(end)) {
//                return Optional.of(route);
//            } else {
//                System.out.printf("Item not a match: %s\n", route);
//            }
//        }
//        return Optional.empty();

//        Inefficient?
        return rawRoutes.stream()
                .filter(r -> r.getStart().equals(start) && r.getEnd().equals(end))
                .findFirst();
    }

    public ArrayList<DeliveryRoute> getOptimalPath(DeliveryAssignment assignment) {
//Optimal!
//        Route 1: ( 0, 0) ---> (50,50) | Cost: 190.00 Route 2: ( 0, 0) ---> (10,20) | Cost: 55.00 Route3:(9,0)--->(0,0)|Cost: 10.50 Route 4: (10,20) ---> (50,50) | Cost: 500

        // Naive neighbours
//        ArrayList<DeliveryRoute> startNeighbours =
//                (ArrayList<DeliveryRoute>) rawRoutes
//                        .stream()
//                        .filter(r -> r.getStart().equals(assignment.getSource()))
//                        .collect(Collectors.toList());
//        Optional?
//        Needs to be ordered
//        TODO: Implement Dijkstras algorithm for path weighting

//        TODO: Improve efficiency of list streams

//        Create a map of every coordinate
        // Copy routes to new set so you can remvoe
        HashSet<Coordinate> unVisited = new HashSet<>();
//        ArrayList<Coordinate> visited = new ArrayList<>();

//        TODO: consider  size
        HashMap<Coordinate, Double> nodes = new HashMap<>();

        DeliveryNetwork network = buildNetwork();



        for (DeliveryRoute route : routes) {
            if (!nodes.containsKey(route.getStart())) {
                nodes.put(route.getStart(), Double.MAX_VALUE);
                unVisited.add(route.getStart());
            }
            // TODO: Make this a simpler addition, does it matter to overwrite?
            if (!nodes.containsKey(route.getEnd())) {
                nodes.put(route.getEnd(), Double.MAX_VALUE);
                unVisited.add(route.getEnd());
            }
        }

//        Start node
        Coordinate currentNode = assignment.getSource();

        // Update start node to having zero distance
        nodes.replace(currentNode, 0.0);


        // Get neighbour coords
//        Map<Coordinate, Double> neighbourWeights = routes
//                .stream()
//                .filter(r -> r.getStart().equals(startNode))
//                .collect(Collectors.toMap(
//                        DeliveryRoute::getEnd,
//                        r -> Double.min(r.getCost(), nodes.get(r.getEnd()))
//                ));


        Map<Coordinate, Double> neighbourWeights = new HashMap<>();

//        Start loop here
//        Do while destination not yet visited or while dest has shortest unvisited dist

//        while (nodes.get(assignment.getDestination()).equals(Double.MAX_VALUE)) {
        while (unVisited.contains(assignment.getDestination())) {


            for (DeliveryRoute route : routes) {
                if (route.getStart().equals(currentNode)) {
                    System.out.printf("Matched starting node: %s", route.getStart());
                    System.out.printf("New k-v will be: %s: %s", route.getEnd(), route.getCost());

                    double existingWeight = nodes.get(route.getEnd());
                    nodes.put(
                            route.getEnd(),
                            Double.min(
                                    route.getCost() + nodes.get(currentNode),
                                    existingWeight
                            )
                    );
                    System.out.println("successful entry");




                }
            }

            currentNode = // get next?
//                    https://en.wikipedia.org/wiki/Dijkstra's_algorithm

        }


        // Update neighbours
//        nodes.putAll(neighbourWeights);

        System.out.println(nodes);


        return null;
    }

    public DeliveryNetwork buildNetwork() {
        DeliveryNetwork network = new DeliveryNetwork();

        for (DeliveryRoute route : routes) {
            network.addDeliveryRoute(route);
        }
        return network;
    }

    public HashMap<DeliveryAssignment, ArrayList<DeliveryRoute>> assignRoutes() {
//        must be done in parallel
//        concurrency risks
        return null;
    }

    public Stream<RoutedDelivery> outputDeliveryRoutes() {
//        Using routed
//        order by priority, high first
//        freedom
        SortedSet<DeliveryAssignment> ordered = new TreeSet<>();
        return null;
    }
}
