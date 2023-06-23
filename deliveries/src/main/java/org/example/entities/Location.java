package org.example.entities;


import lombok.*;

import java.util.HashSet;

//@EqualsAndHashCode @AllArgsConstructor
@EqualsAndHashCode @RequiredArgsConstructor
@Data public class Location implements Comparable {

//    @NonNull
//    private int x;

//    @NonNull
//    private int  y;

    @NonNull
    private Coordinate point;

    private Double cost = null;

    private Coordinate parent = null;

    @Setter(AccessLevel.NONE)
    private HashSet<Coordinate> children = new HashSet<>();

    public void addChild(Coordinate child) {
        children.add(child);
    }

    public HashSet<Coordinate> getChildren() {
        return children;
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

//    @Override
//    public int compareTo(Object o) {
//        try {
//            Coordinate c = (Coordinate) o;
//            return x - c.getX();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location that = (Location) o;

//        if ( != that.x) return false;
        return point == that.point;
    }

    @Override
    public int hashCode() {
//        int result = x;
//        result = 31 * result + y;
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
