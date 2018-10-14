package cs3500.animator.model.action.adapters;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.shape.adapters.ProvPosnAdapter;
import cs3500.animator.provider.model.IPosn;
import cs3500.animator.provider.model.IMove;

/**
 * Wraps an IVisitableAction and adapts it to behave like an IAAnimation, specifically an IMove.
 */
public class MoveAdapter extends AAnimationAdapter implements IMove {

  /**
   * The constructor for this MoveAdaptor.
   *
   * @param action The IVisitableAction that is to be adapted.
   * @param actor  The IVisitableActor that the IVisitableAction operates on.
   */
  public MoveAdapter(IVisitableAction action, IVisitableActor actor) {
    super(action, actor);
  }


  @Override
  public IPosn endLocation() {
    return new ProvPosnAdapter(action.getEndPosition());
  }


  @Override
  public boolean equals(Object other) {
    if (!(other instanceof MoveAdapter)) {
      return false;
    } else {
      MoveAdapter m = (MoveAdapter) other;
      return (m.action.getStartPosition().equals(this.action.getStartPosition())) &&
              (m.action.getEndPosition()).equals(this.action.getEndPosition()) &&
              (m.getAffected().equals(this.getAffected()));
    }
  }

  @Override
  public String toString() {
    return this.actor.getName() + " " + this.action.toString();
  }

  @Override
  public int hashCode() {
    return (this.getAffected().hashCode() * this.action.getStartPosition().hashCode() *
            this.action.getEndPosition().hashCode());
  }
}
