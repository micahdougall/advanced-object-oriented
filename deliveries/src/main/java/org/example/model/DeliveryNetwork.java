package org.example.model;

import lombok.AccessLevel;
import lombok.Setter;
import org.example.entities.Coordinate;
import org.example.entities.Location;
import org.example.entities.DeliveryRoute;

import lombok.Data;

import java.util.*;


@Data
public class DeliveryNetwork {
    @Setter(AccessLevel.NONE)
    private ArrayList<Location> nodes;

    private LinkedList<Coordinate> visitSequence;

    private ArrayList<DeliveryRoute> routes;


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

    public Location getNode(Coordinate coordinate) {
        for (Location node : nodes) {
            if (node.getPoint().equals(coordinate)) {
                return node;
            }
        }
        return null;
    }

    public void updateCost(Coordinate point, Double cost, Coordinate parent) {
        Location location = getNode(point);
        if (location.getCost() == null || cost < location.getCost()) {

            location.setCost(cost);
            location.setParent(parent);
        }
    }

    public void printNetwork() {
        for (Location node : nodes) {
            System.out.println(node);
            for (Coordinate child : node.getChildren()) {
                System.out.printf("\t -> %s\n", child);
            }
        }
    }
}

