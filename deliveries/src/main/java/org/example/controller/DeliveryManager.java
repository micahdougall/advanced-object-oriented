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
//        TODO: Implement Dijkstras algorithm for path weighting
//        TODO: Improve efficiency of list streams
//        TODO: consider  size

        DeliveryNetwork network = buildNetwork();
//        HashMap<Coordinate, LinkedList<Coordinate>> nodesMap = network.getNodes();
        HashMap<Coordinate, HashSet<Coordinate>> nodesMap = network.getNodes();

        // Set of all coordinates
//        HashSet<Coordinate> removableSet = new HashSet<>(network.getCosts().keySet());

        // Breadth first order from starting node
        Coordinate currentNode = assignment.getSource();
        Coordinate target = assignment.getDestination();
        System.out.println("Pre BFS");

        network.breadthFirstList(currentNode);
        System.out.println("Network is BFS ready");

        // Update start node to having zero distance
        network.setCost(currentNode, 0.0);

        LinkedHashSet<Coordinate> unVisited = network.getUnVisited();
        Iterator<Coordinate> iter = unVisited.iterator();

        HashMap<Coordinate, Double> costs = network.getCosts();
        HashSet<Coordinate> neighbours;
        Double routeCost;

        // Do while destination not yet visited
        // or while dest has shortest unvisited dist
        while (unVisited.contains(target) && iter.hasNext()) {
            routeCost = costs.get(currentNode);
//            neighbours = nodesMap.get(currentNode);
            neighbours = nodesMap.get(currentNode);

            // TODO: Not quite right because don't visit visited nodes

            for (Coordinate node : neighbours) {
                network.setCost(node, routeCost + costs.get(node));
            }
            unVisited.remove(currentNode);
            currentNode = iter.next();
        }

        System.out.println(network);


        return null;
    }

//    private void addNodeChildren(Coordinate parent) {
//        for (Coordinate node : nodes.get(parent)) {
//            unVisited.add(node);
//            removableSet.remove(node);
//        }
//    }

    // TODO: Either use bulider pattern or not
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
