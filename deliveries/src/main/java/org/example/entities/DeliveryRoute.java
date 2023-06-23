package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class DeliveryRoute {

    private String name;
    private Coordinate start;
    private Coordinate end;
    private double cost;


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
