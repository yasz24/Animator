package cs3500.animator.view.visitors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IVisitableShape;

/**
 * {@code SwingViewVisitor} visits subclasses of the interface {@code IShape}, using polymorphism
 * to draw them to a given {@link Graphics2D} object. Instantiate with a {@code Graphics2D}, then
 * feed shapes to the {@code visit()} method to use.
 */
public class SwingViewVisitor extends AbstVisitor<Graphics2D> {

  /**
   * Stores a reference to the Graphics2D used in drawing.
   */
  private final Graphics2D g;

  /**
   * Creates a new SwingViewVisitor with the given graphics output.
   *
   * @param graphics graphics object to draw shapes to.
   */
  public SwingViewVisitor(Graphics2D graphics) {
    this.g = graphics;
  }

  @Override
  public void visit(IVisitableShape shape) {
    shape.accept(this);
  }

  @Override
  public void visit(IEllipse ellipse) {
    this.setColor(ellipse.getColor());
    AffineTransform af = g.getTransform();
    g.rotate(Math.toRadians(ellipse.getAngle()),
            ellipse.getPosition().getX(),
            ellipse.getPosition().getY());
    g.fillOval((int)(ellipse.getPosition().getX() - (0.5 * ellipse.getRadiusX())),
            (int)(ellipse.getPosition().getY() - (0.5 * ellipse.getRadiusY())),
            (int)ellipse.getRadiusX(), (int)ellipse.getRadiusY());
    g.setTransform(af);
  }

  @Override
  public void visit(IRectangle rect) {
    this.setColor(rect.getColor());
    AffineTransform af = g.getTransform();
    g.rotate(Math.toRadians(rect.getAngle()),
            rect.getPosition().getX() + (0.5 * rect.getWidth()),
            rect.getPosition().getY() + (0.5 * rect.getHeight()));
    g.fillRect((int)rect.getPosition().getX(), (int)rect.getPosition().getY(),
            (int)rect.getWidth(), (int)rect.getHeight());
    g.setTransform(af);
  }

  /**
   * Converts a {@link cs3500.animator.model.color.Color} to a {@link java.awt.Color}, and sets
   * the color of the graphics2d to that color.
   *
   * @param col Color to set graphics2d.
   */
  private void setColor(IColor col) {
    g.setColor(new Color(col.getR(), col.getG(), col.getB(), col.getA()));
  }

  @Override
  public Graphics2D getResult() {
    return this.g;
  }
}
