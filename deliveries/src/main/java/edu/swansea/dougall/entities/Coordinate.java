package edu.swansea.dougall.entities;

import lombok.*;

/**
 * Class to represent a raw coordinate in the network.
 *
 * @implNote uses Lombok tags to generate a getters, setters and a constructor.
 *
 * @see Location
 */
@AllArgsConstructor
@Data public class Coordinate {

    private int x;

    private int  y;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Coordinate that = (Coordinate) obj;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
