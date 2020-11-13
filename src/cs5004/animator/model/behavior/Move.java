package cs5004.animator.model.behavior;

import cs5004.animator.model.shape.Point;
import cs5004.animator.model.shape.Shape;

import static cs5004.animator.model.StringFormat.getPositionString;
import static java.lang.Math.round;

/**
 * This class represents a move behavior. It offers all the operations mandated by the Behavior
 * interface.
 */
public class Move extends AbstractBehavior {
  private final Point oldPoint;
  private final Point newPoint;
  private final BehaviorType type;

  /**
   * Construct a move class using shape, start time, end time, new point.
   *
   * @param shape    the shape that scale works on.
   * @param start    start time of the scale.
   * @param end      end time of the scale.
   * @param newPoint the new point that the shape is moving to.
   * @throws IllegalArgumentException if the start time or the end time is negative. Or, Start time
   *                                  is after or equals the end time. Or, the two positions are the
   *                                  same.
   */
  public Move(Shape shape, int start, int end, Point oldPoint, Point newPoint)
          throws IllegalArgumentException {
    super(shape, start, end);
    this.oldPoint = oldPoint;
    this.newPoint = newPoint;
    this.type = BehaviorType.MOVE;
  }

  @Override
  public String getCurrentBehavior() {
    return "moves";
  }

  @Override
  public String getStartStatus() {
    return getPositionString(oldPoint);
  }

  @Override
  public String getEndStatus() {
    return getPositionString(newPoint);
  }

  @Override
  public BehaviorType getType() {
    return this.type;
  }

  @Override
  public void change(double currentTime) {
    if ((this.getStartTime() == this.getEndTime() && this.getStartTime() == currentTime)) {
      this.getShape().setPosition(newPoint);
    } else if (currentTime >= this.getEndTime() && !getShape().getPositionData().equals(newPoint)) {
      this.getShape().setPosition(newPoint);
    } else if (currentTime >= this.getStartTime() && currentTime <= this.getEndTime()) {
      double changeX = this.newPoint.getX() - this.oldPoint.getX();
      double changeY = this.newPoint.getY() - this.oldPoint.getY();

      double changeInTime = (currentTime - this.getStartTime())
              / (double) (this.getEndTime() - this.getStartTime());
      double newX = this.oldPoint.getX() + (changeX * changeInTime);
      double newY = this.oldPoint.getY() + (changeY * changeInTime);

      this.getShape().setPosition(new Point(newX, newY));
    }
  }

  @Override
  public String svgString(int speed, boolean isLoop) {
    double start = (double) this.getStartTime() * 1000 / speed;
    double end = (double) this.getEndTime() * 1000 / speed;
    double duration = end - start;
    if (!isLoop) {
      if (this.getShape().getType().equals("oval")) {
        return String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                        "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                round(start), duration, this.getShape().svgPositionTagX(),
                round(this.oldPoint.getX() + this.getShape().getDimension1()),
                round(this.newPoint.getX() + this.getShape().getDimension1()))
                + String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                        "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                round(start), duration, this.getShape().svgPositionTagY(),
                round(this.oldPoint.getY() + this.getShape().getDimension2()),
                round(this.newPoint.getY() + this.getShape().getDimension2()));
      } else {
        return String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                        "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                round(start), duration, this.getShape().svgPositionTagX(),
                round(this.oldPoint.getX()), round(this.newPoint.getX()))
                + String.format("<animate attributeType=\"xml\" begin=\"%dms\" dur=\"%.1fms\" " +
                        "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                round(start), duration, this.getShape().svgPositionTagY(),
                round(this.oldPoint.getY()), round(this.newPoint.getY()));
      }
    }
    if (getShape().getType().equals("oval")) {
      return String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" "
                      + "dur=\"%.1fms\" attributeName=\"%s\" from=\"%d\" to=\"%d\" "
                      + "fill=\"freeze\" />\n",
              round(start), duration, this.getShape().svgPositionTagX(),
              round(this.oldPoint.getX() + this.getShape().getDimension1()),
              round(this.newPoint.getX() + this.getShape().getDimension1())) +
              String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" "
                              + "dur=\"%.1fms\" attributeName=\"%s\" from=\"%d\" to=\"%d\" "
                              + "fill=\"freeze\" />\n",
                      round(start), duration, this.getShape().svgPositionTagY(),
                      round(this.oldPoint.getY() + this.getShape().getDimension2()),
                      round(this.newPoint.getY() + this.getShape().getDimension2())) +
              String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                              + "attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                      this.getShape().svgPositionTagX(),
                      round(this.oldPoint.getX() + this.getShape().getDimension1()))
                              + String.format("<animate attributeType=\"xml\" begin=\"base.end\" "
                      + "dur=\"1ms\" attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                      this.getShape().svgPositionTagY(), round(this.newPoint.getY()
                      + this.getShape().getDimension2()));

    }
    return String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" dur=\"%.1fms\" "
                    + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
            round(start), duration, this.getShape().svgPositionTagX(),
            round(this.oldPoint.getX()), round(this.newPoint.getX())) +
            String.format("<animate attributeType=\"xml\" begin=\"base.begin+%dms\" dur=\"%.1fms\" "
                            + "attributeName=\"%s\" from=\"%d\" to=\"%d\" fill=\"freeze\" />\n",
                    round(start), duration, this.getShape().svgPositionTagY(),
                    round(this.oldPoint.getY()), round(this.newPoint.getY())) +
            String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                            + "attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                    this.getShape().svgPositionTagX(), round(this.oldPoint.getX())) +
            String.format("<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\" "
                            + "attributeName=\"%s\" to=\"%d\" fill=\"freeze\" />\n",
                    this.getShape().svgPositionTagY(), round(this.oldPoint.getY()));
  }

  @Override
  public Behavior copyBehavior(Shape shape) {
    return new Move(shape, this.getStartTime(), this.getEndTime(), this.oldPoint,
            this.newPoint);
  }
}

