package cs3500.animator.view.keyboardlistener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Implementation of {@link IKeyBoardListener}, adds the actual map of key names to the effect
 * they're supposed to have. When a key is pressed, the keyPressed()method is called,
 * when a key is typed, the keyTyped() method is called, when a key is released, the keyReleased()
 * method is called and (hopefully) the key event will map to the effect they are supposed to
 * produce.
 */
public interface IKeyBoardListener extends KeyListener {
  /**
   * Assigns the map for the typed key events to its corresponding instance variable.
   * @param map the map passed in.
   */
  void setTypedKeyMap(Map<Character,Runnable> map);

  /**
   * Assigns the map for the pressed key events to its corresponding instance variable.
   * @param map the map passed in.
   */
  void setPressedKeyMap(Map<Integer,Runnable> map);

  /**
   * Assigns the map for the released key events to its corresponding instance variable.
   * @param map the map passed in.
   */
  void setReleasedKeyMap(Map<Integer,Runnable> map);

  /**
   * This is called when the view detects that a key has been typed.
   * Find if anything has been mapped to this key character and if so, execute it.
   * The method is put in this interface to ensure the user override the keyTyped method.
   * @param e the key hit by input by the user.
   */

  @Override
  void keyTyped(KeyEvent e);

  /**
   * This is called when the view detects that a key has been pressed.
   * Find if anything has been mapped to this key code and if so, execute it.
   * The method is put in this interface to ensure the user override the keyTyped method.
   * @param e the key hit by the user.
   */
  @Override
  void keyPressed(KeyEvent e);

  /**
   * This is called when the view detects that a key has been released.
   * Find if anything has been mapped to this key code and if so, execute it
   * @param e the key hit by the user.
   */
  @Override
  void keyReleased(KeyEvent e);
}
