package cs5004.animator.model.shape;

import java.awt.Color;

/**
 * The concrete class that represents a rectangle. It extends the AbstractShape class and inherits
 * all fields. It overrides the copyShape(), getDimension1Tag(), getDimension2Tag(), and
 * getPositionTag() methods that are not implemented in the abstract class. The dimension tags are
 * "Width: " and "Height: ", and the position tag is "Min corner: ".
 */
public class Rectangle extends AbstractShape {

  /**
   * A constructor that initializes a Rectangle object. It throws the IllegalArgumentException if
   * the same conditions in the AbstractShape constructor are met. It sets the type of the shape
   * to be RECTANGLE and passes it to the super constructor.
   * @param name the name of the rectangle
   * @param position the reference point of the rectangle
   * @param color the color of the rectangle
   * @param dimension1 the first dimension of the rectangle
   * @param dimension2 the second dimension of the rectangle
   * @param appearTime the appear time of the rectangle
   * @param disappearTime the disappear time of the rectangle
   * @throws IllegalArgumentException if name is empty, point or color is null, dimensions aren't
   *                                  positive, time is negative, and appear time is after the
   *                                  disappear time
   */
  public Rectangle(String name, Point position, Color color,
                   double dimension1, double dimension2, int appearTime, int disappearTime)
          throws IllegalArgumentException {
    super(name, ShapeType.RECTANGLE, position, color, dimension1, dimension2, appearTime,
            disappearTime);
  }

  @Override
  public Shape copyShape() {
    return new Rectangle(this.getName(), this.getPositionData(), this.getColorData(),
            this.getDimension1(), this.getDimension2(), this.getAppearTime(),
            this.getDisappearTime()); // copy and return a new rectangle with the same fields
  }

  @Override
  public String getDimension1Tag() {
    return "Width: ";
  }

  @Override
  public String getDimension2Tag() {
    return "Height: ";
  }

  @Override
  public String getPositionTag() {
    return "Min corner: ";
  }

  @Override
  public String svgPositionTagX() {
    return "x";
  }

  @Override
  public String svgPositionTagY() {
    return "y";
  }

  @Override
  public String svgBeginTag() {
    return "<rect id=\"";
  }

  @Override
  public String svgEndTag() {
    return "</rect>\n";
  }

  @Override
  public String dimension1SVGTag() {
    return "width";
  }

  @Override
  public String dimension2SVGTag() {
    return "height";
  }
}
