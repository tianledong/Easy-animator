import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.behavior.BehaviorType;
import cs5004.animator.model.behavior.ChangeColor;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * test class for ChangeColor.
 */
public class ChangeColorTest {
  private ChangeColor changeColor;
  private ChangeColor changeColor1;

  @Before
  public void setUp() {
    Rectangle rectangle1 = new Rectangle("rectangle1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    Oval oval1 = new Oval("oval1", new Point(-1, 2), new Color((float) 1, 1, 1),
            5, 2, 0, 3);
    changeColor = new ChangeColor(rectangle1, 1, 3, new Color((float) 1.0, 0, 0), Color.BLUE);
    changeColor1 = new ChangeColor(oval1, 0, 5, new Color((float) 1, 1, 1),
            new Color((float) 0, 1, 1));
  }

  /**
   * test for getCurrentBehavior().
   */
  @Test
  public void testFetCurrentBehavior() {
    assertEquals("changes color", changeColor.getCurrentBehavior());
    assertEquals("changes color", changeColor1.getCurrentBehavior());
  }

  /**
   * test for getStartStatus().
   */
  @Test
  public void testGetStartStatus() {
    assertEquals("(1.0,0.0,0.0)", changeColor.getStartStatus());
    assertEquals("(1.0,1.0,1.0)", changeColor1.getStartStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetEndStatus() {
    assertEquals("(0.0,0.0,1.0)", changeColor.getEndStatus());
    assertEquals("(0.0,1.0,1.0)", changeColor1.getEndStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetType() {
    assertEquals(BehaviorType.CHANGECOLOR, changeColor.getType());
    assertEquals(BehaviorType.CHANGECOLOR, changeColor1.getType());
  }

  /**
   * test for change().
   */
  @Test
  public void testChange() {
    changeColor.change(1);
    assertEquals("Name: rectangle1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", changeColor.getShape().toString());
  }

  /**
   * test for change().
   */
  @Test
  public void testChange1() {
    changeColor1.change(0.1);
    assertEquals("Name: oval1\n" +
            "Type: oval\n" +
            "Center: (-1.0,2.0), X radius: 5.0, Y radius: 2.0, Color: (1.0,1.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=3", changeColor1.getShape().toString());
  }

  /**
   * test for svgString.
   */
  @Test
  public void testSVGString() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" " +
                    "dur=\"2000.0ms\" attributeName=\"fill\" from=\"rgb(255,0,0)\" " +
                    " to=\"rgb(0,0,255)\"" + "  fill=\"freeze\" />\n"
                    + "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                    " attributeName=\"fill\" to=\"rgb(255,0,0)\"  fill=\"freeze\" />\n",
            changeColor.svgString(1, true));
  }

  /**
   * test for svgString.
   */
  @Test
  public void testSVGString2() {
    assertEquals("<animate attributeType=\"xml\" begin=\"200ms\" dur=\"400.0ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,0,0)\"  to=\"rgb(0,0,255)\"  " +
                    "fill=\"freeze\" />\n",
            changeColor.svgString(5, false));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString3() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+0ms\" " +
                    "dur=\"2500.0ms\" attributeName=\"fill\" from=\"rgb(255,255,255)\" " +
                    " to=\"rgb(0,255,255)\"" + "  fill=\"freeze\" />\n"
                    + "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                    " attributeName=\"fill\" to=\"rgb(255,255,255)\"  fill=\"freeze\" />\n",
            changeColor1.svgString(2, true));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString4() {
    assertEquals("<animate attributeType=\"xml\" begin=\"0ms\" dur=\"500.0ms\" " +
                    "attributeName=\"fill\" from=\"rgb(255,255,255)\"  to=\"rgb(0,255,255)\"  " +
                    "fill=\"freeze\" />\n",
            changeColor1.svgString(10, false));
  }
}

