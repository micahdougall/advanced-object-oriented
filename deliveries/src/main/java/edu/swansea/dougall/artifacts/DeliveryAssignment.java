package edu.swansea.dougall.artifacts;

import edu.swansea.dougall.entities.Coordinate;
import edu.swansea.dougall.entities.Location;
import edu.swansea.dougall.entities.Priority;
import edu.swansea.dougall.util.Colors;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Class to represent a delivery assignment between two locations in the network. Implements
 * {@link Comparable} to allow for sorting by priority.
 *
 * @implNote uses Lombok Data tags to auto-generate a constructor, getters and setters.
 */
@Data @AllArgsConstructor
public class DeliveryAssignment implements Comparable<DeliveryAssignment> {

    private String description;
    private Priority priority;
    private Coordinate source;
    private Coordinate destination;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DeliveryAssignment other = (DeliveryAssignment) obj;
        return source.equals(other.source) && destination.equals(other.destination);
    }

    @Override
    public int hashCode() {
        int hashCode = 31 + source.hashCode();
        hashCode = 31 * hashCode + destination.hashCode();
        return hashCode;
    }

    /**
     * Compare the priority of this assignment to another using their priority levels.
     *
     * @param other the other assignment to compare to
     * @return -1 if this assignment has a lower priority, 0 if they are the same, 1 if
     * the other assignment has a lower priority.
     */
    @Override
    public int compareTo(DeliveryAssignment other) {
        return this.priority.compareTo(other.getPriority());
    }

    @Override
    public String toString() {
        return String.format(
                Colors.ANSI_COLOR_PURPLE + "\t ➫ %s " +
                        Colors.ANSI_COLOR_RESET + "%14s" +
                        Colors.ANSI_COLOR_GRAY + "⤑" +
                        Colors.ANSI_COLOR_RESET + "%-14s" +
                        Colors.ANSI_BOLD_WHITE + "%s" +
                        Colors.ANSI_COLOR_RESET,
                priority, source, destination, description
        );
    }
}
