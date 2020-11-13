package cs5004.animator.view;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.util.AnimationReader;

/**
 * This is the concrete class that represents an editor view. It extends JFrame and implements
 * interface View and ActionListener. It's main feature is to show a GUI to interact with users.
 * First, users can play, pause, resume, restart and loop animations by clink buttons. Second, users
 * can control the speed of animations by increase and decrease speed buttons, but the speed can't
 * be less than 1. Current speed will be shown on the GUI. Third, uses can produce svg and text file
 * by export svg and export text buttons. Last, uses can use Open file button to open files and play
 * other animation.
 */
public class EditorView extends JFrame implements View, ActionListener {
  private AnimationPanel panel;

  private JButton startButton;
  private JButton pauseButton;
  private JButton resumeButton;
  private JButton restartButton;
  private JButton speedUp;
  private JButton speedDown;
  private JButton exportText;
  private JButton openFile;
  private JButton exportSVG;
  private JCheckBox loop;
  private JLabel speedLabel;

  private int speed;
  private final Timer timer;
  private List<Shape> shapes;
  private AnimatorModel model;
  private AnimatorModel modelCopy;
  private double currentTick;
  private int maxTick;
  private boolean isLoop = false;
  private boolean firstTime = true;
  private ViewType viewType;

  /**
   * The constructor that initializes the editor view. It creates an empty animator model and a copy
   * of the model. Both need to be set up by calling the setModel method. It also initializes
   * JFrame, timer, current tick and set the default speed to be 1. The JFrame components are not
   * added until the private method setUpComponents is called.
   */
  public EditorView() {
    super();
    this.model = new AnimatorModelImpl();
    this.modelCopy = model.copyModel();
    this.setCanvas(0, 0, 1300, 1300);
    this.currentTick = this.model.getMinTick();
    this.timer = new Timer(1, this);
    this.setVisible(false);
    this.speed = 1;
    this.viewType = ViewType.EDITOR;
  }

  @Override
  public void setModel(AnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null!");
    }
    this.model = model;
    this.setUpComponents();
    this.panel.setShapes(this.model.getShapes());
    this.modelCopy = this.model.copyModel();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void setSpeed(int speed) throws IllegalArgumentException {
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive!");
    }
    this.speed = speed;
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void setActionListener(ActionListener listener) {
    this.startButton.addActionListener(listener);
    this.pauseButton.addActionListener(listener);
    this.resumeButton.addActionListener(listener);
    this.restartButton.addActionListener(listener);
    this.loop.addActionListener(listener);
    this.speedUp.addActionListener(listener);
    this.speedDown.addActionListener(listener);
    this.exportText.addActionListener(listener);
    this.exportSVG.addActionListener(listener);
    this.openFile.addActionListener(listener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (this.timer.isRunning()) {
      for (Behavior behavior : this.model.getAnimations()) {
        behavior.change(this.currentTick);
      }
    }
    this.refresh();
    currentTick = currentTick + (0.01 * this.speed);
    if (currentTick > this.maxTick) {
      if (firstTime) {
        currentTick = this.maxTick;
        firstTime = false;
      } else {
        if (isLoop) {
          restart();
        } else {
          this.timer.stop();
        }
      }
    }
  }

  @Override
  public void writeState(String file, int speed, boolean isLoop)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't write the animation state for the editor view!");
  }

  @Override
  public double getSpeed() {
    return this.speed;
  }

  @Override
  public String getState(boolean isLoop) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't get the animation state for the editor view");
  }

  @Override
  public List<Shape> getShapes() {
    return this.shapes;
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) {
    this.setBounds(x, y, width, height);
  }

  @Override
  public ViewType getViewType() {
    return this.viewType;
  }

  @Override
  public void setFeature(String feature) {
    switch (feature) {
      case "PLAY":
        this.play();
        break;
      case "PAUSE":
        this.pause();
        break;
      case "RESUME":
        this.resume();
        break;
      case "RESTART":
        this.restart();
        break;
      case "INCREASE SPEED":
        this.increaseSpeed();
        break;
      case "DECREASE SPEED":
        this.decreaseSpeed();
        break;
      case "LOOP":
        this.loop();
        break;
      case "TEXT":
        this.text();
        break;
      case "SVG":
        this.svg();
        break;
      case "OPEN":
        this.open();
        break;
      default:
        JOptionPane.showMessageDialog(null,
                "Unsupported feature!", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  /**
   * A private method that sets up and add the JFrame components.
   */
  private void setUpComponents() {
    this.setTitle("Easy Animator");
    this.setPreferredSize(new Dimension(1300, 1000));
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.shapes = this.model.getShapes();
    this.maxTick = this.model.getMaxTick();
    this.panel = new AnimationPanel(this.shapes);
    this.add(this.panel);

    this.startButton = new JButton("Play");
    this.startButton.setActionCommand("PLAY");

    this.pauseButton = new JButton("Pause");
    this.pauseButton.setActionCommand("PAUSE");

    this.resumeButton = new JButton("Resume");
    this.resumeButton.setActionCommand("RESUME");

    this.restartButton = new JButton("Restart");
    this.restartButton.setActionCommand("RESTART");

    this.loop = new JCheckBox("Loop");
    this.loop.setActionCommand("LOOP");

    this.speedUp = new JButton("Increase speed");
    this.speedUp.setActionCommand("INCREASE SPEED");

    this.speedDown = new JButton("Decrease speed");
    this.speedDown.setActionCommand("DECREASE SPEED");

    this.exportText = new JButton("Export text file");
    this.exportText.setActionCommand("TEXT");

    this.openFile = new JButton("Open file");
    this.openFile.setActionCommand("OPEN");

    this.exportSVG = new JButton("Export SVG file");
    this.exportSVG.setActionCommand("SVG");

    JLabel speedTag = new JLabel("Current speed");
    this.speedLabel = new JLabel("speed");
    this.speedLabel.setText(String.valueOf(this.speed));

    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    filesavePanel.add(this.exportSVG);
    filesavePanel.add(this.exportText);
    filesavePanel.add(this.openFile);


    JPanel speedPanel = new JPanel();
    speedPanel.setLayout(new FlowLayout());
    speedPanel.add(this.speedUp);
    speedPanel.add(this.speedDown);
    speedPanel.add(speedTag);
    speedPanel.add(this.speedLabel);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    buttonPanel.add(this.startButton);
    buttonPanel.add(this.pauseButton);
    buttonPanel.add(this.resumeButton);
    buttonPanel.add(this.restartButton);
    buttonPanel.add(this.loop);
    buttonPanel.add(this.exportText);
    buttonPanel.add(this.exportSVG);
    buttonPanel.add(this.openFile);

    buttonPanel.add(speedPanel);
    buttonPanel.add(filesavePanel);
    this.add(buttonPanel, BorderLayout.SOUTH);
    this.setActionListener(this);

    this.pauseButton.setEnabled(false);
    this.restartButton.setEnabled(false);
    this.resumeButton.setEnabled(false);

    JScrollPane scrollPane = new JScrollPane(this.panel);
    scrollPane.setPreferredSize(new Dimension(1300, 1000));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    this.add(scrollPane, BorderLayout.EAST);

    this.setVisible(false);
    this.pack();
  }

  /**
   * This is a private function for executing the behavior of the Play button to start the timer.
   * Also, enable Pause and Restart button and disable Play button.
   */
  private void play() {
    this.timer.start();
    this.startButton.setEnabled(false);
    this.pauseButton.setEnabled(true);
    this.restartButton.setEnabled(true);
  }

  /**
   * This is a private function for executing the behavior of the Pause button to stop the timer.
   * Also, disable Pause button and enable Resume button.
   */
  private void pause() {
    this.timer.stop();
    this.pauseButton.setEnabled(false);
    this.resumeButton.setEnabled(true);
  }

  /**
   * This is a private function for executing the behavior of the Resume button to restart the
   * timer. Also, enable Pause button and disable Resume button.
   */
  private void resume() {
    this.timer.restart();
    this.pauseButton.setEnabled(true);
    this.resumeButton.setEnabled(false);
  }


  /**
   * This is a private function for executing the behavior of the Decrease speed button to increase
   * the speed by 1. When Decrease speed button is disabled, make it enable.
   */
  private void restart() {
    this.timer.stop();
    this.pauseButton.setEnabled(true);
    this.resumeButton.setEnabled(false);
    this.currentTick = this.model.getMinTick();
    this.model = this.modelCopy.copyModel();
    this.panel.resetShapes(this.model.getShapes());
    this.firstTime = true;
    this.timer.start();
  }

  /**
   * This is a private function for executing the behavior of the Decrease speed button to increase
   * the speed by 1. When Decrease speed button is disabled, make it enable.
   */
  private void increaseSpeed() {
    this.speed++;
    this.speedLabel.setText(String.valueOf(this.speed));
    if (!this.speedDown.isEnabled()) {
      this.speedDown.setEnabled(true);
    }
  }

  /**
   * This is a private function for executing the behavior of the Decrease speed button to decrease
   * the speed by 1. Speed must greater than 1. When speed is 1, the decrease speed button will be
   * disabled.
   */
  private void decreaseSpeed() {
    if (this.speed > 2) {
      this.speed--;
    } else if (this.speed == 2) {
      this.speed--;
      this.speedDown.setEnabled(false);
    }
    this.speedLabel.setText(String.valueOf(this.speed));
  }

  /**
   * This is a private function for executing the behavior of the Loop button to change the boolean
   * value of loop in this view.
   */
  private void loop() {
    this.isLoop = !isLoop;
  }

  /**
   * This is a private function for executing the behavior of the Export txt file button to produce
   * a txt file.
   */
  private void text() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(EditorView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      TextualView textualView = new TextualView();
      textualView.setModel(this.modelCopy.copyModel());
      textualView.setSpeed(this.speed);
      textualView.writeState(f.toString(), this.speed, this.isLoop);
      JOptionPane.showMessageDialog(EditorView.this,
              "Succeed!", "Export Text File", JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * This is a private function for executing the behavior of the Export svg file button to produce
   * a svg file.
   */
  private void svg() {
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(EditorView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      SVGView svgView = new SVGView();
      svgView.setModel(this.modelCopy.copyModel());
      svgView.setSpeed(this.speed);
      svgView.setCanvas(this.model.getX(), this.model.getY(), this.model.getWidth(),
              this.model.getHeight());
      svgView.writeState(f.toString(), speed, this.isLoop);
      JOptionPane.showMessageDialog(EditorView.this,
              "Succeed!", "Export SVG File", JOptionPane.PLAIN_MESSAGE);
    }
  }

  /**
   * This is a private function for executing the behavior of the Open file button to open a new
   * file and play animation in the GUI.
   */
  private void open() {
    Readable in = null;
    final JFileChooser fchooser = new JFileChooser(".");
    int retvalue = fchooser.showSaveDialog(EditorView.this);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      try {
        in = new FileReader(f.toString());
      } catch (FileNotFoundException e) {
        JOptionPane.showMessageDialog(null,
                "File not found!", "Error", JOptionPane.ERROR_MESSAGE);
      }
      AnimatorModel tempModel = AnimationReader.parseFile(in, new AnimatorModelImpl.Builder());
      if (!tempModel.getShapes().isEmpty()) {
        this.model = tempModel;
        this.modelCopy = this.model.copyModel();
        this.shapes = this.model.getShapes();
        this.panel.setShapes(this.shapes);
        this.maxTick = this.model.getMaxTick();
        restart();
        this.timer.stop();
        this.restartButton.setEnabled(false);
        this.pauseButton.setEnabled(false);
        this.startButton.setEnabled(true);
        JOptionPane.showMessageDialog(EditorView.this,
                "Succeed opened file!", "Open File", JOptionPane.PLAIN_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null,
                "Invalid File!", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}

