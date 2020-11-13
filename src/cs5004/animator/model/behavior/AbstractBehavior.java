package cs5004.animator.model.behavior;

import cs5004.animator.model.shape.Shape;

import static cs5004.animator.model.StringFormat.getBehaviorDescription;

/**
 * This abstract contains all operations that all types of Behavior should support.
 */
public abstract class AbstractBehavior implements Behavior {
  private Shape shape;
  private int start;
  private int end;

  /**
   * Construct a behavior object using shape, start time and end time.
   *
   * @param shape shape of the behavior is working on.
   * @param start start time of this behavior.
   * @param end   end time of this behavior.
   * @throws IllegalArgumentException if the start time or the end time is negative. Or, Start time
   *                                  is after or equals the end time.
   */
  public AbstractBehavior(Shape shape, int start, int end) throws IllegalArgumentException {
    if (start < 0 || end < 0) {
      throw new IllegalArgumentException("Start time and end time cannot be negative.");
    }
    if (end < start) {
      throw new IllegalArgumentException("Start time cannot be less than the end time.");
    }
    this.shape = shape;
    this.start = start;
    this.end = end;
  }

  @Override
  public int getStartTime() {
    return this.start;
  }

  @Override
  public int getEndTime() {
    return this.end;
  }

  @Override
  public Shape getShape() {
    return this.shape;
  }

  @Override
  public String getDescription() {
    return getBehaviorDescription(this);
  }
}
