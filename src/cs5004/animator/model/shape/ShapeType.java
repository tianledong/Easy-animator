package cs5004.animator.model.shape;

/**
 * This is the enum class that represent the type of a Shape object. The toString() method returns
 * the string that is the name of the type of a shape, such as, "rectangle", "oval", etc.
 */
public enum ShapeType {
  RECTANGLE("rectangle"),
  OVAL("oval");

  private String txt;

  ShapeType(String txt) {
    this.txt = txt;
  }

  /**
   * Returns the string of the type of a shape ("rectangle", "oval", etc.).
   * @return the string of the type of a shape
   */
  public String toString() {
    return this.txt;
  }
}
