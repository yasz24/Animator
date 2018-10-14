package cs3500.animator.model.action;

import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a ColorAction with a toString that is compatible with the provider's
 * text renderer. We need this because their text renderer uses string comprehension
 * on the toString() of the model to generate the text representation.
 */
public class ColorActionWithToString extends ColorAction {
  /**
   * Creates a new {@code ColorAction} with the specified start and end times and colors.
   *
   * @param startTime  start time of the color change, in ticks
   * @param endTime    end time of the color change, in ticks
   * @param startColor color to transform from
   * @param endColor   color to transform to
   * @throws IllegalArgumentException if any of the arguments are null or if startTime is before
   *                                  endTime
   */
  public ColorActionWithToString(long startTime, long endTime, IColor startColor, IColor endColor) {
    super(startTime, endTime, startColor, endColor);
  }

  @Override
  public String toString() {
    return "changes color from " + this.getStartColor().toString() + " to " +
            this.getStartColor().toString() +
            " from t=" + this.startTime + " to t=" + this.endTime;
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null visitor");
    }
    v.visit(this);
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new ColorActionWithToString(this.startTime, this.endTime,
            this.getStartColor(), this.getEndColor());
  }

  @Override
  public IAction getCopy() {
    return this.getVisitableCopy();
  }
}
