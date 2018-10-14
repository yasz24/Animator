package cs3500.animator.model.action;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Interface for a rotation action on shapes.
 * A rotation action rotates the shape clockwise about its center by a given amount degrees.
 *
 * <p>Change 04/25/18: Created this interface.
 */
public interface IRotateAction extends IVisitableAction {

  @Override
  double getStartAngle();

  @Override
  double getEndAngle();

  @Override
  IPosition getStartPosition();

  @Override
  IPosition getEndPosition();

  @Override
  void transformShape(IVisitableShape shape, long tick);

  @Override
  IAction getCopy();

  @Override
  void accept(ModelVisitor v);

  @Override
  IVisitableAction getVisitableCopy();

  @Override
  String toString();
}
