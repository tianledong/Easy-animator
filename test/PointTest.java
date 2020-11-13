import org.junit.Test;

import cs5004.animator.model.shape.Point;

import static org.junit.Assert.assertEquals;

/**
 * This is the test class for the Point class. All tests passed.
 */
public class PointTest {

  @Test
  public void testGetX() {
    assertEquals(1, new Point(1, 1).getX(), 0.0001);
    assertEquals(0, new Point(0, 0).getX(), 0.0001);
    assertEquals(-1, new Point(-1, 33334).getX(), 0.0001);
    assertEquals(3.234, new Point(3.234, -1.255).getX(), 0.0001);
    assertEquals(-1.486, new Point(-1.4861, 0).getX(), 0.0001);
  }

  @Test
  public void testGetY() {
    assertEquals(1, new Point(-9, 1).getY(), 0.0001);
    assertEquals(0, new Point(1, 0).getY(), 0.0001);
    assertEquals(-1, new Point(3, -1).getY(), 0.0001);
    assertEquals(1.0867, new Point(0.37, 1.0867).getY(), 0.0001);
    assertEquals(-90.670, new Point(11, -90.670).getY(), 0.0001);
  }
}