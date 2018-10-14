package cs3500.animator.model.color.adapters;

import java.awt.Color;

import cs3500.animator.model.color.IColor;
import cs3500.animator.provider.model.IMyColor;

/**
 * Adapts our IColor to the provider's IMyColor interface, using composition.
 * Called methods correspond to methods on the internal IColor.
 */
public class ProvAdaptColor implements IMyColor {

  /**
   * The IColor that is being adapted.
   */
  private IColor myColor;

  /**
   * Constructor for this ProvAdaptColor.
   *
   * @param myColor our IColor that is being adapted.
   */
  public ProvAdaptColor(IColor myColor) {
    this.myColor = myColor;
  }

  @Override
  public double getR() {
    return myColor.getR() * 255.0;
  }

  @Override
  public double getG() {
    return myColor.getG() * 255.0;
  }

  @Override
  public double getB() {
    return myColor.getB() * 255.0;
  }

  @Override
  public Color getColor() {
    return new Color((int) this.getR(), (int) this.getG(), (int) this.getB());
  }

  @Override
  public String toString() {
    return "(" + this.myColor.getR() + "," + this.myColor.getG() + "," +
            this.myColor.getB() + ")";
  }

  @Override
  public void update(double x, double y, double z) {
    this.myColor.mutateColor(
            (float) x / 255.f,
            (float) y / 255.f,
            (float) z / 255.f,
            1.f);
  }
}
