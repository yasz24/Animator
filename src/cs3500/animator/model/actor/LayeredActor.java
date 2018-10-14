package cs3500.animator.model.actor;

import java.util.ArrayList;
import java.util.List;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.shape.IVisitableShape;

/**
 * Adds layering functionality to the {@link Actor} class. Layering works as follows:
 *
 * <p>If two {@code LayeredActor}s are in the same layer,
 * the one that appears first in the input file is rendered on bottom.
 *
 * <p>If {@code LayeredActor} A has a higher layer than {@code LayeredActor} B,
 * A is rendered on top of B
 */
public class LayeredActor extends Actor {

  // The layer the actor is on
  private final int layer;

  /**
   * Create a new {@code LayeredActor}, similar to {@link Actor} but with added layer parameter.
   *
   * @param shape        shape of the {@code LayeredActor}
   * @param actions      actions the {@code LayeredActor} performs
   * @param entranceTick time the {@code LayeredActor} appears
   * @param exitTick     time the {@code LayeredActor} disappears
   * @param name         name of the {@code LayeredActor}
   * @param layer        render layer of the {@code LayeredActor}
   */
  public LayeredActor(IVisitableShape shape,
                      List<IVisitableAction> actions,
                      int entranceTick,
                      int exitTick,
                      String name,
                      int layer)
          throws IllegalArgumentException {

    super(shape, actions, entranceTick, exitTick, name);

    this.layer = layer;
  }

  /**
   * Checks that the actorCopy variable exists. If it doesn't created one and sets the created
   * copy's copy to itself to end any further recursion.
   */
  @Override
  protected void verifyActorCopy() {
    if (this.actorCopy == null) {
      List<IVisitableAction> actionCopies = new ArrayList<>();
      for (IVisitableAction c : this.actions) {
        actionCopies.add(c.getVisitableCopy());
      }
      this.actorCopy =
              new LayeredActor(this.shape.getVisitableCopy(), actionCopies,
                      this.entranceTick, this.exitTick, this.name, this.layer);

      // Set the copy's copy to be itself (spooky)
      this.actorCopy.actorCopy = this.actorCopy;
    }
  }


  /**
   * Compares to another IVisitableActor by layer, as specified in the class JavaDoc.
   *
   * @param o {@link IVisitableActor} to compare to
   * @return this actor's layer minus the given actor's layer
   */
  @Override
  public int compareTo(IVisitableActor o) {
    // Layered actors only comparable to other layered actors
    if (!(o instanceof LayeredActor)) {
      return 0;
    }

    LayeredActor act = (LayeredActor) o;

    return this.layer - act.layer;
  }
}
