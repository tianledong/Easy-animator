package cs5004.animator.model;

import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Shape;

import java.awt.Color;

import static java.lang.Math.round;

/**
 * This class is used to provide formatted strings that represent states and descriptions of shapes
 * and animations.
 */
public class StringFormat {

  /**
   * Return the string that represent a color in the form of (x,y,z). X, y, and z are the sRGB
   * numbers of the color and are between 0 and 1.0. All floats are rounded to exactly one decimal
   * place.
   *
   * @param c the color to be formatted
   * @return the string that represents a color
   */
  public static String getColorString(Color c) {
    return String.format("(%.1f,%.1f,%.1f)", c.getRed() / 255.0, c.getGreen() / 255.0,
            c.getBlue() / 255.0);
  }

  /**
   * Return the string that represents a 2D point in the form of (x.x,y.x). All floats are rounded
   * to exactly one decimal place.
   *
   * @param p a 2D point to be formatted
   * @return the string that represents a 2D point
   */
  public static String getPositionString(Point p) {
    return String.format("(%.1f,%.1f)", p.getX(), p.getY());
  }

  /**
   * Return the string that describes a shape and contains all data of the shape. Example: Shapes:
   * Name: Rectangle1 Type: rectangle Min corner: (2.0,1.0), Width: 5.0, Height: 3.0, Color:
   * (1.0,1.0,0.0) Appears at t=2 Disappears at t=12
   *
   * @param s the shape to be converted
   * @return the string that describes a shape and contains all data of the shape.
   */
  public static String getShapeDescription(Shape s) {
    return String.format("Name: %s\nType: %s\n%s%s, %s, Color: %s\n" +
                    "Appears at t=%d\nDisappears at t=%d", s.getName(),
            s.getType(), s.getPositionTag(), s.getPosition(), s.getDimensions(),
            s.getColor(), s.getAppearTime(), s.getDisappearTime());
  }

  /**
   * Return the string that describes a behavior. Example: Shape R moves from (200.0,200.0) to
   * (300.0,300.0) from t=10 to t=50
   *
   * @param b the behavior to be formatted
   * @return the string that describes a behavior
   */
  public static String getBehaviorDescription(Behavior b) {
    return "Shape " + b.getShape().getName() + " " + b.getCurrentBehavior() + " from "
            + b.getStartStatus() + " to " + b.getEndStatus() + " from t=" + b.getStartTime()
            + " to t=" + b.getEndTime();
  }

  /**
   * Return the string that represents the dimensions of a shape. Example of a rectangle's
   * dimensions: Width: 50.0, Height: 100.0
   *
   * @param s the shape that provides dimensions to be formatted
   * @return the string that represents the dimensions of a shape
   */
  public static String getDimensionsString(Shape s) {
    return String.format("%s%.1f, %s%.1f", s.getDimension1Tag(), s.getDimension1(),
            s.getDimension2Tag(), s.getDimension2());
  }

  /**
   * Return a string that can be used in the svg file to describe the color of a shape.
   *
   * @param c the color to be formatted
   * @return the string that can be used in the svg file to describe the color of a shape
   */
  public static String getSVGColor(Color c) {
    return "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")\" ";
  }

  /**
   * Return a string that can be used in the svg file to describe a shape, including its reference
   * point, name, type, color, dimensions, appear and disappear time.
   *
   * @param s     the shape to be formatted
   * @param speed the speed of the animation
   * @return a string that can be used in the svg file to describe a shape
   */
  public static String shapeSVG(Shape s, int speed) {
    int visibleTime = s.getAppearTime() * 1000 / speed;

    if (s.getType().equals("Oval")) {
      return s.svgBeginTag() + s.getName() + "\" " + s.svgPositionTagX() + "=\""
              + round(s.getPositionData().getX() + s.getDimension1()) + "\" " + s.svgPositionTagY()
              + "=\"" + round(s.getPositionData().getY() + s.getDimension2()) + "\" "
              + s.dimension1SVGTag() + "=\"" + round(s.getDimension1()) + "\" "
              + s.dimension2SVGTag() + "=\"" + round(s.getDimension2()) + "\" fill=\""
              + getSVGColor(s.getColorData())
              + " visibility=\"hidden\" >\n    "
              + "<set attributeName=\"visibility\" attributeType=\"XML\"\n"
              + "         to=\"visible\"\n"
              + "         begin=\"" + visibleTime + "ms\" />\n";
    }
    return s.svgBeginTag() + s.getName() + "\" " + s.svgPositionTagX() + "=\""
            + round(s.getPositionData().getX()) + "\" " + s.svgPositionTagY() + "=\""
            + round(s.getPositionData().getY()) + "\" " + s.dimension1SVGTag() + "=\""
            + round(s.getDimension1()) + "\" " + s.dimension2SVGTag() + "=\""
            + round(s.getDimension2()) + "\" fill=\"" + getSVGColor(s.getColorData())
            + " visibility=\"hidden\" >\n    "
            + "<set attributeName=\"visibility\" attributeType=\"XML\"\n"
            + "         to=\"visible\"\n"
            + "         begin=\"" + visibleTime + "ms\" />\n";
  }
}
