package cs5004.animator.model.behavior;

import cs5004.animator.model.shape.Shape;

import static java.lang.Math.round;

/**
 * This class represents a move behavior. It offers all the operations mandated by the Behavior
 * interface.
 */
public class Scale extends AbstractBehavior {
  private final double oldDimension1;
  private final double oldDimension2;
  private final double newDimension1;
  private final double newDimension2;
  private final BehaviorType type;

  /**
   * Construct a scale class using shape, start time, end time, new dimensions.
   *
   * @param shape         the shape that scale works on.
   * @param start         start time of the scale.
   * @param end           end time of the scale.
   * @param newDimension1 new dimension 1 of this scale.
   * @param newDimension2 new dimension 2 of this scale.
   * @throws IllegalArgumentException if new dimensions are not positive or the new one is the same
   *                                  with the old one.
   */
  public Scale(Shape shape, int start, int end, double oldDimension1, double oldDimension2,
               double newDimension1, double newDimension2) throws IllegalArgumentException {
    super(shape, start, end);
    if (newDimension1 <= 0 || newDimension2 <= 0 || oldDimension1 <= 0 || oldDimension2 <= 0) {
      throw new IllegalArgumentException("Dimensions can't be negative or zero!");
    }
    this.oldDimension1 = oldDimension1;
    this.oldDimension2 = oldDimension2;
    this.newDimension1 = newDimension1;
    this.newDimension2 = newDimension2;
    this.type = BehaviorType.SCALE;
  }

  @Override
  public String getCurrentBehavior() {
    return "scales";
  }

  @Override
  public String getStartStatus() {
    return String.format(this.getShape().getDimension1Tag() + "%.1f" + ", "
            + this.getShape().getDimension2Tag() + "%.1f", this.oldDimension1, this.oldDimension2);
  }

  @Override
  public String getEndStatus() {
    return String.format(this.getShape().getDimension1Tag() + "%.1f" + ", "
            + this.getShape().getDimension2Tag() + "%.1f", this.newDimension1, this.newDimension2);
  }

  @Override
  public BehaviorType getType() {
    return this.type;
  }

  @Override
  public void change(double currentTime) {
    if ((this.getStartTime() == this.getEndTime() && this.getStartTime() == currentTime)) {
      this.getShape().setDimension1(this.newDimension1);
      this.getShape().setDimension2(this.newDimension2);
    } else if (currentTime >= this.getEndTime() && (getShape().getDimension1() != newDimension1
              || getShape().getDimension2() != newDimension2)) {
      this.getShape().setDimension1(this.newDimension1);
      this.getShape().setDimension2(this.newDimension2);
    } else if (currentTime >= this.getStartTime() && currentTime <= this.getEndTime()) {

      double dimension1Change = this.newDimension1 - this.oldDimension1;
      double dimension2Change = this.newDimension2 - this.oldDimension2;

      double changeInTime = (currentTime - this.getStartTime())
              / (double) (this.getEndTime() - this.getStartTime());

      double tweenDimension1 = this.oldDimension1 + (changeInTime * dimension1Change);
      double tweenDimension2 = this.oldDimension2 + (changeInTime * dimension2Change);

      this.getShape().setDimension1(tweenDimension1);
      this.getShape().setDimension2(tweenDimension2);
    }
  }

  @Override
  public String svgString(int speed, boolean isLoop) {
    double start = (double) this.getStartTime() * 1000 / speed;
    double end = (double) this.getEndTime() * 1000 / speed;
    double duration = end - start;
    if (!isLoop) {
      return String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                      "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
              round(start), duration, this.getShape().dimension1SVGTag(),
              round(this.oldDimension1), round(this.newDimension1))
              + String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                      "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
              round(start), duration, this.getShape().dimension2SVGTag(),
              round(this.oldDimension2), round(this.newDimension2));
    }
    return String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" dur=\"%.1fms\" "
                    + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            round(start), duration, this.getShape().dimension1SVGTag(),
            round(this.oldDimension1), round(this.newDimension1)) +
            String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" dur=\"%.1fms\" "
                            + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    round(start), duration, this.getShape().dimension2SVGTag(),
                    round(this.oldDimension2), round(this.newDimension2)) +
            String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                            + "attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                    this.getShape().dimension1SVGTag(), round(this.oldDimension1)) +
            String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                            + "attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                    this.getShape().dimension2SVGTag(), round(this.oldDimension2));
  }

  @Override
  public Behavior copyBehavior(Shape shape) {
    return new Scale(shape, this.getStartTime(), this.getEndTime(), this.oldDimension1,
            this.oldDimension2, this.newDimension1, this.newDimension2);
  }
}
