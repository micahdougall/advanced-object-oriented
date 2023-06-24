package org.example.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Location;

import java.util.Stack;

@Data @AllArgsConstructor
public class RoutedDelivery implements Comparable<RoutedDelivery> {

    private DeliveryAssignment assignment;

    // TODO: Convert to DeliveryRoute
//    private LinkedList<DeliveryRoute> path;
    private Stack<Location> path;
    private double cost; // is this the best

    @Override
    public int compareTo(RoutedDelivery other) {
        return this.assignment.compareTo(other.getAssignment());
    }
}
