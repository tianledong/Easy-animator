import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.ChangeColor;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;


import static org.junit.Assert.assertEquals;

/**
 * test class for AbstractBehavior class.
 */
public class AbstractBehaviorTest {
  private Behavior move;
  private Behavior changeColor;
  private Behavior scale;
  private Shape rectangle;

  @Before
  public void setUp() {
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    Shape oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    move = new Move(rectangle, 0, 1,new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2,1,1, 3, 3);
    changeColor = new ChangeColor(oval, 0, 1, new Color((float) 1.0, 0, 0),
            new Color((float) 1.0, 1, 0));
  }

  /**
   * test for initialize. Negative start time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial() {
    new Move(rectangle, -1, 1, new Point(0, 0), new Point(1, 0));
  }

  /**
   * test for initialize. Negative end time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial1() {
    new Move(rectangle, 1, -3, new Point(0, 0),
            new Point(1, 0));
  }

  /**
   * test for initialize. Negative time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial3() {
    new Move(rectangle, -1, -1, new Point(0, 0),
            new Point(1, 0));
  }

  /**
   * test for initialize. start time is after end time.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial2() {
    new Move(rectangle, 4, 3, new Point(0, 0),
            new Point(1, 0));
  }

  /**
   * test for getDescription().
   */
  @Test
  public void testGetDescription() {
    assertEquals("Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1",
            move.getDescription());
    assertEquals("Shape R scales from X radius: 1.0, Y radius: 1.0 to X radius: 3.0,"
            + " Y radius: 3.0 from t=1 to t=2", scale.getDescription());
    assertEquals("Shape R changes color from (1.0,0.0,0.0) to (1.0,1.0,0.0)"
            + " from t=0 to t=1", changeColor.getDescription());
  }

  /**
   * test for getStartTime().
   */
  @Test
  public void testGetStartTime() {
    assertEquals(0, move.getStartTime());
    assertEquals(1, scale.getStartTime());
    assertEquals(0, changeColor.getStartTime());
  }

  /**
   * test for getEndTime().
   */
  @Test
  public void testGetEndTime() {
    assertEquals(1, move.getEndTime());
    assertEquals(2, scale.getEndTime());
    assertEquals(1, changeColor.getEndTime());
  }

  /**
   * test for getStartTime().
   */
  @Test
  public void testGetShape() {
    assertEquals("Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", move.getShape().toString());
    assertEquals("Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", scale.getShape().toString());
    assertEquals("Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", changeColor.getShape().toString());
  }
}