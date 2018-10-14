package cs3500.animator.model.shape;

/**
 * Represents a position for a shape. Can be mutated.
 *
 * <p>Change 04/16/18: Created this interface for our client.
 */
public interface IPosition {
  /**
   * Gets the x co-ordinate of this position.
   *
   * @return the x-coordinate
   */
  double getX();

  /**
   * Sets the x co-ordinate of this position.
   *
   * @param otherX the value that the x-coordinate is to be set to.
   */
  void setX(double otherX);

  /**
   * Gets the y co-ordinate of this position.
   *
   * @return the y-coordinate
   */
  double getY();

  /**
   * Sets the y co-ordinate of this position.
   *
   * @param otherY the value that the y-coordinate is to be set to.
   */
  void setY(double otherY);
}
