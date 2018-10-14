package cs3500.animator.view.changeEventListener;

import java.util.Map;

import javax.swing.event.ChangeEvent;

/**
 * Listens for the slider being changed.
 */
public class ChangeEventListener implements IChangeListener {
  Map<String, Runnable> changeEventMap;


  @Override
  public void setChangeEventMap(Map<String, Runnable> changeEventMap) {
    this.changeEventMap = changeEventMap;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    if (changeEventMap.containsKey("slider")) {
      changeEventMap.get("slider").run();
    } else {
      throw new IllegalArgumentException("Slider isn't configured");
    }
  }
}
