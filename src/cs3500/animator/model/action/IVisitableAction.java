package cs3500.animator.model.action;

import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Holds methods that are visible to {@code IAction} subclasses of this interface, but are hidden
 * from {@code IAction} itself. The reason for hiding from {@code IAction} is because we want to
 * avoid filling {@code IAction} with many default methods and also want to avoid modifying
 * {@code IAction}. To add more methods visible to visitors, add them here and give them the
 * default {@code UnsupportedOperationException()} throw.
 *
 * <p>Change 04/16/18: Changed all IAction, IShape, IActor to visitable versions.
 *
 * <p>Change 04/16/18: Moved accept() from IAction to IVisitableAction.
 *
 * <p>Change 04/16/18: Created getVisitableCopy() method.
 *
 * <p>Change 04/25/18: Created getStartAngle and getEndAngle methods.
 */
public interface IVisitableAction extends IAction {

  /**
   * Applies the transformation held in this {@code IAction} to the given {@link IVisitableShape}.
   *
   * @param shape {@code IVisitableShape} to apply transformation to
   * @param tick  Used for interpolation of the transformation
   */
  void transformShape(IVisitableShape shape, long tick);

  /**
   * Returns the start position of this {@code IVisitableAction}. This is the position that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return position containing the initial location of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default IPosition getStartPosition() {
    throw new UnsupportedOperationException("Unsupported operation getStartPosition()");
  }

  /**
   * Returns the ending position of this {@code IVisitableAction}. This is the position that
   * the {@code IVisitableAction} holds at the end of the transformation.
   *
   * @return position containing the final location of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default IPosition getEndPosition() {
    throw new UnsupportedOperationException("Unsupported operation getEndPosition()");
  }

  /**
   * Returns the start color of this {@code IVisitableAction}. This is the color that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return color containing the initial color of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default IColor getStartColor() {
    throw new UnsupportedOperationException("Unsupported operation getStartColor()");
  }

  /**
   * Returns the final color of this {@code IVisitableAction}. This is the color that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return color containing the initial color of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default IColor getEndColor() {
    throw new UnsupportedOperationException("Unsupported operation getEndColor()");
  }

  /**
   * Returns the initial width of this {@code IVisitableAction}. This is the width that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return double containing the initial width of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getStartWidth() {
    throw new UnsupportedOperationException("Unsupported operation getStartWidth()");
  }

  /**
   * Returns the initial height of this {@code IVisitableAction}. This is the height that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return double containing the initial height of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getStartHeight() {
    throw new UnsupportedOperationException("Unsupported operation getStartHeight()");
  }

  /**
   * Returns the final width of this {@code IVisitableAction}. This is the width that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return double containing the final width of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getEndWidth() {
    throw new UnsupportedOperationException("Unsupported operation getEndWidth()");
  }

  /**
   * Returns the final height of this {@code IVisitableAction}. This is the height that
   * the {@code IVisitableAction} holds at the beginning of the transformation.
   *
   * @return double containing the initial height of this {@code IVisitableAction}
   * @throws UnsupportedOperationException unless overridden
   */
  default double getEndHeight() {
    throw new UnsupportedOperationException("Unsupported operation getEndHeight()");
  }

  /**
   * Returns the angle that the action begins from.
   *
   * @return the angle the action begins from
   * @throws UnsupportedOperationException unless overridden
   */
  default double getStartAngle() {
    throw new UnsupportedOperationException("Unsupported operation getStartAngle()");
  }

  /**
   * Returns the final angle of this {@code IVisitableAction}. This is the angle that the
   * transformed shape will have at the end of the animation.
   *
   * @return the angle the action ends with
   * @throws UnsupportedOperationException unless overridden
   */
  default double getEndAngle() {
    throw new UnsupportedOperationException("Unsupported operation getStartAngle()");
  }

  /**
   * Checks to see if this {@code IAction} is compatible with the given {@code IAction}.
   * This {@code IAction} is compatible with the given {@code IAction} if it either operates
   * in a different time interval, or modifies different {@code IVisitableShape} attributes from
   * the other {@code IAction}.
   *
   * <p>To add a new shape, one must create a new method in {@link AbstractAction} to determine
   * compatibility with that shape, as well as possibly override the other concrete compatibility
   * testing methods.
   *
   * @param other the given action, against which compatibility needs to checked.
   * @return True if the given action is compatible with this action.
   */
  boolean compatibleWith(IVisitableAction other);

  /**
   * Returns a copy of this {@code IVisitableAction}.
   *
   * @return a copy of this action
   */
  IVisitableAction getVisitableCopy();

  /**
   * Accepts the given {@link ModelVisitor}, then calls visit() on it from one of {@code IAction}'s
   * subclasses, thus allowing {@code ModelVisitor} to access methods not in {@code IAction} using
   * polymorphism.
   *
   * @param v ModelVisitor to accept
   * @throws IllegalArgumentException if given a null visitor.
   */
  void accept(ModelVisitor v);

}
