package cs5004.animator.model.shape;

import java.awt.Color;

/**
 * An interface that contains all public methods a Shape object should support. One object of the
 * model represents one Shape object.
 */
public interface Shape {

  /**
   * Copy a Shape object and return it. This is for the use of observation and avoid mutating the
   * original shape's attributes.
   * @return a copy of the Shape object
   */
  Shape copyShape();

  /**
   * Get the reference point of the shape in the form of "Tag: (x.x,y.y)". All floats are rounded
   * to exactly one decimal place. The tag is determined by the actual type of the shape. For
   * example, a rectangle's position tag is "Min corner: ", and the full string might be
   * "Min corner: (0.0,1.0)".
   * @return the string that represents the reference point of a shape
   */
  String getPosition();

  /**
   * Get the reference point itself. Return a Point object instead of a string.
   * @return a Point that represents the reference point of a shape
   */
  Point getPositionData();

  /**
   * Get the dimensions of a shape. The string that is returned is of the form
   * "Tag1: x.x, Tag2: y.y". The tags are determined by the actual type of the shape. All floats
   * are rounded to exactly one decimal place.
   * @return the strings that represents the dimensions of a shape
   */
  String getDimensions();

  /**
   * Get the color string of a shape in the form of (x,y,z). X, y, and z are the sRGB numbers of the
   * color and are between 0 and 1.0. All floats are rounded to exactly one decimal place.
   * @return the color string of a shape
   */
  String getColor();

  /**
   * Get the color of a shape. Returns a Color object instead of a string.
   * @return a Color object that is the shape's color
   */
  Color getColorData();

  /**
   * Get the appear time of a shape. The appear time is an int and represents the time reference,
   * which is unitless.
   * @return the time reference of the appear time of a shape
   */
  int getAppearTime();

  /**
   * Get the disappear time of a shape. The appear time is an int and represents the time reference,
   * which is unitless.
   * @return the time reference of the disappear time of a shape
   */
  int getDisappearTime();

  /**
   * Get the first dimension of the shape.
   * @return the first dimension of the shape
   */
  double getDimension1();

  /**
   * Get the second dimension of the shape.
   * @return the second dimension of the shape
   */
  double getDimension2();

  /**
   * Get the first dimension's tag. For example, a Rectangle's dimension1 tag is "Width: ".
   * @return the first dimension's tag
   */
  String getDimension1Tag();

  /**
   * Get the second dimension's tag. For example, a Rectangle's dimension2 tag is "Height: ".
   * @return the second dimension's tag
   */
  String getDimension2Tag();

  /**
   * Get the position tag of a shape. For instance, an Oval's position tag is "Center: ".
   * @return the position tag of a shape
   */
  String getPositionTag();

  /**
   * Set the first dimension of a shape to the given value. It can't be 0 or negative.
   * @param dimension1 the new first dimension of a shape
   * @throws IllegalArgumentException if the new dimension is the same as the old one or is negative
   */
  void setDimension1(double dimension1) throws IllegalArgumentException;

  /**
   * Set the second dimension of a shape to the given value. It can't be 0 or negative.
   * @param dimension2 the new first dimension of a shape
   * @throws IllegalArgumentException if the new dimension is the same as the old one or is negative
   */
  void setDimension2(double dimension2) throws IllegalArgumentException;

  /**
   * Set the color of a shape to the given color. It can't be null.
   * @param color the new color of a shape
   * @throws IllegalArgumentException if the new color is the same as the old one or is null
   */
  void setColor(Color color) throws IllegalArgumentException;

  /**
   * Set the reference point of a shape to the given point. It can't be null.
   * @param position the new reference point of a shape
   * @throws IllegalArgumentException if the new reference point is the same as the old one or null
   */
  void setPosition(Point position) throws IllegalArgumentException;

  /**
   * Get the name of the shape.
   * @return the name of the shape
   */
  String getName();

  /**
   * Get the string that represents the type of the shape, like "oval", "rectangle", etc.
   * @return the string that represents the type of the shape
   */
  String getType();

  /**
   * Get the string that contains all data of a shape.
   * Example:
   *   Shapes:
   *   Name: Rectangle1
   *   Type: rectangle
   *   Min corner: (2.0,1.0), Width: 5.0, Height: 3.0, Color: (1.0,1.0,0.0)
   *   Appears at t=2
   *   Disappears at t=12
   * @return the string that contains all data of a shape
   */
  String toString();

  /**
   * Get the svg tag of the x coordinate of the reference point. Example: "x" (rectangle),
   * "cx"(oval).
   * @return the svg tag of the x coordinate of the reference point
   */
  String svgPositionTagX();

  /**
   * Get the svg tag of the y coordinate of the reference point. Example: "y" (rectangle),
   * "cy"(oval).
   * @return the svg tag of the y coordinate of the reference point
   */
  String svgPositionTagY();

  /**
   * Get the svg tag that locates at the beginning of a svg statement. It indicates what type of
   * shape it is. Example: "rect id=" (rectangle), "ellipse id=" (oval).
   * @return the svg tag that locates at the beginning of a svg statement
   */
  String svgBeginTag();

  /**
   * Get the svg tag that locates at the end of a svg statement. It indicates the end of the current
   * shape's animation. Example: "rect" (rectangle), "ellipse" (oval).
   * @return the svg tag that locates at the end of a svg statement
   */
  String svgEndTag();

  /**
   * The svg tag that represents the first dimension of a shape. It indicates what attribute of
   * the shape will change. Example: "width" (rectangle), "rx" (oval).
   * @return the svg tag that represents the first dimension of a shape
   */
  String dimension1SVGTag();

  /**
   * The svg tag that represents the second dimension of a shape. It indicates what attribute of
   * the shape will change. Example: "height" (rectangle), "ry" (oval).
   * @return the svg tag that represents the second dimension of a shape
   */
  String dimension2SVGTag();
}
