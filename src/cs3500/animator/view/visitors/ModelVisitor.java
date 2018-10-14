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
 * Represents a visitor for classes that are part of the model package, including IActor, IShape,
 * and IAction. Used for text printing and graphics. Uses polymorphism to allow access to
 * methods of subclasses of interfaces without casting.
 *
 * <p>To add a new type of {@link IVisitableActor}, {@link IVisitableShape}, or
 * {@link IVisitableAction}, you must add the visit method for it here.
 *
 * <p>Change 04/16/18: Changed IActor, IShape, IAction to visitable versions.
 *
 * <p>Change 04/16/18: Added visit(IShapeAdapter) method to allow the ModelVisitor to visit the
 * adapters for the provider's IAShape.
 *
 * <p>Change 04/17/18: Generified Ellipse, IRectangle, MoveAction, ScaleAction, ColorAction.
 *
 * <p>Change 04/25/17: Added IRotateAction visitor.
 *
 * @param <T> If the visitor accumulates some value then T will be the type of the value.
 */
public interface ModelVisitor<T> {

  // ACTOR VISITORS.

  /**
   * Visits the {@link IVisitableActor} interface, whose sub-classes will then call visit back,
   * allowing access to the sub-classes' methods using dynamic dispatch.
   *
   * @param actor Actor to visit.
   * @throws IllegalArgumentException if scaleAction is null.
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for ScaleActions.
   */
  void visit(IVisitableActor actor);

  // END ACTOR VISITORS

  // SHAPE VISITORS
  /**
   * Visits the {@link IVisitableShape} interface, whose sub-classes will then call visit back,
   * allowing access to the sub-classes' methods using dynamic dispatch.
   *
   * @param shape Shape to visit.
   * @throws IllegalArgumentException if shape is null.
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for IShape.
   */
  void visit(IVisitableShape shape);

  /**
   * Visits the {@link IEllipse} interface.
   *
   * @param ellipse Ellipse object to visit
   * @throws IllegalArgumentException if ellipse is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for Ellipse
   */
  void visit(IEllipse ellipse);

  /**
   * Visits the {@link IRectangle} interface.
   *
   * @param rect IRectangle object to visit
   * @throws IllegalArgumentException if rect is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for IRectangle
   */
  void visit(IRectangle rect);

  // ACTION VISITORS
  /**
   * Visits the {@link IVisitableAction} interface, whose sub-classes will then call visit back,
   * allowing access to the sub-classes' methods using dynamic dispatch.
   *
   * @param action IAction to visit.
   * @throws IllegalArgumentException if action is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for IAction
   */
  void visit(IVisitableAction action);

  /**
   * Visits the {@link IColorAction} interface.
   *
   * @param colorAction ColorAction object to visit
   * @throws IllegalArgumentException if colorAction is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for IColorAction
   */
  void visit(IColorAction colorAction);

  /**
   * Visits the {@link IRotateAction} interface.
   *
   * @param rotateAction RotateAction object to visit
   * @throws IllegalArgumentException if colorAction is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for IRotateAction
   */
  void visit(IRotateAction rotateAction);

  /**
   * Visits the {@link IMoveAction} interface.
   *
   * @param moveAction MoveAction object to visit
   * @throws IllegalArgumentException if moveAction is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for MoveAction
   */
  void visit(IMoveAction moveAction);

  /**
   * Visits the {@link IScaleAction} interface.
   *
   * @param scaleAction ScaleAction object to visit
   * @throws IllegalArgumentException if scaleAction is null
   * @throws UnsupportedOperationException if the implementation of ModelVisitor does not implement
   *                                       visit for ScaleActions
   */
  void visit(IScaleAction scaleAction);
  // END ACTION VISITORS

  /**
   * If ths visitor accumulates some value through it's visits to various model classes, then
   * it will be accumulated to a variable of type T. Call this function to return the accumulated
   * value.
   *
   * @return Accumulated value
   * @throws UnsupportedOperationException if the visitor does not accumulate a value
   */
  T getResult();
}
