package cs3500.animator.view.visitors;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.model.shape.adapters.OvalAdapter;
import cs3500.animator.model.shape.adapters.RectangleAdapter;
import cs3500.animator.provider.model.IAShape;

/**
 * This class wraps our IVistableActor to the appropriate IAShape adapters. The visitor
 * pattern is used to achieve this.
 */
public class IAShapeAdapterVisitor extends AbstVisitor<IAShape> {
  /**
   * The adapted IVisitableActor (now an IAShape).
   */
  private IAShape adaptedObject;

  /**
   * The adaptee IVisitableActor.
   */
  IVisitableActor actor;

  /**
   * The constructor for this IShapeAdapterVisitor.
   *
   * @param given The IVisitableActor to be adapted.
   */
  public IAShapeAdapterVisitor(IVisitableActor given) {
    if (given == null) {
      throw new IllegalArgumentException("can't adapt a null object");
    }
    else {
      this.actor = given;
    }
  }

  @Override
  public void visit(IVisitableActor actor) {
    this.visit(actor.getShape());
  }

  @Override
  public void visit(IVisitableShape shape) {
    shape.accept(this);
  }

  @Override
  public void visit(IEllipse ellipse) {
    adaptedObject = new OvalAdapter(actor);
  }

  @Override
  public void visit(IRectangle rect) {
    adaptedObject = new RectangleAdapter(actor);
  }

  @Override
  public IAShape getResult() {
    return adaptedObject;
  }
}
