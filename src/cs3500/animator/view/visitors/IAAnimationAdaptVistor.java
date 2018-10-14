package cs3500.animator.view.visitors;

import cs3500.animator.model.action.IRotateAction;
import cs3500.animator.model.action.adapters.ColorChangeAdapter;
import cs3500.animator.model.action.IColorAction;
import cs3500.animator.model.action.IMoveAction;
import cs3500.animator.model.action.IScaleAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.action.adapters.MoveAdapter;
import cs3500.animator.model.action.adapters.ScaleAdapter;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.IAAnimation;

/**
 * This class wraps our IVistableActions to the appropriate IAAnimation adapters. The visitor
 * pattern is used to achieve this.
 */
public class IAAnimationAdaptVistor extends AbstVisitor<IAAnimation> {
  /**
   * The adapted IVisitableAction (now an IAAnimation).
   */
  private IAAnimation adaptedAction;

  /**
   * The actor that the given IVisitableAction operates on.
   */
  private IVisitableActor actor;

  /**
   * Constructor for this IAAnimationAdaptVisitor.
   *
   * @param given the IVisitableActor that the adaptee IVisitableAction operates on.
   */
  public IAAnimationAdaptVistor(IVisitableActor given) {
    if (given == null) {
      throw new IllegalArgumentException("can't adapt a null object");
    }
    else {
      this.actor = given;
    }
  }

  @Override
  public void visit(IVisitableAction action) {
    action.accept(this);
  }

  @Override
  public void visit(IColorAction colorAction) {
    adaptedAction = new ColorChangeAdapter(colorAction, actor);
  }

  @Override
  public void visit(IRotateAction rotateAction) {
    throw new UnsupportedOperationException("Provider does not support rotation");
  }

  @Override
  public void visit(IMoveAction moveAction) {
    adaptedAction = new MoveAdapter(moveAction, actor);
  }

  @Override
  public void visit(IScaleAction scaleAction) {
    adaptedAction = new ScaleAdapter(scaleAction, actor);
  }

  @Override
  public IAAnimation getResult() {
    return adaptedAction;
  }
}
