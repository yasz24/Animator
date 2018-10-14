package cs3500.animator.model.shape.adapters;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.IPosn;
import cs3500.animator.provider.model.IOval;

/**
 * Wraps an IVisitableActor and adapts it to behave like an IAShape, specifically an IOval.
 */
public class OvalAdapter extends AShapeAdapter implements IOval {

  /**
   * Constructor for this OvalAdapter.
   *
   * @param given the IVisitableActor to be adapted.
   */
  public OvalAdapter(IVisitableActor given) {
    super(given);
  }

  @Override
  public IPosn getCenter() {
    return new ProvPosnAdapter(this.actor.getShape().getPosition());
  }

  @Override
  public double getyRadius() {
    return this.actor.getShape().getRadiusY();
  }

  @Override
  public double getxRadius() {
    return this.actor.getShape().getRadiusX();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof OvalAdapter)) {
      return false;
    } else {
      OvalAdapter o = (OvalAdapter) other;
      return o.actor.equals(this.actor);
    }
  }

  @Override
  public String toString() {
    return this.actor.toString();
  }

  @Override
  public int hashCode() {
    return this.actor.getName().hashCode();
  }
}
