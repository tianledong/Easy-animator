package cs5004.animator.view;

import java.awt.event.ActionListener;
import java.util.List;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.AnimatorModelImpl;
import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Shape;

import static cs5004.animator.model.StringFormat.shapeSVG;

/**
 * This is the concrete class that represents a svg view. It's main feature is to produce a svg
 * file that can be played directly in the internet browser. It doesn't support the refresh and
 * the setCanvas method.
 */
public class SVGView implements View {
  private AnimatorModel model;
  private int speed;
  private ViewType viewType;
  private int[] canvas;

  /**
   * The constructor that initializes the SVG view. It creates an empty animator model to store
   * the shapes and the animations. The speed is set to 1 by default and the canvas's default bounds
   * are set to a certain value until the setSpeed and the setCanvas methods are called.
   */
  public SVGView() {
    this.model = new AnimatorModelImpl();
    this.speed = 1;
    this.viewType = ViewType.SVG;
    this.canvas = new int[]{0, 0, 1000, 1000};
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
    throw new UnsupportedOperationException("Can't refresh for the SVG view!");
  }

  @Override
  public void writeState(String file, int speed, boolean isLoop) {
    String viewState = this.getState(isLoop);
    TextualView.output(file, viewState);
  }

  @Override
  public double getSpeed() {
    return this.speed;
  }

  /**
   * Get the duration time of the whole animation by calculating the greatest disappear time of
   * shapes.
   *
   * @return the duration time of the whole animation
   */
  private double getDuration() {
    double duration = 0;
    for (Shape shape : this.model.getShapes()) {
      if (shape.getDisappearTime() > duration) {
        duration = shape.getDisappearTime();
      }
    }
    return duration * 1000 / this.speed;
  }

  @Override
  public String getState(boolean isLoop) {
    double duration = this.getDuration();
    String svgState = "<svg viewBox=\"" + this.canvas[0] + " " + this.canvas[1] + " "
            + this.canvas[2] + " " + this.canvas[3] + "\" version=\"1.1\" xmlns=\""
            + "http://www.w3.org/2000/svg\">\n";
    if (isLoop) {
      svgState += String.format("<rect>\n <animate id=\"base\" begin=\"0;base.end\" dur=\"%.1fms\""
                      + " attributeName=\"visibility\" from=\"hide\" to=\"hide\"/> \n</rect>\n\n",
              duration);
    }
    for (Shape shape : this.model.getShapes()) {
      svgState += shapeSVG(shape, this.speed);
      for (Behavior behavior : this.model.getAnimations()) {
        if (shape.getName().equals(behavior.getShape().getName())) {
          svgState += behavior.svgString(this.speed, isLoop);
        }
      }
      svgState += shape.svgEndTag();
    }
    return svgState + "</svg>";
  }

  @Override
  public void setCanvas(int x, int y, int width, int height) {
    this.canvas[0] = x;
    this.canvas[1] = y;
    this.canvas[2] = width;
    this.canvas[3] = height;
  }

  @Override
  public List<Shape> getShapes() {
    return this.model.getShapes();
  }


  @Override
  public void setActionListener(ActionListener listener) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set action listeners for the SVG view!");
  }


  @Override
  public void makeVisible() throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't make visible for the SVG view!");
  }

  @Override
  public void setFeature(String feature) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Can't set features for the SVG view");
  }

  @Override
  public ViewType getViewType() {
    return this.viewType;
  }
}
