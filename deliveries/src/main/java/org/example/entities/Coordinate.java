package org.example.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode @AllArgsConstructor
@Data public class Coordinate implements Comparable {
    private int x;
    private int  y;

    @Override
    public int compareTo(Object o) {
        try {
            Coordinate c = (Coordinate) o;
            return x - c.getX();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
