package cs5004.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cs5004.animator.model.behavior.Behavior;
import cs5004.animator.model.behavior.BehaviorType;
import cs5004.animator.model.behavior.ChangeColor;
import cs5004.animator.model.behavior.Move;
import cs5004.animator.model.behavior.Scale;
import cs5004.animator.model.shape.Oval;
import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Rectangle;
import cs5004.animator.model.shape.Shape;
import cs5004.animator.model.shape.ShapeType;
import cs5004.animator.util.AnimationBuilder;

/**
 * This class implements all public methods in the AnimatorModel interface. It uses 2 array lists to
 * store shapes and animations in the animator. The shapes list can't store shapes with the same
 * name to avoid contradictions in assigning animations. The animations list can't store animations
 * that contradict to each other (eg: a shape can't move upward and downward at the same time).
 */
public class AnimatorModelImpl implements AnimatorModel {
  private List<Shape> shapes;
  private List<Behavior> animations;
  private int x;
  private int y;
  private int width;
  private int height;

  /**
   * A constructor that initializes the lists that store the shapes and animations.
   */
  public AnimatorModelImpl() {
    this.shapes = new ArrayList<Shape>();
    this.animations = new ArrayList<Behavior>();
    this.x = 0;
    this.y = 0;
    this.width = 1500;
    this.height = 1500;
  }

  @Override
  public AnimatorModel copyModel() {
    Shape newShape;
    AnimatorModel newModel = new AnimatorModelImpl();
    for (Shape s : this.shapes) {
      newShape = s.copyShape();
      newModel.addShape(newShape);
      for (Behavior b : this.animations) {
        if (b.getShape().getName().equals(s.getName())) {
          newModel.addAnimation(b.copyBehavior(newShape));
        }
      }
    }
    newModel.setX(this.x);
    newModel.setY(this.y);
    newModel.setHeight(this.height);
    newModel.setWidth(this.width);
    return newModel;
  }

  @Override
  public int getX() {
    return this.x;
  }

  @Override
  public int getY() {
    return this.y;
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void setY(int y) {
    this.y = y;
  }

  @Override
  public void setWidth(int width) {
    this.width = width;
  }

  @Override
  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public List<Shape> getShapes() {
    return this.shapes;
  }

  @Override
  public List<Behavior> getAnimations() {
    return this.animations;
  }

  @Override
  public void addShape(Shape s) throws IllegalArgumentException {
    if (isInShapes(s)) {
      throw new IllegalArgumentException("Can't add a shape that's already in the list");
    }
    this.shapes.add(s);
  }

  /**
   * Compare two behaviors if they work on the same shape in the overlapping time with the same
   * behavior type.
   *
   * @param thisBehavior  the first behavior will be valued
   * @param otherBehavior the second behavior will be valued
   * @return if they are overlapping return true. Otherwise, return false.
   */
  private boolean compareOverlapBehavior(Behavior thisBehavior, Behavior otherBehavior) {
    String thisName = thisBehavior.getShape().getName();
    String otherName = otherBehavior.getShape().getName();

    BehaviorType thisType = thisBehavior.getType();
    BehaviorType otherType = otherBehavior.getType();

    int thisStart = thisBehavior.getStartTime();
    int otherEnd = otherBehavior.getEndTime();

    return thisName.equals(otherName) && thisType == otherType && thisStart < otherEnd;
  }

  /**
   * helper function return this shape s is in the shapes list or not.
   *
   * @param s the shape is going to be evaluated.
   * @return true if s in the list. Otherwise, return no.
   */
  private boolean isInShapes(Shape s) {
    if (shapes.isEmpty()) {
      return false;
    }
    for (Shape each : shapes) {
      if (each.getName().equals(s.getName())) {
        return true;
      }
    }
    return false;
  }


  @Override
  public void addAnimation(Behavior b) throws IllegalArgumentException {
    int success = 0;

    // if overlap, then throw.
    for (Behavior each : animations) {
      if (compareOverlapBehavior(b, each)) {
        throw new IllegalArgumentException("Cannot execute the same behavior to a shape in an"
                + "overlapping time");
      }
    }

    // if this behavior's shape is not in the shape list, add it to the list.
    if (shapes.isEmpty()) {
      this.addShape(b.getShape());
    } else {
      if (!isInShapes(b.getShape())) {
        this.addShape(b.getShape());
      }
    }

    // add the behavior by start time.
    if (this.animations.size() > 0) {
      for (int i = 0; i < this.animations.size(); i++) {
        if (b.getStartTime() < this.animations.get(i).getStartTime()) {
          this.animations.add(i, b);
          success = 1;
          break;
        }
      }
    }
    if (success != 1) {
      this.animations.add(b);
    }
  }

  @Override
  public String getState() {
    if (this.shapes.isEmpty() && this.animations.isEmpty()) {
      return "";
    }
    String shapeState =
            this.shapes.stream().map(Shape::toString).collect(Collectors.joining("\n\n"));
    String animationState =
            this.animations.stream().map(Behavior::getDescription)
                    .collect(Collectors.joining("\n"));
    return shapeState + "\n\n" + animationState;
  }


  @Override
  public int getMinTick() {
    if (this.shapes.isEmpty()) {
      return 0;
    }
    int minTick = this.shapes.get(0).getAppearTime();
    for (Shape each : this.shapes) {
      if (each.getAppearTime() < minTick) {
        minTick = each.getAppearTime();
      }
    }
    return minTick;
  }

  @Override
  public int getMaxTick() {
    int maxTick = 0;
    for (Shape s : this.shapes) {
      if (maxTick < s.getDisappearTime()) {
        maxTick = s.getDisappearTime();
      }
    }
    return maxTick;
  }

  /**
   * A class that implements from AnimationBuilder and contains all public methods from
   * AnimationBuilder. It is implemented in **AnimatorModelImpl. The function of builder is to make
   * AnimationReader works. It uses LinkedHashMap to store the data from the file and use the
   * build() function to make a AnimatorModelImpl object.
   */
  public static final class Builder implements AnimationBuilder<AnimatorModel> {
    private AnimatorModel m;
    private Map<String, ShapeType> shape;
    private Map<String, List<Transform>> shapeTrans;
    private Map<String, Integer> maxTicks;
    private Map<String, Integer> minTicks;

    /**
     * Initialize Builder with two LinkedHashMaps, two HashMap and one Model.
     */
    public Builder() {
      this.m = new AnimatorModelImpl();
      this.shape = new LinkedHashMap<>();
      this.shapeTrans = new LinkedHashMap<>();
      this.maxTicks = new HashMap<>();
      this.minTicks = new HashMap<>();
    }

    // for store the data for addMotion();
    static class Transform {
      int t1;
      int x1;
      int y1;
      int w1;
      int h1;
      int r1;
      int g1;
      int b1;
      int t2;
      int x2;
      int y2;
      int w2;
      int h2;
      int r2;
      int g2;
      int b2;

      public Transform(int t1, int x1, int y1, int w1, int h1, int r1, int g1, int b1,
                       int t2, int x2, int y2, int w2, int h2, int r2, int g2, int b2) {
        this.t1 = t1;
        this.x1 = x1;
        this.y1 = y1;
        this.w1 = w1;
        this.h1 = h1;
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.t2 = t2;
        this.x2 = x2;
        this.y2 = y2;
        this.w2 = w2;
        this.h2 = h2;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
      }
    }

    @Override
    public AnimatorModel build() {
      Shape newShape;
      Transform transform;
      Behavior behavior;

      for (String each : shapeTrans.keySet()) {
        transform = shapeTrans.get(each).get(0);
        if (shape.get(each) == ShapeType.RECTANGLE) {
          newShape = new Rectangle(each, new Point(transform.x1, transform.y1),
                  new Color(transform.r1, transform.g1, transform.b1), transform.w1, transform.h1,
                  minTicks.get(each), maxTicks.get(each));
        } else {
          newShape = new Oval(each, new Point(transform.x1, transform.y1),
                  new Color(transform.r1, transform.g1, transform.b1), transform.w1 / 2,
                  transform.h1 / 2,
                  minTicks.get(each), maxTicks.get(each));
        }

        // add the new shape to the shape list.
        m.addShape(newShape);


        for (Transform i : shapeTrans.get(each)) {
          if (i.x1 != i.x2 || i.y1 != i.y2) {
            behavior = new Move(newShape, i.t1, i.t2, new Point(i.x1, i.y1), new Point(i.x2, i.y2));
            m.addAnimation(behavior);
          }
          if (i.w1 != i.w2 || i.h1 != i.h2) {
            behavior = new Scale(newShape, i.t1, i.t2, i.w1, i.h1, i.w2, i.h2);
            m.addAnimation(behavior);
          }
          if (i.r1 != i.r2 || i.g1 != i.g2 || i.b1 != i.b2) {
            behavior = new ChangeColor(newShape, i.t1, i.t2,
                    new Color(i.r1, i.g1, i.b1), new Color(i.r2, i.g2, i.b2));
            m.addAnimation(behavior);
          }
          if (i.x1 == i.x2 && i.y1 == i.y2 && i.w1 == i.w2 && i.h1 == i.h2 && i.r1 == i.r2
                  && i.g1 == i.g2 && i.b1 == i.b2) {
            behavior = new Move(newShape, i.t1, i.t2, new Point(i.x1, i.y1),
                    new Point(i.x2, i.y2));
            m.addAnimation(behavior);
          }
        }
      }
      return m;
    }


    @Override
    public AnimationBuilder<AnimatorModel> setBounds(int x, int y, int width, int height) {
      if (width <= 0 || height <= 0) {
        throw new IllegalArgumentException("Width or height should be positive.");
      }
      m.setX(x);
      m.setY(y);
      m.setWidth(width);
      m.setHeight(height);
      return this;
    }

    /**
     * helper function return this String s is in the shapes list or not.
     *
     * @param s the shape is going to be evaluated.
     * @return true if s in the list. Otherwise, return no.
     */
    private boolean isNameInShapes(String s) {
      if (m.getShapes().isEmpty()) {
        return false;
      }
      for (Shape each : m.getShapes()) {
        if (each.getName().equals(s)) {
          return true;
        }
      }
      return false;
    }

    /**
     * Adds a new shape to the growing document.
     *
     * @param name The unique name of the shape to be added. No shape with this name should already
     *             exist.
     * @param type The type of shape (e.g. "ellipse", "rectangle") to be added. The set of supported
     *             shapes is unspecified, but should include "ellipse" and "rectangle" as a
     *             minimum.
     * @return This {@link AnimationBuilder}
     * @throws IllegalArgumentException if name already exits or invalid type.
     */
    @Override
    public AnimationBuilder<AnimatorModel> declareShape(String name, String type)
            throws IllegalArgumentException {
      ShapeType shapeType;

      if (isNameInShapes(name)) {
        throw new IllegalArgumentException("The name is already been used!");
      }
      if (type.equals("rectangle")) {
        shapeType = ShapeType.RECTANGLE;
      } else if (type.equals("oval") || type.equals("ellipse")) {
        shapeType = ShapeType.OVAL;
      } else {
        throw new IllegalArgumentException("Invalid type!");
      }
      this.shape.put(name, shapeType);
      return this;
    }

    /**
     * Adds a transformation to the growing document.
     *
     * @param name The name of the shape (added with {@link AnimationBuilder#declareShape})
     * @param t1   The start time of this transformation
     * @param x1   The initial x-position of the shape
     * @param y1   The initial y-position of the shape
     * @param w1   The initial width of the shape
     * @param h1   The initial height of the shape
     * @param r1   The initial red color-value of the shape
     * @param g1   The initial green color-value of the shape
     * @param b1   The initial blue color-value of the shape
     * @param t2   The end time of this transformation
     * @param x2   The final x-position of the shape
     * @param y2   The final y-position of the shape
     * @param w2   The final width of the shape
     * @param h2   The final height of the shape
     * @param r2   The final red color-value of the shape
     * @param g2   The final green color-value of the shape
     * @param b2   The final blue color-value of the shape
     * @return This {@link AnimationBuilder}
     * @throws IllegalArgumentException if start time or end time is negative. The width or height
     *                                  is negative. End time is before start time. Or, Color index
     *                                  is out of range.
     */
    @Override
    public AnimationBuilder<AnimatorModel> addMotion(String name, int t1, int x1, int y1, int w1,
                                                     int h1, int r1, int g1, int b1, int t2, int x2,
                                                     int y2, int w2, int h2, int r2, int g2,
                                                     int b2) throws IllegalArgumentException {
      if (t1 < 0 || t1 > t2) {
        throw new IllegalArgumentException("Invalid start time or end time!");
      }

      if (w1 <= 0 || w2 <= 0) {
        throw new IllegalArgumentException("Width should be positive!");
      }

      if (h1 <= 0 || h2 <= 0) {
        throw new IllegalArgumentException("Height cannot be positive!");
      }

      if (r1 < 0 || r2 < 0 || g1 < 0 || g2 < 0 || b1 < 0 || b2 < 0
              || r1 > 255 || r2 > 255 || g1 > 255 || g2 > 255 || b1 > 255 || b2 > 255) {
        throw new IllegalArgumentException("RGB range is between 0 to 255!");
      }

      // check the min ticks
      if (!minTicks.containsKey(name)) {
        minTicks.put(name, t1);
      } else {
        if (minTicks.get(name) > t1) {
          minTicks.put(name, t1);
        }
      }

      // check the max ticks
      if (!maxTicks.containsKey(name)) {
        maxTicks.put(name, t2);
      } else {
        if (maxTicks.get(name) < t2) {
          maxTicks.put(name, t2);
        }
      }

      // if name is not in the map, create a new list. Otherwise, add value in the existing list.
      if (this.shapeTrans.containsKey(name)) {
        this.shapeTrans.get(name).add(new Transform(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2,
                h2, r2, g2, b2));
      } else {
        List<Transform> value = new ArrayList<>();
        this.shapeTrans.put(name, value);
        value.add(new Transform(t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2,
                h2, r2, g2, b2));
      }
      return this;
    }
  }
}
