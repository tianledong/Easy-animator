import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.shape.AbstractShape;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;

import static cs5004.animator.model.StringFormat.getPositionString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * A test class for the AbstractShape class. All tests passed.
 */
public class AbstractShapeTest {
  private AbstractShape rectangle1;
  private AbstractShape oval1;

  @Before
  public void setUp() {
    rectangle1 = new Rectangle("rectangle1", new Point(0, 0), new Color((float) 1.0,
            0, 0), 1, 1, 1, 2);
    oval1 = new Oval("oval1", new Point(-1, 2), new Color((float) 1, 1, 1),
            5, 2, 0, 3);
  }

  @Test
  public void testGetColor() {
    assertEquals("(1.0,0.0,0.0)", rectangle1.getColor());
    assertEquals("(1.0,1.0,1.0)", oval1.getColor());
    assertEquals("(0.0,1.0,1.0)", new Rectangle("rectangle1",
            new Point(5, 7), Color.CYAN,
            1, 1, 1, 2).getColor());
    assertEquals("(0.0,0.0,1.0)", new Oval("oval1", new Point(-1, 2),
            Color.BLUE, 5, 2, 0, 3).getColor());
  }

  @Test
  public void testGetColorData() {
    assertEquals(Color.red, rectangle1.getColorData());
    assertEquals(Color.white, oval1.getColorData());
    assertEquals(Color.cyan, new Rectangle("rectangle1", new Point(5, 7), Color.CYAN,
            1, 1, 1, 2).getColorData());
    assertEquals(Color.blue, new Oval("oval1", new Point(-1, 2), Color.BLUE,
            5, 2, 0, 3).getColorData());
  }

  @Test
  public void testGetPosition() {
    assertEquals("(0.0,0.0)", rectangle1.getPosition());
    assertEquals("(-1.0,2.0)", oval1.getPosition());
    assertEquals("(5.0,7.0)", new Rectangle("rectangle1", new Point(5, 7),
            Color.CYAN, 1, 1, 1, 2).getPosition());
    assertEquals("(-1.0,2.0)", new Oval("oval1", new Point(-1, 2),
            Color.BLUE, 5, 2, 0, 3).getPosition());
  }

  @Test
  public void testGetPositionData() {
    assertEquals(0, rectangle1.getPositionData().getX(), 0.00001);
    assertEquals(0, rectangle1.getPositionData().getY(), 0.00001);
    assertEquals(-1, oval1.getPositionData().getX(), 0.00001);
    assertEquals(2, oval1.getPositionData().getY(), 0.00001);
    assertEquals("(0.0,0.0)", getPositionString(rectangle1.getPositionData()));
    assertEquals("(-1.0,2.0)", getPositionString(oval1.getPositionData()));
  }

  @Test
  public void testGetDimensions() {
    assertEquals("Width: 1.0, Height: 1.0", rectangle1.getDimensions());
    assertEquals("X radius: 5.0, Y radius: 2.0", oval1.getDimensions());
    assertEquals("Width: 90.0, Height: 8.0", new Rectangle("rectangle1",
            new Point(5, 7), Color.CYAN, 90, 8, 1,
            2).getDimensions());
    assertEquals("X radius: 6.0, Y radius: 4.0", new Oval("oval1",
            new Point(-1, 2), Color.BLUE,
            6, 4, 0, 3).getDimensions());
  }

  @Test
  public void testGetName() {
    assertEquals("rectangle1", rectangle1.getName());
    assertEquals("oval1", oval1.getName());
  }

  @Test
  public void testGetType() {
    assertEquals("rectangle", rectangle1.getType());
    assertEquals("oval", oval1.getType());
  }

  @Test
  public void testGetAppearTime() {
    assertEquals(1, rectangle1.getAppearTime());
    assertEquals(0, oval1.getAppearTime());
  }

  @Test
  public void testGetDisappearTime() {
    assertEquals(2, rectangle1.getDisappearTime());
    assertEquals(3, oval1.getDisappearTime());
  }

  @Test
  public void testGetDimension1() {
    assertEquals(1, rectangle1.getDimension1(), 0.00001);
    assertEquals(5.0, oval1.getDimension1(), 0.00001);
    assertEquals(9.01, new Rectangle("rectangle1", new Point(5, 7),
            Color.CYAN, 9.01, 8, 1,
            2).getDimension1(), 0.00001);
    assertEquals(2.97, new Oval("oval1", new Point(-1, 2), Color.BLUE,
            2.97, 2, 0, 3).getDimension1(), 0.00001);
  }

  @Test
  public void testGetDimension2() {
    assertEquals(1.0, rectangle1.getDimension2(), 0.00001);
    assertEquals(2.0, oval1.getDimension2(), 0.00001);
    assertEquals(9.66, new Rectangle("rectangle1", new Point(5, 7),
            Color.CYAN, 9.01, 9.66, 1,
            2).getDimension2(), 0.00001);
    assertEquals(1.92, new Oval("oval1", new Point(-1, 2), Color.BLUE,
            2.97, 1.92, 0, 3).getDimension2(), 0.00001);
  }

  @Test
  public void testToString() {
    assertEquals("Name: rectangle1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", rectangle1.toString());
    assertEquals("Name: oval1\n" +
            "Type: oval\n" +
            "Center: (-1.0,2.0), X radius: 5.0, Y radius: 2.0, Color: (1.0,1.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=3", oval1.toString());
  }

  @Test
  public void testSetColor() {
    rectangle1.setColor(Color.BLUE);
    assertEquals(Color.blue, rectangle1.getColorData());
    assertNotEquals(Color.red, rectangle1.getColorData());
    oval1.setColor(Color.gray);
    assertEquals(Color.gray, oval1.getColorData());
    assertNotEquals(Color.white, oval1.getColorData());
  }

  @Test
  public void testInvalidSetColor() {

    // change to null color
    try {
      rectangle1.setColor(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Color can't be null!", e.getMessage());
    }

    try {
      oval1.setColor(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Color can't be null!", e.getMessage());
    }
  }

  @Test
  public void testSetPosition() {
    rectangle1.setPosition(new Point(-9, 2));
    assertEquals("(-9.0,2.0)", getPositionString(rectangle1.getPositionData()));
    assertEquals(-9.0, rectangle1.getPositionData().getX(), 0.00001);
    assertEquals(2.0, rectangle1.getPositionData().getY(), 0.00001);
    assertNotEquals("(0.0,0.0)", getPositionString(rectangle1.getPositionData()));

    oval1.setPosition(new Point(100.2, 3.21));
    assertEquals("(100.2,3.2)", getPositionString(oval1.getPositionData()));
    assertEquals(100.2, oval1.getPositionData().getX(), 0.00001);
    assertEquals(3.21, oval1.getPositionData().getY(), 0.00001);
    assertNotEquals("(-1.0,2.0)", getPositionString(oval1.getPositionData()));
  }

  @Test
  public void testInvalidSetPosition() {

    // move to a null point
    try {
      rectangle1.setPosition(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Point can't be null!", e.getMessage());
    }

    try {
      oval1.setPosition(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Point can't be null!", e.getMessage());
    }
  }

  @Test
  public void testSetDimension1() {
    rectangle1.setDimension1(9.2);
    assertEquals(9.2, rectangle1.getDimension1(), 0.00001);
    assertNotEquals(1, rectangle1.getDimension1(), 0.00001);
    oval1.setDimension1(2.121);
    assertEquals(2.121, oval1.getDimension1(), 0.00001);
    assertNotEquals(5, oval1.getDimension1(), 0.00001);
  }

  @Test
  public void testSetDimension2() {
    rectangle1.setDimension2(0.21);
    assertEquals(0.21, rectangle1.getDimension2(), 0.00001);
    assertNotEquals(1, rectangle1.getDimension2(), 0.00001);
    oval1.setDimension2(9);
    assertEquals(9, oval1.getDimension2(), 0.00001);
    assertNotEquals(2, oval1.getDimension2(), 0.00001);
  }

  @Test
  public void testInvalidSetDimension1() {

    // set the first dimension to be 0
    try {
      rectangle1.setDimension1(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    try {
      oval1.setDimension1(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    // set the first dimension to be negative
    try {
      rectangle1.setDimension1(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    try {
      oval1.setDimension1(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }
  }

  @Test
  public void testInvalidSetDimension2() {

    // set the second dimension to be 0
    try {
      rectangle1.setDimension2(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    try {
      oval1.setDimension2(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    // set the second dimension to be negative
    try {
      rectangle1.setDimension2(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }

    try {
      oval1.setDimension2(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimension must be positive!", e.getMessage());
    }
  }

  @Test
  public void testConstructor() {
    Shape tetRectangle = new Rectangle("test rectangle", new Point(15, 7), Color.CYAN,
            9.01, 9.66, 1, 2);
    assertEquals("Name: test rectangle\n" +
            "Type: rectangle\n" +
            "Min corner: (15.0,7.0), Width: 9.0, Height: 9.7, Color: (0.0,1.0,1.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", tetRectangle.toString());
    assertEquals("test rectangle", tetRectangle.getName());
    assertEquals("rectangle", tetRectangle.getType());
    assertEquals("(15.0,7.0)", tetRectangle.getPosition());
    assertEquals("(0.0,1.0,1.0)", tetRectangle.getColor());
    assertEquals(9.01, tetRectangle.getDimension1(), 0.00001);
    assertEquals(9.66, tetRectangle.getDimension2(), 0.00001);
    assertEquals(1, tetRectangle.getAppearTime());
    assertEquals(2, tetRectangle.getDisappearTime());
    Shape testOval = new Oval("test oval", new Point(-1.211, 2), Color.BLUE,
            2.97, 2, 0, 8);
    assertEquals("Name: test oval\n" +
            "Type: oval\n" +
            "Center: (-1.2,2.0), X radius: 3.0, Y radius: 2.0, Color: (0.0,0.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=8", testOval.toString());
    assertEquals("test oval", testOval.getName());
    assertEquals("oval", testOval.getType());
    assertEquals("(-1.2,2.0)", testOval.getPosition());
    assertEquals("(0.0,0.0,1.0)", testOval.getColor());
    assertEquals(2.97, testOval.getDimension1(), 0.00001);
    assertEquals(2, testOval.getDimension2(), 0.00001);
    assertEquals(0, testOval.getAppearTime());
    assertEquals(8, testOval.getDisappearTime());
  }

  @Test
  public void testInvalidConstructor1() {

    // test empty name
    try {
      new Rectangle("", new Point(-1.2, 2), Color.BLUE,
              2, 1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Name can't be empty!", e.getMessage());
    }

    // test negative dimension
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              -2, 1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              1, -1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    // test 0 dimension
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              0, 1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              1, 0, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    // test null reference point
    try {
      new Rectangle("test", null, Color.BLUE,
              1, -1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Position can't be null!", e.getMessage());
    }

    // test null color
    try {
      new Rectangle("test", new Point(1, 2), null,
              1, -1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Color can't be null!", e.getMessage());
    }

    // test negative appear time
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              2, 1, -1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Time can't be negative!", e.getMessage());
    }

    // test negative disappear time
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              2, 1, 1, -2);
    } catch (IllegalArgumentException e) {
      assertEquals("Time can't be negative!", e.getMessage());
    }

    // test disappear before appear
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              2, 1, 2, 1);
    } catch (IllegalArgumentException e) {
      assertEquals("Disappear time is before appear time!", e.getMessage());
    }

    // test appear time equals disappear time
    try {
      new Rectangle("test", new Point(-1.2, 2), Color.BLUE,
              2, 1, 2, 2);
    } catch (IllegalArgumentException e) {
      assertEquals("Disappear time is before appear time!", e.getMessage());
    }
  }

  @Test
  public void testInvalidConstructor2() {

    // test invalid constructors for Oval objects
    try {
      new Oval("", new Point(-1.2, 2), Color.BLUE,
              2, 1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Name can't be empty!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE,
              -6, 1, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE,
              0, 12.8, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE,
              1, -999, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE,
              1.8, 0, 1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Dimensions must be positive!", e.getMessage());
    }

    try {
      new Oval("test", null, Color.BLACK, 1, -1,
              1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Position can't be null!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1, 2), null, 1, -1,
              1, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Color can't be null!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.RED, 2, 1,
              -10, 22);
    } catch (IllegalArgumentException e) {
      assertEquals("Time can't be negative!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE, 2, 1,
              1, -12);
    } catch (IllegalArgumentException e) {
      assertEquals("Time can't be negative!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE, 2, 1,
              20, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Disappear time is before appear time!", e.getMessage());
    }

    try {
      new Oval("test", new Point(-1.2, 2), Color.BLUE, 2, 2,
              20, 0);
    } catch (IllegalArgumentException e) {
      assertEquals("Disappear time is before appear time!", e.getMessage());
    }
  }
}