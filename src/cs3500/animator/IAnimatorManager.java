package cs3500.animator;

/**
 * Manages a collection of a model, view, and controller to enable them to be
 * of different types. For us, we used two separate implementations of this interface
 * to manage our views vs our provider's views.
 */
public interface IAnimatorManager {

  /**
   * Starts the held controller.
   */
  void start();
}
