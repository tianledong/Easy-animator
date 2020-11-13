import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewFactory;

import static org.junit.Assert.assertEquals;

/**
 * This is the test class for the view factory class. All tests passed.
 */
public class ViewFactoryTest {
  private ViewFactory viewFactory;
  private AnimatorModel animatorModel;
  private Behavior move;
  private Behavior scale;
  private Shape rectangle;
  private Shape oval;

  @Before
  public void setUp() {
    viewFactory = new ViewFactory();
    animatorModel = new AnimatorModelImpl();
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2, 1, 1, 3, 3);
  }

  @Test
  public void testCreateTextualView() {
    View test1 = ViewFactory.createView("text");
    assertEquals(0, test1.getShapes().size());
    animatorModel.addShape(rectangle);
    animatorModel.addAnimation(move);
    test1.setSpeed(2);
    test1.setModel(animatorModel);
    assertEquals(2, test1.getSpeed(), 0.0001);
    assertEquals(1, test1.getShapes().size());
    assertEquals("Created a rectangle r1 with Min corner: (0.0,0.0), Width: 1.0 and " +
            "Height: 1.0, Color: (1.0,0.0,0.0)\n\n\n" +
            "Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1", test1.getState(false));
  }

  @Test
  public void testCreateSVGView() {
    View test = ViewFactory.createView("svg");
    assertEquals(0, test.getShapes().size());
    animatorModel.addShape(oval);
    animatorModel.addAnimation(scale);
    assertEquals(1, test.getSpeed(), 0.0001);
    test.setSpeed(5);
    test.setModel(animatorModel);
    assertEquals(5, test.getSpeed(), 0.0001);
    assertEquals(1, test.getShapes().size());
    assertEquals("<svg viewBox=\"0 0 1000 1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<ellipse id=\"R\" cx=\"0\" cy=\"0\" rx=\"1\" ry=\"1\" fill=\"rgb(255,0,0)\"  " +
            "visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"200ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"200.0ms\" " +
            "attributeName=\"rx\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"200.0ms\" " +
            "attributeName=\"ry\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "</ellipse>\n</svg>", test.getState(false));
    test.setCanvas(0, 0, 500, 800);
    assertEquals("<svg viewBox=\"0 0 500 800\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect>\n" +
            " <animate id=\"base\" begin=\"0;base.end\" dur=\"400.0ms\" " +
            "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/> \n" +
            "</rect>\n\n" +
            "<ellipse id=\"R\" cx=\"0\" cy=\"0\" rx=\"1\" ry=\"1\" fill=\"rgb(255,0,0)\"  " +
            "visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"200ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+200ms\" dur=\"200.0ms\" " +
            "attributeName=\"rx\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+200ms\" dur=\"200.0ms\" " +
            "attributeName=\"ry\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"rx\" " +
            "to=\"1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" attributeName=\"ry\" " +
            "to=\"1\" fill=\"freeze\" />\n" +
            "</ellipse>\n</svg>", test.getState(true));
  }

  @Test
  public void testCreateVisualView() {
    View test = viewFactory.createView("visual");
    assertEquals(0, test.getShapes().size());
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    assertEquals(0, test.getShapes().size());
    test.setModel(animatorModel);
    assertEquals(2, test.getShapes().size());
  }

  @Test
  public void testInvalidView() {
    try { // pass in an empty view type string
      viewFactory.createView("");
    } catch (IllegalArgumentException e) {
      assertEquals("Unknown view type", e.getMessage());
    }

    try { // pass in a view type string that's not one of the supported types
      viewFactory.createView("132412341");
    } catch (IllegalArgumentException e) {
      assertEquals("Unknown view type", e.getMessage());
    }
  }
}