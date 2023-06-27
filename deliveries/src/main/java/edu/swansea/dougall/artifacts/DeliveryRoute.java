package edu.swansea.dougall.artifacts;

import edu.swansea.dougall.entities.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Data;
import edu.swansea.dougall.util.Colors;

import java.text.DecimalFormat;

/**
 * Class to represent a delivery route between two locations in the network. Uses
 * {@link Coordinate} to represent the start and end points of the route. The cost of the route
 * may change according to the starting point in the network.
 *
 * @implNote uses Lombok Data tags to auto-generate a constructor, getters and setters.
 */
@Data @AllArgsConstructor
public class DeliveryRoute {

    private String name;

    private Coordinate start;

    private Coordinate end;

    private double cost;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        DeliveryRoute route = (DeliveryRoute) obj;
        return start.equals(route.start) && end.equals(route.end);
    }

    @Override
    public int hashCode() {
        return (31 * start.hashCode()) + end.hashCode();
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("###,###.00");
        String currency = String.format("£%S", formatter.format(cost));
        return String.format(
                Colors.ANSI_COLOR_PURPLE + "\t ➫ " +
                Colors.ANSI_BOLD_WHITE + "%-12s " +
                Colors.ANSI_COLOR_RESET + "%14s" +
                Colors.ANSI_COLOR_GRAY + "⤑" +
                Colors.ANSI_COLOR_RESET + "%-14s" +
                Colors.ANSI_COLOR_YELLOW + "%15s" +
                Colors.ANSI_COLOR_RESET,
                name, start, end, currency
        );
    }
}
