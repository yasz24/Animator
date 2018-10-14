package cs3500.animator.model.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * An {@code Actor} is a concrete implementation of {@link IActor}. It adds fields describing
 * the {@code Actor}'s entry time, exit time, name, and actions. It holds a copy of itself to
 * modify and return when asked for an update based on the current tick. This is opposed to
 * creating a new copy of the {@code Actor} every time, which would be quite slow.
 */
public class Actor implements IVisitableActor {

  // Represents the shape of this actor. Not final because
  // transformations may lead to the actor needing to change its shape
  protected final IVisitableShape shape;

  // Holds the name of the actor
  protected final String name;

  // Actions that can be applied to the Actor's IShape
  protected final List<IVisitableAction> actions;

  // Tick for the actor to appear and disappear at
  protected final int entranceTick;
  protected final int exitTick;

  // Copy of the actor to modify and return when asked for an update
  protected Actor actorCopy;

  // Make the actor invisible despite time-based visibility?
  private boolean overrideInvisible = false;

  // Is the actor time-based visible?
  private boolean isVisible = false;

  /**
   * Creates a new actor with the given shape, script, entrance tick, exit tick, and name.
   *
   * @param shape        The shape this actor should have
   * @param actions      The tasks this actor will complete during the animation
   * @param entranceTick The tick when the actor should appear
   * @param exitTick     The tick when the actor should disappear
   * @param name         The name of the actor
   * @throws IllegalArgumentException if given an entrance tick that comes after the exit tick,
   *                                  or if either numbers are less than zero, or if given
   *                                  actions list or shape or name is null
   */
  public Actor(IVisitableShape shape, List<IVisitableAction> actions, int entranceTick,
               int exitTick, String name) throws IllegalArgumentException {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape");
    }
    if (actions == null) {
      throw new IllegalArgumentException("Given a null list of actions");
    }

    if (name == null) {
      throw new IllegalArgumentException("Given a null name");
    }

    if (entranceTick < 0) {
      throw new IllegalArgumentException("Given an entrance tick smaller than zero");
    }

    if (exitTick < 0) {
      throw new IllegalArgumentException("Given an exit tick smaller than zero");
    }

    if (entranceTick >= exitTick) {
      throw new
              IllegalArgumentException("Specified an entrance time that comes "
              + "after or during the exit time");
    }

    if (!testCompatibility(actions)) {
      throw new IllegalArgumentException("actor given incompatible actions.");
    }

    this.actions = actions;
    Collections.sort(this.actions);
    this.name = name;
    this.shape = shape;

    this.entranceTick = entranceTick;
    this.exitTick = exitTick;
    this.actorCopy = null;
  }

  /**
   * Tests that all the actions in this {@link Actor} are compatible with one another.
   *
   * @param events {@link List} of {@link IVisitableAction}s to test.
   * @return true if all the given events are pairwise compatible with one another, false otherwise
   * @throws IllegalArgumentException if events is null
   */
  private boolean testCompatibility(List<IVisitableAction> events) {
    if (events == null) {
      throw new IllegalArgumentException("Given null list of events");
    }
    for (int idxa = 0; idxa < events.size() - 1; ++idxa) {
      for (int idxb = idxa + 1; idxb < events.size(); ++idxb) {
        if (!events.get(idxa).compatibleWith(events.get(idxb))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Checks that the actorCopy variable exists. If it doesn't created one and sets the created
   * copy's copy to itself to end any further recursion.
   */
  protected void verifyActorCopy() {
    if (this.actorCopy == null) {
      List<IVisitableAction> actionCopies = new ArrayList<>();
      for (IVisitableAction c : this.actions) {
        actionCopies.add(c.getVisitableCopy());
      }
      this.actorCopy =
              new Actor(this.shape.getVisitableCopy(), actionCopies,
                      this.entranceTick, this.exitTick, this.name);

      // Set the copy's copy to be itself (spooky)
      this.actorCopy.actorCopy = this.actorCopy;
    }
  }

  @Override
  public void update(long currentTick) {
    if (currentTick < 0) {
      throw new IllegalArgumentException("Given a negative current tick");
    }

    this.verifyActorCopy();

    if (this.entranceTick <= currentTick && currentTick <= this.exitTick) {
      this.isVisible = true;
      this.actorCopy.isVisible = true;
    } else {
      this.isVisible = false;
      this.actorCopy.isVisible = false;
    }

    // We gotta loop backwards AND forwards and stuff... or else the actor's next
    // state isn't independent of its previous
    for (int actIdx = 0; actIdx < this.actions.size(); ++actIdx) {
      if (this.actions.get(actIdx).getStartTime() <= currentTick || currentTick <= 1) {
        this.actions.get(actIdx).applyTo(this.actorCopy.shape, currentTick);
      }
      if (this.actions.get(this.actions.size() - actIdx - 1).getEndTime() > currentTick) {
        this.actions.get(this.actions.size() - actIdx - 1)
                .applyTo(this.actorCopy.shape, currentTick);
      }
    }
  }

  @Override
  public void addAction(IVisitableAction a) {
    if (a == null) {
      throw new IllegalArgumentException("Given a null IAction");
    }

    verifyActorCopy();

    for (IVisitableAction action : this.actions) {
      if (!action.compatibleWith(a)) {
        throw new IllegalArgumentException("Given an incompatible IAction to add");
      }
    }
    this.actions.add(a);
    Collections.sort(this.actions);
    this.actorCopy.actions.add(a.getVisitableCopy());
    Collections.sort(this.actorCopy.actions);
  }

  @Override
  public IActor getCopy() {
    this.verifyActorCopy();
    return this.actorCopy;
  }

  @Override
  public boolean isVisible() {
    return this.isVisible && !this.overrideInvisible;
  }

  @Override
  public void toggleVisibility() {
    this.overrideInvisible = !this.overrideInvisible;
  }

  @Override
  public List<IVisitableAction> getActions() {
    verifyActorCopy();

    return this.actorCopy.actions;
  }

  @Override
  public String getName() {
    verifyActorCopy();

    return this.name;
  }

  /**
   * Returns a string representation of this {@code Actor}.
   *
   * @return String containing details about the state of this {@code Actor}, including
   *         appearance time, exit time, shape, location, and color.
   */
  @Override
  public String toString() {
    return "Name: " + this.name + "\n" + this.shape.toString()
            + "\nAppears at t=" + Integer.toString(this.entranceTick)
            + "\nDisappears at t=" + Integer.toString(this.exitTick);
  }

  @Override
  public IVisitableShape getShape() {
    verifyActorCopy();

    return this.shape;
  }

  @Override
  public long getAppearTime() {
    return this.entranceTick;
  }

  @Override
  public long getDisappearTime() {
    return this.exitTick;
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null visitor");
    }
    v.visit(this.actorCopy);
  }

  /**
   * {@code Actor} equality is based solely on name.
   *
   * @param other Other {@code Actor} to compare equality with
   * @return True if this {@code Actor} is equal to the given {@code Actor}
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    }
    if (this == other) {
      return true;
    }
    if (!(other instanceof Actor)) {
      return false;
    } else {
      Actor o = (Actor) other;
      return o.name.equals(this.name);
    }
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }

  @Override
  public IVisitableActor getVisitableCopy() {
    this.verifyActorCopy();
    return this.actorCopy;
  }
}
