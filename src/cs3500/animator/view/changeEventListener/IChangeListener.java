package cs3500.animator.view.changeEventListener;

import java.util.Map;

import javax.swing.event.ChangeListener;

/**
 * Represents an object that listens for a change event.
 */
public interface IChangeListener extends ChangeListener {
  /**
   * Sets the associated actions when the slider is changed.
   *
   * @param changeEventMap map of event names to their actions
   */
  void setChangeEventMap(Map<String, Runnable> changeEventMap);
}
