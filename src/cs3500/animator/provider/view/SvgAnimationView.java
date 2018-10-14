package cs3500.animator.provider.view;

import java.awt.event.ActionListener;


import cs3500.animator.provider.model.IColorChange;
import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.provider.model.IMyColor;
import cs3500.animator.provider.model.IMove;
import cs3500.animator.provider.model.IOval;
import cs3500.animator.provider.model.IRectangle;
import cs3500.animator.provider.model.IScale;

/**
 * Represents the animation as text using the SVG format.
 */
public class SvgAnimationView implements IView<String, IAShape, IAAnimation> {
  private int tickRate;
  private int animationWidth;
  private int animationHeight;
  private boolean shouldLoop;

  /**
   * Default constructor, sets the animation to be 1000x1000.
   *
   * @param tickRate the desired speed of the animation
   */
  public SvgAnimationView(int tickRate) {
    this.tickRate = tickRate;
    this.animationWidth = 1000;
    this.animationHeight = 1000;
    this.shouldLoop = false;
  }

  /**
   * Constructs an SVG cs3500.animator.provider.view using the given window width and height and allows for looping.
   *
   * @param animationWidth  the width of the animation window
   * @param animationHeight the height of the animation window
   * @param tickRate        the speed of the animation
   * @param shouldLoop      whether the animation should loop
   */
  public SvgAnimationView(int animationWidth, int animationHeight, boolean shouldLoop,
                          int tickRate) {
    this.tickRate = tickRate;
    this.animationHeight = animationHeight;
    this.animationWidth = animationWidth;
    this.shouldLoop = shouldLoop;
  }

  /**
   * Converts ticks to milliseconds using the tick rate of the animation.
   *
   * @param tick the current tick
   * @return the millisecond corresponding to the current tick.
   */
  @Override
  public double convertTicks(int tick) {
    return (tick / this.tickRate) * 1000;
  }

  @Override
  public void quit() {
    // Does nothing, but needs to be implemented according to super class.
  }

  @Override
  public void togglePause(boolean toggle) {
    // Does nothing, but needs to be implemented according to super class.
  }

  @Override
  public void toggleLoop(String toggle) {
    if (toggle.equals("On")) {

      this.shouldLoop = true;

    } else if (toggle.equals("Off")) {

      this.shouldLoop = false;

    } else {

      throw new IllegalArgumentException("Options are On/Off");

    }
  }

  @Override
  public void reset() {
    // Does nothing, but needs to be implemented according to super class.
  }

  @Override
  public void addListener(ActionListener k) {
    //Do Nothing
  }

  @Override
  public int currentShape() {
    return 0;
  }

  @Override
  public void increment() {
    // not needed for this instance.
  }

  @Override
  public String render(EasyAnimatorOperations<IAShape, IAAnimation> model) {
    StringBuilder result = new StringBuilder();
    result.append(String.format("svg width=\"%d\" height=\"%d\" version\"1.1\""
            + "xmlns=\"http://www.w3.org/2000/svg\"", this.animationWidth, this.animationHeight));
    if (this.shouldLoop) {
      result.append("<rect>\n");
      result.append("<animate id=\"base\" begin=\"0;base.end\" dur=\"10000.0ms\""
              + " attributeName=\"visibility\" from=\"hide\" to=\"hide\"/>\n");
      result.append("</rect>");
    }

    for (IAShape s : model.getShapes()) {
      if (s instanceof IRectangle) {
        IRectangle r = (IRectangle) s;
        double initX = r.getLLC().getX();
        double initY = r.getLLC().getY();
        IMyColor initColor = r.getColor();
        double initHeight = r.getHeight();
        double initWidth = r.getWidth();
        result.append(drawRectangle(r));
        for (IAAnimation a : model.getAnimations()) {
          String beg;
          if (this.shouldLoop) {
            beg = String.format("base.begin+%f", convertTicks(a.getStart()));
          } else {
            beg = String.format("%f", convertTicks(a.getStart()));
          }
          if (a.getAffected().getName().equals(r.getName())) {
            if (a instanceof IMove) {
              IMove m = (IMove) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"x\" from=\"%f\" to=\"%f\" " +
                              "fill=\"freeze\" />\n", beg,
                      convertTicks(m.duration()), r.getLLC().getX(), m.endLocation().getX()));
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"y\" from=\"%f\" to=\"%f\" " +
                              "fill=\"freeze\" />\n", beg,
                      convertTicks(m.duration()), r.getLLC().getY(), m.endLocation().getY()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                              " attributeName=\"x\" to=\"%f\" fill=\"freeze\" />\n", initX));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                              " attributeName=\"y\" to=\"%f\" fill=\"freeze\" " + "/>\n", initY));
            } else if (a instanceof IScale) {
              IScale sc = (IScale) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"width\" from=\"%f\" to=\"%f\" " +
                              "fill=\"freeze\" />\n", beg,
                      convertTicks(sc.duration()), r.getWidth(), sc.xParam()));
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"height\" from=\"%f\" to=\"%f\" " +
                              "fill=\"freeze\" />\n", beg,
                      convertTicks(sc.duration()), r.getHeight(), sc.yParam()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\""
                              + " attributeName=\"width\" to=\"%f\" "
                              + "fill=\"freeze\" />\n", initWidth));
              result.append(String.format("<animate attributeType=\"xml\" begin=\"base.end\""
                      + " dur=\"1ms\""
                      + " attributeName=\"height\" to=\"%f\" "
                      + "fill=\"freeze\" />\n", initHeight));
            } else if (a instanceof IColorChange) {
              IColorChange cc = (IColorChange) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"fill\" from=\"rgb%s\" to=\"rgb%s\" " +
                              "fill=\"freeze\" />\n", beg,
                      convertTicks(cc.duration()), r.getColor().toString(),
                      cc.getNewColor().toString()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                              " attributeName=\"fill\" to=\"rgb%s\" "
                              + "fill=\"freeze\" />\n", initColor));
            }
          } else {
            continue;
          }
        }
        result.append("</rect>\n");
      } else if (s instanceof IOval) {
        IOval c = (IOval) s;
        double initX = c.getCenter().getX();
        double initY = c.getCenter().getY();
        IMyColor initColor = c.getColor();
        double inityRad = c.getyRadius();
        double initxRad = c.getxRadius();
        result.append(drawOval(c));
        for (IAAnimation a : model.getAnimations()) {
          String beg;
          if (this.shouldLoop) {
            beg = String.format("base.begin+%f", convertTicks(a.getStart()));
          } else {
            beg = String.format("%f", convertTicks(a.getStart()));
          }
          if (a.getAffected().getName().equals(s.getName())) {
            if (a instanceof IMove) {
              IMove m = (IMove) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" "
                              + "dur=\"%fms\" attributeName=\"cx\" from=\"%f\" to=\"%f\" "
                              + "fill=\"freeze\" /\n", beg,
                      convertTicks(m.duration()), c.getCenter().getX(), m.endLocation().getX()));
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" "
                              + "dur=\"%fms\" attributeName=\"cy\" from=\"%f\" to=\"%f\" "
                              + "fill=\"freeze\" /\n", beg,
                      convertTicks(m.duration()), c.getCenter().getY(), m.endLocation().getY()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\""
                                              + " attributeName=\"x\" to=\"%f\""
                                              + " fill=\"freeze\" />\n", initX));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\""
                              + " attributeName=\"y\" to=\"%f\" fill=\"freeze\" " + "/>\n", initY));
            } else if (a instanceof IScale) {
              IScale sc = (IScale) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" "
                              + "dur=\"%fms\" attributeName=\"rx\" from=\"%f\" to=\"%f\" "
                              + "fill=\"freeze\" /\n", beg,
                      convertTicks(sc.duration()), c.getxRadius(), sc.xParam()));
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" "
                              + "dur=\"%fms\" attributeName=\"ry\" from=\"%f\" to=\"%f\" "
                              + "fill=\"freeze\" /\n", beg,
                      convertTicks(sc.duration()), c.getyRadius(), sc.yParam()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\""
                              + " attributeName=\"rx\" to=\"%f\" "
                              + "fill=\"freeze\" />\n", initxRad));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                                      " attributeName=\"ry\" to=\"%f\" " +
                                      "fill=\"freeze\" />\n", inityRad));
            } else if (a instanceof IColorChange) {
              IColorChange cc = (IColorChange) a;
              result.append(String.format("<animate attributeType=\"xml\" begin=\"%sms\" " +
                              "dur=\"%fms\" attributeName=\"fill\" from=\"rgb%s\" to=\"rgb%s\" " +
                              "fill=\"freeze\" /\n", beg,
                      convertTicks(cc.duration()), c.getColor().toString(),
                      cc.getNewColor().toString()));
              result.append(String.format(
                      "<animate attributeType=\"xml\" begin=\"base.end\" dur=\"1ms\"" +
                                      " attributeName=\"fill\" to=\"rgb%s\" " +
                                      "fill=\"freeze\" />\n", initColor.toString()));
            }
          } else {
            continue;
          }
        }
        result.append("</ellipse>\n");
      }
    }
    result.append("</svg>");
    return result.toString();
  }

  private String drawRectangle(IRectangle r) {
    return String.format("\n<rect id=\"%s\" x=\"%f\" y=\"%f\" width=\"%f\" " +
                    "height=\"%f\" fill=\"rgb%s\" visibility=\"visible\"\n", r.getName(),
            r.getLLC().getX(), r.getLLC().getY(), r.getWidth(), r.getHeight(),
            r.getColor().toString());
  }

  private String drawOval(IOval c) {
    return String.format("\n<ellipse id=\"%s\" cx=\"%f\" cy=\"%f\" rx=\"%f\" " +
                    "ry=\"%f\" fill=\"rgb%s\" visibility=\"visible\"", c.getName(),
            c.getCenter().getX(), c.getCenter().getY(), c.getxRadius(), c.getyRadius(),
            c.getColor().toString());
  }
}
