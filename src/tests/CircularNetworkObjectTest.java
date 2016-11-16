package tests;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;


public class CircularNetworkObjectTest {

    private CircularNetworkObject object;

    @Before
    public void setUpObject() {
        /* circle radius is 20 */
        object = new CircularNetworkObject(100, 100);
    }

    @Test
    public void clickingOnEdgeShouldNotRegisterCollision() {
        assertFalse(object.isClicked(new Point(119, 199)));
    }

    @Test
    public void clickingInsideOnNegativeSideShouldRegisterCollision() {
        assertTrue(object.isClicked(new Point(86, 86)));
    }

    @Test
    public void clickingOnHorizontalLimitShouldRegisterCollision() {
        assertTrue(object.isClicked(new Point(120, 100)));
    }
}