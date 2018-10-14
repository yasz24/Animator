package cs3500.animator.model.shape.adapters;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.IPosn;
import cs3500.animator.provider.model.IRectangle;

/**
 * Wraps an IVisitableActor and adapts it to behave like an IAShape, specifically a IRectangle.
 */
public class RectangleAdapter extends AShapeAdapter implements IRectangle {

  /**
   * Constructor for this RectangleAdapter.
   *
   * @param given the IVisitableActor to be adapted.
   */
  public RectangleAdapter(IVisitableActor given) {
    super(given);
  }

  @Override
  public IPosn getLLC() {
    return new ProvPosnAdapter(this.actor.getShape().getPosition());
  }

  @Override
  public double getHeight() {
    return this.actor.getShape().getHeight();
  }

  @Override
  public double getWidth() {
    return this.actor.getShape().getHeight();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof RectangleAdapter)) {
      return false;
    } else {
      RectangleAdapter r = (RectangleAdapter) other;
      return r.actor.equals(this.actor);
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
