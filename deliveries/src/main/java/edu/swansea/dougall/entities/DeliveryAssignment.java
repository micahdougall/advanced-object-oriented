package edu.swansea.dougall.entities;

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
        if (obj == this) return true;
        if (obj instanceof DeliveryAssignment) {
            DeliveryAssignment other = (DeliveryAssignment) obj;
            return source.equals(other.source) && destination.equals(other.destination);
        } else return false;
    }

    @Override
    public int hashCode() {
        int hashCode = 31 + source.hashCode();
        hashCode = 31 * hashCode + destination.hashCode();
        return hashCode;
    }

    @Override
    public int compareTo(DeliveryAssignment other) {
        return this.priority.compareTo(other.getPriority());
    }

    @Override
    public String toString() {
        // Colour code the priority
        String flag = "";
        switch (priority) {
            case LOW:
                flag = String.format(
                        Colors.ANSI_COLOR_GREEN + "%-6s" + Colors.ANSI_COLOR_RESET, priority );
                break;
            case MEDIUM:
                flag = String.format(
                        Colors.ANSI_COLOR_YELLOW + "%-6s" + Colors.ANSI_COLOR_RESET, priority );
                break;
            case HIGH:
                flag = String.format(
                        Colors.ANSI_COLOR_RED + "%-6s" + Colors.ANSI_COLOR_RESET, priority );
                break;
        }
        return String.format(
                Colors.ANSI_COLOR_PURPLE + "\t ➫ %s " +
                        Colors.ANSI_COLOR_RESET + "%14s" +
                        Colors.ANSI_COLOR_GRAY + "⤑" +
                        Colors.ANSI_COLOR_RESET + "%-14s" +
                        Colors.ANSI_BOLD_WHITE + "%s" +
                        Colors.ANSI_COLOR_RESET,
                flag, source, destination, description
        );
    }
}
