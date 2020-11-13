package cs5004.animator.model.behavior;

import cs5004.animator.model.shape.Shape;

/**
 * This interface represents all the method all Behaviors should have. An animation behavior should
 * be able to return its start time, end time, shape, current behavior, begin status , end status,
 * type and description.
 */

public interface Behavior {
  /**
   * Return the start time of the animation behavior.
   *
   * @return the start time of the animation behavior.
   */
  int getStartTime();

  /**
   * Return the end time of the animation behavior.
   *
   * @return the end time of the animation behavior.
   */
  int getEndTime();

  /**
   * Return the shape of the animation behavior.
   *
   * @return the shape of the animation behavior.
   */
  Shape getShape();

  /**
   * Return the string of the current behavior does.
   *
   * @return the string of the current behavior does.
   */
  String getCurrentBehavior();

  /**
   * Get the start status of the shape.
   *
   * @return the start status of the shape.
   */
  String getStartStatus();

  /**
   * Get the end status of the shape.
   *
   * @return the end status of the shape.
   */
  String getEndStatus();

  /**
   * Return a string of description of the behavior does.
   *
   * @return a string of description of the behavior does.
   */
  String getDescription();

  /**
   * Return the type of the behavior.
   *
   * @return the type of the behavior.
   */
  BehaviorType getType();

  /**
   * Change the shape by the behavior for controller by current time.
   *
   * @param currentTime the current time to use change the shape data.
   */
  void change(double currentTime);

  /**
   * Return a string that is used to generate a svg file. The boolean isLoop represents if the
   * animation will loop back. Based on the loop status, the string could be different.
   *
   * @param speed  the speed of the animation
   * @param isLoop whether the animation loops back after finish
   * @return a string that is used to generate a svg file.
   */
  String svgString(int speed, boolean isLoop);

  /**
   * Get copy of this behavior with given shape.
   * @return A new behavior class with the same data.
   */
  Behavior copyBehavior(Shape shape);
}
