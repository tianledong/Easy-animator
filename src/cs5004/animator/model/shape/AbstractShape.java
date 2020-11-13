package cs5004.animator.model.shape;

import java.awt.Color;

import static cs5004.animator.model.StringFormat.getColorString;
import static cs5004.animator.model.StringFormat.getDimensionsString;
import static cs5004.animator.model.StringFormat.getPositionString;
import static cs5004.animator.model.StringFormat.getShapeDescription;

/**
 * This is the abstract class that implements the Shape interface. It implements all public methods
 * except copyShape(), getDimension1Tag(), getDimension2Tag(), and getPositionTag(). This class
 * represents an abstract Shape object. All fields are shared by the shapes that our animator
 * supports. Besides, all shape can only appear and disappear once so that the appear time and
 * disappear time are final.
 */
public abstract class AbstractShape implements Shape {
  private String name;
  private ShapeType shapeType;
  private Point position;
  private Color color;
  private double dimension1;
  private double dimension2;
  private final int appearTime; // a shape can only appear and disappear once
  private final int disappearTime;

  /**
   * A constructor that assigns values to the fields. Position, color can't be null. Dimensions
   * must be positive and the name can't be empty. The shape type is determined and passed by the
   * concrete classes' constructors. Also, appear time and disappear time can't be negative and the
   * appear time must be less than the disappear time.
   * @param name the name of the shape
   * @param shapeType the type of the shape
   * @param position the reference point of the shape
   * @param color the color of the shape
   * @param dimension1 the first dimension of the shape
   * @param dimension2 the second dimension of the shape
   * @param appearTime the appear time of the shape
   * @param disappearTime the disappear time of the shape
   * @throws IllegalArgumentException if name is empty, point or color is null, dimensions aren't
   *                                  positive, time is negative, and appear time is after the
   *                                  disappear time
   */
  public AbstractShape(String name, ShapeType shapeType, Point position, Color color,
                       double dimension1, double dimension2, int appearTime, int disappearTime)
          throws IllegalArgumentException {
    if (name.equals("")) {
      throw new IllegalArgumentException("Name can't be empty!");
    } else if (position == null) {
      throw new IllegalArgumentException("Position can't be null!");
    } else if (color == null) {
      throw new IllegalArgumentException("Color can't be null!");
    } else if (dimension1 <= 0 || dimension2 <= 0) {
      throw new IllegalArgumentException("Dimensions must be positive!");
    } else if (appearTime < 0 || disappearTime < 0) {
      throw new IllegalArgumentException("Time can't be negative!");
    } else if (disappearTime <= appearTime) {
      throw new IllegalArgumentException("Disappear time is before appear time!");
    }

    this.name = name;
    this.shapeType = shapeType;
    this.color = color;
    this.position = position;
    this.dimension1 = dimension1;
    this.dimension2 = dimension2;
    this.appearTime = appearTime;
    this.disappearTime = disappearTime;
  }

  @Override
  public String getColor() {
    return getColorString(this.color);
  }

  @Override
  public Color getColorData() {
    return this.color;
  }

  @Override
  public String getPosition() {
    return getPositionString(this.position);
  }

  @Override
  public Point getPositionData() {
    return this.position;
  }

  @Override
  public String getDimensions() {
    return getDimensionsString(this);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getType() {
    return this.shapeType.toString();
  }

  @Override
  public int getAppearTime() {
    return this.appearTime;
  }

  @Override
  public int getDisappearTime() {
    return this.disappearTime;
  }

  @Override
  public double getDimension1() {
    return this.dimension1;
  }

  @Override
  public double getDimension2() {
    return this.dimension2;
  }

  @Override
  public String toString() {
    return getShapeDescription(this);
  }

  @Override
  public void setColor(Color color) throws IllegalArgumentException {
    if (color == null) {
      throw new IllegalArgumentException("Color can't be null!");
    }
    this.color = color;
  }

  @Override
  public void setPosition(Point position) throws IllegalArgumentException {
    if (position == null) {
      throw new IllegalArgumentException("Point can't be null!");
    }
    this.position = position;
  }

  @Override
  public void setDimension1(double dimension1) throws IllegalArgumentException {
    if (dimension1 <= 0) {
      throw new IllegalArgumentException("Dimension must be positive!");
    }
    this.dimension1 = dimension1;
  }

  @Override
  public void setDimension2(double dimension2) throws IllegalArgumentException {
    if (dimension2 <= 0) {
      throw new IllegalArgumentException("Dimension must be positive!");
    }
    this.dimension2 = dimension2;
  }
}
