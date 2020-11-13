import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.behavior.BehaviorType;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;

import static org.junit.Assert.assertEquals;

/**
 * test class for move.
 */
public class MoveTest {
  private Move move;
  private Move move1;

  @Before
  public void setUp() {
    Rectangle rectangle1 = new Rectangle("rectangle1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    Oval oval1 = new Oval("oval1", new Point(-1, 2), new Color((float) 1, 1, 1),
            5, 2, 0, 3);
    move = new Move(rectangle1, 1, 3, new Point(0, 0), new Point(3, 3));
    move1 = new Move(oval1, 0, 5, new Point(-1, 2), new Point(-2, -2));
  }

  /**
   * test for getCurrentBehavior().
   */
  @Test
  public void testFetCurrentBehavior() {
    assertEquals("moves", move.getCurrentBehavior());
    assertEquals("moves", move1.getCurrentBehavior());
  }

  /**
   * test for getStartStatus().
   */
  @Test
  public void testGetStartStatus() {
    assertEquals("(0.0,0.0)", move.getStartStatus());
    assertEquals("(-1.0,2.0)", move1.getStartStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetEndStatus() {
    assertEquals("(3.0,3.0)", move.getEndStatus());
    assertEquals("(-2.0,-2.0)", move1.getEndStatus());
  }

  /**
   * test for getEndStatus().
   */
  @Test
  public void testGetType() {
    assertEquals(BehaviorType.MOVE, move.getType());
    assertEquals(BehaviorType.MOVE, move1.getType());
  }

  /**
   * test for change().
   */
  @Test
  public void testChange() {
    move.change(1);
    assertEquals("Name: rectangle1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2", move.getShape().toString());
  }

  /**
   * test for change().
   */
  @Test
  public void testChange1() {
    move1.change(0.1);
    assertEquals("Name: oval1\n" +
            "Type: oval\n" +
            "Center: (-1.0,1.9), X radius: 5.0, Y radius: 2.0, Color: (1.0,1.0,1.0)\n" +
            "Appears at t=0\n" +
            "Disappears at t=3", move1.getShape().toString());
  }


  /**
   * test for svgString.
   */
  @Test
  public void testSVGString() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" " +
                    "dur=\"2000.0ms\" attributeName=\"x\" from=\"0\" to=\"3\" fill=\"freeze\" />\n"
                    + "<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" dur=\"2000.0ms\" "
                    + "attributeName=\"y\" from=\"0\" to=\"3\" fill=\"freeze\" />\n"
                    + "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"x\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"y\" to=\"0\" fill=\"freeze\" />\n",
            move.svgString(1, true));
  }

  /**
   * test for svgString.
   */
  @Test
  public void testSVGString2() {
    assertEquals("<animate attributeType=\"xml\" begin=\"200ms\" dur=\"400.0ms\"" +
                    " attributeName=\"x\" from=\"0\" to=\"3\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"200ms\" dur=\"400.0ms\" " +
                    "attributeName=\"y\" from=\"0\" to=\"3\" fill=\"freeze\" />\n",
            move.svgString(5, false));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString3() {
    assertEquals("<animate attributeType=\"xml\" begin=\"base.begin+0ms\" " +
                    "dur=\"2500.0ms\" attributeName=\"cx\" from=\"4\" to=\"3\" fill=\"freeze\" />\n"
                    + "<animate attributeType=\"xml\" begin=\"base.begin+0ms\" dur=\"2500.0ms\"" +
                    " attributeName=\"cy\" from=\"4\" to=\"0\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"cx\" to=\"4\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
                    "attributeName=\"cy\" to=\"0\" fill=\"freeze\" />\n",
            move1.svgString(2, true));
  }

  /**
   * test for svgString oval.
   */
  @Test
  public void testSVGString4() {
    assertEquals("<animate attributeType=\"xml\" begin=\"0ms\" dur=\"500.0ms\"" +
                    " attributeName=\"cx\" from=\"4\" to=\"3\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"500.0ms\"" +
                    " attributeName=\"cy\" from=\"4\" to=\"0\" fill=\"freeze\" />\n",
            move1.svgString(10, false));
  }
}

