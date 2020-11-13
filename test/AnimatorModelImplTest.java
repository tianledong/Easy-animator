import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.ChangeColor;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is the test class for the AnimatorModelImpl class. All tests passed.
 */
public class AnimatorModelImplTest {
  private AnimatorModelImpl animatorModel;
  private Behavior move;
  private Behavior changeColor;
  private Behavior scale;
  private cs5004.animator.model.shape.Shape rectangle;
  private Shape oval;

  @Before
  public void setUp() {
    animatorModel = new AnimatorModelImpl();
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2, 1, 1, 3, 3);
    changeColor = new ChangeColor(oval, 0, 1, new Color((float) 1.0, 0, 0),
            new Color((float) 1.0, 1, 0));
  }

  /**
   * test for initialized model.
   */
  @Test
  public void testModel() {
    AnimatorModel model = new AnimatorModelImpl();
    assertTrue(model.getShapes().isEmpty());
    assertTrue(model.getAnimations().isEmpty());
    assertEquals(1500, model.getHeight());
    assertEquals(1500, model.getWidth());
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(0, model.getMinTick());
    assertEquals("", model.getState());
  }

  /**
   * test for addAnimation().
   */
  @Test
  public void testAddAnimation() {
    // When the behavior target shape is not in the shape list, also add behavior shape
    // to the shape list
    animatorModel.addAnimation(move);
    Move move1 = new Move(rectangle, 2, 4, new Point(2, 2), new Point(1, 1));
    animatorModel.addAnimation(move1);
    Move move2 = new Move(rectangle, 6, 8, new Point(1, 1), new Point(10, -1));
    animatorModel.addAnimation(move2);
    assertEquals("Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1\n"
            + "Shape r1 moves from (2.0,2.0) to (1.0,1.0) from t=2 to t=4\n"
            + "Shape r1 moves from (1.0,1.0) to (10.0,-1.0) from t=6 to t=8",
            animatorModel.getState());

  }

  /**
   * test for addAnimation(). add the repeat behavior.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badAddAnimation() {
    animatorModel.addAnimation(move);
    animatorModel.addAnimation(move);
  }

  /**
   * test for addAnimation(). add the overlap behavior. same time, different point.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badAddAnimation1() {
    animatorModel.addAnimation(move);
    Move move1 = new Move(rectangle, 0, 1, new Point(0, 0),
            new Point(1, 1));
    animatorModel.addAnimation(move1);
  }

  /**
   * test for addAnimation(). add the overlap behavior. Same start time and end time > the end time
   * in the list.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badAddAnimation2() {
    Move move1 = new Move(rectangle, 0, 4, new Point(0, 0),
            new Point(1, 1));
    animatorModel.addAnimation(move1);
    animatorModel.addAnimation(move);
  }

  @Test
  public void testInvalidAddShape() {

    // test add shapes with the same name to the animator
    try {
      animatorModel.addShape(rectangle);
      animatorModel.addShape(rectangle);
    } catch (IllegalArgumentException e) {
      assertEquals("Can't add a shape that's already in the list", e.getMessage());
    }

    try {
      animatorModel.addShape(oval);
      animatorModel.addShape(oval);
    } catch (IllegalArgumentException e) {
      assertEquals("Can't add a shape that's already in the list", e.getMessage());
    }
  }

  @Test
  public void testAddShape() {
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);

    assertEquals("Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n", animatorModel.getState());
  }

  @Test
  public void testGetState() {
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    animatorModel.addAnimation(move);
    animatorModel.addAnimation(scale);
    animatorModel.addAnimation(changeColor);

    assertEquals("Name: r1\n" +
                    "Type: rectangle\n" +
                    "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
                    "Appears at t=1\n" +
                    "Disappears at t=2\n" +
                    "\n" +
                    "Name: R\n" +
                    "Type: oval\n" +
                    "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
                    "Appears at t=1\n" +
                    "Disappears at t=2\n" +
                    "\n" +
                    "Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1\n" +
                    "Shape R changes color from (1.0,0.0,0.0) to (1.0,1.0,0.0) from t=0 to t=1\n" +
                    "Shape R scales from X radius: 1.0, Y radius: 1.0 to X radius: 3.0, " +
                    "Y radius: 3.0 from t=1 to t=2",
            animatorModel.getState());
  }

  /**
   * test for getMinTick().
   */
  @Test
  public void testGetMin() {
    animatorModel.addAnimation(move);
    animatorModel.addAnimation(scale);
    animatorModel.addAnimation(changeColor);
    assertEquals(1, animatorModel.getMinTick());
  }
}