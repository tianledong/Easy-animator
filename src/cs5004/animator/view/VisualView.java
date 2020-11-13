package cs5004.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.Timer;

import javax.swing.JScrollPane;
import javax.swing.JFrame;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Shape;

/**
 * This is the concrete class for the visual view. It uses JFrame and JPanel to create a window to
 * play the animation. It only supports the refresh, setCanvas, setShapeList, and the getShapes
 * methods.
 */
public class VisualView extends JFrame implements View, ActionListener {
  private AnimationPanel panel;
  private AnimatorModel model;
  private ViewType viewType;
  private int speed;
  private double currentTick = 0;
  private int maxTick = 100;
  private boolean firstTime = true;
  private Timer timer;


  /**
   * The constructor that initializes the visual view. It sets up the size and title and other
   * components in the JFrame. The animator model needs to be set up by calling the setModel
   * method. It's set to be not visible by default and needs to use the makeVisible method to make
   * it visible and start the timer.
   */
  public VisualView() {
    super();
    this.viewType = ViewType.VISUAL;
    this.setTitle("Easy Animator");
    this.setSize(1200, 1200);
    this.model = new AnimatorModelImpl();
    this.speed = 1;
    this.timer = new Timer(1, this);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.panel = new AnimationPanel(this.model.getShapes());
    this.add(panel);

    JScrollPane scrollPane = new JScrollPane(this.panel);
    scrollPane.setPreferredSize(new Dimension(350, 350));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane, BorderLayout.CENTER);
    scrollPane.setBounds(10, 10, 100, 50);
    this.setVisible(false);
    this.pack();
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void writeState(String file, int speed, boolean isLoop)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't write files for the visual view!");
  }

  @Override
  public void setSpeed(int speed) throws IllegalArgumentException {
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive!");
    }
    this.speed = speed;
  }

  @Override
  public double getSpeed() {
    return this.speed;
  }

  @Override
  public String getState(boolean isLoop) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't get animation state for the visual view!");
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) throws UnsupportedOperationException {
    this.setBounds(x, y, width, height);
  }

  @Override
  public List<Shape> getShapes() {
    return this.model.getShapes();
  }

  @Override
  public void setActionListener(ActionListener listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set action listeners for the visual view!");
  }

  @Override
  public void setModel(AnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
    this.panel.setShapes(this.model.getShapes());
    this.currentTick = this.model.getMinTick();
    this.maxTick = this.model.getMaxTick();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
    this.timer.start();
  }

  @Override
  public void setFeature(String feature) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set features for the visual view");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    for (Behavior behavior : model.getAnimations()) {
      behavior.change(currentTick);
    }
    refresh();
    currentTick = currentTick + (0.01 * this.speed);
    if (currentTick > this.maxTick) {
      if (firstTime) {
        currentTick = this.maxTick;
        firstTime = false;
      } else {
        this.timer.stop();
      }
    }
  }

  @Override
  public ViewType getViewType() {
    return this.viewType;
  }
}