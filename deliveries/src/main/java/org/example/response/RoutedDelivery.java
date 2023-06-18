package org.example.response;

import org.example.entities.DeliveryAssignment;
import org.example.entities.DeliveryRoute;

import java.util.ArrayList;

public class RoutedDelivery {

    private DeliveryAssignment assignment;
    private ArrayList<DeliveryRoute> path;
    private float cost; // is this the best
}
