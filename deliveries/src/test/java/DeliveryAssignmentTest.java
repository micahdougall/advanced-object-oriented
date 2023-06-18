import org.example.entities.Coordinate;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Priority;
import org.junit.Assert;
import org.junit.Test;

import javax.rmi.CORBA.Util;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeliveryAssignmentTest {

    @Test
    public void testEquals() {
        DeliveryAssignment a = new DeliveryAssignment(
                "A", Priority.HIGH, new Coordinate(3, 4), new Coordinate(5, 6)
        );
        DeliveryAssignment b = new DeliveryAssignment(
                "B", Priority.LOW, new Coordinate(3, 4), new Coordinate(5, 6)
        );
        Assert.assertEquals(a, b);
    }


    @Test
//    public void testVariableEquals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    public void testVariableEquals() throws ReflectiveOperationException {
        DeliveryAssignment assignment = new DeliveryAssignment(
                "A", Priority.HIGH, new Coordinate(3, 4), new Coordinate(5, 6)
        );

        Coordinate fromOtherNotEqual = new Coordinate(1, 2);
        Coordinate fromOtherEqual = new Coordinate(3, 4);

        Method variableEquals = DeliveryAssignment
                .class
                .getDeclaredMethod("variableEquals", Object.class, Object.class);
        variableEquals.setAccessible(true);

        Assert.assertFalse(
                (Boolean) variableEquals.invoke(
                        assignment,
                        assignment.getSource(),
                        fromOtherNotEqual
                )
        );
        Assert.assertTrue(
                (Boolean) variableEquals.invoke(
                        assignment,
                        assignment.getSource(),
                        fromOtherEqual
                )
        );
    }
}
