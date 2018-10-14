package cs3500.animator.view.visitors;

import java.util.HashMap;
import java.util.Map;


import cs3500.animator.model.action.IAction;
import cs3500.animator.model.action.IColorAction;
import cs3500.animator.model.action.IMoveAction;
import cs3500.animator.model.action.IRotateAction;
import cs3500.animator.model.action.IScaleAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IShape;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.xml.Tag;
import cs3500.animator.view.xml.ITag;

/**
 * Visits the {@link IShape}, {@link IAction}, and {@link IVisitableActor} interfaces, using
 * polymorphism to string the data within their concrete classes, to create an SVG file. SVG files
 * are loosely based off XML.
 */
public class SvgViewVisitor extends AbstVisitor<ITag> {

  protected Map<String, String> valNames;

  /**
   * Holds the intermediate string that gets built over calls to visit().
   */
  protected Tag base;

  /**
   * Used for converting ticks to seconds.
   */
  protected final int tps;

  /**
   * Creates a new {@code SvgViewVisitor} with the given ticks per second.
   *
   * @param tps ticks per second, used to convert ticks to seconds
   */
  public SvgViewVisitor(int tps) {
    this.tps = tps;
    this.valNames = new HashMap<>();
  }

  /**
   * Strings together the {@link IVisitableActor}'s name with its {@code IShape}'s description,
   * then the {@code Actor}s appearance and disappearance tick.
   *
   * @param actor Actor to describe textually
   */
  @Override
  public void visit(IVisitableActor actor) {
    this.base = new Tag();
    this.base.addAttribute("id", actor.getName());

    this.valNames.put("r", "r");
    this.valNames.put("g", "g");
    this.valNames.put("b", "b");

    this.valNames.put("fill", "fill");

    this.visit(actor.getShape());
    for (IVisitableAction action : actor.getActions()) {
      this.visit(action);
    }

    // Add the visibility tag so that shapes can appear.
    ITag visib = new Tag();
    visib.addAttribute("attributeName", "visibility");
    this.addAnimsTo(visib, actor.getAppearTime(), actor.getAppearTime() + 1,
            "hidden", "visible");

    base.addSubTag(visib);

    // Add the invisibility tag so that shapes can disappear.
    ITag dissap = new Tag();
    dissap.addAttribute("attributeName", "visibility");
    this.addAnimsTo(dissap, actor.getDisappearTime(), actor.getDisappearTime() + 1,
            "visible", "hidden");

    base.addSubTag(dissap);

    this.valNames = new HashMap<>();
  }

  @Override
  public void visit(IVisitableShape shape) {
    shape.accept(this);
  }

  @Override
  public void visit(IEllipse ellipse) {
    String posX = this.toVal(ellipse.getPosition().getX());
    String posY = this.toVal(ellipse.getPosition().getY());
    String radX = this.toVal(ellipse.getRadiusX());
    String radY = this.toVal(ellipse.getRadiusY());
    String fill = this.colorToString(ellipse.getColor());

    this.base.setName("ellipse");
    this.base.addAttribute("cx", posX);
    this.base.addAttribute("cy", posY);
    this.base.addAttribute("rx", radX);
    this.base.addAttribute("ry", radY);
    this.base.addAttribute("fill", fill);
    this.base.addAttribute("visibility", "hidden");

    this.valNames.put("width", "rx");
    this.valNames.put("height", "ry");
    this.valNames.put("x", "cx");
    this.valNames.put("y", "cy");
  }

  @Override
  public void visit(IRectangle rect) {
    String posX = this.toVal(rect.getPosition().getX());
    String posY = this.toVal(rect.getPosition().getY());
    String width = this.toVal(rect.getWidth());
    String height = this.toVal(rect.getHeight());
    String fill = this.colorToString(rect.getColor());


    this.base.setName("rect");
    this.base.addAttribute("x", posX);
    this.base.addAttribute("y", posY);
    this.base.addAttribute("width", width);
    this.base.addAttribute("height", height);
    this.base.addAttribute("fill", fill);
    this.base.addAttribute("visibility", "hidden");

    this.valNames.put("width", "width");
    this.valNames.put("height", "height");
    this.valNames.put("x", "x");
    this.valNames.put("y", "y");
  }

  @Override
  public void visit(IVisitableAction action) {
    action.accept(this);
  }

  @Override
  public void visit(IColorAction colorAction) {
    IColor sc = colorAction.getStartColor();
    IColor ec = colorAction.getEndColor();

    ITag anim = new Tag();
    this.addAnimsTo(anim,
            colorAction.getStartTime(),
            colorAction.getEndTime(),
            this.colorToString(sc),
            this.colorToString(ec));

    anim.addAttribute("attributeName", this.valNames.get("fill"));

    this.base.addSubTag(anim);
  }

  @Override
  public void visit(IRotateAction rotateAction) {
    double initialAng = rotateAction.getStartAngle();
    double finalAng = rotateAction.getEndAngle();

    String rotAxis = this.toVal(rotateAction.getStartPosition().getX())
            + " " + this.toVal(rotateAction.getStartPosition().getY());

    String initState = this.toVal(initialAng) + " " + rotAxis;
    String finalState = this.toVal(finalAng) + " " + rotAxis;

    ITag anim = new Tag();
    this.addAnimsTo(anim,
            rotateAction.getStartTime(),
            rotateAction.getEndTime(),
            initState,
            finalState);

    anim.setName("animateTransform");
    anim.addAttribute("attributeName", "transform");
    anim.addAttribute("type", "rotate");
    this.base.addSubTag(anim);
  }

  @Override
  public void visit(IMoveAction moveAction) {
    IPosition endPos = moveAction.getEndPosition();
    IPosition startPos = moveAction.getStartPosition();
    if (Math.abs(endPos.getX() - startPos.getX()) > .001) {
      ITag anim = new Tag();
      this.addAnimsTo(anim, moveAction.getStartTime(), moveAction.getEndTime(),
              Double.toString(startPos.getX()),
              Double.toString(endPos.getX()));
      anim.addAttribute("attributeName", this.valNames.get("x"));
      this.base.addSubTag(anim);
    }
    if (Math.abs(endPos.getY() - startPos.getY()) > .001) {
      ITag anim = new Tag();
      this.addAnimsTo(anim, moveAction.getStartTime(), moveAction.getEndTime(),
              Double.toString(startPos.getY()),
              Double.toString(endPos.getY()));
      anim.addAttribute("attributeName", this.valNames.get("y"));
      this.base.addSubTag(anim);
    }
  }

  @Override
  public void visit(IScaleAction scaleAction) {
    // WIDTH TAG
    if (Math.abs(scaleAction.getStartWidth() - scaleAction.getEndWidth()) > .001) {
      ITag anim = new Tag();
      this.addAnimsTo(anim, scaleAction.getStartTime(), scaleAction.getEndTime(),
              Double.toString(scaleAction.getStartWidth()),
              Double.toString(scaleAction.getEndWidth()));
      anim.addAttribute("attributeName", this.valNames.get("width"));

      this.base.addSubTag(anim);
    }

    // HEIGHT TAG
    if (Math.abs(scaleAction.getStartHeight() - scaleAction.getEndHeight()) > .001) {
      ITag anim = new Tag();
      this.addAnimsTo(anim, scaleAction.getStartTime(), scaleAction.getEndTime(),
              Double.toString(scaleAction.getStartHeight()),
              Double.toString(scaleAction.getEndHeight()));
      anim.addAttribute("attributeName", this.valNames.get("height"));

      this.base.addSubTag(anim);
    }
  }

  /**
   * Turns a color into an SVG-interpretable string.
   *
   * @param c Color to stringify
   * @return String version that corresponds to an svg-readable color
   */
  protected String colorToString(IColor c) {
    return "rgb("
            + String.format("%.0f", 255 * c.getR()) + ","
            + String.format("%.0f", 255 * c.getG()) + ","
            + String.format("%.0f", 255 * c.getB()) + ")";
  }

  /**
   * Helps initialize the given animate tag. An animate tag is used to animate shapes, and its
   * name is always "animate".
   *
   * @param anim      Tag to add features to
   * @param startTime Time the animation starts, in ticks
   * @param endTime   Time the animation ends, in ticks
   * @param finVal    Time the animation runs for, in ticks
   * @param startVal  Starting value of the animation, as a string
   * @param finVal    Ending value of the animation, as a string
   */
  protected void addAnimsTo(ITag anim, long startTime, long endTime,
                            String startVal, String finVal) {
    anim.setName("animate");
    anim.addAttribute("attributeType", "xml");
    anim.addAttribute("begin",
            this.toMillis(startTime) + "ms");
    anim.addAttribute("dur",
            this.toMillis(endTime - startTime) + "ms");
    anim.addAttribute("from", startVal);
    anim.addAttribute("to", finVal);
    anim.addAttribute("fill", "freeze");
  }

  @Override
  public ITag getResult() {
    return this.base;
  }

  /**
   * Converts a long number of ticks into milliseconds using this.tps.
   *
   * @param tick the number to be converted to milliseconds.
   * @return tick in milliseconds.
   */
  protected String toMillis(long tick) {
    return this.toVal((double) tick / ((double) this.tps) * 1000d);
  }

  /**
   * Converts a double into a single decimal string representation.
   *
   * @param dub double containing the value to convert
   * @return resulting string
   */
  protected String toVal(double dub) {
    return String.format("%.1f", dub);
  }
}
