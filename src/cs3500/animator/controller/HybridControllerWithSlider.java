package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IView;
import cs3500.animator.view.changeEventListener.ChangeEventListener;
import cs3500.animator.view.changeEventListener.IChangeListener;

/**
 * A {@code HybridControllerWithSlider} is essentially a {@code HybridANimController} offering
 * visual animation functionality, the ability to output
 * to SVG, selection of shapes, and now a new feature: the slider. This slider allows for users
 * to scrub the animation forwards or backwards at will.
 * One can also set the animation to loop, and the outputted SVG to loop.
 * Finally, one can change the speed of the animation using the up-down arrow keys.
 *
 * <p>Change 04/21/18: Created class
 */
public class HybridControllerWithSlider extends HybridAnimController {
  /**
   * The constructor for the HybridControllerWithSlider.
   *
   * @param model the IAnimatorModel that stores all the information about the animation.
   * @param view  the InteractiveViewWithSlider that renders the animation
   * @param tps   the number of animation ticks in a second.
   */
  public HybridControllerWithSlider(IAnimatorModel model, IView view, int tps) {
    super(model, view, tps);
    this.configureChangeListener();
  }

  /*
   * Configures the change listener so that the animator can respond to change events. Relevant to
   * the slider in this animator.
   */
  private void configureChangeListener() {
    Map<String, Runnable> changeEventMap = new HashMap<>();
    IChangeListener changeListener = new ChangeEventListener();

    changeEventMap.put("slider", () -> {
      this.tick = this.view.getSlider().getValue();
      this.model.updateActors(this.tick);
      try {
        this.view.outputActors();
      } catch (IOException except) {
        except.printStackTrace();
      }
      this.view.resetFocus();
    });
    changeListener.setChangeEventMap(changeEventMap);
    this.view.addChangeListener(changeListener);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String timerAction = e.getActionCommand();
    if ("Timer".equals(timerAction) && !this.view.getSlider().getValueIsAdjusting()) {
      super.actionPerformed(e);
    }
    this.view.getSlider().setValue(this.tick);
  }
}
