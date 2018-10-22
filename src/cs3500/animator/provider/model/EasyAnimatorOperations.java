package cs3500.animator.provider.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The provider's model interface that represents the operations that comprise an animation.
 * @param <T> The interface for Shapes. Providers use IAShapes.
 * @param <U> The interface for Animations. Providers use IAAnimations.
 */
public interface EasyAnimatorOperations<T, U> {
  /**
   * Returns the list of shapes in the model.
   *
   * @return list of shapes in the model
   */
  List<T> getShapes();

  /**
   * Returns the list of animations in the model.
   *
   * @return list of animations in the model.
   */
  List<U> getAnimations();

  /**
   * Returns whether tha animation is started or not.
   *
   * @return Is the animation started?
   */
  boolean animationStarted();

  /**
   * Calculates the current time frame.
   *
   * @return an integer representation of the current time frame of the implementation of the
   *         animation.
   */
  int currentTick();

  /**
   * Updates the current time to move to the next frame.
   */
  void updateTick(double x);

  /**
   * Starts the animator by setting hasAnimatorStarted to true.
   */
  void startAnimator();

  /**
   * Adds a shape to the animation.
   *
   * @param shape Shape to be added
   */
  void addShape(T shape);

  /**
   * Removes the given shape from the animation.
   *
   * @param shape Shape to be removed
   * @throws IllegalArgumentException if shape is not part of the animation
   */
  void removeShape(T shape);

  /**
   * Adds an animation to the animation.
   *
   * @param anim Animation to be added
   */
  void addAnimation(U anim);

  /**
   * Removes an animation from the animation.
   *
   * @param anim animation to be removed
   * @throws IllegalArgumentException if animation is not part of the animation
   */
  void removeAnimation(U anim);

  /**
   * Gets the animation state for the current animation.
   *
   * @return Animation State
   */
  String getAnimatorState();


  /**
   * Returns the arraylist of updatedshapes.
   *
   * @return the arraylist of updatedshapes
   */
  ArrayList<IAShape> getUpdatedShapes();

  /**
   * Returns the total ticks needed for the animation.
   *
   * @return ticks needed
   */
  int getTotal();

  /**
   * Removes the shape with name name from the shapes to animate.
   * @param name Shape to be removed
   */
  void deAnimate(String name);

  /**
   * Resets the cs3500.animator.provider.model to original settings.
   */
  void reset();

  /**
   * Getter method for the map saying which shapes to animate.
   * @return map of which shapes to animate.
   */
  Map<T, Boolean> getMap();

  /**
   * Returns when the last shape disappears.
   * @return when the last shape disappears.
   */
  int getEndTick();

  /**
   * Turns the shape off.
   */
  void toggleShape(int i);
}
