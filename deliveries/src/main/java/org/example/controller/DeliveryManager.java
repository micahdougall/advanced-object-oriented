package org.example.controller;

import org.example.entities.*;
import org.example.model.DeliveryNetwork;
import org.example.util.ArtifactReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


public class DeliveryManager {

    private HashSet<DeliveryRoute> routes;
    private HashSet<DeliveryAssignment> assignments;

    public HashMap<DeliveryAssignment, Stack<Location>> getAssignmentPaths(
            HashSet<DeliveryAssignment> assignments) {

        HashMap<DeliveryAssignment, Stack<Location>> routes = new HashMap<>();
        for (DeliveryAssignment assignment : assignments) {
            routes.put(assignment, getOptimalPath(assignment));
        }
        return routes;
    }

    // TODO: Cost of list vs Stack
    public Stack<Location> getOptimalPath(DeliveryAssignment assignment) {

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

        while (!path.contains(startLocation)) {
            path.push(targetLocation);
            targetLocation = network.getNode(targetLocation.getParent());
        }

//        return null;
        Collections.reverse(path);
        return path;
    }

    public void parseRoutes(String filePath) throws IOException {
        int lines = ArtifactReader.lineCount(filePath);
        routes = new HashSet<>(lines, 1);

        Scanner in = new Scanner(new File(filePath));

        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");

//            double cost = Double.parseDouble(line[3]);


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

//            if (routes.contains(newRoute) && r)
            routes.add(newRoute);
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

    public HashSet<DeliveryRoute> getRoutes() {
        return routes;
    }
}
