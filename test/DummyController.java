import javax.swing.Timer;

import cs3500.animator.controller.HybridAnimController;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IView;

/**
 * A dummy controller that is used for testing purposes.
 */
public class DummyController extends HybridAnimController {
  /**
   * The constructor for the Graphical Controller.
   *
   * @param model the IAnimatorModel that stores all the information about the animation.
   * @param view  the GraphicalView that takes renders the animation
   * @param tps   the number of animation ticks in a second.
   */
  public DummyController(IAnimatorModel model, IView view, int tps) {
    super(model, view, tps);
  }

  /**
   * Gets the current tps for the animation.
   *
   * @return the tps.
   */
  public int getTps() {
    return this.tps;
  }

  /**
   * Gets the timer for the animation.
   *
   * @return the timer.
   */
  public Timer getTimer() {
    return this.timer;
  }

  /**
   * Stops the timer.
   */
  public void stopTimer() {
    if (timer.isRunning()) {
      timer.stop();
    } else {
      timer.start();
    }
  }
}
