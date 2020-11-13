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
import cs5004.animator.util.AnimationBuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * test for builder.
 */
public class BuilderTest {
  private AnimationBuilder<AnimatorModel> animationBuilder;

  @Before
  // set up
  public void setUp() {
    AnimatorModelImpl animatorModel = new AnimatorModelImpl();
    Shape rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 0, 1);
    Shape oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    Behavior move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    Behavior scale = new Scale(oval, 1, 2, 1, 1, 3, 3);
    Behavior changeColor = new ChangeColor(oval, 0, 1, new Color((float) 1.0, 0, 0),
            new Color((float) 1.0, 1, 0));
    animationBuilder = new AnimatorModelImpl.Builder();
  }

  @Test
  // test for build() and initialize;
  public void testBuild() {
    AnimatorModel model = animationBuilder.build();
    assertTrue(model.getShapes().isEmpty());
    assertTrue(model.getAnimations().isEmpty());
    assertEquals(1500, model.getHeight());
    assertEquals(1500, model.getWidth());
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    assertEquals(0, model.getMinTick());
    assertEquals("", model.getState());
  }

  @Test
  // test setBounds();
  public void testSetBounds() {
    AnimatorModel model = animationBuilder.build();
    assertEquals(1500, model.getHeight());
    assertEquals(1500, model.getWidth());
    assertEquals(0, model.getX());
    assertEquals(0, model.getY());
    animationBuilder.setBounds(2, 2, 1000, 1000);
    AnimatorModel model1 = animationBuilder.build();
    assertEquals(1000, model.getHeight());
    assertEquals(1000, model.getWidth());
    assertEquals(2, model.getX());
    assertEquals(2, model.getY());
  }


  @Test
  // test addAddMotion and DeclareShape
  public void testAddMotionAndDeclareShape() {
    animationBuilder.declareShape("r1", "rectangle");
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, 1, 0, 0);
    AnimatorModel model = animationBuilder.build();
    assertEquals(1, model.getShapes().size());
    assertEquals(1, model.getAnimations().size());
    assertEquals("Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (0.0,0.0,0.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=1", model.getShapes().get(0).toString());
    assertEquals("Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1",
            model.getAnimations().get(0).getDescription());
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            -1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion1() {
    animationBuilder.addMotion("r1", -1, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion2() {
    animationBuilder.addMotion("r1", 0, 0, 0, -1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion3() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, -1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion4() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, -1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion5() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, -1, 1, 0, 0,
            1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion6() {
    animationBuilder.addMotion("r1", -1, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion7() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            -1, 2, 2, 1, 1, 1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion8() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, 256, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion9() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 0, 0,
            1, 2, 2, 1, 1, -1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion10() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, -1, 0, 0,
            1, 2, 2, 1, 1, -1, 0, 0);
  }

  // bad add motion
  @Test(expected = IllegalArgumentException.class)
  public void testAddMotion11() {
    animationBuilder.addMotion("r1", 0, 0, 0, 1, 1, 1, 259, 0,
            1, 2, 2, 1, 1, -1, 0, 0);
  }
}
