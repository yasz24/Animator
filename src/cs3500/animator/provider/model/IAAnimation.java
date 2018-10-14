package cs3500.animator.provider.model;

import java.util.ArrayList;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * This interface represents the operations the comprise an Animation. An animation is capable of
 * changing certain attributes of a shape like changing its color, scaling its size or moving the
 * shape to a new location.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IAAnimation {

  /**
   * Getter for affected.
   *
   * @return affected
   */
  IAShape getAffected();

  /**
   * Checks to see if this animation is compatible with the animations in arr.
   * @param arr List of animations
   * @return Whether or not this animation can be used
   */
  boolean validAnim(ArrayList<IAAnimation> arr);


  int getStart();

  /**
   * Returns the duration of the animation.
   *
   * @return the difference between the start and the end ticks
   */
  int duration();

  /**
   * Returns the start time of the animation.
   *
   * @return the start
   */
  int getEnd();

  /**
   * Updates the toBeUpdated shape to have the correct new values.
   *
   * @param shape original shape
   * @param ticks current ticks
   * @param toBeUpdated shape to be updated
   */
  void update(IAShape shape, int ticks, IAShape toBeUpdated);


  //MOVE METHODS
  /**
   * Gets the IPosn end location of this animation. Relevant only to IMove animations.
   *
   * @return endLocation of this animation as an IPosn.
   */
  default IPosn endLocation() {
    throw new UnsupportedOperationException("undefined method");
  }
  //END: MOVE METHODS


  //SCALE METHODS
  /**
   * Gets the x-axis scaling factor for the shape that this animation is applied on.
   *
   * @return a double representing the x-axis scale factor.
   */
  default double xParam() {
    throw new UnsupportedOperationException("undefined method");
  }

  /**
   * Gets the y-axis scaling factor for the shape that this animation is applied on.
   *
   * @return a double representing the y-axis scale factor.
   */
  default double yParam() {
    throw new UnsupportedOperationException("undefined method");
  }
  //END: SCALE METHODS


  //COLOR CHANGE METHODS

  /**
   * Gets the new IMyColor that the color of the shape, that this animation is applied on, changes
   * to.
   *
   * @return IMyColor representing the new Color.
   */
  default IMyColor getNewColor() {
    throw new UnsupportedOperationException("undefined method");
  }
  //END: COLOR CHANGE METHODS

}
