package cs3500.animator.model.action.adapters;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.IScale;

/**
 * Wraps an IVisitableAction and adapts it to behave like an IAAnimation, specifically an IScale.
 */
public class ScaleAdapter extends AAnimationAdapter implements IScale {

  /**
   * The constructor for this ScaleAdaptor.
   *
   * @param action The IVisitableAction that is to be adapted.
   * @param actor  The IVisitableActor that the IVisitableAction operates on.
   */
  public ScaleAdapter(IVisitableAction action, IVisitableActor actor) {
    super(action, actor);
  }

  @Override
  public double xParam() {
    return Math.abs(action.getEndWidth() - action.getStartWidth());
  }

  @Override
  public double yParam() {
    return Math.abs(action.getEndHeight() - action.getStartHeight());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ScaleAdapter)) {
      return false;
    } else {
      ScaleAdapter s = (ScaleAdapter) other;
      return (s.action.getStartWidth() == this.action.getStartWidth()) &&
              (s.action.getEndWidth() == this.action.getEndWidth()) &&
              (s.action.getStartHeight() == this.action.getStartHeight()) &&
              (s.action.getEndHeight() == this.action.getEndHeight()) &&
              s.getAffected().equals(this.getAffected());
    }
  }

  @Override
  public String toString() {
    return this.actor.getName() + " " + this.action.toString();
  }

  @Override
  public int hashCode() {
    return (int) (this.getAffected().hashCode() * this.action.getStartHeight() *
            this.action.getStartWidth() * this.action.getEndWidth() *
            this.action.getEndHeight());
  }
}
