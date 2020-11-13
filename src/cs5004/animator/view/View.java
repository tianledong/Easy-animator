package cs5004.animator.view;

import java.awt.event.ActionListener;
import java.util.List;

import cs5004.animator.model.AnimatorModel;
import cs5004.animator.model.shape.Shape;

/**
 * This is the interface that supports all public methods a concrete view type should implement.
 * Depends on the actual concrete view class, some methods throw UnsupportedOperationException
 * to stop users from calling the methods. Some methods are designed for the future possible use
 * when the controller is added, thus, there might be some features that are not being used in the
 * current version.
 */
public interface View {

  /**
   * Refresh the current frame and ask the JFrame to repaint all components. It's not supported by
   * the textual view and the svg view.
   *
   * @throws UnsupportedOperationException if the textual view or the svg view calls the method
   */
  void refresh() throws UnsupportedOperationException;

  /**
   * Output the string description of the animation to a file. The visual view doesn't support this
   * method. The output of the svg view is a file that can be played in an internet browser. If the
   * file name is empty, the default output is System.out. The boolean isLoop determines whether the
   * output SVG file loops back, and it only works in the SVG view.
   * The visual view doesn't support this method.
   *
   * @param file the name of the file that is the target of the output
   * @param speed the speed (ticks per second) of the animation
   * @param isLoop determines if the animation loops back
   * @throws UnsupportedOperationException if the visual view calls the method
   */
  void writeState(String file, int speed, boolean isLoop) throws UnsupportedOperationException;

  /**
   * Set the play speed for the view. The visual view doesn't support this method. The speed can't
   * be non-positive.
   *
   * @param speed the speed (ticks per second) of the animation
   * @throws IllegalArgumentException if speed is 0 or negative
   * @throws UnsupportedOperationException if the visual view calls the method
   */
  void setSpeed(int speed) throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Get the play speed of the current view. The visual view doesn't support this method.
   *
   * @return the speed of the current view
   * @throws UnsupportedOperationException if the visual view calls the method
   */
  double getSpeed() throws UnsupportedOperationException;

  /**
   * Get the string description of the animation. The visual view doesn't support this method. Since
   * there is a svg file example provided, we decide to set 2 types of svg descriptions (loops back
   * or not) for the future use, though it's not being used in the current version.
   *
   * @param isLoop if the animation loops back after finish
   * @return the string description of the animation
   * @throws UnsupportedOperationException if the visual view calls the method
   */
  String getState(boolean isLoop) throws UnsupportedOperationException;

  /**
   * Get the list of shapes in the current view.
   *
   * @return the list of shapes in the current view
   */
  List<Shape> getShapes();

  /**
   * Set the bounds of the view window. The textual view doesn't support this method.
   *
   * @param x the x coordinate of the canvas
   * @param y the y coordinate of the canvas
   * @param width the width of the canvas
   * @param height the height of the canvas
   * @throws UnsupportedOperationException if the textual view calls the method
   */
  void setCanvas(int x, int y, int width, int height) throws UnsupportedOperationException;

  /**
   * Set the action listeners for each button. It's only supported by the editor view and the
   * listener is the editor view itself.
   *
   * @param listener the action listener to be set to the buttons.
   * @throws UnsupportedOperationException if views other than the editor view calls the method
   */
  void setActionListener(ActionListener listener) throws UnsupportedOperationException;

  /**
   * Make the views that extend JFrame visible. The textual view and the SVG view don't support
   * this method.
   *
   * @throws UnsupportedOperationException if the textual view or the SVG view calls the method
   */
  void makeVisible() throws UnsupportedOperationException;

  /**
   * Set the animator model for the view. The model can't be null.
   *
   * @param model the animator model to be set
   * @throws IllegalArgumentException if the model is null
   */
  void setModel(AnimatorModel model) throws IllegalArgumentException;

  /**
   * Call different methods based on the string passed in. All feature methods are private, and this
   * method is only supported by the editor view. Features include play, pause, resume, restart,
   * export files, load files, and loop back the animation.
   *
   * @param feature the string that determines which feature will be called
   * @throws UnsupportedOperationException if views other than the editor view calls the method
   */
  void setFeature(String feature) throws UnsupportedOperationException;

  /**
   * Get the view type of the current view class. Available types: TEXTUAL for the textual view, SVG
   * for the svg view, VISUAL for the visual view, and EDITOR for the editor view.
   *
   * @return the view type of the current view class
   */
  ViewType getViewType();
}
