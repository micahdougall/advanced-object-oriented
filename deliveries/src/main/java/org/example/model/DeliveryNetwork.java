package org.example.model;

import org.example.entities.Coordinate;
import org.example.entities.DeliveryRoute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.*;

// TODO: Any benefit from Lombok here?
@Data
public class DeliveryNetwork {
//    @Setter(AccessLevel.NONE)
//    private HashMap<Coordinate, LinkedList<Coordinate>> nodes = new HashMap<>();
    private HashMap<Coordinate, HashSet<Coordinate>> nodes = new HashMap<>();

    @Setter(AccessLevel.NONE)
    private HashMap<Coordinate, Double> costs = new HashMap<>();

    // Set of all coordinates
//    = new HashSet<>(network.getCosts().keySet());

    private LinkedHashSet<Coordinate> unVisited = new LinkedHashSet<>();

//    private LinkedList<Coordinate> addNode(Coordinate node) {
    private HashSet<Coordinate> addNode(Coordinate node) {
        if (!nodes.containsKey(node)) {
//            nodes.put(node, new LinkedList<>());
            nodes.put(node, new HashSet<>());
        }
        return nodes.get(node);
    }

    public void addDeliveryRoute(DeliveryRoute route) {
        Coordinate startNode = route.getStart();
        Coordinate endNode = route.getEnd();
//        System.out.printf("Adding new route with %s, %s\n", startNode, endNode);

        // Join nodes in LinkedList whilst adding to HashMap
//        LinkedList<Coordinate> startAdjacents = addNode(startNode);
        HashSet<Coordinate> startAdjacents = addNode(startNode);
//        System.out.println(startAdjacents);
//        System.exit(0);
        startAdjacents.add(endNode);

//        LinkedList<Coordinate> endAdjacents = addNode(endNode);
        HashSet<Coordinate> endAdjacents = addNode(endNode);
        endAdjacents.add(startNode);

        setCost(startNode, Double.MAX_VALUE);
        setCost(endNode, Double.MAX_VALUE);

//        System.out.printf("Nodes created for %s and %s\n", startNode, endNode);
    }

    public void breadthFirstList(Coordinate currentNode) {
        HashSet<Coordinate> remaining = new HashSet<>(nodes.keySet());

        unVisited.add(currentNode);
        remaining.remove(currentNode);

        System.out.printf("Starting at node: %s\n", currentNode);

        HashSet<Coordinate> children = nodes.get(currentNode);
        Iterator<Coordinate> iter = children.iterator();

        while (!remaining.isEmpty()) {
//            LinkedList<Coordinate> children = nodes.get(currentNode);
            printRemaining(currentNode, children, remaining);


            for (Coordinate node : children) {
                unVisited.add(node);
                remaining.remove(node);
            }
            printRemaining(currentNode, children, remaining);

            Coordinate childNode;
            while (iter.hasNext()) {
                childNode = iter.next();
                children = nodes.get(childNode);
                for (Coordinate node : children) {
                    unVisited.add(node);
                    remaining.remove(node);
                }
                printRemaining(childNode, children, remaining);
            }
            if (currentNode.getX() == 52) System.exit(0);
//            System.exit(0);
        }
    }

    public void printRemaining(
            Coordinate current,
            HashSet<Coordinate> children,
            HashSet<Coordinate> left
    ) {
        System.out.printf(
                "\nCurrent node is (%s, %s) with %s children and %s remaining nodes\n",
                current.getX(), current.getY(), children.size(), left.size());
//        for (Coordinate n : left) {
//            System.out.printf("  ->  (%s, %s)\n", n.getX(), n.getY());
//        }
        System.out.println(children);
        left.stream()
            .sorted()
            .forEach(n ->
                System.out.printf(
                    "  ->  (%s, %s)\n",
                    n.getX(), n.getY())
            );
    }

    public void setCost(Coordinate location, Double cost) {
        if (costs.get(location) == null || cost < costs.get(location)) {
            costs.replace(location, cost);
        }
    }
}

