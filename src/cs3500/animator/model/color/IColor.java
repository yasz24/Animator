package cs3500.animator.model.color;

/**
 * Represents a color for a shape. Can be mutated.
 *
 * <p>CHANGE 04/16/18: Created this interface.
 */
public interface IColor {

  /**
   * Modifies the color values for this {@code Color}.
   *
   * @param newR New float red value for the {@code Color}
   * @param newG New float green value for the {@code Color}
   * @param newB New float blue value for the {@code Color}
   * @param newA New float alpha value for the {@code Color}
   */
  void mutateColor(float newR, float newG, float newB, float newA);

  /**
   * Returns the red value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  float getR();

  /**
   * Returns the green value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  float getG();

  /**
   * Returns the blue value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  float getB();

  /**
   * Returns the alpha value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  float getA();

  /**
   * Should override toString to provide a description of the color.
   *
   * @return textual description of the color.
   */
  @Override
  String toString();
}
