package cs3500.animator.model.shape;

import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a rectangle. A rectangle has a position (lower-left corner), width, height,
 * and color. A rectangle can be visited and transformed using visitors.
 *
 * <p>Change 04/17/18: Created this interface for our client.
 */
public interface IRectangle extends IVisitableShape {

  @Override
  double getWidth();

  @Override
  double getHeight();

  @Override
  void scale(double amountX, double amountY);

  @Override
  String toString();

  @Override
  void accept(ModelVisitor v);

  @Override
  IVisitableShape getVisitableCopy();

}
