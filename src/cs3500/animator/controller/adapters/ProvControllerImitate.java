package cs3500.animator.controller.adapters;

import java.awt.event.ActionEvent;

import cs3500.animator.controller.IAnimationController;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.provider.view.IView;

/**
 * This controller acts as the mediator between our provider's IView and their
 * EasyAnimatorOperations model. This controller imitates the role that their controller might have
 * played in their code.
 */
public class ProvControllerImitate implements IAnimationController<IAnimatorModel> {

  // Their view
  private IView<Void, IAShape, IAAnimation> theirView;

  // An adapter for their model interface
  private EasyAnimatorOperations<IAShape, IAAnimation> theirModel;

  /**
   * The constructor for the provider's controller imitator.
   *
   * @param theirView  The provider's IView
   * @param theirModel The provider's EasyAnimatorOperations model.
   */
  public ProvControllerImitate(IView<Void, IAShape, IAAnimation> theirView,
                               EasyAnimatorOperations<IAShape, IAAnimation> theirModel) {

    this.theirView = theirView;
    this.theirModel = theirModel;
  }

  @Override
  public void start() {
    this.theirView.addListener(this);
    this.theirView.render(this.theirModel);
  }

  @Override
  public IAnimatorModel getModel() {
    throw new UnsupportedOperationException("getModel() not supported by provider");
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // hear no evil
    // see no evil
    // speak no evil
    // (this method does nothing)
  }
}
