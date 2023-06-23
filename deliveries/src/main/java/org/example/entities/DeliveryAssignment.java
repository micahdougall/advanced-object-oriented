package org.example.entities;

public class DeliveryAssignment {

    private String description;
    private Priority priority;
    private Coordinate source; // From javatuples, maybe not available
    private Coordinate destination; // From javatuples, maybe not available

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public Coordinate getSource() {
        return source;
    }

    public Coordinate getDestination() {
        return destination;
    }

    public DeliveryAssignment(
            String description, Priority priority, Coordinate source, Coordinate destination
    ) {
        this.description = description;
        this.priority = priority;
        this.source = source;
        this.destination = destination;
    }


    @Override
    public boolean equals(Object obj) {
        return true;
//        type
//        if (obj instanceof DeliveryAssignment) {
//            DeliveryAssignment other = (DeliveryAssignment) obj;
//            return this.source.equals(other.source) && this.destination.equals(other.destination);
//        } else {
//            return false;
//        }
//
//        DeliveryAssignment other = (DeliveryAssignment) obj;
//        System.out.printf("Testing equality of %s and %s...\n", this.description, other.description);


//        if (this == obj) {
//            System.out.println("Objects are the same");
//            return true;
//        }
//
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//
//        DeliveryAssignment other = (DeliveryAssignment) obj;
//        if (
//            !variableEquals(this.source, other.source) || !variableEquals(this.destination, other.destination)
//        ) {
//            System.out.println("Variables are not equal");
//            return false;
//        }
//
////        System.out.println("Variables ARE equal");
//        return true;

    }

    private boolean variableEquals(Object thisVar, Object otherVar) {
        System.out.printf("Comparing vars: %s and %s", thisVar, otherVar);
        if (
//                TODO: DOes it need both?
                !thisVar.equals(otherVar)
                || (thisVar == null && otherVar != null)
                || (thisVar != null && otherVar == null)
        ) {
            return false;
        }
        return true;
//        if
    }

    @Override
    public int hashCode() {
//        Look up string hash function for efficient example
        int hashCode = 1;
        hashCode = 31 * hashCode + this.source.hashCode();
        hashCode = 31 * hashCode + this.destination.hashCode();

        return hashCode;
    }

    @Override
    public String toString() {
//        return "DeliveryAssignment{" +
//                "description='" + description + '\'' +
//                ", priority=" + priority +
//                ", source=" + source +
//                ", destination=" + destination +
//                '}';
        return String.format(
            "(%d, %d) -> (%d, %d) [%s] - %s",
            source.getX(), source.getY(),
            destination.getX(), destination.getY(),
            this.hashCode(), description
        );
    }
}
