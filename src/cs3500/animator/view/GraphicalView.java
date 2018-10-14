package cs3500.animator.view;

import java.util.LinkedHashMap;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * Represents an implementation of {@link IView} that somehow draws the model to a buffer.
 * An example implementation would use Swing, but it could hypothetically be implemented with
 * LWJGL or some other graphical library.
 *
 * <p>Change 04/16/18: Changed IActor to IVisitableActor.
 *
 * <p>Change 04/16/18: Changed HashMap to LinkedHashMap.
 */
public interface GraphicalView extends IView {

  /**
   * Sets the actors to be drawn on the Animation Panel.
   *
   * @param actors The Map of Actors retrieved from the model.
   */
  void setActors(LinkedHashMap<String, IVisitableActor> actors);

  /**
   * Signals to the view that it needs to draw itself.
   */
  void outputActors();
}
