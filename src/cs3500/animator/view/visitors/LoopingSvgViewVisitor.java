package cs3500.animator.view.visitors;

import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.view.xml.ITag;
import cs3500.animator.view.xml.Tag;

/**
 * Extension of the {@link SvgViewVisitor}, adding looping functionality.
 * Looping functionality is done by adding a single "dummy" shape and animation that loops,
 * and basing all other shapes' times off of the aforementioned "dummy" shape.
 * Animations are also added to reset each shape to its previous position at the end of the
 * animation.
 */
public class LoopingSvgViewVisitor extends SvgViewVisitor {

  /**
   * Creates a new {@code SvgViewVisitor} with the given ticks per second.
   *
   * @param tps ticks per second, used to convert ticks to seconds.
   */
  public LoopingSvgViewVisitor(int tps) {
    super(tps);
  }


  /**
   * First calls super.visit(), then adds reset animations to each {@link IEllipse}.
   *
   * @param ellipse Ellipse to create svg tag for.
   */
  @Override
  public void visit(IEllipse ellipse) {
    super.visit(ellipse);
    // Adding refs to looping dummy shape
    String posX = this.toVal(ellipse.getPosition().getX());
    String posY = this.toVal(ellipse.getPosition().getY());
    String radX = this.toVal(ellipse.getRadiusX());
    String radY = this.toVal(ellipse.getRadiusY());
    String fill = this.colorToString(ellipse.getColor());

    ITag initialX = new Tag();
    this.addAnimsTo(initialX, 0, 1, posX, posX);
    initialX.addAttribute("attributeName", "cx");

    ITag initialY = new Tag();
    this.addAnimsTo(initialY, 0, 1, posY, posY);
    initialY.addAttribute("attributeName", "cy");

    ITag initialRadX = new Tag();
    this.addAnimsTo(initialRadX, 0, 1, radX, radX);
    initialRadX.addAttribute("attributeName", "rx");

    ITag initialRadY = new Tag();
    this.addAnimsTo(initialRadY, 0, 1, radY, radY);
    initialRadY.addAttribute("attributeName", "ry");

    ITag initialCol = new Tag();
    this.addAnimsTo(initialCol, 0, 1, fill, fill);
    initialCol.addAttribute("attributeName", "fill");

    ITag initialVisib = new Tag();
    this.addAnimsTo(initialVisib, 0, 1, "hidden", "hidden");
    initialVisib.addAttribute("attributeName", "visibility");

    ITag initialRot = new Tag();
    this.addAnimsTo(initialRot, 0, 1,
            "0 " + posX + " " + posY,
            "0 " + posX + " " + posY);
    initialRot.setName("animateTransform");
    initialRot.addAttribute("attributeName", "transform");
    initialRot.addAttribute("type", "rotate");

    this.base.addSubTag(initialX);
    this.base.addSubTag(initialY);
    this.base.addSubTag(initialRadX);
    this.base.addSubTag(initialRadY);
    this.base.addSubTag(initialCol);
    this.base.addSubTag(initialVisib);
    this.base.addSubTag(initialRot);
  }

  /**
   * First calls super.visit(), then adds reset animations to each {@link IRectangle}.
   *
   * @param rect IRectangle to create svg tag for
   */
  @Override
  public void visit(IRectangle rect) {
    super.visit(rect);
    // Adding refs to looping dummy shape
    String posX = Double.toString(rect.getPosition().getX());
    String posY = Double.toString(rect.getPosition().getY());
    String width = Double.toString(rect.getWidth());
    String height = Double.toString(rect.getHeight());
    String fill = this.colorToString(rect.getColor());

    ITag initialX = new Tag();
    this.addAnimsTo(initialX, 0, 1, posX, posX);
    initialX.addAttribute("attributeName", "x");

    ITag initialY = new Tag();
    this.addAnimsTo(initialY, 0, 1, posY, posY);
    initialY.addAttribute("attributeName", "y");

    ITag initialWid = new Tag();
    this.addAnimsTo(initialWid, 0, 1, width, width);
    initialWid.addAttribute("attributeName", "width");

    ITag initialHei = new Tag();
    this.addAnimsTo(initialHei, 0, 1, height, height);
    initialHei.addAttribute("attributeName", "height");

    ITag initialCol = new Tag();
    this.addAnimsTo(initialCol, 0, 1, fill, fill);
    initialCol.addAttribute("attributeName", "fill");

    ITag initialVisib = new Tag();
    this.addAnimsTo(initialVisib, 0, 1, "hidden", "hidden");
    initialVisib.addAttribute("attributeName", "visibility");

    ITag initialRot = new Tag();
    this.addAnimsTo(initialRot, 0, 1,
            "0 " + posX + " " + posY,
            "0 " + posX + " " + posY);
    initialRot.setName("animateTransform");
    initialRot.addAttribute("attributeName", "transform");
    initialRot.addAttribute("type", "rotate");

    this.base.addSubTag(initialX);
    this.base.addSubTag(initialY);
    this.base.addSubTag(initialWid);
    this.base.addSubTag(initialHei);
    this.base.addSubTag(initialCol);
    this.base.addSubTag(initialVisib);
    this.base.addSubTag(initialRot);
  }

  /**
   * Helps initialize the given animate tag. An animate tag is used to animate shapes, and its
   * name is always "animate".
   * Adds relative timing to the base shape.
   *
   * @param anim      Tag to add features to
   * @param startTime Time the animation starts, in ticks
   * @param finVal    Time the animation runs for, in ticks
   * @param startVal  Starting value of the animation, as a string
   * @param finVal    Ending value of the animation, as a string
   */
  @Override
  protected void addAnimsTo(ITag anim, long startTime, long endTime,
                            String startVal, String finVal) {
    anim.setName("animate");
    anim.addAttribute("attributeType", "xml");
    anim.addAttribute("begin",
            "__base__.begin+" + this.toMillis(startTime) + "ms");
    anim.addAttribute("dur",
            this.toMillis(endTime - startTime) + "ms");
    anim.addAttribute("from", startVal);
    anim.addAttribute("to", finVal);
    anim.addAttribute("fill", "freeze");
  }
}
