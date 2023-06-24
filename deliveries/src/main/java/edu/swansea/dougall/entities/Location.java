package edu.swansea.dougall.entities;


import lombok.*;

import java.util.HashSet;

/**
 * Represents a location in a given route or @emphasis{node} within the delivery network,
 * providing additional information about a location in addition to its raw {@link Coordinate}.
 * <p>
 * Implements {@link Comparable} to allow for comparison of cost between locations. This provides
 * a means of prioritising lower cost routes during file parsing.
 *
 * @implNote uses Lombok Data tags to auto-generate a constructor with only the required, non-null
 * parameters. Also generates getters and setters for all fields, excluding #children. Avoids
 * using @EqualsAndHashCode and @ToString Lombok tags in order to control the implementation.
 *
 * @see Coordinate
 */
@RequiredArgsConstructor
@Data public class Location implements Comparable {

    @NonNull
    private Coordinate point;

    /**
     * Represents the cost of this location in an optimised route.
     */
    private Double cost = null;

    /**
     * Represents the preceding parent node of this location in an optimised route.
     */
    private Coordinate parent = null;

    @Setter(AccessLevel.NONE)
    private HashSet<Coordinate> children = new HashSet<>();

    /**
     * Add a child node to this location.
     *
     * @param child the child node to add
     */
    public void addChild(Coordinate child) {
        children.add(child);
    }

    @Override
    public int compareTo(Object o) {
        try {
            Location c = (Location) o;
            return (int) (cost - c.getCost());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location that = (Location) o;

        return point.equals(that.point);
    }

    @Override
    public int hashCode() {
        return point.hashCode();
    }

    @Override
    public String toString() {
        String cost = this.cost == null
                ? "null"
                : String.format("%.2f", this.cost);
        if (parent == null) {
            return String.format(
                    "%s (cost=%s) (child count=%s)",
                    point, cost, children.size());
        } else {
            return String.format(
                    "%s (cost=%s) (child count=%s) parent=(%s, %s)",
                    point, cost, children.size(), parent.getX(), parent.getY());
        }
    }
}
