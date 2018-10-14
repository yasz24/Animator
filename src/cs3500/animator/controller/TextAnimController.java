package cs3500.animator.controller;

import java.awt.event.ActionEvent;
import java.io.IOException;

import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.IView;

/**
 * This controller facilitates the text view or the svg view to be output in the file specified
 * in the command-line arguments. The controller passes information from the model to view where it
 * is rendered in the appropriate manner. This controller should be created with a
 * {@code AppendableView} which is a subset of {@code IView} for the controller to work as intended.
 */
public class TextAnimController implements IAnimationController<IAnimatorModel> {
  /**
   * Model held in state.
   */
  private IAnimatorModel model;

  /**
   * View held in state.
   */
  private IView view;

  /**
   * The constructor for the Graphical Controller.
   *
   * @param model the IAnimatorModel that stores all the information about the animation.
   * @param view the AppendableView that outputs the text/svg output.
   */
  public TextAnimController(IAnimatorModel model, IView view) {
    if (model == null) {
      throw new IllegalArgumentException("Controller requires a non-null model");
    }
    this.model = model;
    if (view == null) {
      throw new IllegalArgumentException("Controller requires a non-null view");
    }
    this.view = view;

    view.setActors(model.getActorCopies());
  }

  @Override
  public void start() {
    try {
      view.outputActors();
    } catch (IOException e) {
      // Svg could not output, so we exit
      System.exit(-1);
    }
  }

  @Override
  public IAnimatorModel getModel() {
    return this.model;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    throw new UnsupportedOperationException("No actionperformed here.");
  }
}
