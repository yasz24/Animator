package cs3500.animator.provider.view;

import java.awt.event.ActionListener;

import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;

/**
 * Represents a hybrid cs3500.animator.provider.view that allows the user to modify the
 * animation visually and render a subset of shapes and export that animation to an svg.
 */
public class HybridView implements IView<String, IAShape, IAAnimation> {

  private int tickRate;
  VisualView visual;
  SvgAnimationView svg;

  /**
   * Constructs an instance of the hybrid cs3500.animator.provider.view.
   * @param tickRate the tick rate the animation is to run at
   */
  public HybridView(int tickRate) {

    this.tickRate = tickRate;
    this.visual = new VisualView(tickRate);
    this.svg = new SvgAnimationView(tickRate);

  }

  @Override
  public String render(EasyAnimatorOperations<IAShape, IAAnimation> model) {

    this.visual.render(model);
    return svg.render(model);

  }

  @Override
  public double convertTicks(int tick) {
    return tick / this.tickRate;
  }

  @Override
  public void quit() {

    this.visual.quit();

  }

  @Override
  public void togglePause(boolean toggle) {

    this.visual.togglePause(toggle);

  }

  @Override
  public void toggleLoop(String toggle) {

    this.visual.toggleLoop(toggle);

  }

  @Override
  public void reset() {

    this.visual.reset();

  }

  @Override
  public void addListener(ActionListener k) {
    this.visual.addListener(k);
  }

  @Override
  public int currentShape() {
    return this.visual.currentShape();
  }

  @Override
  public void increment() {

    this.visual.increment();

  }
}
