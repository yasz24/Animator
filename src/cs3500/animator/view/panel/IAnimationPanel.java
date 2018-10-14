package cs3500.animator.view.panel;

import java.util.List;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * An {@code IAnimationPanel} supports holding a list of {@code IActor}s and drawing them to
 * a screen. The only current implementation uses Swing to accomplish this.
 *
 * <p>Change 04/16/18: Changed IActor to IVisitableActor.
 */
public interface IAnimationPanel {

  /**
   * Set the internal list of {@code IActor}s to the given list.
   *
   * @param list The actors that need to be drawn.
   * @throws IllegalArgumentException If given a null list.
   */
  void setActors(List<IVisitableActor> list);
}
