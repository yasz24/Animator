package cs3500.animator.model.shape;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.color.IColor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents an ellipse. An ellipse has a position (center), radius-x, radius-y,
 * and color. An ellipse can be visited and transformed using visitors.
 *
 * <p>Change 04/17/18: Created this interface for our client.
 */
public interface IEllipse extends IVisitableShape {

  @Override
  double getRadiusX();

  @Override
  double getRadiusY();

  @Override
  void scale(double amountX, double amountY);

  @Override
  String toString();

  @Override
  void accept(ModelVisitor v);

  @Override
  IVisitableShape getVisitableCopy();

  @Override
  void setColor(float r, float g, float b, float a);

  @Override
  void setPosition(IPosition position);

  @Override
  IColor getColor();

  @Override
  IPosition getPosition();

  @Override
  void acceptVisitableAction(IVisitableAction action, long tick);
}
