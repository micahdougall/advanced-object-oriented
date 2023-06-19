package org.example;

import org.example.controller.DeliveryManager;
import org.example.entities.Coordinate;
import org.example.entities.DeliveryAssignment;
import org.example.entities.DeliveryRoute;
import org.example.util.ArtifactPrinter;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test...");
//        TODO: Run tests if possible...

        System.out.println("Run..");

//        TODO: Make this a singleton
        DeliveryManager manager = new DeliveryManager();

        loadRoutes(manager);
        loadAssignments(manager);

//        TODO: Tidy up toString and colors
//        ArtifactPrinter.printReport(manager.getRoutes());
//        ArtifactPrinter.printReport(manager.getAssignments());

//        HashSet assignments = manager.deDuplicateAssignments();
        manager.deDuplicateAssignments();
//        ArtifactPrinter.printReport(manager.getDistinctRoutes());

//        TODO: Use iterator for hashset

//        System.out.println("Comparing assignments");
//        compareAssignments(controller);

//        ArtifactPrinter.findRoutes(manager);

        DeliveryRoute route = manager.findRoute(
                new Coordinate(50, 95),
                new Coordinate(62, 14)
        ).get();
        System.out.println(route);

        manager.getOptimalPath(manager.getAssignments().get(0));

    }


    public static void loadRoutes(DeliveryManager manager) {
        String routeFile = "src/main/resources/small_routes.txt";
        try {
            manager.parseRoutes(new File(routeFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void loadAssignments(DeliveryManager manager) {
        String assignmentFile = "src/main/resources/small_assignments.txt";
        try {
            manager.parseAssignments(new File(assignmentFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void compareAssignments(DeliveryManager manager) {
        DeliveryAssignment test = manager.getAssignments().get(1);
        for (DeliveryAssignment assignment : manager.getAssignments()) {
//            if (manager.getAssignments().contains(assignment)   ) {
            if (assignment.equals(test)) {
                System.out.printf(" -> %s\n", assignment);
            }
        }
    }

    public static void testDeliveryRouteSearch() {

    }

    public static void testOptimalRoute() {
//        print score for each

    }

    public static void testMapping() {

    }

    public static void testOutputDeliveryRoutes() {

    }
}



