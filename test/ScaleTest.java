import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.behavior.BehaviorType;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * test class for Scale.
 */
public class ScaleTest {
  private Scale scale;
  private Scale scale1;
  private Rectangle rectangle1;

  @Before
  public void setUp() {
    rectangle1 = new Rectangle("rectangle1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    Oval oval1 = new Oval("oval1", new Point(-1, 2), new Color((float) 1, 1, 1),
            5, 2, 0, 3);
    scale = new Scale(rectangle1, 1, 3, 1, 1, 2, 2);
    scale1 = new Scale(oval1, 0, 5, 5, 2, 3, 3);
  }

  /**
   * test for getCurrentBehavior().
   */
  @Test
  public void testFetCurrentBehavior() {
    assertEquals("scales", scale.getCurrentBehavior());
    assertEquals("scales", scale1.getCurrentBehavior());
  }

  /**
   * test for getStartStatus().
   */
  @Test
  public void testGetStartStatus() {
    assertEquals("Width: 1.0, Height: 1.0", scale.getStartStatus());
    assertEquals("X radius: 5.0, Y radius: 2.0", scale1.getStartStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetEndStatus() {
    assertEquals("Width: 2.0, Height: 2.0", scale.getEndStatus());
    assertEquals("X radius: 3.0, Y radius: 3.0", scale1.getEndStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetType() {
    assertEquals(BehaviorType.SCALE, scale.getType());
    assertEquals(BehaviorType.SCALE, scale1.getType());
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial5() {
    new Scale(rectangle1, 1, 3, 1, 1,1, 0);
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial6() {
    new Scale(rectangle1, 1, 3, 1, 1,0, 1);
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial7() {
    new Scale(rectangle1, 1, 3, 1, 1,0, 0);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial8() {
    new Scale(rectangle1, 1, 3, 1, 1, -1, 1);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial9() {
    new Scale(rectangle1, 1, 3, 1, 1, 1, -1);
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial10() {
    new Scale(rectangle1, 1, 3, 1, 1, -10, -20);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial11() {
    new Scale(rectangle1, 1, 3, -1, 1, 1, 1);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial12() {
    new Scale(rectangle1, 1, 3, 1, -1, 1, 1);
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial13() {
    new Scale(rectangle1, 1, 3, 0, 0, 10, 20);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial14() {
    new Scale(rectangle1, 1, 3, -1, -1, 1, 1);
  }

  /**
   * test for initialize. Dimension is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial15() {
    new Scale(rectangle1, 1, 3, 1, 0, 1, 1);
  }

  /**
   * test for initialize. Dimension equals 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void badInitial16() {
    new Scale(rectangle1, 1, 3, -1, 1, 10, 20);
  }

  /**
   * test for change().
   */
  @Test
  public void testChange() {
    scale.change(1);
    assertEquals("Name: rectangle1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", scale.getShape().toString());
  }

  /**
   * test for change().
   */
  @Test
  public void testChange1() {
    scale1.change(0.1);
    assertEquals("Name: oval1\n" +
            "Type: oval\n" +
            "Center: (-1.0,2.0), X radius: 5.0, Y radius: 2.0, Color: (1.0,1.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=3", scale1.getShape().toString());
  }

  /**
   * test for svgString.
   */
  @Test
  public void testSVGString() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" " +
                    "dur=\"2000.0ms\" attributeName=\"width\" from=\"1\" " +
                    "to=\"2\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" " +
                    "dur=\"2000.0ms\" attributeName=\"height\" from=\"1\" to=\"2\" " +
                    "fill=\"freeze\" />\n" + "<animate attributeType=\"xml\" begin=\"base.end\" " +
                    "dur=\"1ms\" attributeName=\"width\" to=\"1\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"height\" to=\"1\" fill=\"freeze\" />\n",
            scale.svgString(1, true));
  }

  /**
   * test for svgString.
   */
  @Test
  public void testSVGString2() {
    assertEquals("<animate attributeType=\"xml\" begin=\"200ms\" dur=\"400.0ms\" " +
                    "attributeName=\"width\" from=\"1\" to=\"2\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"400.0ms\"" +
                    " attributeName=\"height\" from=\"1\" to=\"2\" fill=\"freeze\" />\n",
            scale.svgString(5, false));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString3() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+0ms\"" +
                    " dur=\"2500.0ms\" attributeName=\"rx\" from=\"5\" to=\"3\" fill=\"" +
                    "freeze\" />\n" + "<animate attributeType=\"xml\" begin=\"base.begin+0ms\"" +
                    " dur=\"2500.0ms\" attributeName=\"ry\" from=\"2\" to=\"3\" " +
                    "fill=\"freeze\" />\n" + "<animate attributeType=\"xml\" begin=\"base.end\"" +
                    " dur=\"1ms\" attributeName=\"rx\" to=\"5\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"ry\" to=\"2\" fill=\"freeze\" />\n",
            scale1.svgString(2, true));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString4() {
    assertEquals("<animate attributeType=\"xml\" begin=\"0ms\" dur=\"500.0ms\" " +
                    "attributeName=\"rx\" from=\"5\" to=\"3\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"500.0ms\" " +
                    "attributeName=\"ry\" from=\"2\" to=\"3\" fill=\"freeze\" />\n",
            scale1.svgString(10, false));
  }
}

