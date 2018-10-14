package cs3500.animator.view.buttonlistener;

import java.awt.event.ActionEvent;
import java.util.Map;

/**
 * Implementation of {@link IButtonListener}, adds the actual map of button names to their
 * actions. When actionPerformed() is called, the given action will (hopefully) map.
 */
public class ButtonListener implements IButtonListener {
  // Map of button names to their actions.
  private Map<String,Runnable> buttonClickedActions;

  @Override
  public void setButtonClickedActionMap(Map<String,Runnable> map) {
    buttonClickedActions = map;
  }

  @Override
  public void actionPerformed(ActionEvent e) throws IllegalArgumentException {
    if (buttonClickedActions.containsKey(e.getActionCommand())) {
      buttonClickedActions.get(e.getActionCommand()).run();
    } else {
      throw new IllegalArgumentException("Given a nonexistent ActionEvent");
    }
  }
}
