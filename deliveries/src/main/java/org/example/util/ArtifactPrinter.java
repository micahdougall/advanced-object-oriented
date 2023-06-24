package org.example.util;

import org.example.controller.DeliveryManager;
import org.example.entities.Coordinate;
import org.example.entities.Location;
import org.example.entities.DeliveryRoute;

import java.util.*;

public class ArtifactPrinter {

    // TODO: Restrict print count
    public static <E> void printReport(Collection<E> artifacts) {
        System.out.printf("Printing report for %d artifacts...\n", artifacts.size());

        for (E record : artifacts) {
            System.out.printf("\t->%s\n", record);
        }
    }

    public static void findRoutes(DeliveryManager manager) {
        Coordinate start = new Coordinate(50, 95);
        Coordinate end = new Coordinate(62, 14);

        Optional<DeliveryRoute> optional = manager.findRoute(start, end);

        try {
            System.out.printf("Route for coordinates: (%s -> %s) => %s\n", start, end, optional.get());
        } catch (NoSuchElementException e) {
            System.out.printf("Item not found for coordinates: (%s -> %s)\n", start, end);
        }
    }

    public static void printOrderedCoordinates(Collection<Location> coordinates) {
        System.out.println("Printing ordered coordinates...");
        for (Location coordinate : coordinates) {
            System.out.printf("\t->%s\n", coordinate);
        }
    }

    public static void printDeliveryPath(Stack<Location> path, Coordinate start, Coordinate end) {
        System.out.printf(
                "Printing optimal path from %s to %s (total cost=%.2f)\n",
                start, end, path.peek().getCost());
        for (Location location : path) {
            System.out.printf("\t ->%s\n", location.getPoint());
        }
    }
}
