package cs3500.animator.model.shape.adapters;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.provider.model.IPosn;

/**
 * This class adapts our IPosition interface to the provider's IPosn interface. It does so by using
 * composition.
 */
public class ProvPosnAdapter implements IPosn {

  /**
   * Our IPosition that we're wrapping.
   */
  private IPosition myPosn;

  /**
   * Constructor for this ProvPosnAdapter.
   *
   * @param myPosn the IPosition to be adapted.
   */
  public ProvPosnAdapter(IPosition myPosn) {
    this.myPosn = myPosn;
  }

  @Override
  public double getX() {
    return myPosn.getX();
  }

  @Override
  public void setX(double otherX) {
    myPosn.setX(otherX);
  }

  @Override
  public double getY() {
    return myPosn.getY();
  }

  @Override
  public void setY(double otherY) {
    myPosn.setY(otherY);
  }
}
