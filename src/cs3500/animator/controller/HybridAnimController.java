package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.action.IAction;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.IView;
import cs3500.animator.view.buttonlistener.ButtonListener;
import cs3500.animator.view.buttonlistener.IButtonListener;
import cs3500.animator.view.keyboardlistener.IKeyBoardListener;
import cs3500.animator.view.keyboardlistener.KeyBoardListener;

/**
 * A {@code HybridController} offers both visual animation functionality and the ability to output
 * to SVG. Additionally, one can deselect shapes, causing them to be hidden from the animation.
 * One can also set the animation to loop, and the outputted SVG to loop.
 * Finally, one can change the speed of the animation using the up-down arrow keys.
 *
 * <p>Change 04/21/18: Changed the access modifiers for instance variables from private to protect
 * to allow for use by extending classes
 */
public class HybridAnimController extends GraphicsAnimController {
  // Min/max ticks per second
  protected final int MIN_TPS = 5;
  protected final int MAX_TPS = 1000;

  /**
   * Time when the animation ends.
   */
  protected final long lastTick;

  /**
   * Flag to determine if the animation should be looped.
   */
  protected boolean looping;

  /**
   * The constructor for the Graphical Controller.
   *
   * @param model the IAnimatorModel that stores all the information about the animation.
   * @param view  the GraphicalView that takes renders the animation
   * @param tps   the number of animation ticks in a second.
   */
  public HybridAnimController(IAnimatorModel model, IView view, int tps) {
    super(model, view, tps);
    timer.setActionCommand("Timer");
    lastTick = this.getTimeLastAction();
    this.configureButtonListener();
    this.configureKeyBoardListener();
    looping = false;
  }

  /**
   * Creates and sets a keyboard listener for the view.
   * In effect it creates snippets of code as Runnable objects and that code is run each time a key
   * is typed, pressed or released. Code snippets are created only for those keys that mean anything
   * to the application.
   *
   * <p>Lastly we create our KeyboardListener object, set all its maps and then give it to
   * the view.
   */
  private void configureKeyBoardListener() {
    Map<Character, Runnable> keyTypes = new HashMap<>();
    Map<Integer, Runnable> keyPresses = new HashMap<>();
    Map<Integer, Runnable> keyReleases = new HashMap<>();


    //space button starts or stops the animation.
    keyPresses.put(KeyEvent.VK_SPACE, () -> {
      if (timer.isRunning()) {
        timer.stop();
      } else {
        timer.start();
      }
    });

    //hitting up arrow increases the tps by 10 units
    keyPresses.put(KeyEvent.VK_UP, () -> {
      if (this.tps < this.MAX_TPS) {
        this.tps += 5;
        this.timer.setDelay(1000 / this.tps);
      }
    });

    //hitting down arrow decreases the tps by 10 units
    keyPresses.put(KeyEvent.VK_DOWN, () -> {
      if (this.tps > this.MIN_TPS) {
        this.tps -= 5;
        this.timer.setDelay(1000 / this.tps);
      }
    });

    IKeyBoardListener kbd = new KeyBoardListener();
    kbd.setTypedKeyMap(keyTypes);
    kbd.setPressedKeyMap(keyPresses);
    kbd.setReleasedKeyMap(keyReleases);

    view.addKeyListener(kbd);
  }

  /**
   * This method stores code snippets that are to be run corresponding to specific button clicks.
   * To do this it uses a {@code Map<String, Runnable>}, the runnable being the aforementioned
   * code snippet. It gives this map to an {@link IButtonListener} and then passes that listener
   * to the view, so that the buttons respond to user clicks.
   */
  private void configureButtonListener() {
    Map<String, Runnable> buttonClickedMap = new HashMap<>();
    IButtonListener buttonListener = new ButtonListener();

    //configuring Rewind button
    buttonClickedMap.put("Rewind Button", () -> {
      this.timer.restart();
      timer.stop();
      this.tick = 1;
      this.model.updateActors(this.tick);
      try {
        this.view.outputActors();
      } catch (IOException e) {
        e.printStackTrace();
      }
      //set focus back to main frame so that keyboard events work
      this.view.resetFocus();
    });

    //configuring SVG button.
    buttonClickedMap.put("SVG Button", () -> {
      this.saveSvgPopup();
      //set focus back to main frame so that keyboard events work
      this.view.resetFocus();
    });

    //configuring looping checkbox
    buttonClickedMap.put("Looping", () -> {
      this.looping = !looping;
      //set focus back to main frame so that keyboard events work
      this.view.resetFocus();
    });

    buttonClickedMap.put("Select", () -> {
      timer.stop();
      this.view.showShapeSelectionPane();
      this.view.resetFocus();
    });

    buttonClickedMap.put("Instructions", () -> {
      this.instructionsPopup();
      this.view.resetFocus();
    });

    //configuring checkBoxes.
    Map<String, IVisitableActor> actors = this.model.getActorCopies();
    for (String key : actors.keySet()) {
      buttonClickedMap.put(key, () -> {
        actors.get(key).toggleVisibility();
        try {
          this.view.outputActors();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }

    buttonListener.setButtonClickedActionMap(buttonClickedMap);
    this.view.addActionListener(buttonListener);
  }


  @Override
  public void start() {
    this.view.makeVisible();
    this.view.resetFocus();
  }

  /**
   * Gets the very last tick when any action in this animation occurs.
   *
   * @return very last tick of the animation.
   */
  private long getTimeLastAction() {
    long maxEndTime = 0;
    for (String key : model.getActorCopies().keySet()) {
      IActor actor = model.getActorCopies().get(key);
      if (actor.getDisappearTime() > maxEndTime) {
        maxEndTime = actor.getDisappearTime();
      }

      // Store all the actor's actions for printing at bottom of file
      for (IAction action : actor.getActions()) {
        if (action.getEndTime() > maxEndTime) {
          maxEndTime = action.getEndTime();
        }
      }
    }
    return maxEndTime;
  }

  /**
   * Pops up a dialog containing instructions about key bindings.
   */
  private void instructionsPopup() {
    this.view.instructionsPopup();
  }

  /**
   * Configures the pop-up that shows when the svg button is hit.
   */
  private void saveSvgPopup() {
    this.view.saveSvgPopup(this.model.getActorCopies());
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String timerAction = e.getActionCommand();
    if ("Timer".equals(timerAction)) {
      this.tick += 1;
      this.model.updateActors(this.tick);
      if ((this.tick == this.lastTick) && this.looping) {
        this.tick = 1;
        this.timer.restart();
      }
      try {
        this.view.outputActors();
      } catch (IOException except) {
        except.printStackTrace();
      }
    }
  }
}
