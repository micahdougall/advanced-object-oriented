package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

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
        return "DeliveryRoute{" +
                "name='" + name + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", cost=" + cost +
                '}';
    }
}
