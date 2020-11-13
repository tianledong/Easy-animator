import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.view.SVGView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This is the test class for the svg view class. All tests passed.
 */
public class SVGViewTest {
  private View svgView;
  private AnimatorModel animatorModel;
  private Shape rectangle;
  private Shape oval;
  private Behavior move;
  private Behavior scale;

  @Before
  public void setUp() {
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2, 1, 1, 3, 3);
    animatorModel = new AnimatorModelImpl();
    svgView = new SVGView();
    svgView.setModel(animatorModel);
  }

  @Test
  public void testConstructor() {
    View test = new SVGView();
    assertEquals(0, test.getShapes().size());
    animatorModel.addAnimation(scale);
    animatorModel.addShape(rectangle);
    test.setSpeed(3);
    test.setModel(animatorModel);
    assertEquals("<svg viewBox=\"0 0 1000 1000\" version=\"1.1\" xmlns=\"" +
                    "http://www.w3.org/2000/svg\">\n" +
                    "<ellipse id=\"R\" cx=\"0\" cy=\"0\" rx=\"1\" ry=\"1\" fill=\"" +
                    "rgb(255,0,0)\"  visibility=\"hidden\" >\n" +
                    "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
                    "         to=\"visible\"\n" +
                    "         begin=\"333ms\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"333ms\" dur=\"333.3ms\" " +
                    "attributeName=\"rx\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
                    "<animate attributeType=\"xml\" begin=\"333ms\" dur=\"333.3ms\" " +
                    "attributeName=\"ry\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
                    "</ellipse>\n" +
                    "<rect id=\"r1\" x=\"0\" y=\"0\" width=\"1\" height=\"1\" fill=\"" +
                    "rgb(255,0,0)\"  visibility=\"hidden\" >\n" +
                    "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
                    "         to=\"visible\"\n" +
                    "         begin=\"333ms\" />\n" +
                    "</rect>\n" +
                    "</svg>",
            test.getState(false));
  }


  @Test
  public void testSetShapeList() {
    View test = new SVGView();
    test.setModel(animatorModel);
    assertEquals(0, test.getShapes().size());
    animatorModel.addShape(rectangle);
    assertEquals(1, test.getShapes().size());
    assertEquals("r1", test.getShapes().get(0).getName());
    animatorModel.addShape(oval);
    assertEquals(2, test.getShapes().size());
    assertEquals("oval", test.getShapes().get(1).getType());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetInvalidModel() {
    svgView.setModel(null); // set new shape list to be null
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testRefresh() {
    svgView.refresh();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testMakeVisible() {
    svgView.makeVisible();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetActionListener() {
    svgView.setActionListener(e -> {
      // test adding an action listener to the SVG view
    });
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetFeature() {
    svgView.setFeature("test");
  }

  @Test
  public void testSetCanvas() {
    assertTrue(svgView.getState(false).contains("viewBox=\"0 0 1000 1000\""));
    svgView.setCanvas(1, 2, 300, 400);
    assertTrue(svgView.getState(false).contains("viewBox=\"1 2 300 400\""));
  }

  @Test
  public void testSetSpeed() {
    assertEquals(1, svgView.getSpeed(), 0.0001);
    svgView.setSpeed(2);
    assertEquals(2, svgView.getSpeed(), 0.0001);
  }

  @Test
  public void testSetInvalidSpeed() {
    try { // set 0 speed
      svgView.setSpeed(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }

    try { // set negative speed
      svgView.setSpeed(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }
  }

  @Test
  public void testGetShapes() {
    assertEquals(0, svgView.getShapes().size());
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    svgView.setModel(animatorModel);
    assertEquals(2, svgView.getShapes().size());
    assertEquals("R", svgView.getShapes().get(1).getName());
  }

  @Test
  public void testGetSpeed() {
    assertEquals(1, svgView.getSpeed(), 0.0001);
    svgView.setSpeed(20);
    assertEquals(20, svgView.getSpeed(), 0.0001);
  }



  @Test
  public void testGetLoopState() {
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    animatorModel.addAnimation(scale);
    svgView.setModel(animatorModel);
    assertEquals("<svg viewBox=\"0 0 1000 1000\" version=\"1.1\" " +
            "xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect>\n <animate id=\"base\" begin=\"0;base.end\" dur=\"2000.0ms\" " +
            "attributeName=\"visibility\" from=\"hide\" to=\"hide\"/> \n" +
            "</rect>\n\n" +
            "<rect id=\"r1\" x=\"0\" y=\"0\" width=\"1\" height=\"1\" fill=\"rgb(255,0,0)\"  " +
            "visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"1000ms\" />\n" +
            "</rect>\n<ellipse id=\"R\" cx=\"0\" cy=\"0\" rx=\"1\" ry=\"1\" " +
            "fill=\"rgb(255,0,0)\"  visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"1000ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" dur=\"1000.0ms\" " +
            "attributeName=\"rx\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.begin+1000ms\" dur=\"1000.0ms\" " +
            "attributeName=\"ry\" from=\"1\" to=\"3\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
            "attributeName=\"rx\" to=\"1\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" " +
            "attributeName=\"ry\" to=\"1\" fill=\"freeze\" />\n" +
            "</ellipse>\n" +
            "</svg>", svgView.getState(true));
  }

  @Test
  public void testGetNotLoopState() {
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    animatorModel.addAnimation(move);
    svgView.setModel(animatorModel);
    assertEquals("<svg viewBox=\"0 0 1000 1000\" version=\"1.1\"" +
            " xmlns=\"http://www.w3.org/2000/svg\">\n" +
            "<rect id=\"r1\" x=\"0\" y=\"0\" width=\"1\" height=\"1\" fill=\"rgb(255,0,0)\"  " +
            "visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"1000ms\" />\n" +
            "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000.0ms\" attributeName=\"x\" " +
            "from=\"0\" to=\"2\" fill=\"freeze\" />\n" +
            "<animate attributeType=\"xml\" begin=\"0ms\" dur=\"1000.0ms\" attributeName=\"y\" " +
            "from=\"0\" to=\"2\" fill=\"freeze\" />\n" +
            "</rect>\n" +
            "<ellipse id=\"R\" cx=\"0\" cy=\"0\" rx=\"1\" ry=\"1\" fill=\"rgb(255,0,0)\"  " +
            "visibility=\"hidden\" >\n" +
            "    <set attributeName=\"visibility\" attributeType=\"XML\"\n" +
            "         to=\"visible\"\n" +
            "         begin=\"1000ms\" />\n" +
            "</ellipse>\n" +
            "</svg>", svgView.getState(false));
  }

  @Test
  public void testWriteState() {
    animatorModel.addShape(rectangle);
    animatorModel.addAnimation(move);
    svgView.setModel(animatorModel);
    svgView.writeState("textual-test.txt", 1, false);
    try {
      FileReader reader = new FileReader("textual-test.txt");
      Scanner scanner = new Scanner(reader);
      if (scanner.hasNext()) {
        String word1 = scanner.next();
        assertEquals("<svg", word1);
      }
      String word2 = "";
      while (scanner.hasNext()) {
        word2 = scanner.next();
      }
      assertEquals("</svg>", word2);
      reader.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Test
  public void testGetViewType() {
    assertEquals(ViewType.SVG, svgView.getViewType());
  }
}