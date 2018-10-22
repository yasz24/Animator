package cs3500.animator.provider.controller;

/**
 * Interface that defines common methods that a controller must implement to handle user input and
 * output of the animator.
 *
 * @param <T> The cs3500.animator.cs3500.animator.provider.view of the animator
 * @param <U> The animator model.
 */
public interface AnimatorControllerInterface<T, U> {

  /**
   * Starts the animator.
   */
  void useAnimator();

  /**
   * Updates the animator based on user input.
   *
   * @param s The key event
   */
  void update(String s);

}
