package cs3500.animator.provider.model;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * <p>Represents a Scaling event.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IScale extends IAAnimation {

  /**
   * Returns the scaling coefficient in the x-direction.
   *
   * @return a double representing the scaling in the x-direction.
   */
  double xParam();

  /**
   * Returns the scaling coefficient in the y-direction.
   *
   * @return a double representing the scaling in the y-direction.
   */
  double yParam();
}
