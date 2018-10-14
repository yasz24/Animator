package cs3500.animator.view;

import javax.swing.JSlider;

import cs3500.animator.view.changeEventListener.IChangeListener;

/**
 * Represents a view with a Java Swing slider that allows the user to change the current tick.
 * The slider is draggable. If the animation is paused while the slider is being used,
 * the animation remains paused. If the animation is running while the slider is being used,
 * the animation remains running.
 */
public interface ScrubbableView extends HybridView {

  /**
   * Returns the slider tha allows the user to change the current time.
   *
   * @return the JSlider object that allows scrubbing
   */
  JSlider getSlider();

  /**
   * Allows the slider to know when it is being dragged.
   *
   * @param listener the IChangeListener that lets the Slider listen to Change Events.
   */
  void addChangeListener(IChangeListener listener);
}
