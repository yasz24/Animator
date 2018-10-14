package cs3500.animator.model.shape;

import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Holds methods that are visible to {@code IShape} subclasses of this interface, but are hidden
 * from {@code IShape} itself. The reason for hiding from {@code IShape} is because we want to
 * avoid filling {@code IShape} with many default methods that are suppressed by different
 * subclasses, and also want to avoid modifying {@code IShape.java} itself.
 * To add more methods visible to visitors, add them here and give them the
 * default {@code UnsupportedOperationException()} throw.
 *
 * <p>Change 04/16/18: Moved accept() from IShape to IVisitableShape.
 *
 * <p>Change 04/16/18: Moved accept() from IShape to IVisitableShape.
 *
 * <p>Change 04/24/18: added getAngle() and setAngle() methods.
 */
public interface IVisitableShape extends IShape {

  /**
   * Returns the radius of this {@code IVisitableAction} in the x-direction.
   *
   * @return Double containing the x-radius of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getRadiusX() {
    throw new UnsupportedOperationException("Unsupported operation getRadiusX()");
  }

  /**
   * Returns the radius of this {@code IVisitableAction} in the y-direction.
   *
   * @return Double containing the y-radius of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getRadiusY() {
    throw new UnsupportedOperationException("Unsupported operation getRadiusY()");
  }

  /**
   * Returns the angle that this shape is currently oriented at. Only relevant to IRotatable shapes.
   *
   * @return a double that represents the current angle of this shape.
   */
  default double getAngle() {
    throw new UnsupportedOperationException("Shape doesn't support rotation");
  }

  /**
   * Sets the angle of the shape to orientation in radians based of the given angle in degrees.
   *
   * @param angle angle to rotate the shape to
   */
  default void setAngle(double angle) {
    throw new UnsupportedOperationException("Shape doesn't support rotation");
  }

  /**
   * Returns the width of this {@code IVisitableAction}.
   *
   * @return Double containing the width of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getWidth() {
    throw new UnsupportedOperationException("Unsupported operation getWidth()");
  }

  /**
   * Returns the height of this {@code IVisitableAction}.
   *
   * @return Double containing the height of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getHeight() {
    throw new UnsupportedOperationException("Unsupported operation getHeight()");
  }

  /**
   * Sets the color of this {@code IVisitableShape}.
   *
   * @param r Red amount, as a float from 0 to 1
   * @param g Red amount, as a float from 0 to 1
   * @param b Red amount, as a float from 0 to 1
   * @param a Red amount, as a float from 0 to 1
   */
  void setColor(float r, float g, float b, float a);

  /**
   * Scales the shape by the given amount. Scaling is from the pinhole of the shape, which
   * varies by shape. Scales by a proportion, where 1 is no scaling, .5 is half size, 2.0 is
   * double size, etc.
   *
   * @param x percent to scale the width of the shape by
   * @param y percent to scale the height oxf the shape by
   */
  void scale(double x, double y);

  /**
   * Changes the position of this shape to the given position. Does this by moving the center
   * of the shape to the given position.
   *
   * @param position Location to move the pinhole of this shape to
   * @throws IllegalArgumentException if given a null position
   */
  void setPosition(IPosition position);

  /**
   * Returns a visitable copy of this {@code IVisitableShape}.
   *
   * @return Copy of this {@code IVisitableShape}
   */
  IVisitableShape getVisitableCopy();

  /**
   * Accepts the given {@link ModelVisitor}, then calls visit() on it from one of {@code IShape}'s
   * subclasses, thus allowing {@code ModelVisitor} to access non-interface methods using
   * polymorphism.
   *
   * @param v ModelVisitor to accept
   * @throws IllegalArgumentException if v is null
   */
  void accept(ModelVisitor v);
}
