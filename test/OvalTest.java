import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Shape;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * This is the test class for the Oval class. All tests passed.
 */
public class OvalTest {
  private Shape oval;

  @Before
  public void setUp() {
    oval = new Oval("oval", new Point(1, 0),
            Color.BLACK, 21, 24, 2, 3);
  }

  @Test
  public void testGetDimension1Tag() {
    assertEquals("X radius: ", oval.getDimension1Tag());
  }

  @Test
  public void testGetDimension2Tag() {
    assertEquals("Y radius: ", oval.getDimension2Tag());
  }

  @Test
  public void testGetPositionTag() {
    assertEquals("Center: ", oval.getPositionTag());
  }

  @Test
  public void testGetCopyShape() {
    Shape testRectangle = oval.copyShape();
    assertEquals("Name: oval\n" +
            "Type: oval\n" +
            "Center: (1.0,0.0), X radius: 21.0, Y radius: 24.0, Color: (0.0,0.0,0.0)\n" +
            "Appears at t=2\n" +
            "Disappears at t=3", testRectangle.toString());
    testRectangle.setColor(Color.WHITE);
    assertEquals(Color.white, testRectangle.getColorData());
    assertNotEquals(Color.white, oval.getColorData());
    assertNotEquals(testRectangle.toString(), oval.toString());
  }

  @Test
  public void testSVGPositionTagX() {
    assertEquals("cx", oval.svgPositionTagX());
  }

  @Test
  public void testSVGPositionTagY() {
    assertEquals("cy", oval.svgPositionTagY());
  }

  @Test
  public void testSVGBeginTag() {
    assertEquals("<ellipse id=\"", oval.svgBeginTag());
  }

  @Test
  public void testSVGEndTag() {
    assertEquals("</ellipse>\n", oval.svgEndTag());
  }

  @Test
  public void testDimension1SVGTag() {
    assertEquals("rx", oval.dimension1SVGTag());
  }

  @Test
  public void testDimension2SVGTag() {
    assertEquals("ry", oval.dimension2SVGTag());
  }
}