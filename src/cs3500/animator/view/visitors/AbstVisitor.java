package cs3500.animator.view.visitors;

import cs3500.animator.model.action.IColorAction;
import cs3500.animator.model.action.IMoveAction;
import cs3500.animator.model.action.IRotateAction;
import cs3500.animator.model.action.IScaleAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IVisitableShape;

/**
 * Implements the default visitor methods for every object type. The default is an
 * {@link UnsupportedOperationException}, which can be overridden by a subclass to
 * "support" the method." This way, we don't have to worry about implementing visitor
 * methods for object types we do not want to support.
 *
 * @param <T> Passing on the return type parameter for use in subclasses.
 */
public abstract class AbstVisitor<T> implements ModelVisitor<T> {

  @Override
  public void visit(IVisitableActor actor) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IVisitableShape shape) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IEllipse ellipse) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IRectangle rect) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IVisitableAction action) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IColorAction colorAction) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IMoveAction moveAction) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IScaleAction scaleAction) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public void visit(IRotateAction rotateAction) {
    throw new UnsupportedOperationException("Unsupported visit");
  }

  @Override
  public T getResult() {
    throw new UnsupportedOperationException("Cannot call getResult() on this visitor");
  }
}
