package cs3500.animator.model.action;

import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Interface for a scale action on shapes. A scale action changes the size of
 * one shape to a new size.
 *
 * <p>Change 04/16/18: Created this interface for our client.
 */
public interface IScaleAction extends IVisitableAction {

  @Override
  void transformShape(IVisitableShape shape, long tick);

  @Override
  IAction getCopy();

  @Override
  String toString();

  @Override
  void accept(ModelVisitor v);

  @Override
  double getStartWidth();

  @Override
  double getStartHeight();

  @Override
  double getEndWidth();

  @Override
  double getEndHeight();

  @Override
  IVisitableAction getVisitableCopy();

}
