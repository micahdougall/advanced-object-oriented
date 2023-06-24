package org.example.entities;


import lombok.*;

import java.util.HashSet;

@EqualsAndHashCode @RequiredArgsConstructor
@Data public class Location implements Comparable {

    @NonNull
    private Coordinate point;

    private Double cost = null;

    private Coordinate parent = null;

    @Setter(AccessLevel.NONE)
    private HashSet<Coordinate> children = new HashSet<>();

    public HashSet<Coordinate> getChildren() {
        return children;
    }

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
