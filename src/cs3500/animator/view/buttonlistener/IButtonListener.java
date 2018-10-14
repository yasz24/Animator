package cs3500.animator.view.buttonlistener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

/**
 * Represents an object that maps names of actions to actions themselves (e.g. functions). This
 * allows us to generalize button input and have many buttons/add more buttons without changing
 * much code.
 */
public interface IButtonListener extends ActionListener {

  /**
   * Assigns the map of the button names to their corresponding actions.
   * @param map the map passed in.
   */
  void setButtonClickedActionMap(Map<String,Runnable> map);

  /**
   * This method is inherited from the ActionListener interface. Its in here to
   * make sure that a user of this interface overrides the method in an implementation.
   *
   * @throws IllegalArgumentException if given an {@code ActionEvent} not in the action map.
   */
  @Override
  void actionPerformed(ActionEvent e) throws IllegalArgumentException;
}
