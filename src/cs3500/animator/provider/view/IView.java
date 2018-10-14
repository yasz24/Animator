package cs3500.animator.provider.view;

import java.awt.event.ActionListener;

import cs3500.animator.provider.model.EasyAnimatorOperations;

/**
 * Represents the cs3500.animator.provider.view for the animator.
 *
 * @param <T> The Return type for render.
 * @param <U> The Type of Shape used.
 * @param <V> The Type of Animation used.
 */
public interface IView<T, U, V> {
  /**
   * Renders the model according to the type T.
   *
   * @param model model to be rendered
   * @return Something of type T.
   */
  T render(EasyAnimatorOperations<U, V> model);

  /**
   * Converts from ticks to seconds.
   *
   * @param tick Ticks to be converted.
   * @return the seconds that were converted from ticks.
   */
  double convertTicks(int tick);

  /**
   * Quits the animation.
   */
  void quit();

  /**
   * Pauses or unpauses the animation based on the cs3500.animator.provider.view.
   *
   * @param toggle Determines whether to pause or unpause.
   */
  void togglePause(boolean toggle);

  /**
   * Toggles whether the cs3500.animator.provider.view should loop.
   *
   * @param toggle determines toggle on/off
   */
  void toggleLoop(String toggle);

  /**
   * Restarts the animation.
   */
  void reset();

  /**
   * Adds the listener for handling key input.
   *
   * @param k the key
   */
  void addListener(ActionListener k);

  /**
   * Returns the current shape.
   */
  int currentShape();

  /**
   * Changes the current shape.
   */
  void increment();
}
