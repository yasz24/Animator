package cs3500.animator.model.action.adapters;

import java.util.ArrayList;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.view.visitors.IAShapeAdapterVisitor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * This is an adapter class that tries to adapt our IVisitableAction objects to their analog in our
 * providers code, namely objects of type IAAnimation. It does this by storing an internal actor
 * and action, since the providers' IAAnimation needs both.
 */
public class AAnimationAdapter implements IAAnimation {

  /**
   * The action we're wrapping.
   */
  protected IVisitableAction action;

  /**
   * The actor that this action operates on.
   */
  protected IVisitableActor actor;

  /**
   * The constructor for this AAnimationAdapter.
   *
   * @param action The IVisitable action that needs to be adapted.
   * @param given  The IVisitableActor that this IVisitableAction operates on.
   */
  public AAnimationAdapter(IVisitableAction action, IVisitableActor given) {
    if (action != null) {
      this.action = action;
    } else {
      throw new IllegalArgumentException("cannot adapt a null action");
    }
    if (given != null) {
      this.actor = given;
    }
  }

  @Override
  public void update(IAShape shape, int ticks, IAShape toBeUpdated) {
    // We already know what we're updating, since when this method is called
    // it gets called essentially with itself.
    this.actor.update(ticks);
  }

  /***
   * Returns the affected shape.
   *
   * @return affected shape.
   */
  @Override
  public IAShape getAffected() {
    ModelVisitor<IAShape> shapeVisitor = new IAShapeAdapterVisitor(actor);
    shapeVisitor.visit(actor);
    return shapeVisitor.getResult();
  }

  @Override
  public boolean validAnim(ArrayList<IAAnimation> arr) {
    for (IAAnimation anim : arr) {
      // AAnimationAdapters can only potentially not be compatible if the given is also
      // an AAnimationAdapter
      if (anim instanceof AAnimationAdapter) {
        AAnimationAdapter given = (AAnimationAdapter) anim;
        if (!given.action.compatibleWith(this.action)) {
          return false;
        }
      }
      // Else do nothing, because if they aren't the same type they are compatible
    }
    return true;
  }

  @Override
  public int getStart() {
    return (int) action.getStartTime();
  }

  @Override
  public int duration() {
    return (int) action.getStartTime() - (int) action.getEndTime();
  }

  @Override
  public int getEnd() {
    return (int) action.getEndTime();
  }
}
