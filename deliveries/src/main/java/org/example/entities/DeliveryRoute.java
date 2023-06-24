package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.example.Colors;

import java.text.DecimalFormat;

@Data @AllArgsConstructor
public class DeliveryRoute {

    private String name;

    private Coordinate start;

    private Coordinate end;
    private double cost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DeliveryRoute route = (DeliveryRoute) o;

        if (!start.equals(route.start)) return false;
        return end.equals(route.end);
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
