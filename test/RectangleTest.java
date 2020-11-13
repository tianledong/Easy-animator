import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This is the test class for the Rectangle class. All tests passed.
 */
public class RectangleTest {
  private Shape rectangle;

  @Before
  public void setUp() {
    rectangle = new Rectangle("rectangle", new Point(0, 0),
            Color.BLACK, 2, 2, 1, 5);
  }

  @Test
  public void testGetDimension1Tag() {
    assertEquals("Width: ", rectangle.getDimension1Tag());
  }

  @Test
  public void testGetDimension2Tag() {
    assertEquals("Height: ", rectangle.getDimension2Tag());
  }

  @Test
  public void testGetPositionTag() {
    assertEquals("Min corner: ", rectangle.getPositionTag());
  }

  @Test
  public void testGetCopyShape() {
    Shape testRectangle = rectangle.copyShape();
    assertEquals("Name: rectangle\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 2.0, Height: 2.0, Color: (0.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=5", testRectangle.toString());
    testRectangle.setColor(Color.BLUE);
    assertEquals(Color.blue, testRectangle.getColorData());
    assertNotEquals(Color.blue, rectangle.getColorData());
    assertNotEquals(testRectangle.toString(), rectangle.toString());
  }

  @Test
  public void testSVGPositionTagX() {
    assertEquals("x", rectangle.svgPositionTagX());
  }

  @Test
  public void testSVGPositionTagY() {
    assertEquals("y", rectangle.svgPositionTagY());
  }

  @Test
  public void testSVGBeginTag() {
    assertEquals("<rect id=\"", rectangle.svgBeginTag());
  }

  @Test
  public void testSVGEndTag() {
    assertEquals("</rect>\n", rectangle.svgEndTag());
  }

  @Test
  public void testDimension1SVGTag() {
    assertEquals("width", rectangle.dimension1SVGTag());
  }

  @Test
  public void testDimension2SVGTag() {
    assertEquals("height", rectangle.dimension2SVGTag());
  }
}