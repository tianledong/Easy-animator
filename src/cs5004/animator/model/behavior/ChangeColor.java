package cs5004.animator.model.behavior;

import java.awt.Color;

import cs5004.animator.model.shape.Shape;

import static cs5004.animator.model.StringFormat.getColorString;
import static cs5004.animator.model.StringFormat.getSVGColor;
import static java.lang.Math.round;

/**
 * This class represents a change color behavior. It offers all the operations mandated by the
 * Behavior interface.
 */
public class ChangeColor extends AbstractBehavior {
  private final Color oldColor;
  private final Color newColor;
  private final BehaviorType type;

  /**
   * Construct a change color class using shape, start time, end time, new color.
   *
   * @param shape    the shape that scale works on.
   * @param start    start time of the scale.
   * @param end      end time of the scale.
   * @param newColor the new color that the shape is changing to.
   * @throws IllegalArgumentException if the start time or the end time is negative. Or, Start time
   *                                  is after or equals the end time. Or the new color is the same
   *                                  with the old color.
   */
  public ChangeColor(Shape shape, int start, int end, Color oldColor, Color newColor)
          throws IllegalArgumentException {
    super(shape, start, end);
    if (getColorString(newColor).equals(getColorString(oldColor))) {
      throw new IllegalArgumentException("The old color and the new color cannot be same!");
    }
    this.oldColor = oldColor;
    this.newColor = newColor;
    this.type = BehaviorType.CHANGECOLOR;
  }

  @Override
  public String getCurrentBehavior() {
    return "changes color";
  }

  @Override
  public String getStartStatus() {
    return getColorString(oldColor);
  }

  @Override
  public String getEndStatus() {
    return getColorString(newColor);
  }

  @Override
  public BehaviorType getType() {
    return this.type;
  }

  @Override
  public void change(double currentTime) {
    if (this.getStartTime() == this.getEndTime() && this.getStartTime() == currentTime) {
      this.getShape().setColor(newColor);
    } else if (currentTime >= this.getEndTime() && !getShape().getColorData().equals(newColor)) {
      this.getShape().setColor(newColor);
    } else if (currentTime >= this.getStartTime() && currentTime <= this.getEndTime()) {
      int changeRed = (this.newColor.getRed() - this.oldColor.getRed());
      int changeGreen = (this.newColor.getGreen() - this.oldColor.getGreen());
      int changeBlue = (this.newColor.getBlue() - this.oldColor.getBlue());

      double changeInTime = (currentTime - this.getStartTime())
              / (this.getEndTime() - this.getStartTime());

      float newRed = (float) ((this.oldColor.getRed() + (changeRed * changeInTime)) / 255);
      float newGreen = (float) ((this.oldColor.getGreen() + (changeGreen * changeInTime)) / 255);
      float newBlue = (float) ((this.oldColor.getBlue() + (changeBlue * changeInTime)) / 255);

      this.getShape().setColor(new Color(newRed, newGreen, newBlue));

    }
  }

  @Override
  public String svgString(int speed, boolean isLoop) {
    double start = (double) this.getStartTime() * 1000 / speed;
    double end = (double) this.getEndTime() * 1000 / speed ;
    double duration = end - start;
    if (!isLoop) {
      return String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" "
                      + "attributeName=\"fill\" from=\"%s to=\"%s fill=\"freeze\" />\n",
              round(start), duration, getSVGColor(this.oldColor),
              getSVGColor(this.newColor));
    }
    return String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" dur=\"%.1fms\" "
                    + "attributeName=\"fill\" from=\"%s to=\"%s fill=\"freeze\" />\n",
            round(start), duration, getSVGColor(this.oldColor),
            getSVGColor(this.newColor)) +
            String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                            + "attributeName=\"fill\" to=\"%s fill=\"freeze\" />\n",
                    getSVGColor(this.oldColor));
  }

  @Override
  public Behavior copyBehavior(Shape shape) {
    Behavior newBehavior;

    newBehavior = new ChangeColor(shape, this.getStartTime(), this.getEndTime(), this.oldColor,
            this.newColor);
    return newBehavior;
  }
}

