package cs3500.animator.model.action;

import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a ScaleAction with a toString that is compatible with the provider's
 * text renderer. We need this because their text renderer uses string comprehension
 * on the toString() of the model to generate the text representation.
 */
public class ScaleActionWithToString extends ScaleAction {

  /**
   * Creates a new {@code ScaleAction} that scales a shape from the given widths and heights
   * to the other given widths and heights over the time period specified by startTime and
   * endTime.
   *
   * @param startTime   long for the tick that the {@code ScaleAction} begins at
   * @param endTime     long fort the tick that the {@code ScaleAction} ends at
   * @param startWidth  the initial width of the shape to scale from
   * @param startHeight the initial height of the shape to scale from
   * @param endWidth    the final width of the shape to scale to
   * @param endHeight   the final height of the shape to scale to
   */
  public ScaleActionWithToString(long startTime, long endTime, double startWidth, double startHeight, double endWidth, double endHeight) {
    super(startTime, endTime, startWidth, startHeight, endWidth, endHeight);
  }

  @Override
  public String toString() {
    return "scales from Width: " + this.getStartWidth() + ", Height: " +
            this.getStartHeight() + " to Width: " + this.getEndWidth() +
            ", Height: " + this.getEndHeight() +
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
    return new ScaleActionWithToString(this.startTime, this.endTime,
            this.getStartWidth(), this.getStartHeight(),
            this.getEndWidth(), this.getEndHeight());
  }

  @Override
  public IAction getCopy() {
    return this.getVisitableCopy();
  }
}
