package cs5004.animator.controller;

/**
 * The interface that lists all public methods a controller should support. This controller is
 * designed for every view supported by the animator.
 */
public interface Controller {

  /**
   * Set the model and speed for each view and then calls the main features of the views. Depending
   * on the view type of view stored in the controller, methods that are called might vary from
   * different views. For example, calling the makeVisible method for the editor view and the visual
   * view is necessary.
   */
  void start();

  /**
   * Get the operation log of the controller. Each time the user pressed a button in the editor
   * view, it's recorded in the log, like "Play button pressed", etc..
   *
   * @return the operation log of the controller
   */
  String getLog();
}
