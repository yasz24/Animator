package cs3500.animator.model.shape;


import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * The class is an ellipse that supports rotation.Rotation is in degrees,
 * and a positive rotation amount translates to clockwise motion.
 * The class extends an Ellipse to reuse existing code.
 */
public class RotEllipse extends Ellipse {
  /**
   * The orientation for this rectangle in degrees.
   */
  private double angle;

  /**
   * Constructs a new {@code RotEllipse} with given parameters.
   *
   * @param centerX X coordinate of the center of the ellipse
   * @param centerY y coordinate of the center of the ellipse
   * @param radiusX radius of the ellipse in the x direction
   * @param radiusY radius of the ellipse in the y direction
   * @param color   color of the ellipse
   * @param angle   the current angle at which this ellipse is oriented.
   * @throws IllegalArgumentException if given null color
   */
  public RotEllipse(double centerX, double centerY, double radiusX, double radiusY, IColor color,
                    double angle) {
    super(centerX, centerY, radiusX, radiusY, color);
    this.angle = angle;
  }


  @Override
  public double getAngle() {
    return angle;
  }

  @Override
  public void setAngle(double angle) {
    this.angle = angle;
  }

  @Override
  public void acceptVisitableAction(IVisitableAction action, long tick) {
    if (action == null) {
      throw new IllegalArgumentException("Given a null IVisitableAction");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }
    action.transformShape(this, tick);
  }

  @Override
  public IVisitableShape getVisitableCopy() {
    return new RotEllipse(this.pinhole.getX(), this.pinhole.getY(),
            this.radiusX, this.radiusY, new Color(this.color), this.angle);
  }

  @Override
  public IShape getCopy() {
    return this.getVisitableCopy();
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null ModelVisitor");
    }
    v.visit(this);
  }
}
