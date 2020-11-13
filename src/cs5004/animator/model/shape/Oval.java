package cs5004.animator.model.shape;

import java.awt.Color;

/**
 * The concrete class that represents a oval. It extends the AbstractShape class and inherits
 * all fields. It overrides the copyShape(), getDimension1Tag(), getDimension2Tag(), and
 * getPositionTag() methods that are not implemented in the abstract class. The dimension tags are
 * "X radius: " and "Y radius: ", and the position tag is "Center: ".
 */
public class Oval extends AbstractShape {

  /**
   * A constructor that initializes a Oval object. It throws the IllegalArgumentException if
   * the same conditions in the AbstractShape constructor are met. It sets the type of the shape
   * to be OVAL and passes it to the super constructor.
   * @param name the name of the oval
   * @param position the reference point of the oval
   * @param color the color of the oval
   * @param dimension1 the first dimension of the oval
   * @param dimension2 the second dimension of the oval
   * @param appearTime the appear time of the oval
   * @param disappearTime the disappear time of the oval
   * @throws IllegalArgumentException if name is empty, point or color is null, dimensions aren't
   *                                  positive, time is negative, and appear time is after the
   *                                  disappear time
   */
  public Oval(String name, Point position, Color color, double dimension1,
              double dimension2, int appearTime, int disappearTime)
          throws IllegalArgumentException {
    super(name, ShapeType.OVAL, position, color, dimension1, dimension2, appearTime, disappearTime);
  }

  @Override
  public Shape copyShape() {
    return new Oval(this.getName(), this.getPositionData(), this.getColorData(),
            this.getDimension1(), this.getDimension2(), this.getAppearTime(),
            this.getDisappearTime()); // copy and return an new oval with the same fields
  }

  @Override
  public String getDimension1Tag() {
    return "X radius: ";
  }

  @Override
  public String getDimension2Tag() {
    return "Y radius: ";
  }

  @Override
  public String getPositionTag() {
    return "Center: ";
  }

  @Override
  public String svgPositionTagX() {
    return "cx";
  }

  @Override
  public String svgPositionTagY() {
    return "cy";
  }

  @Override
  public String svgBeginTag() {
    return "<ellipse id=\"";
  }

  @Override
  public String svgEndTag() {
    return "</ellipse>\n";
  }

  @Override
  public String dimension1SVGTag() {
    return "rx";
  }

  @Override
  public String dimension2SVGTag() {
    return "ry";
  }
}
