package cs3500.animator.model.actor;

import java.util.List;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.shape.IVisitableShape;

/**
 * Represents an actor, in a similar manner to one in real life.
 * An actor has a body ({@link IVisitableShape}) and script
 * ({@code List<{@link IVisitableAction}>}).
 * The actor can be told to return a copy of itself at a given time, and will loop through
 * stored {@link IVisitableAction}s to do so.
 *
 * <p>Change 03/30/18: added toggleVisibility() method to override time-based visibility.
 *
 * <p>Change 04/16/18: Changed all IShape, IAction to IVisitableShape, IVisitableAction.
 *
 * <p>Change 04/16/18: Moved accept() from IActor to IVisitableActor.
 */
public interface IActor {

  /**
   * Updates this {@code IActor}'s stored copy stored copy of itself.
   *
   * @param currentTick long representing the current tick of the animation.
   * @throws IllegalArgumentException if currentTick is negative
   */
  void update(long currentTick);

  /**
   * Tells whether the {@code IActor} should be displayed or not. (aka is the current tick
   * between the {@code IActor}'s appearance and disappearance time.
   *
   * @return true if the IActor should be displayed, false otherwise
   */
  boolean isVisible();

  /**
   * Toggles the visibility of the {@code IActor}. e.g., if the {@code IActor} was visible and
   * this is called, then the {@code IActor} becomes invisible. The {@code IActor} is initially
   * visible.
   */
  void toggleVisibility();

  /**
   * Adds the given action to the {@code IActor}'s list of actions.
   *
   * @param a Action to add to this {@code IActor}
   * @throws IllegalArgumentException if IAction is null
   */
  void addAction(IVisitableAction a);

  /**
   * Creates, stores to state, and returns a copy of this {@code IActor}. If copy already exists,
   * returns existing copy.
   *
   * @return copy of this {@code IActor}
   */
  IActor getCopy();

  /**
   * Returns a list of all the {@link IVisitableAction}s this {@code Actor} has.
   *
   * @return A copy list of (possibly empty) {@code IAction}s.
   */
  List<IVisitableAction> getActions();

  /**
   * Returns the name of this {@code Actor}.
   *
   * @return string containing the name of this {@code Actor}
   */
  String getName();

  /**
   * Returns a copy of the shape of this {@code Actor}.
   *
   * @return Copy of this {@code IActor}'s shape.
   */
  IVisitableShape getShape();

  /**
   * Returns the tick this {@code Actor} appears at.
   *
   * @return long containing the tick the {@code Actor} appears at
   */
  long getAppearTime();

  /**
   * Returns the tick this {@code Actor} disappears at.
   *
   * @return long containing the tick the {@code Actor} disappears at
   */
  long getDisappearTime();

  /**
   * Returns a string representation of this {@code IActor}.
   *
   * @return String containing details about the state of this {@code IActor}, including
   *         appearance time, exit time, shape, and possibly more.
   */
  @Override
  String toString();
}
