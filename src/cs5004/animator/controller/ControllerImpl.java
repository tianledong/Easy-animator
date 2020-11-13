package cs5004.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.view.View;

/**
 * The concrete class that implements the Controller interface and represents an actual Controller
 * object. It requires the Main class to pass in a copy of the animator mode, a View object, a
 * positive speed, and a non-empty file name. Based on the type of the view, the controller calls
 * different methods to initialize the View.
 */
public class ControllerImpl implements Controller, ActionListener {
  private View view;
  private StringBuilder log;
  private AnimatorModel model;
  private int speed;
  private String fileName;

  /**
   * The constructor that passes parameters' value to the private fields. It doesn't sets up model
   * and speed for the views.
   *
   * @param model the animator model of the views
   * @param view the View object to be run
   * @param speed the play speed of the view
   * @param fileName name of the output file
   * @throws IllegalArgumentException if the model or the view is null, or the speed isn't positive,
   *                                  or the file name is empty
   */
  public ControllerImpl(AnimatorModel model, View view, int speed, String fileName)
          throws IllegalArgumentException {
    if (fileName.equals("")) {
      throw new IllegalArgumentException("File name can't be empty!");
    } else if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    } else if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive!");
    } else if (view == null) {
      throw new IllegalArgumentException("View can't be null!");
    }
    this.view = view;
    this.log = new StringBuilder();
    this.model = model;
    this.speed = speed;
    this.fileName = fileName;
  }

  @Override
  public void start() {
    switch (this.view.getViewType()) {
      case TEXTUAL:
        view.setSpeed(this.speed);
        view.setModel(this.model);
        view.writeState(this.fileName, speed, false);
        break;
      case SVG:
        view.setSpeed(this.speed);
        view.setModel(this.model);
        view.setCanvas(this.model.getX(), this.model.getY(), this.model.getWidth(),
                this.model.getHeight());
        view.writeState(this.fileName, speed, false);
        break;
      case VISUAL:
        view.setSpeed(this.speed);
        view.setModel(this.model);
        view.setCanvas(this.model.getX(), this.model.getY(),
                this.model.getWidth() + this.model.getX() * 3,
                this.model.getHeight() + model.getY() * 3);
        view.makeVisible();
        break;
      case EDITOR:
        view.setSpeed(this.speed);
        view.setModel(this.model);
        this.view.setActionListener(this);
        this.view.makeVisible();
        break;
      default:
        JOptionPane.showMessageDialog(null,
                "Invalid view type", "Error", JOptionPane.ERROR_MESSAGE);
    }

  }

  @Override
  public String getLog() {
    return this.log.toString();
  }

  /**
   * Get the performed action and record it to the log, then call the setFeature method to perform
   * the command.
   *
   * @param e the performed action event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    switch (action) {
      case "PLAY":
        this.log.append("Play button pressed\n");
        break;
      case "PAUSE":
        this.log.append("Pause button pressed\n");
        break;
      case "RESUME":
        this.log.append("Resume button pressed\n");
        break;
      case "RESTART":
        this.log.append("Restart button pressed\n");
        break;
      case "INCREASE SPEED":
        this.log.append("Increase speed button pressed\n");
        break;
      case "DECREASE SPEED":
        this.log.append("Decrease speed button pressed\n");
        break;
      case "LOOP":
        this.log.append("Loop checkbox selected\n");
        break;
      case "TEXT":
        this.log.append("Export text file button pressed\n");
        break;
      case "SVG":
        this.log.append("Export SVG file button pressed\n");
        break;
      case "OPEN":
        this.log.append("Open file button pressed\n");
        break;
      default:
        this.log.append("Unsupported feature called\n");
        JOptionPane.showMessageDialog(null,
                "Unsupported feature!", "Error", JOptionPane.ERROR_MESSAGE);
    }
    this.view.setFeature(action);
  }
}