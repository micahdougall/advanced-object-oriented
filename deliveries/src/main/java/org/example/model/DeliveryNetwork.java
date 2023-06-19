package org.example.model;

import org.example.entities.Coordinate;
import org.example.entities.DeliveryRoute;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;

// TODO: Any benefit from Lombok here?
@Data
public class DeliveryNetwork {
    @Setter(AccessLevel.NONE)
    private HashMap<Coordinate, LinkedList<Coordinate>> nodes;

    private LinkedList<Coordinate> addNode(Coordinate node) {
        if (!nodes.containsKey(node)) {
            return nodes.put(node, null);
        }
        return nodes.get(node);
    }

    public void addDeliveryRoute(DeliveryRoute route) {
        Coordinate startNode = route.getStart();
        Coordinate endNode = route.getEnd();

        // Join nodes in LinkedList whilst adding to HashMap
        addNode(startNode).add(endNode);
        addNode(endNode).add(startNode);

        System.out.printf("Nodes created for %s and %s\n", startNode, endNode);
    }
}

