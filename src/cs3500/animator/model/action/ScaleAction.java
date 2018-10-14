package cs3500.animator.model.action;

import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a transformation that scales the width and height of a {@code IShape}.
 * Does so over time, storing a starting and ending width and height and interpolating
 * between them before applying to the {@code IShape}.
 */
public class ScaleAction extends AbstractAction implements IScaleAction {
  // Starting and ending widths and heights for the applied-upon shape
  private final double startWidth;
  private final double startHeight;
  private final double endWidth;
  private final double endHeight;

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
  public ScaleAction(long startTime, long endTime, double startWidth, double startHeight,
                     double endWidth, double endHeight) {
    super(startTime, endTime);
    this.startWidth = startWidth;
    this.startHeight = startHeight;
    this.endWidth = endWidth;
    this.endHeight = endHeight;
  }

  @Override
  public void transformShape(IVisitableShape shape, long tick) {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }

    double newWidth = linInterpolation(tick, startWidth, endWidth);
    double newHeight = linInterpolation(tick, startHeight, endHeight);

    shape.scale(newWidth, newHeight);
  }

  @Override
  protected boolean compatibleScaleTransformation(ScaleAction other) {
    return !this.simultaneousWith(other);
  }

  @Override
  protected boolean compatibleAbstractAction(AbstractAction other) {
    return other.compatibleScaleTransformation(this);
  }

  @Override
  public IAction getCopy() {
    return this.getVisitableCopy();
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null ModelVisitor");
    }
    v.visit(this);
  }

  @Override
  public double getStartWidth() {
    return startWidth;
  }

  @Override
  public double getStartHeight() {
    return startHeight;
  }

  @Override
  public double getEndWidth() {
    return endWidth;
  }

  @Override
  public double getEndHeight() {
    return endHeight;
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new ScaleAction(this.startTime, this.endTime,
            this.startWidth, this.startHeight, this.endWidth, this.endHeight);
  }

}
