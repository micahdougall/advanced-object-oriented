package edu.swansea.dougall.model;

import edu.swansea.dougall.entities.Location;
import edu.swansea.dougall.entities.Coordinate;
import edu.swansea.dougall.artifacts.DeliveryRoute;

import lombok.Getter;
import java.util.*;

/**
 * Class to facilitate the routing of deliveries across a network of nodes. Builds the network on
 * instantiation from a list of {@link DeliveryRoute} objects. For each route, the start and end
 * coordinates are added to the network as {@link Location} nodes, with each start node receiving
 * the end node as a viable route from its location.
 *
 * @implNote The network is built as a directed graph, with each node having a list of children
 * to which it can route a delivery.
 *
 * @implNote Uses Lombok to generate getters for each of the Collection fields.
 */
@Getter
public class DeliveryNetwork {
    private final ArrayList<Location> nodes;

    private final LinkedList<Coordinate> visitSequence;

    private final ArrayList<DeliveryRoute> routes;


    /**
     * Constructs a new DeliveryNetwork from a list of {@link DeliveryRoute} objects by iterating
     * through each route and adding the start and end coordinates to the network as
     * {@link Location} nodes.
     *
     * @param deliveryRoutes the list of {@link DeliveryRoute} objects to build the network from.
     */
    public DeliveryNetwork(ArrayList<DeliveryRoute> deliveryRoutes) {
        this.routes = deliveryRoutes;
        nodes = new ArrayList<>();
        visitSequence = new LinkedList<>();

        for (DeliveryRoute route : routes) {
            Location startNode = new Location(route.getStart());
            Location endNode = new Location(route.getEnd());

            if (!nodes.contains(startNode)) {
                nodes.add(startNode);
            }
            getNode(route.getStart()).addChild(route.getEnd());


            if (!nodes.contains(endNode)) {
                nodes.add(endNode);
            }
        }
    }

    /**
     * Generates a queue of {@link Coordinate} nodes in the order the should be visited in for a
     * breadth-first search of the network, starting from the given {@link Coordinate} node. This
     * is effectively the first step in Dijkstra's algorithm for finding the shortest path
     * between the start node provided and a subsequent target node.
     *
     * @param startNode the {@link Coordinate} node to start the search from.
     * @return a {@link LinkedList} of {@link Coordinate} nodes in the order they should be visited.
     */
    public LinkedList<Coordinate> breadthFirstQueue(Coordinate startNode) {
        Queue<Coordinate> queue = new LinkedList<>();

        queue.add(startNode);
        visitSequence.add(startNode);

        while (!queue.isEmpty()) {
            Coordinate next = queue.poll();
//            System.out.println("Coordinate = " + next);
            HashSet<Coordinate> children = getNode(next).getChildren();

//            System.out.println("Children = " + children);
            if (children != null) {
                for (Coordinate child : children) {
                    if (!visitSequence.contains(child)) {
                        visitSequence.add(child);
                        queue.add(child);
                    }
                }
            }
        }
        return visitSequence;
    }

    /**
     * Gets a {@link Location} node from the network with the given {@link Coordinate} point.
     *
     * @param coordinate the {@link Coordinate} point to search for.
     * @return the {@link Location} node with the given {@link Coordinate} point, or null if no
     * node exists with that coordinate.
     */
    public Location getNode(Coordinate coordinate) {
        for (Location node : nodes) {
            if (node.getPoint().equals(coordinate)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Updates the cost of a {@link Location} node in the network with the given
     * {@link Coordinate} to a new cost, @emp{if} the new cost is lower, and sets the parent of
     * the node to the given {@link Coordinate} parent such that a traversal of the network in
     * reverse order will reveal which parent node to trace in order to track the lowest cost route.
     *
     * @param point the {@link Coordinate} point of the {@link Location} node to update.
     * @param cost the new cost to update the node with.
     * @param parent the {@link Coordinate} point of the parent node.
     */
    public void updateCost(Coordinate point, Double cost, Coordinate parent) {
        Location location = getNode(point);
        if (location.getCost() == null || cost < location.getCost()) {
            location.setCost(cost);
            location.setParent(parent);
        }
    }

    /**
     * Prints the network to the console, with each node and its children on a new line.
     */
    public void printNetwork() {
        for (Location node : nodes) {
            System.out.println(node);
            for (Coordinate child : node.getChildren()) {
                System.out.printf("\t -> %s\n", child);
            }
        }
    }
}

