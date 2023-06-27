package edu.swansea.dougall.entities;

import edu.swansea.dougall.artifacts.DeliveryAssignment;
import edu.swansea.dougall.util.Colors;

/**
 * Allows comparison on priority for assignments.
 *
 * @see DeliveryAssignment
 */
public enum Priority {
    HIGH, MEDIUM, LOW;

    @Override
    public String toString() {
        // Colour code the priority
        switch (this) {
            case LOW:
                return String.format(
                        Colors.ANSI_COLOR_GREEN + "%-6s" + Colors.ANSI_COLOR_RESET, this.name());
            case MEDIUM:
                return String.format(
                        Colors.ANSI_COLOR_YELLOW + "%-6s" + Colors.ANSI_COLOR_RESET, this.name());
            case HIGH:
                return String.format(
                        Colors.ANSI_COLOR_RED + "%-6s" + Colors.ANSI_COLOR_RESET, this.name());
        }
        return this.name().toString();
    }
}
