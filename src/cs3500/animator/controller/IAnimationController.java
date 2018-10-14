package cs3500.animator.controller;

import java.awt.event.ActionListener;

import cs3500.animator.view.IView;

/**
 * Controls an {@link IView} and {@code T} type model. After creating an instance of the
 * controller, call start() to give control to the controller, who will then run the model
 * and view until the animation completes.
 *
 * @param <T> Type of model to control
 */
public interface IAnimationController<T> extends ActionListener {

  /**
   * Start the program, i.e. give control to the controller.
   */
  void start();

  /**
   * Returns the model.
   *
   * @return the model
   */
  T getModel();
}
