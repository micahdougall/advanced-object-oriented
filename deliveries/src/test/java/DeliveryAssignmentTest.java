import org.example.entities.Location;
import org.example.entities.DeliveryAssignment;
import org.example.entities.Priority;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

public class DeliveryAssignmentTest {

    @Test
    public void testEquals() {
        DeliveryAssignment a = new DeliveryAssignment(
                "A", Priority.HIGH, new Location(3, 4), new Location(5, 6)
        );
        DeliveryAssignment b = new DeliveryAssignment(
                "B", Priority.LOW, new Location(3, 4), new Location(5, 6)
        );
        Assert.assertEquals(a, b);
    }


    @Test
//    public void testVariableEquals() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    public void testVariableEquals() throws ReflectiveOperationException {
        DeliveryAssignment assignment = new DeliveryAssignment(
                "A", Priority.HIGH, new Location(3, 4), new Location(5, 6)
        );

        Location fromOtherNotEqual = new Location(1, 2);
        Location fromOtherEqual = new Location(3, 4);

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
