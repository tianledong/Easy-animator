package cs5004.animator.model.shape;

/**
 * This class represents a 2D point. It's used as the reference point of a Shape object.
 */
public class Point {
  private double x;
  private double y;

  /**
   * A constructor that assigns values to the fields.
   * @param x the x coordinate of the point
   * @param y the y coordinate of the point
   */
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Get the x coordinate of the point.
   * @return the x coordinate of the point
   */
  public double getX() {
    return this.x;
  }

  /**
   * Get the Y coordinate of the point.
   * @return the y coordinate of the point
   */
  public double getY() {
    return this.y;
  }
}
