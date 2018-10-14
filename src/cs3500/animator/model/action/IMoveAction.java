package cs3500.animator.model.action;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Interface for a move action on shapes. A move action changes the location of a shape
 * from one place to another.
 *
 * <p>Change 04/16/18: Created this interface for our client.
 */
public interface IMoveAction extends IVisitableAction {

  @Override
  void transformShape(IVisitableShape shape, long tick);

  @Override
  IAction getCopy();

  @Override
  String toString();

  @Override
  void accept(ModelVisitor v);

  @Override
  IPosition getStartPosition();

  @Override
  IPosition getEndPosition();

  @Override
  IVisitableAction getVisitableCopy();

}
