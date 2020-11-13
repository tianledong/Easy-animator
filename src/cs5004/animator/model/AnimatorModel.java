package cs5004.animator.model;

import java.util.List;

import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Shape;

/**
 * This is the interface that lists all public methods an animator model should support. One object
 * represents one animator model. The shapes and animations are stored in array lists, which is an
 * easy way to store, find, and sort the elements we need.
 */
public interface AnimatorModel {
  /**
   * Copy this model to protect the data.
   *
   * @return a new model that has the same data with this model.
   */
  AnimatorModel copyModel();

  /**
   * get the leftmost x value of bound.
   *
   * @return the leftmost x value
   */
  int getX();

  /**
   * get the topmost y value of bound.
   *
   * @return the topmost y value
   */
  int getY();

  /**
   * get the width of the bounding box.
   *
   * @return width of the bounding box.
   */
  int getWidth();

  /**
   * get the height of the bounding box.
   *
   * @return height the height of the bounding box
   */
  int getHeight();

  /**
   * set the leftmost x value.
   *
   * @param x the leftmost x value
   */
  void setX(int x);

  /**
   * set the topmost y value.
   *
   * @param y the topmost y value
   */
  void setY(int y);

  /**
   * set the width of the bounding box.
   *
   * @param width width of the bounding box.
   */
  void setWidth(int width);

  /**
   * set the height of the bounding box.
   *
   * @param height the height of the bounding box
   */
  void setHeight(int height);

  /**
   * return the list which is storing shapes.
   *
   * @return the list which is storing shapes.
   */
  List<Shape> getShapes();

  /**
   * return the list which is storing behaviors.
   *
   * @return the list which is storing behaviors.
   */
  List<Behavior> getAnimations();

  /**
   * Add the given shape to the shape list. Can't add shape that has the same name to the list to
   * avoid contradictions in assigning animations.
   *
   * @param s the shape to be added.
   * @throws IllegalArgumentException if the given shape is already in the list (same name)
   */
  void addShape(Shape s) throws IllegalArgumentException;

  /**
   * Add a new animation behavior.
   *
   * @param b behavior that is going to be added.
   * @throws IllegalArgumentException if the animation is slapped.
   */
  void addAnimation(Behavior b) throws IllegalArgumentException;

  /**
   * Return the string that records the state of the animator. Example: Shapes: Name: R Type:
   * rectangle Min corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0) Appears
   * at t=1 Disappears at t=100
   * <p></p>
   * Name: C Type: oval Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)
   * Appears at t=6 Disappears at t=100
   * <p></p>
   * Shape R moves from (200.0,200.0) to (300.0,300.0) from t=10 to t=50 Shape C moves from
   * (500.0,100.0) to (500.0,400.0) from t=20 to t=70 Shape C changes color from (0.0,0.0,1.0) to
   * (0.0,1.0,0.0) from t=50 to t=80 Shape R scales from Width: 50.0, Height: 100.0 to Width: 25.0,
   * Height: 100.0 from t=51 to t=70 Shape R moves from (300.0,300.0) to (200.0,200.0) from t=70 to
   * t=100
   *
   * @return the string that records the state of the animator
   */
  String getState();

  /**
   * get the minimum start time of the shape list and return it.
   *
   * @return the minimum start time of the shape list
   */
  int getMinTick();

  /**
   * get the maximum end time of the shape list and return it.
   *
   * @return the maximum end time time of the shape list
   */
  int getMaxTick();
}
