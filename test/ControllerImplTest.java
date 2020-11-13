import org.junit.Before;
import org.junit.Test;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import cs5004.animator.controller.ControllerImpl;
import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.view.EditorView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is the test class for the controller. All tests passed.
 */
public class ControllerImplTest {
  JButton playButton;
  JButton pauseButton;
  JButton restartButton;
  JButton resumeButton;
  JButton increaseSpeedButton;
  JButton decreaseSpeedButton;
  JCheckBox loopCheckbox;

  ControllerImpl controller;
  AnimatorModel animatorModel;
  EditorView view;

  @Before
  public void setUp() {
    playButton = new JButton("Play");
    pauseButton = new JButton("Pause");
    resumeButton = new JButton("Resume");
    restartButton = new JButton("Restart");
    increaseSpeedButton = new JButton("Increase speed");
    decreaseSpeedButton = new JButton("Decrease speed");
    loopCheckbox = new JCheckBox("Loop");

    animatorModel = new AnimatorModelImpl();
    view = new EditorView();
    controller = new ControllerImpl(animatorModel, view, 2, "test");
  }

  @Test
  public void testStart() {
    assertFalse(view.isVisible());
    controller.start();
    assertEquals("", controller.getLog());
    assertTrue(view.isVisible());
    assertEquals(0, view.getShapes().size());
    assertEquals(2, view.getSpeed(), 0.0001);
  }

  @Test
  public void testActionPerformed() {
    ActionEvent play = new ActionEvent(playButton, 1, "PLAY");
    ActionEvent pause = new ActionEvent(pauseButton, 2, "PAUSE");
    ActionEvent resume = new ActionEvent(resumeButton, 3, "RESUME");
    ActionEvent restart = new ActionEvent(restartButton, 4, "RESTART");
    ActionEvent increaseSpeed = new ActionEvent(increaseSpeedButton, 5, "INCREASE SPEED");
    ActionEvent decreaseSpeed = new ActionEvent(decreaseSpeedButton, 6, "DECREASE SPEED");
    ActionEvent loop = new ActionEvent(loopCheckbox, 7, "LOOP");

    controller.start();
    assertTrue(view.isVisible());

    // test whether each action is recorded
    assertEquals("", controller.getLog());
    controller.actionPerformed(play);
    assertEquals("Play button pressed\n", controller.getLog());
    controller.actionPerformed(pause);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n", controller.getLog());
    controller.actionPerformed(play);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n", controller.getLog());
    controller.actionPerformed(resume);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n"
            + "Resume button pressed\n", controller.getLog());
    controller.actionPerformed(loop);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n"
            + "Resume button pressed\n"
            + "Loop checkbox selected\n", controller.getLog());
    controller.actionPerformed(restart);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n"
            + "Resume button pressed\n"
            + "Loop checkbox selected\n"
            + "Restart button pressed\n", controller.getLog());
    controller.actionPerformed(increaseSpeed);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n"
            + "Resume button pressed\n"
            + "Loop checkbox selected\n"
            + "Restart button pressed\n"
            + "Increase speed button pressed\n", controller.getLog());
    controller.actionPerformed(decreaseSpeed);
    assertEquals("Play button pressed\n"
            + "Pause button pressed\n"
            + "Play button pressed\n"
            + "Resume button pressed\n"
            + "Loop checkbox selected\n"
            + "Restart button pressed\n"
            + "Increase speed button pressed\n"
            + "Decrease speed button pressed\n", controller.getLog());

  }

  @Test
  public void testInvalidConstructor() {
    try {
      new ControllerImpl(null, view, 1, "test");
      fail("Should fail here");
    } catch (IllegalArgumentException e) {
      assertEquals("Model can't be null!", e.getMessage());
    }

    try {
      new ControllerImpl(animatorModel, null, 1, "test");
      fail("Should fail here");
    } catch (IllegalArgumentException e) {
      assertEquals("View can't be null!", e.getMessage());
    }

    try {
      new ControllerImpl(animatorModel, view, 0, "test");
      fail("Should fail here");
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }

    try {
      new ControllerImpl(animatorModel, view, -1, "test");
      fail("Should fail here");
    } catch (IllegalArgumentException e) {
      assertEquals("Speed must be positive!", e.getMessage());
    }

    try {
      new ControllerImpl(animatorModel, view, 1, "");
      fail("Should fail here");
    } catch (IllegalArgumentException e) {
      assertEquals("File name can't be empty!", e.getMessage());
    }
  }

  @Test
  public void testGetLog() {
    ActionEvent play = new ActionEvent(playButton, 1, "PLAY");
    ActionEvent pause = new ActionEvent(pauseButton, 2, "PAUSE");
    ActionEvent resume = new ActionEvent(resumeButton, 3, "RESUME");
    ActionEvent restart = new ActionEvent(restartButton, 4, "RESTART");
    controller.start();

    assertEquals("", controller.getLog());
    controller.actionPerformed(play);
    assertEquals("Play button pressed\n", controller.getLog());
    controller.actionPerformed(play);
    controller.actionPerformed(play);
    controller.actionPerformed(play);
    assertEquals("Play button pressed\n"
            + "Play button pressed\n"
            + "Play button pressed\n"
            + "Play button pressed\n", controller.getLog());
    controller.actionPerformed(pause);
    controller.actionPerformed(resume);
    controller.actionPerformed(restart);
    assertEquals("Play button pressed\n"
            + "Play button pressed\n"
            + "Play button pressed\n"
            + "Play button pressed\n"
            + "Pause button pressed\n"
            + "Resume button pressed\n"
            + "Restart button pressed\n", controller.getLog());
  }
}