package cs3500.animator.model.action;

import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Interface for a color change for shapes. A color change for shapes changes the color of a shape
 * from one color to another.
 *
 * <p>Change 04/16/18: Created this interface for our client.
 */
public interface IColorAction extends IVisitableAction {

  @Override
  void transformShape(IVisitableShape shape, long tick);

  @Override
  IAction getCopy();

  @Override
  String toString();

  @Override
  void accept(ModelVisitor v);

  @Override
  IColor getStartColor();

  @Override
  IColor getEndColor();

  @Override
  IVisitableAction getVisitableCopy();
}
