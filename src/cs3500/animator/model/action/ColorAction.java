package cs3500.animator.model.action;

import cs3500.animator.model.color.Color;
import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents the color change action that can be performed on shapes. Applied over time, and
 * linearly interpolates between a starting and ending color according to a given tick.
 */
public class ColorAction extends AbstractAction implements IColorAction {

  /**
   * Color that the applied-upon {@code IActor} will begin at.
   */
  private final IColor startColor;

  /**
   * Color that the applied-upon {@code IActor} will end at.
   */
  private final IColor endColor;

  /**
   * Creates a new {@code ColorAction} with the specified start and end times and colors.
   *
   * @param startTime  start time of the color change, in ticks
   * @param endTime    end time of the color change, in ticks
   * @param startColor color to transform from
   * @param endColor   color to transform to
   * @throws IllegalArgumentException if any of the arguments are null or if startTime is before
   *                                  endTime
   */
  public ColorAction(long startTime, long endTime, IColor startColor, IColor endColor) {
    //Calling the super constructor.
    super(startTime, endTime);

    //checking to see if start color is null.
    if (startColor == null) {
      throw new IllegalArgumentException("Given a null from color");
    }

    //checking to see if end color is null.
    if (endColor == null) {
      throw new IllegalArgumentException("Given a null to color");
    }

    this.startColor = startColor;
    this.endColor = endColor;
  }

  @Override
  public void transformShape(IVisitableShape shape, long tick) {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }
    // Interpolate all the r,g,b,a values
    float r = (float) linInterpolation(tick, startColor.getR(), endColor.getR());
    float g = (float) linInterpolation(tick, startColor.getG(), endColor.getG());
    float b = (float) linInterpolation(tick, startColor.getB(), endColor.getB());
    float a = (float) linInterpolation(tick, startColor.getA(), endColor.getA());

    shape.setColor(r, g, b, a);
  }

  @Override
  protected boolean compatibleColorTransformation(ColorAction other) {
    return !this.simultaneousWith(other);
  }

  @Override
  protected boolean compatibleAbstractAction(AbstractAction other) {
    return other.compatibleColorTransformation(this);
  }

  @Override
  public IAction getCopy() {
    return new ColorAction(this.startTime, this.endTime,
            this.startColor, this.endColor);
  }

  @Override
  public String toString() {
    return "Color change from " + this.startColor.toString() + " to " + this.endColor.toString();
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null ModelVisitor");
    }
    v.visit(this);
  }

  @Override
  public IColor getStartColor() {
    return new Color(this.startColor);
  }

  @Override
  public IColor getEndColor() {
    return new Color(this.endColor);
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new ColorAction(this.startTime, this.endTime,
            this.startColor, this.endColor);
  }
}
