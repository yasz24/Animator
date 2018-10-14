package cs3500.animator.view;

import java.io.IOException;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.visitors.LoopingSvgViewVisitor;
import cs3500.animator.view.visitors.ModelVisitor;
import cs3500.animator.view.xml.ITag;
import cs3500.animator.view.xml.Tag;

/**
 * Extends the {@link SvgView} class, adding the ability to loop the outputted SVG.
 * Does this through the use of a "dummy" base shape and animation that loops, then basing
 * other animations relative to the "dummy" animation.
 */
public class LoopingSvgView extends SvgView {

  /**
   * Constructs a new LoopingSvgView to output to the given {@code Appendable}
   * with given width and height. The animation will loop at the end of every play.
   *
   * @param out {@code Appendable} to output results to
   * @param tps ticks per second for the model, used for conversion
   * @throws IllegalArgumentException if tps, windowWidth, or windowHeight is 0 or negative, or
   *                                  if out is null
   */
  public LoopingSvgView(Appendable out, int tps) {
    super(out, tps);
  }


  /**
   * Essentially the same as {@code super}, but adds relative times.
   *
   * @throws IOException if the {@link Appendable} cannot be written to.
   */
  @Override
  public void outputActors() throws IOException {
    ITag loopDummy = new Tag();
    loopDummy.setName("animate");
    loopDummy.addAttribute("id", "__base__");
    loopDummy.addAttribute("begin", "0;__base__.end");
    long endAnim = this.getTimeLastAction();
    loopDummy.addAttribute("dur",
            String.format("%.1f", ((double) endAnim
                    / ((double) this.tps) * 1000d)) + "ms");
    loopDummy.addAttribute("attributeName", "visibility");
    loopDummy.addAttribute("from", "hidden");
    loopDummy.addAttribute("to", "hidden");

    ITag dummRect = new Tag();
    dummRect.setName("rect");
    dummRect.addSubTag(loopDummy);

    this.base.addSubTag(dummRect);

    for (IVisitableActor actor : this.actors.values()) {
      ModelVisitor<ITag> visitor = new LoopingSvgViewVisitor(tps);
      // Check if actor should be visible
      actor.update(actor.getAppearTime() + 1);
      if (!actor.isVisible()) {
        continue;
      }
      // Bring actor to initial state to output
      actor.update(0);
      visitor.visit(actor);
      this.base.addSubTag(visitor.getResult());
    }
    this.out.append(this.base.toXml());
  }
}
