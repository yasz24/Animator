package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Timer;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IView;

/**
 * This controller facilitates the Graphical view to be displayed using information form the model.
 * It implements the ActionListener interface in addition to the IController interface. This ensures
 * that the action event produced by the timer is listened to and the animations are displayed
 * every tick. The view used to construct a controller her must be a {@code GraphicalView} which
 * is a subset of {@code IView} for the controller to work as intended.
 */
public class GraphicsAnimController implements IAnimationController<IAnimatorModel> {

  /**
   * Model to operate upon.
   */
  protected final IAnimatorModel model;

  /**
   * View to operate upon.
   */
  protected final IView view;

  /**
   * Used to run model at specified ticks per second.
   */
  protected final Timer timer;

  /**
   * Current tick.
   */
  protected int tick;

  /**
   * Ticks per second to run the model at.
   */
  protected int tps;

  /**
   * The constructor for the Graphical Controller.
   *
   * @param model the IAnimatorModel that stores all the information about the animation.
   * @param view  the GraphicalView that takes renders the animation
   * @param tps   the number of animation ticks in a second.
   */
  public GraphicsAnimController(IAnimatorModel model, IView view, int tps) {
    if (model == null) {
      throw new IllegalArgumentException("Controller requires a non-null model");
    }
    this.model = model;
    if (view == null) {
      throw new IllegalArgumentException("Controller requires a non-null view");
    }
    this.view = view;
    if (tps > 0) {
      this.tps = tps;
      this.timer = new Timer(1000 / this.tps, this);
    } else {
      throw new IllegalArgumentException("Ticks per second must be a positive integer.");
    }
    tick = 1;
    // Give the intended drawn actors to the view
    view.setActors(model.getActorCopies());
    model.updateActors(tick);
  }

  @Override
  public void start() {
    timer.start();
    this.view.makeVisible();
    // this.view.resetFocus();
  }

  @Override
  public IAnimatorModel getModel() {
    return this.model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    tick += 1;

    this.model.updateActors(tick);

    try {
      view.outputActors();
    } catch (IOException except) {
      except.printStackTrace();
    }
  }
}
