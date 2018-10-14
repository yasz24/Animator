import java.io.IOException;
import java.util.LinkedHashMap;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.IView;

/**
 * Represents a dummy verion of an {@link IView} that just writes down the commands it
 * receives.
 */
public class DummyView implements IView {

  /**
   * The actors added to the model by the controller.
   */
  private LinkedHashMap<String, IVisitableActor> actors;

  /**
   * Constructor.
   */
  public DummyView() {
    actors = new LinkedHashMap<>();
  }

  /**
   * Returns the actors in the order they were added.
   *
   * @return The actors in the order they were added
   */
  public LinkedHashMap<String, IVisitableActor> getActors() {
    return this.actors;
  }

  @Override
  public void makeVisible() {
    // Does nothing
  }

  @Override
  public void outputActors() throws IOException {
    // Does nothing
  }

  @Override
  public void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    this.actors = actors;
  }
}
