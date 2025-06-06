package edu.swansea.dougall.artifacts;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Stack;

/**
 * Class to represent a fully routed delivery assignment whose optimal path has been
 * pre-calculated and stored in an ordered list.
 *
 * @implNote uses Lombok Data tags to auto-generate a constructor with all parameters, getters and
 * setters.
 *
 * @see DeliveryAssignment
 */
@Data @AllArgsConstructor
public class RoutedDelivery implements Comparable<RoutedDelivery> {

    private DeliveryAssignment assignment;

    private Stack<DeliveryRoute> path;

    private double cost;

    /**
     * Compare the cost of this delivery to another.
     *
     * @param other the other delivery to compare to
     * @return -1 if this assignment has a lower priority, 0 if they are the same, 1 if
     * the other delivery's assignment has a lower priority.
     */
    @Override
    public int compareTo(RoutedDelivery other) {
        return this.assignment.compareTo(other.getAssignment());
    }
}
