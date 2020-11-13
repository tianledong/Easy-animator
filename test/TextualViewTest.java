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
import cs5004.animator.view.TextualView;
import cs5004.animator.view.View;
import cs5004.animator.view.ViewType;

import static org.junit.Assert.assertEquals;

/**
 * This is the test class for the textual view class. All tests passed.
 */
public class TextualViewTest {
  private View textualView;
  private AnimatorModel animatorModel;
  private Behavior move;
  private Behavior scale;
  private Shape rectangle;
  private Shape oval;

  @Before
  public void setUp() {
    animatorModel = new AnimatorModelImpl();
    textualView = new TextualView();
    textualView.setModel(animatorModel);
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
    move = new Move(rectangle, 0, 1, new Point(0, 0), new Point(2, 2));
    scale = new Scale(oval, 1, 2, 1, 1, 3, 3);
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testRefresh() {
    textualView.refresh();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testMakeVisible() {
    textualView.makeVisible();
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetCanvas() {
    textualView.setCanvas(0, 0, 100, 100);
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetActionListener() {
    textualView.setActionListener(e -> {
      // test adding an action listener to the textual view
    });
  }

  @Test (expected = UnsupportedOperationException.class)
  public void testSetFeature() {
    textualView.setFeature("test");
  }

  @Test
  public void testSetModel() {
    View test = new TextualView();
    test.setModel(animatorModel);
    animatorModel.addShape(oval);

    assertEquals(1, test.getShapes().size());
    assertEquals("R", test.getShapes().get(0).getName());
    animatorModel.addShape(rectangle);

    test.setModel(animatorModel);
    assertEquals(2, test.getShapes().size());
    assertEquals("r1", test.getShapes().get(1).getName());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSetInvalidModel() {
    textualView.setModel(null); // set new shape list to be null
  }

  @Test
  public void testSetSpeed() {
    assertEquals(1, textualView.getSpeed(), 0.0001);
    textualView.setSpeed(10);
    assertEquals(10, textualView.getSpeed(), 0.0001);
  }

  @Test
  public void testSetInvalidSpeed() {
    try { // set negative speed
      textualView.setSpeed(-1);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }

    try { // set 0 speed
      textualView.setSpeed(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }
  }

  @Test
  public void testConstructor() {
    View test = new TextualView();
    assertEquals(0, test.getShapes().size());

    animatorModel.addAnimation(move);
    animatorModel.addShape(oval);
    test.setSpeed(24);
    test.setModel(animatorModel);

    assertEquals("Created a rectangle r1 with Min corner: (0.0,0.0), Width: 1.0" +
            " and Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Created a oval R with Center: (0.0,0.0), X radius: 1.0 and Y radius: 1.0, " +
            "Color: (1.0,0.0,0.0)\n\n\n" +
            "Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1",
            test.getState(false));
  }

  @Test
  public void testGetShapes() {
    assertEquals(0, textualView.getShapes().size());
    animatorModel.addShape(rectangle);
    animatorModel.addShape(oval);
    textualView.setModel(animatorModel);
    assertEquals(2, textualView.getShapes().size());
    assertEquals("rectangle", textualView.getShapes().get(0).getType());
  }

  @Test
  public void testGetSpeed() {
    assertEquals(1, textualView.getSpeed(), 0.0001);
    textualView.setSpeed(5);
    assertEquals(5, textualView.getSpeed(), 0.0001);
  }

  @Test
  public void testWriteState() {
    animatorModel.addShape(rectangle);
    animatorModel.addAnimation(move);
    textualView.setModel(animatorModel);
    textualView.writeState("textual-test.txt", 1, false);
    try {
      FileReader reader = new FileReader("textual-test.txt");
      Scanner scanner = new Scanner(reader);
      if (scanner.hasNext()) {
        String word1 = scanner.next();
        assertEquals("Speed", word1);
      }
      String word2 = "";
      while (scanner.hasNext()) {
        word2 = scanner.next();
      }
      assertEquals("t=1", word2);
      reader.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  @Test
  public void testGetState() {
    assertEquals("\n\n\n\n", textualView.getState(false));
    animatorModel.addShape(oval);
    animatorModel.addShape(rectangle);
    assertEquals("Created a oval R with Center: (0.0,0.0), X radius: 1.0 and " +
            "Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Created a rectangle r1 with Min corner: (0.0,0.0), Width: 1.0 and Height: 1.0, " +
            "Color: (1.0,0.0,0.0)\n\n\n" +
            "Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n" +
            "Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n" +
            "\n", textualView.getState(false));
    animatorModel.addAnimation(move);
    animatorModel.addAnimation(scale);
    assertEquals("Created a oval R with Center: (0.0,0.0), X radius: 1.0 and " +
            "Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Created a rectangle r1 with Min corner: (0.0,0.0), Width: 1.0 and Height: 1.0, " +
            "Color: (1.0,0.0,0.0)\n\n\n" +
            "Name: R\n" +
            "Type: oval\n" +
            "Center: (0.0,0.0), X radius: 1.0, Y radius: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n\n" +
            "Name: r1\n" +
            "Type: rectangle\n" +
            "Min corner: (0.0,0.0), Width: 1.0, Height: 1.0, Color: (1.0,0.0,0.0)\n" +
            "Appears at t=1\n" +
            "Disappears at t=2\n\n" +
            "Shape r1 moves from (0.0,0.0) to (2.0,2.0) from t=0 to t=1\n" +
            "Shape R scales from X radius: 1.0, Y radius: 1.0 to X radius: 3.0, " +
            "Y radius: 3.0 from t=1 to t=2", textualView.getState(false));
  }

  @Test
  public void testGetViewType() {
    assertEquals(ViewType.TEXTUAL, textualView.getViewType());
  }
}
