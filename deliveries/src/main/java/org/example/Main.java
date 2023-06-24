package org.example;

import jdk.nashorn.internal.ir.Assignment;
import org.example.controller.DeliveryManager;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Location;
import org.example.util.ArtifactPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        DeliveryManager manager = new DeliveryManager();


        loadRoutes(manager);
        loadAssignments(manager);

        // Print loaded data
        ArtifactPrinter.printReport(manager.getRoutes());
        ArtifactPrinter.printReport(manager.getAssignments());

        // TODO: Show de-duping proof

        // Search for routes
        ArtifactPrinter.findRoutes(manager);

        // Test path calculations
        routeDeliveryAssignment(manager);

        // Test all paths
        routeAllDeliveryAssignments(manager);


    }


    public static void loadRoutes(DeliveryManager manager) {
        String routeFile = "src/main/resources/small_routes.txt";
        try {
            manager.parseRoutes(routeFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static void routeDeliveryAssignment(DeliveryManager manager) {
        DeliveryAssignment assignment = manager
                .getAssignments()
                .stream()
                .findFirst()
                .get();

        Stack<Location> path = manager.getOptimalPath(assignment);

        ArtifactPrinter.printDeliveryPath(
                path, assignment.getSource(), assignment.getDestination());
    }

    public static void routeAllDeliveryAssignments(DeliveryManager manager) {

        HashMap<DeliveryAssignment, Stack<Location>> routes = manager
                .getAssignmentPaths(manager.getAssignments());

        for (Map.Entry<DeliveryAssignment, Stack<Location>> route : routes.entrySet()) {
            DeliveryAssignment assignment = route.getKey();
            ArtifactPrinter.printDeliveryPath(
                    route.getValue(), assignment.getSource(), assignment.getDestination());
        }
    }
}



