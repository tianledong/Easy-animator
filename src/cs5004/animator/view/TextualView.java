package cs5004.animator.view;

import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Shape;

/**
 * This is the concrete class that represents a textual view. It's main feature is to produce a text
 * file that describes the animation. It doesn't support the refresh and the setCanvas method.
 */
public class TextualView implements View {
  private AnimatorModel model;
  private int speed;
  private ViewType viewType;

  /**
   * The constructor that initializes the textual view. It creates an empty animator model
   * to store the shapes and the animations. The actual model needs to be set up by calling the
   * setModel method. The speed is set to 1 by default.
   */
  public TextualView() {
    this.speed = 1; // set default speed
    this.model = new AnimatorModelImpl();
    this.viewType = ViewType.TEXTUAL;
  }

  @Override
  public void setModel(AnimatorModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model can't be null");
    }
    this.model = model;
  }

  @Override
  public void setSpeed(int speed) throws IllegalArgumentException {
    if (speed <= 0) {
      throw new IllegalArgumentException("Speed must be positive!");
    }
    this.speed = speed;
  }

  @Override
  public void refresh() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't refresh for the textual view!");
  }

  @Override
  public void writeState(String file, int speed, boolean isLoop) {
    String viewState = "Speed = " + speed + " tick(s) per second\n" + this.getState(isLoop);
    output(file, viewState);
  }

  /**
   * Output the string description of an animation to a file. It's package private and it's only
   * used in the textual view and the svg view. The default output is System.out.
   *
   * @param file the name of the file to be output
   * @param viewState the string description of an animation
   */
  static void output(String file, String viewState) {
    BufferedWriter writer = null;
    try {
      if (file.equalsIgnoreCase("System.out")) {
        writer = new BufferedWriter(new OutputStreamWriter(System.out));
      } else {
        File newFile = new File(file);
        if (!newFile.exists()) { // make sure the file exists
          newFile.createNewFile();
        }
        writer = new BufferedWriter(new FileWriter(newFile));
      }
      writer.write(viewState);
    } catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      try { // safely close the file
        if (writer != null) {
          writer.close();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public double getSpeed() {
    return this.speed;
  }

  private String getAllShapes() {
    StringBuilder allShapes = new StringBuilder();
    for (Shape shape : this.model.getShapes()) {
      allShapes.append("Created a ").append(shape.getType()).append(" ").append(shape.getName())
              .append(" with ").append(shape.getPositionTag()).append(shape.getPosition())
              .append(", ").append(shape.getDimension1Tag()).append(shape.getDimension1())
              .append(" and ").append(shape.getDimension2Tag()).append(shape.getDimension2())
              .append(", Color: ").append(shape.getColor()).append("\n");
    }
    return allShapes.toString();
  }

  @Override
  public String getState(boolean isLoop) {
    String allShapes = getAllShapes();
    String shapeState =
            this.model.getShapes().stream().map(Shape::toString)
                    .collect(Collectors.joining("\n\n"));
    String animationState =
            this.model.getAnimations().stream().map(Behavior::getDescription)
                    .collect(Collectors.joining("\n"));
    return allShapes + "\n\n" + shapeState + "\n\n" + animationState;
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set canvas for the textual view");
  }

  @Override
  public List<Shape> getShapes() {
    return this.model.getShapes();
  }


  @Override
  public void setActionListener(ActionListener listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set action listeners for the textual view!");
  }

  @Override
  public void makeVisible() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't make visible for the textual view!");
  }

  @Override
  public void setFeature(String feature) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set features for the textual view");
  }

  @Override
  public ViewType getViewType() {
    return this.viewType;
  }
}
