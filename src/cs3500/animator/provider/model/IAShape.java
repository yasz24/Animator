package cs3500.animator.provider.model;

import java.awt.Graphics;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * This interface represents the operations the comprise a Shape. A Shape has some attributes like a
 * Name, Position, Color, as well as AppearTime and DisappearTime's.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IAShape {

  /**
   * Gets the appear time of this IAShape.
   *
   * @return an int representing the appear time.
   */
  int starts();

  /**
   * Gets the disappear time of this IAShape.
   *
   * @return an int representing the disappear time.
   */
  int ends();

  /**
   * Gets the name of this IAShape.
   *
   * @return a string representing the name of this IAShape.
   */
  String getName();

  /**
   * Gets the positon of this IAShape.
   *
   * @return an IPosn representing the position of this IAShape.
   */
  IPosn getPosition();

  /**
   * Gets the color of this IAShape.
   *
   * @return an IMyColor representing the color of this IAShape.
   */
  IMyColor getColor();

  @Override
  boolean equals(Object other);

  @Override
  int hashCode();

  /**
   * Updates where the shape is.
   *
   * @param a new x value
   * @param b new y value
   */
  void updatePosn(double a, double b);

  /**
   * Updates the size of the shape.
   *
   * @param a new width
   * @param b new height
   */
  void updateScale(double a, double b);

  /**
   * Updates the color of the shape.
   *
   * @param r red
   * @param g green
   * @param b blue
   */
  void updateColor(double r, double g, double b);

  /**
   * Updates the displayed image.
   *
   * @param g graphics
   */
  void updateDrawing(Graphics g);
  //END: COMMON METHODS


  //RECTANGLE METHODS

  /**
   * Gets the lower-left corner of this IAShape. Only relevant to Rectangles.
   *
   * @return an IPosn representing the lower-left corner.
   */
  default IPosn getLLC() {
    throw new UnsupportedOperationException("undefined method");
  }

  /**
   * Gets the height of this IAShape. Only relevant to Rectangles.
   *
   * @return a double representing the height.
   */
  default double getHeight() {
    throw new UnsupportedOperationException("undefined method");
  }

  /**
   * Gets the width of this IAShape. Only relevant to Rectangles.
   *
   * @return a double representing the width.
   */
  default double getWidth() {
    throw new UnsupportedOperationException("undefined method");
  }
  //END: RECTANGLE METHODS


  //OVAL METHODS

  /**
   * Gets the center of this IAShape. Only relevant to Ovals.
   *
   * @return an IPosn representing the center.
   */
  default IPosn getCenter() {
    throw new UnsupportedOperationException("undefined method");
  }

  /**
   * Gets the x-radius of this IAShape. Only relevant to Ovals.
   *
   * @return a double representing the x-radius.
   */
  default double getyRadius() {
    throw new UnsupportedOperationException("undefined method");
  }

  /**
   * Gets the y-radius of this IAShape. Only relevant to Ovals.
   *
   * @return a double representing the y-radius.
   */
  default double getxRadius() {
    throw new UnsupportedOperationException("undefined method");
  }
  //END: OVAL METHODS
}
