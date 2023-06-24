//package org.example.archive;
//
//import lombok.Data;
//import org.example.entities.Coordinate;
//import org.example.entities.DeliveryRoute;
//
//import java.util.*;
//
//// TODO: Any benefit from Lombok here?
//@Data
//public class DeliveryNetworkArchive {
////    @Setter(AccessLevel.NONE)
////    private HashMap<Coordinate, LinkedList<Coordinate>> nodes = new HashMap<>();
//    private HashMap<Coordinate, HashSet<Coordinate>> nodes = new HashMap<>();
//
////    @Setter(AccessLevel.NONE)
////    private HashMap<Coordinate, Double> costs = new HashMap<>();
//
//    // Set of all coordinates
////    = new HashSet<>(network.getCosts().keySet());
//
//    private LinkedHashSet<Coordinate> visitSequence = new LinkedHashSet<>();
//
////    private LinkedList<Coordinate> addNode(Coordinate node) {
//    private HashSet<Coordinate> addNode(Coordinate node) {
//        if (!nodes.containsKey(node)) {
////            nodes.put(node, new LinkedList<>());
//            nodes.put(node, new HashSet<>());
//        }
//        return nodes.get(node);
//    }
//
//    public void addDeliveryRoute(DeliveryRoute route) {
//        Coordinate startNode = route.getStart();
//        Coordinate endNode = route.getEnd();
//
//        HashSet<Coordinate> startAdjacents = addNode(startNode);
//        startAdjacents.add(endNode);
//
//        // DO NOT ADD bi-directional routes
////        HashSet<Coordinate> endAdjacents = addNode(endNode);
////        endAdjacents.add(startNode);
//
////        setCost(startNode, Double.MAX_VALUE);
////        setCost(startNode, 100.0);
////        setCost(endNode, Double.MAX_VALUE);
////        setCost(endNode, 100.0);
//
////        System.out.printf("Nodes created for %s and %s with costs: %s and %s\n", startNode, endNode, costs.get(startNode), costs.get(endNode));
//    }
//
////    private int counter = 0;
//
//
////    private void addChildNodes(HashSet<Coordinate> remaining, HashSet<Coordinate> children) {
//////        if (counter > 20) {
//////            System.exit(0);
//////        } else {
//////            counter++;
//////        }
////        System.out.println("...children are " + children);
////        if (!remaining.isEmpty()) {
////            visitSequence.addAll(children);
////            remaining.removeAll(children);
////
////            HashSet<Coordinate> childNodes;
////            for (Coordinate child : children) {
////                childNodes = nodes.get(child);
////                childNodes.removeAll(visitSequence);
////                visitSequence.addAll(childNodes);
////                remaining.removeAll(childNodes);
//////                printRemaining(child, nodes.get(child), remaining);
//////                System.out.println("Adding children of " + child + " to visit sequence");
//////                addChildNodes(remaining, childNodes);
////            }
//////
////            for (Coordinate child : children) {
////                childNodes = nodes.get(child);
////                System.out.println("Adding children of " + child + " to visit sequence");
////                addChildNodes(remaining, childNodes);
////            }
////
////
////        }
////    }
//
////    public void breadthFirstList(Coordinate currentNode) {
////        HashSet<Coordinate> remaining = new HashSet<>(nodes.keySet());
////
////        visitSequence.add(currentNode);
////        remaining.remove(currentNode);
////        printRemaining(currentNode, nodes.get(currentNode), remaining);
////
////        addChildNodes(remaining, nodes.get(currentNode));
////    }
//
//    public LinkedHashSet<Coordinate> breadthFirstQueue(Coordinate startNode) {
//        LinkedHashSet<Coordinate> visitSequence = new LinkedHashSet<>();
////        HashSet<Coordinate> remaining = new HashSet<>(nodes.keySet());
//        Queue<Coordinate> queue = new LinkedList<>();
//
//        queue.add(startNode);
//        visitSequence.add(startNode);
//
//        // https://www.baeldung.com/java-graphs
//        // https://www.baeldung.com/java-breadth-first-search
//
//        while (!queue.isEmpty()) {
//            Coordinate next = queue.poll();
//            HashSet<Coordinate> children = nodes.get(next);
//
//            children.stream()
//                .filter(child -> !visitSequence.contains(child))
//                .forEach(child -> {
//                    visitSequence.add(child);
//                    queue.add(child);
//                });
////            for (Coordinate child : children) {
////                if (!visitSequence.contains(child)) {
////                    visitSequence.add(child);
////                    queue.add(child);
////                }
////            }
//        }
//        return visitSequence;
//    }
//
//    public void printRemaining(
//            Coordinate current,
//            HashSet<Coordinate> children,
//            HashSet<Coordinate> left
//    ) {
//        System.out.printf(
//                "\nCurrent node is (%s, %s) with %s children and %s remaining nodes\n",
//                current.getX(), current.getY(), children.size(), left.size());
//        System.out.println(children);
//        System.out.println(!left.isEmpty());
//        left.stream()
//            .sorted()
//            .forEach(System.out::println);
//    }
//
//    public void setCost(Coordinate location, Double cost) {
//        if (location.getCost() == null || cost < location.getCost()) {
//            System.out.println("Updating cost to " + cost);
//
//
//
//            location.setCost(cost);
//        }
//    }
//
//    public void printNetwork() {
//        System.out.println("Printing network...");
//        for (Coordinate node : nodes.keySet()) {
//            System.out.println(node);
//            for (Coordinate child : nodes.get(node)) {
//                System.out.printf("\t -> %s\n", child);
//            }
//        }
//    }
//
//    public Coordinate getNode(Coordinate coordinate) {
//        for (Coordinate node : nodes.keySet()) {
//            if (node.equals(coordinate)) {
//                return node;
//            }
//        }
//        return null;
//    }
//}
//
