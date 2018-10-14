package cs3500.animator.controller.adapters;

import java.awt.event.ActionEvent;
import java.io.IOException;

import cs3500.animator.EasyAnimator;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.provider.view.IView;

/**
 * Imitates the providers controller, so that we can use ours internally. Inherits from the
 * provider controller's interface. Outputs to an appendable.
 */
public class ProvControllerTextualImitate implements IAnimationController<IAnimatorModel> {

  // View to control
  private IView<String, IAShape, IAAnimation> theirView;

  // Model to control
  private EasyAnimatorOperations<IAShape, IAAnimation> theirModel;

  // Appendable to output to
  private Appendable out;

  /**
   * Creates a new provider controller imitator with given arguments.
   *
   * @param theirView the view that will be used
   * @param theirModel the provider model that will be used
   * @param out appendable to output to if any
   */
  public ProvControllerTextualImitate(IView<String, IAShape, IAAnimation> theirView,
                                      EasyAnimatorOperations<IAShape, IAAnimation> theirModel,
                                      Appendable out) {
    this.theirView = theirView;
    this.theirModel = theirModel;
    this.out = out;
  }

  @Override
  public void start() {
    try {
      this.out.append(this.theirView.render(this.theirModel));
    } catch (IOException e) {
      EasyAnimator.inputError("manager", "Failed to create input manager");
    }
  }

  @Override
  public IAnimatorModel getModel() {
    throw new UnsupportedOperationException("getModel() not supported by provider");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // This does nothing
  }
}
