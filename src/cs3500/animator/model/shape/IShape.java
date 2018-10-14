package cs3500.animator.model.shape;

import cs3500.animator.model.action.IAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;

/**
 * Represents a polygon or curved shape that possesses {@link IPosition}s.
 *
 * <p>Usually part of an {@link IActor}.
 *
 * <p>Can be transformed by an {@link IAction}.
 *
 * <p>Change 04/16/18: Changed accept IAction to accept IVisitableAction.
 */
public interface IShape {

  /**
   * Returns the color of this {@code IShape}.
   *
   * @return {@link Color} holding the color information of this {@code IShape}
   */
  IColor getColor();

  /**
   * Returns a copy of the position of this {@code IShape}.
   *
   * @return {@link IPosition} holding the positional information of this {@code IShape}
   */
  IPosition getPosition();

  /**
   * Accepts an {@link IVisitableAction}, then uses double dispatch to allow the
   * {@code VisitorTransformation} to modify the subclass.
   *
   * @param action Instance of {@code AbstractAction} to modify this {@code IShape}
   * @param tick   Specifies completeness amount of the action
   */
  void acceptVisitableAction(IVisitableAction action, long tick);

  /**
   * Returns a string representation of this {@code IShape}.
   *
   * @return String representing the shape
   */
  @Override
  String toString();

  /**
   * Returns a deep copy of this {@code IShape}.
   *
   * @return copy of this {@code IShape}
   */
  IShape getCopy();

}
