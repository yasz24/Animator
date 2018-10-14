package cs3500.animator.provider.model;

import java.awt.Color;

/**
 * Created by Yash and Jack because our provider did not do so after multiple emails.
 *
 * Represents a color with red, blue, and green values.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IMyColor {

  /**
   * Return the red value of the color.
   * @return a double representing r
   */
  double getR();

  /**
   * Return the green value of the color.
   * @return a double representing g
   */
  double getG();

  /**
   * Return the blue value of the color.
   * @return a double representing b.
   */
  double getB();

  /**
   * Tests equality with a given object.
   *
   * @param o object to test equality with
   * @return true if the equality is equal, false otherwise
   */
  @Override
  boolean equals(Object o);

  /**
   * Creates a unique-ish hash code for this color.
   *
   * @return hashcode for the color.
   */
  @Override
  int hashCode();

  /**
   * Turns myColor into a Color.
   * @return a Color
   */
  Color getColor();

  @Override
  String toString();

  /**
   * Updates the color.
   * @param x the red value to be changed to.
   * @param y the green value to be changed to.
   * @param z the blue value to be changed to.
   */
  void update(double x, double y, double z);
}
