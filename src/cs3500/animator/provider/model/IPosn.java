package cs3500.animator.provider.model;

/**
 * Created by Yash and Jack because our provider did not do so after multiple emails.
 *
 * Represents a position. Uses doubles for position.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IPosn {

  /**
   * Return the x-coordinate of this posn.
   *
   * @return double with x-coordinate
   */
  double getX();

  /**
   * Set the x-coordinate of this posn.
   *
   * @param otherX x-coordinate to set
   */
  void setX(double otherX);

  /**
   * Return the y-coordinate of this posn.
   *
   * @return double with y-coordinate
   */
  double getY();

  /**
   * Set the y-coordinate of this posn.
   *
   * @param otherY y-coordinate to set
   */
  void setY(double otherY);

  /**
   * Checks if this posn is equal to the given posn.
   *
   * @param other posn to check equality with
   * @return true if this posn is equal to given, false otherwise
   */
  @Override
  boolean equals(Object other);

  /**
   * Generates a hashcode for this posn.
   *
   * @return generated hashcode
   */
  @Override
  int hashCode();

  /**
   * Generates a string representation for this posn.
   *
   * @return string representing the porn in (x;y) format
   */
  @Override
  String toString();
}
