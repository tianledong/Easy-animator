import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.view.EditorView;
import cs5004.animator.view.ViewType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * This is the test class for the editor view. All tests passed.
 */
public class EditorViewTest {
  private EditorView editorView;
  private AnimatorModel animatorModel;
  private cs5004.animator.model.shape.Shape rectangle;
  private Shape oval;

  @Before
  public void setUp() {
    editorView = new EditorView();
    animatorModel = new AnimatorModelImpl();
    editorView.setModel(animatorModel);
    rectangle = new Rectangle("r1", new Point(0, 0),
            new Color((float) 1.0, 0, 0), 1, 1, 1, 2);
    oval = new Oval("R", new Point(0, 0), new Color((float) 1.0, 0, 0),
            1, 1, 1, 2);
  }

  // test methods that aren't supported
  @Test
  public void testUnsupportedMethods() {
    try {
      editorView.writeState("testFile", 1, false);
      fail("Should fail here");
    } catch (UnsupportedOperationException e) {
      assertEquals("Can't write the animation state for the editor view!", e.getMessage());
    }

    try {
      editorView.getState(true);
      fail("Should fail here");
    } catch (UnsupportedOperationException e) {
      assertEquals("Can't get the animation state for the editor view", e.getMessage());
    }

    try {
      editorView.getState(false);
      fail("Should fail here");
    } catch (UnsupportedOperationException e) {
      assertEquals("Can't get the animation state for the editor view", e.getMessage());
    }
  }

  @Test
  public void testMakeVisible() {
    assertFalse(editorView.isVisible());
    editorView.makeVisible();
    assertTrue(editorView.isVisible());
  }

  @Test
  public void testSetSpeed() {
    assertEquals(1, editorView.getSpeed(), 0.0001);
    editorView.setSpeed(2);
    assertEquals(2, editorView.getSpeed(), 0.0001);
    editorView.setSpeed(3);
    assertNotEquals(2, editorView.getSpeed());
  }

  @Test
  public void testSetInvalidSpeed() {
    try {
      editorView.setSpeed(0);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }

    try {
      editorView.setSpeed(-10);
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }
  }

  @Test
  public void testGetSpeed() {
    assertEquals(1, editorView.getSpeed(), 0.0001);
    editorView.setSpeed(3);
    assertNotEquals(1, editorView.getSpeed());
    assertEquals(3, editorView.getSpeed(), 0.0001);
  }

  @Test
  public void testGetShapes() {
    assertEquals(0, editorView.getShapes().size());
    animatorModel.addShape(rectangle);
    assertEquals(1, editorView.getShapes().size());
    assertEquals("r1", editorView.getShapes().get(0).getName());
    animatorModel.addShape(oval);
    assertEquals(2, editorView.getShapes().size());
    assertEquals("R", editorView.getShapes().get(1).getName());
  }

  @Test
  public void testSetModel() {
    AnimatorModel test = new AnimatorModelImpl();
    test.addShape(rectangle);
    test.addShape(oval);
    animatorModel.addShape(oval);
    assertEquals("R", editorView.getShapes().get(0).getName());
    assertEquals(1, editorView.getShapes().size());
    editorView.setModel(test);
    assertEquals("r1", editorView.getShapes().get(0).getName());
    assertEquals(2, editorView.getShapes().size());
    assertNotEquals("R", editorView.getShapes().get(0).getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidModel() {
    editorView.setModel(null);
  }

  @Test
  public void testSetCanvas() {
    assertEquals(0, editorView.getX());
    assertEquals(0, editorView.getY());
    assertEquals(1300, editorView.getWidth());
    assertEquals(1000, editorView.getHeight());

    editorView.setCanvas(100, 100, 500, 500);
    assertEquals(100, editorView.getX());
    assertEquals(100, editorView.getY());
    assertEquals(500, editorView.getWidth());
    assertEquals(500, editorView.getHeight());
  }

  @Test
  public void testConstructor() {
    assertEquals(0, editorView.getShapes().size());
    assertEquals(1, editorView.getSpeed(), 0.0001);
    assertEquals(0, editorView.getX());
    assertEquals(0, editorView.getY());
    assertEquals(1300, editorView.getWidth());
    assertEquals(1000, editorView.getHeight());
  }

  @Test
  public void testGetViewType() {
    assertEquals(ViewType.EDITOR, editorView.getViewType());
  }
}