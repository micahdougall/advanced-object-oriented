//package org.example.model;
//
//import lombok.AccessLevel;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.example.entities.Coordinate;
//
//import java.util.HashSet;
//
//@Data @RequiredArgsConstructor
//public class Location {
//    private final Coordinate point;
//
////    @Setter(AccessLevel.NONE)
////    private HashSet<Location> neighbours;
//    // TODO: Consider using coordinates instead to avoid recursion
//
////    public boolean joinNode(Location node) {
////        if (!neighbours.contains(node)) {
////            return neighbours.add(node);
////        }
////        return false;
////    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Location location = (Location) o;
//        return point.equals(location.point);
//    }
//
//    @Override
//    public int hashCode() {
//        return 31 * point.hashCode();
//    }
//}
