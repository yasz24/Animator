package cs3500.animator.model.action.adapters;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.adapters.ProvAdaptColor;
import cs3500.animator.provider.model.IColorChange;
import cs3500.animator.provider.model.IMyColor;

/**
 * Wraps an IVisitableAction and adapts it to behave like an IAAnimation, specifically a
 * IColorChange.
 */
public class ColorChangeAdapter extends AAnimationAdapter implements IColorChange {

  /**
   * The constructor for this ColorChangeAdaptor.
   *
   * @param action The IVisitableAction that is to be adapted.
   * @param actor  The IVisitableActor that the IVisitableAction operates on.
   */
  public ColorChangeAdapter(IVisitableAction action, IVisitableActor actor) {
    super(action, actor);
  }


  @Override
  public IMyColor getNewColor() {
    return new ProvAdaptColor(this.action.getEndColor());
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof ColorChangeAdapter)) {
      return false;
    } else {
      ColorChangeAdapter c = (ColorChangeAdapter) other;
      return (c.action.getStartColor().equals(this.action.getStartColor())) &&
              (c.action.getEndColor()).equals(this.action.getEndColor()) &&
              (c.getAffected().equals(this.getAffected()));
    }
  }

  @Override
  public String toString() {
    return this.actor.getName() + " " + this.action.toString();
  }

  @Override
  public int hashCode() {
    return (this.getAffected().hashCode() * this.action.getStartColor().hashCode() *
            this.action.getEndColor().hashCode());
  }
}
