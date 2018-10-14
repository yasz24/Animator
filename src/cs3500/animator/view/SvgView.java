package cs3500.animator.view;

import java.io.IOException;
import java.util.LinkedHashMap;

import cs3500.animator.model.action.IAction;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.visitors.ModelVisitor;
import cs3500.animator.view.visitors.SvgViewVisitor;
import cs3500.animator.view.xml.Tag;
import cs3500.animator.view.xml.ITag;

/**
 * Represents an implementation of the {@link AppendableView} interface that outputs a file in the
 * scalable vector graphics (SVG henceforth) format.
 */
public class SvgView implements AppendableView {

  /**
   * Version of SVG file that we're writing.
   */
  private final String SVG_VERSION = "1.1";

  /**
   * Appendable that we output to.
   */
  protected final Appendable out;

  /**
   * Root of the SVG xml tree we create.
   */
  protected final ITag base;

  /**
   * Ticks per second, for unit conversion.
   */
  protected final int tps;

  /**
   * Map of {@code IVisitableActor}s to their names.
   */
  protected final LinkedHashMap<String, IVisitableActor> actors;

  /**
   * Constructs a new SvgView to output to the given {@code Appendable} with given width and height.
   *
   * @param out          {@code Appendable} to output results to
   * @param tps          ticks per second for the model, used for conversion
   * @throws IllegalArgumentException if tps, windowWidth, or windowHeight is 0 or negative, or
   *                                  if out is null
   */
  public SvgView(Appendable out, int tps) {
    if (out == null) {
      throw new IllegalArgumentException("Given a null appendable");
    }

    this.out = out;

    if (tps <= 0) {
      throw new IllegalArgumentException("Given 0 or negative tps");
    }

    this.tps = tps;
    this.actors = new LinkedHashMap<>();

    base = new Tag();
    base.setName("svg");
    base.addAttribute("version", this.SVG_VERSION);
    base.addAttribute("xmlns", "http://www.w3.org/2000/svg");

  }

  /**
   * Loops through all the {@code IActions} in the {@code LoopingSvgView}
   * and gets the ending time of the one that ends last.
   *
   * @return Ending tick of the final {@code IAction}.
   */
  protected long getTimeLastAction() {
    long maxEndTime = 0;
    for (String key : actors.keySet()) {
      IActor actor = actors.get(key);
      if (actor.getDisappearTime() > maxEndTime) {
        maxEndTime = actor.getDisappearTime();
      }
      // Store all the actor's actions for printing at bottom of file
      for (IAction action : actor.getActions()) {
        if (action.getEndTime() > maxEndTime) {
          maxEndTime = action.getEndTime();
        }
      }
    }
    return maxEndTime;
  }

  @Override
  public void outputActors() throws IOException {
    for (IVisitableActor actor : this.actors.values()) {
      ModelVisitor<ITag> visitor = new SvgViewVisitor(tps);
      actor.update(actor.getAppearTime() + 1);
      if (!actor.isVisible()) {
        continue;
      }
      actor.update(0);
      visitor.visit(actor);
      this.base.addSubTag(visitor.getResult());
    }
    this.out.append(this.base.toXml());
  }

  @Override
  public void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    if (actors == null) {
      throw new IllegalArgumentException("Given null map of actors");
    }

    this.actors.putAll(actors);
  }
}
