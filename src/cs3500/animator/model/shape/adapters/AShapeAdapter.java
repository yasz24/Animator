package cs3500.animator.model.shape.adapters;

import java.awt.Graphics;
import java.awt.Graphics2D;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.IColor;
import cs3500.animator.model.color.adapters.ProvAdaptColor;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.provider.model.IMyColor;
import cs3500.animator.provider.model.IPosn;
import cs3500.animator.view.visitors.SwingViewVisitor;

/**
 * This is an adapters class that tries to adapt our IVisitableActor objects to their analog in our
 * providers code, namely objects of type IAShape.
 */
public class AShapeAdapter implements IAShape {

  /**
   * The actor that we're wrapping.
   */
  protected IVisitableActor actor;

  /**
   * Constructor for this AShapeAdapter.
   *
   * @param actor the IVisitableActor that that needs to be adapted.
   */
  public AShapeAdapter(IVisitableActor actor) {
    this.actor = actor.getVisitableCopy();
  }

  @Override
  public int starts() {
    return (int) this.actor.getAppearTime();
  }

  @Override
  public int ends() {
    return (int) this.actor.getDisappearTime();
  }

  @Override
  public String getName() {
    return this.actor.getName();
  }

  @Override
  public IPosn getPosition() {
    return new ProvPosnAdapter(actor.getShape().getPosition());
  }

  @Override
  public IMyColor getColor() {
    IColor col = this.actor.getShape().getColor();
    return new ProvAdaptColor(col);
  }

  @Override
  public void updatePosn(double a, double b) {
    throw new UnsupportedOperationException("Does not support this kind of updating");
  }

  @Override
  public void updateScale(double a, double b) {
    throw new UnsupportedOperationException("Does not support this kind of updating");
  }

  @Override
  public void updateColor(double r, double g, double b) {
    throw new UnsupportedOperationException("Does not support this kind of updating");
  }

  @Override
  public void updateDrawing(Graphics g) {
    this.actor.getShape().accept(new SwingViewVisitor((Graphics2D) g));
  }
}
