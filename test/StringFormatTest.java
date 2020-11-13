import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.ChangeColor;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.AbstractShape;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;

import static cs5004.animator.model.StringFormat.getBehaviorDescription;
import static cs5004.animator.model.StringFormat.getColorString;
import static cs5004.animator.model.StringFormat.getDimensionsString;
import static cs5004.animator.model.StringFormat.getPositionString;
import static cs5004.animator.model.StringFormat.getSVGColor;
import static cs5004.animator.model.StringFormat.getShapeDescription;
import static cs5004.animator.model.StringFormat.shapeSVG;
import static org.junit.Assert.assertEquals;

/**
 * This is the test class for the StringFormat class. All tests passed.
 */
public class StringFormatTest {
  private AbstractShape rectangle;
  private AbstractShape oval;
  private Behavior move;
  private Behavior changeColor;
  private Behavior scale;


  @Before
  public void setUp() {
    rectangle = new Rectangle("rectangle", new Point(0, 0), new Color((float) 1.0,
            0, 0), 1, 1, 1, 2);
    oval = new Oval("oval", new Point(-1, 2), new Color((float) 1, 1, 1),
            5, 2, 0, 3);
    move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2, 5, 2, 3, 3);
    changeColor = new ChangeColor(oval, 0, 1, new Color((float) 1, 1, 1),
            new Color((float) 1.0, 1, 0));
  }

  @Test
  public void testGetColorString() {
    assertEquals("(1.0,1.0,1.0)", getColorString(Color.WHITE));
    assertEquals("(0.0,0.0,0.0)", getColorString(Color.BLACK));
    assertEquals("(1.0,0.0,0.0)", getColorString(Color.RED));
  }

  @Test
  public void testGetPositionString() {
    assertEquals("(0.0,0.0)", getPositionString(new Point(0, 0)));
    assertEquals("(-1.0,2.0)", getPositionString(new Point(-1, 2)));
    assertEquals("(2.0,-2.0)", getPositionString(new Point(2, -2)));
    assertEquals("(-1.0,-2.0)", getPositionString(new Point(-1, -2)));
    assertEquals("(1.0,5.0)", getPositionString(new Point(1, 5)));
    assertEquals("(-1.5,2.1)", getPositionString(new Point(-1.48, 2.1)));
    assertEquals("(5.3,-3.0)", getPositionString(new Point(5.34, -3)));
  }

  @Test
  public void testGetShapeDescription() {
    assertEquals("Name: rectangle\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", getShapeDescription(rectangle));
    assertEquals("Name: oval\n" +
            "Type: oval\n" +
            "Center: (-1.0,2.0), X radius: 5.0, Y radius: 2.0, Color: (1.0,1.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=3", getShapeDescription(oval));
  }

  @Test
  public void testGetBehaviorDescription() {
    assertEquals("Shape rectangle moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1",
            getBehaviorDescription(move));
    assertEquals("Shape oval scales from X radius: 5.0, Y radius: 2.0 to X radius: 3.0, "
            + "Y radius: 3.0 from t=1 to t=2", getBehaviorDescription(scale));
    assertEquals("Shape oval changes color from (1.0,1.0,1.0) to (1.0,1.0,0.0) "
            + "from t=0 to t=1", getBehaviorDescription(changeColor));
  }

  @Test
  public void testGetDimensionsString() {
    assertEquals("Width: 1.0, Height: 1.0", getDimensionsString(rectangle));
    assertEquals("X radius: 5.0, Y radius: 2.0", getDimensionsString(oval));
  }

  @Test
  public void testGetSVGColor() {
    assertEquals("rgb(255,0,0)\" ", getSVGColor(rectangle.getColorData()));
    assertEquals("rgb(255,255,255)\" ", getSVGColor(oval.getColorData()));
  }

  @Test
  public void testShapeSVG() {
    assertEquals("<rect id=\"rectangle\" x=\"0\" y=\"0\" width=\"1\" height=\"1\" " +
            "fill=\"rgb(255,0,0)\"  visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"500ms\" />\n", shapeSVG(rectangle, 2));
    assertEquals("<ellipse id=\"oval\" cx=\"-1\" cy=\"2\" rx=\"5\" ry=\"2\" " +
            "fill=\"rgb(255,255,255)\"  visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"0ms\" />\n", shapeSVG(oval, 1));
  }
}