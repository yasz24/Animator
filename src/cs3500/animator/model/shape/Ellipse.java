package cs3500.animator.model.shape;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents an ellipse shape. Ellipses have centers, x radii, and y radii. They can be scaled,
 * translated, and color changed.
 *
 * <p>Change 04/24/18: Changed access modifiers for instance variables from private to protected.
 */
public class Ellipse implements IEllipse {

  // Multiplier for the x and y radius of the shape. 1 means no scaling, 2 means double scaling, etc
  protected double scaleAmountX;
  protected double scaleAmountY;

  /**
   * Radius of the {@code Ellipse} in the x direction.
   */
  protected double radiusX;

  /**
   * Radius of the {@code Ellipse} in the y direction.
   */
  protected double radiusY;

  /**
   * Color of the {@code Ellipse}.
   */
  protected IColor color;

  /**
   * Position of the {@code Ellipse}
   */
  protected IPosition pinhole;


  /**
   * Constructs a new {@code Ellipse} with given parameters.
   *
   * @param centerX X coordinate of the center of the ellipse
   * @param centerY y coordinate of the center of the ellipse
   * @param radiusX radius of the ellipse in the x direction
   * @param radiusY radius of the ellipse in the y direction
   * @param color   color of the ellipse
   * @throws IllegalArgumentException if given null color
   */
  public Ellipse(double centerX, double centerY, double radiusX, double radiusY, IColor color) {

    this.radiusX = radiusX;
    this.radiusY = radiusY;

    this.scaleAmountX = 1;
    this.scaleAmountY = 1;

    this.pinhole = new Position(centerX, centerY);

    this.color = color;
  }

  @Override
  public double getRadiusX() {
    return this.radiusX * this.scaleAmountX;
  }

  @Override
  public double getRadiusY() {
    return this.radiusY * this.scaleAmountY;
  }

  @Override
  public void scale(double amountX, double amountY) {
    this.scaleAmountX = amountX;
    this.scaleAmountY = amountY;
  }

  @Override
  public String toString() {
    return "Type: oval\nCenter: " + this.pinhole.toString() + ", X radius: " + this.radiusX
            + ", Y radius: " + this.radiusY + ", Color: " + this.color.toString();
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

  @Override
  public IVisitableShape getVisitableCopy() {
    return new Ellipse(this.pinhole.getX(), this.pinhole.getY(),
            this.radiusX, this.radiusY, new Color(this.color));
  }

  @Override
  public void setColor(float r, float g, float b, float a) {
    this.color.mutateColor(r, g, b, a);
  }

  @Override
  public void setPosition(IPosition position) {
    if (position == null) {
      throw new IllegalArgumentException("Given a null position");
    }
    this.pinhole.setX(position.getX());
    this.pinhole.setY(position.getY());
  }

  @Override
  public IColor getColor() {
    return new Color(this.color);
  }

  @Override
  public IPosition getPosition() {
    return new Position(this.pinhole);
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
}
