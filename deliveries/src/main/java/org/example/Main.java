package org.example;

import org.example.controller.DeliveryManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        DeliveryManager manager = new DeliveryManager();

        loadRoutes(manager);
        loadAssignments(manager);

        // Proof
//        ArtifactPrinter.printReport(manager.getRoutes());
//        ArtifactPrinter.printReport(manager.getAssignments());
//        ArtifactPrinter.findRoutes(manager);

//        DeliveryRoute route = manager.findRoute(
//                new Coordinate(50, 95),
//                new Coordinate(62, 14)
//        ).get();
//        System.out.println(route);



        manager.getOptimalPath(manager.getAssignments().stream().findFirst().get());
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
}



