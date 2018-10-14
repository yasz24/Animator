package cs3500.animator.model.shape;

import cs3500.animator.model.action.IAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a rectangular shape that has a width, height, and pinhole (which is the lower-left
 * corner).
 * <br>Can be transformed by {@link IAction}s, and can be visited by {@link ModelVisitor}s.
 */
public class Rectangle implements IRectangle {

  // Proportion to scale the IRectangle in x and y directions
  private double scaleAmountX;
  private double scaleAmountY;

  /**
   * Width of the {@code IRectangle}.
   */
  protected double width;

  /**
   * Height of the {@code IRectangle}.
   */
  protected double height;

  /**
   * Color of the {@code IRectangle}.
   */
  protected IColor color;

  /**
   * Position of the {@code IRectangle}
   */
  protected IPosition pinhole;


  /**
   * Constructs a new {@code IRectangle} with given lower left corner and given dimensions.
   *
   * @param leftX  double containing the x coordinate of the lower left corner of the rectangle
   * @param leftY  double containing the y coordinate of the lower left corner of the rectangle
   * @param width  double containing the width of the rectangle
   * @param height double containing the height of the rectangle
   * @param color  Color containing the color of the rectangle
   * @throws IllegalArgumentException if given a null color
   */
  public Rectangle(double leftX, double leftY, double width, double height, IColor color) {

    this.width = width;
    this.height = height;

    this.scaleAmountX = 1;
    this.scaleAmountY = 1;

    this.pinhole = new Position(leftX, leftY);

    this.color = color;
  }

  @Override
  public double getWidth() {
    return this.width * this.scaleAmountX;
  }

  @Override
  public double getHeight() {
    return this.height * this.scaleAmountY;
  }

  @Override
  public void scale(double amountX, double amountY) {
    this.width = amountX;
    this.height = amountY;
  }

  @Override
  public String toString() {
    return "Type: rectangle\nLower-left corner: " + this.pinhole.toString()
            + ", Width: " + this.width + ", Height: " + this.height
            + ", Color: " + this.color.toString();
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
    return new Rectangle(this.pinhole.getX(), this.pinhole.getY(),
            this.width, this.height, new Color(this.color));
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
