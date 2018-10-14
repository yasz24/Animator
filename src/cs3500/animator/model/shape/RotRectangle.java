package cs3500.animator.model.shape;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a rectangle that supports rotation about its center. Rotation is in degrees,
 * and a positive rotation amount translates to clockwise motion.
 *
 * <p>Change 04/24/18: created this class.
 */
public class RotRectangle extends Rectangle {

  /**
   *The orientation for this rectangle in degrees.
   */
  private double angle;

  /**
   * Constructs a new {@code RotRectangle} with given lower left corner and given dimensions.
   *
   * @param leftX  double containing the x coordinate of the lower left corner of the rectangle
   * @param leftY  double containing the y coordinate of the lower left corner of the rectangle
   * @param width  double containing the width of the rectangle
   * @param height double containing the height of the rectangle
   * @param color  Color containing the color of the rectangle
   * @param angle  the current angle at which this rectangle is oriented.
   * @throws IllegalArgumentException if given a null color
   */
  public RotRectangle(double leftX, double leftY, double width, double height, IColor color,
                      double angle) {
    super(leftX, leftY, width, height, color);
    this.angle = angle;
  }

  @Override
  public double getAngle() {
    return this.angle;
  }

  @Override
  public void setAngle(double angle) {
    this.angle = angle;
  }

  @Override
  public IVisitableShape getVisitableCopy() {
    return new RotRectangle(this.pinhole.getX(), this.pinhole.getY(),
            this.width, this.height, new Color(this.color), this.angle);
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
