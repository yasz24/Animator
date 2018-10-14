package cs3500.animator.provider.model;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * This interface represents the operations the comprise a IColorChange. A color change for IAShape's
 * changes the color of a shape from one color to another.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IColorChange {

  /**
   * Returns the duration of the event.
   *
   * @return duration of the color change
   */
  int duration();

  /**
   * Returns the new color for the colorchange.
   *
   * @return the new color
   */
  IMyColor getNewColor();
}
