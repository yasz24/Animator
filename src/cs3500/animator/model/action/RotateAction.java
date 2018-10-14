package cs3500.animator.model.action;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a transformation that rotates an {@code IShape} about a rotationAxis. Given a start
 * and ending tick, {@code RotateAction} interpolates the current orientation of the given shape to
 * be somewhere between the startAngle and endAngle, based on the given current tick.
 */
public class RotateAction extends AbstractAction implements IRotateAction {
  /*
   * The start-angle for this rotation.
   */
  private double startAngle;
  /*
   * The end-angle for this rotation.
   */
  private double endAngle;
  /*
   * The axis about which the shape is to be rotated.
   */
  private IPosition rotationAxis;

  /**
   * Creates a new {@code RotateAction} with specified start and times, as well as orientations, as
   * an angles in degrees.
   *
   * @param startTime Non-negative long value that is smaller than endTime
   * @param endTime   Non-negative long value that is greater than startTime
   * @param startAngle the angle at which the actor is oriented at the startTime with.
   * @param endAngle the angle at which the actor is oriented at the endTime
   */
  public RotateAction(long startTime,
                      long endTime,
                      double startAngle,
                      double endAngle,
                      IPosition rotationAxis) {
    super(startTime, endTime);
    this.startAngle = startAngle;
    this.endAngle = endAngle;
    this.rotationAxis = rotationAxis;
  }

  @Override
  public double getStartAngle() {
    return startAngle;
  }

  @Override
  public double getEndAngle() {
    return endAngle;
  }

  @Override
  public IPosition getStartPosition() {
    return this.rotationAxis;
  }

  @Override
  public IPosition getEndPosition() {
    return this.rotationAxis;
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new RotateAction(startTime, endTime, startAngle, endAngle, rotationAxis);
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null visitor");
    }
    v.visit(this);
  }

  @Override
  public void transformShape(IVisitableShape shape, long tick) {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }

    //interpolate angle
    double angle = linInterpolation(tick, startAngle, endAngle);

    shape.setAngle(angle);
  }

  @Override
  protected boolean compatibleRotateTransformation(RotateAction other) {
    return !this.simultaneousWith(other);
  }

  @Override
  protected boolean compatibleAbstractAction(AbstractAction other) {
    return other.compatibleRotateTransformation(this);
  }

  @Override
  public IAction getCopy() {
    return this.getVisitableCopy();
  }
}
