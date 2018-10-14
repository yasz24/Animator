package cs3500.animator.view.visitors;

import cs3500.animator.model.action.IColorAction;
import cs3500.animator.model.action.IMoveAction;
import cs3500.animator.model.action.IRotateAction;
import cs3500.animator.model.action.IScaleAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IVisitableShape;

/**
 * Visits the {@link IVisitableActor}, {@link IVisitableShape}, and {@link IVisitableAction}
 * interfaces, using polymorphism to access their subclasses and render them to a textual
 * description.
 *
 * <p>An example description may look like:<br>
 *
 * <p>Shapes:<br>
 * Name: R<br>
 * Type: rectangle<br>
 * Min-corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)<br>
 * Appears at t=0.5s<br>
 * Disappears at t=50.0s<br>
 * <br>
 * Name: C<br>
 * Type: oval<br>
 * Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)<br>
 * Appears at t=3.0s<br>
 * Disappears at t=50.0s<br>
 *
 *  <p>Shape R moves from (200.0,200.0) to (300.0,300.0) from t=5.0s to t=25.0s<br>
 *  Shape C moves from (500.0,100.0) to (500.0,400.0) from t=10.0s to t=35.0s<br>
 *  Shape C changes color from (0.0,0.0,1.0) to (0.0,1.0,0.0) from t=25.0s to t=40.0s<br>
 *  Shape R scales from Width: 50.0, Height: 10.0 to Width: 25.0,
 *  Height: 30.0 from t=1.5s to t=3.0s<br>
 *  Shape R moves from (300.0,300.0) to (200.0,200.0) from t=35.0s to t=50.0s<br>
 */
public class TextViewVisitor extends AbstVisitor<String> {

  /**
   * Holds the current build up text.
   */
  private final StringBuilder sb;

  /**
   * Holds the ticks per second, used for conversion.
   */
  private final int tps;


  public TextViewVisitor(int tps) {
    this.sb = new StringBuilder();
    this.tps = tps;
  }

  @Override
  public void visit(IVisitableActor actor) {
    sb.append("Name: ").append(actor.getName()).append("\n");
    this.visit(actor.getShape());
    sb.append("Appears at t=")
            .append(this.fmt((double) (actor.getAppearTime()) / (double) tps)).append("s\n");

    sb.append("Disappears at t=")
            .append(this.fmt((double) (actor.getDisappearTime()) / (double) tps));

    sb.append("s\n\n");
  }

  @Override
  public void visit(IVisitableShape shape) {
    shape.accept(this);
  }

  @Override
  public void visit(IEllipse ellipse) {
    sb.append("Type: oval\nCenter: ").append(ellipse.getPosition().toString())
            .append(", X-Radius: ").append(this.fmt(ellipse.getRadiusX()))
            .append(", Y-Radius: ").append(this.fmt(ellipse.getRadiusY()))
            .append(", Color: ").append(ellipse.getColor().toString()).append("\n");
  }

  @Override
  public void visit(IRectangle rect) {
    sb.append("Type: rectangle\nLower-left corner: ").append(rect.getPosition().toString())
            .append(", Width: ").append(this.fmt(rect.getWidth()))
            .append(", Height: ").append(this.fmt(rect.getHeight()))
            .append(", Color: ").append(rect.getColor().toString()).append("\n");
  }

  @Override
  public void visit(IVisitableAction action) {
    action.accept(this);
  }

  @Override
  public void visit(IRotateAction action) {
    sb.append("rotates from ").append(action.getStartAngle()).append("deg")
            .append(" to ").append(action.getEndAngle()).append("deg from t=")
            .append(this.fmt(((double) action.getStartTime()) / (double) this.tps))
            .append("s to t=")
            .append(this.fmt( ((double) action.getEndTime()) / (double) this.tps))
            .append("s\n");

  }

  @Override
  public void visit(IColorAction colorAction) {
    sb.append("changes color from ").append(colorAction.getStartColor().toString())
            .append(" to ").append(colorAction.getEndColor().toString()).append(" from t=")
            .append(this.fmt((double) (colorAction.getStartTime()) / (double) this.tps))
            .append("s to t=")
            .append(this.fmt((double) (colorAction.getEndTime()) / (double) this.tps))
            .append("s\n");
  }

  @Override
  public void visit(IMoveAction moveAction) {
    sb.append("moves from ").append(moveAction.getStartPosition().toString())
            .append(" to ").append(moveAction.getEndPosition().toString()).append(" from t=")
            .append(this.fmt((double) (moveAction.getStartTime()) / (double) this.tps))
            .append("s to t=")
            .append(this.fmt((double) (moveAction.getEndTime()) / (double) this.tps))
            .append("s\n");
  }

  @Override
  public void visit(IScaleAction scaleAction) {
    sb.append("scales from Width: ").append(scaleAction.getStartWidth())
            .append(", Height: ").append(scaleAction.getStartHeight())
            .append(" to Width: ").append(scaleAction.getEndWidth()).append(", Height: ")
            .append(scaleAction.getEndHeight()).append(" from t=")
            .append(this.fmt((double) (scaleAction.getStartTime()) / (double) this.tps))
            .append("s to t=")
            .append(this.fmt((double) (scaleAction.getEndTime()) / (double) this.tps))
            .append("s\n");
  }

  @Override
  public String getResult() {
    this.sb.substring(0, this.sb.length() - 1);
    return this.sb.toString();
  }

  /**
   * Formats the given double to a string with one decimal place.
   *
   * @param s double to format
   * @return resulting string
   */
  private String fmt(Double s) {
    return String.format("%.1f", s);
  }
}
