package cs3500.animator.view.keyboardlistener;

import java.awt.event.KeyEvent;
import java.util.Map;

/**
 * This class implements the {@code IKeyBoardListener} interface that allows it to run the snippets
 * of code that are mapped to the appropriate key events, and in doing so, this key board listener
 * produces the desired effect in the animation. We use the "Space Bar" keyevent to
 * Start/Pause/Resume the animation. And we use the "Up-Arrow" and "Down-Arrow" keys to increase and
 * decrease the speed of the animation respectively.
 */
public class KeyBoardListener implements IKeyBoardListener {
  //Map of typed characters to their effects.
  private Map<Character,Runnable> typedKeyMap;
  //Map of pressed keys to their effects
  private Map<Integer,Runnable> pressedKeyMap;
  //Map of key releases to their effects.
  private Map<Integer,Runnable> releasedKeyMap;

  @Override
  public void setTypedKeyMap(Map<Character,Runnable> map) {
    this.typedKeyMap = map;
  }

  @Override
  public void setPressedKeyMap(Map<Integer,Runnable> map) {
    this.pressedKeyMap = map;
  }

  @Override
  public void setReleasedKeyMap(Map<Integer,Runnable> map) {
    this.releasedKeyMap = map;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (this.typedKeyMap.containsKey(e.getKeyChar())) {
      this.typedKeyMap.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.pressedKeyMap.containsKey(e.getKeyCode())) {
      this.pressedKeyMap.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (this.releasedKeyMap.containsKey(e.getKeyCode())) {
      this.releasedKeyMap.get(e.getKeyCode()).run();
    }
  }
}
