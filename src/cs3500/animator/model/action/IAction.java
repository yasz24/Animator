package cs3500.animator.model.action;

import cs3500.animator.model.shape.IVisitableShape;

/**
 * Represents a transformation of some shape. Occurs over a period of time, beginning
 * with a start tick and ending on an end tick. Linearly interpolates some final state
 * of an {@link IVisitableShape} from the beginning state of an {@code IVisitableShape}.
 *
 * <p>{@code IAction}s are allowed to be discontinuous; For example, a move action that starts at
 * (50, 50) and ends at (100, 100) can be followed by (on the same shape) another move action
 * that starts at (110, 125).
 *
 * <p>Change 04/16/18: Moved accept() from IAction to IVisitableAction.
 */
public interface IAction extends Comparable<IAction> {

  /**
   * Mutates the given {@code IVisitableShape} to what its state should be at the given time.
   *
   * @param shape The shape to be mutated.
   * @param tick  the time to which the shape state needs to be adjusted.
   * @throws IllegalArgumentException if given a null shape or negative tick
   */
  void applyTo(IVisitableShape shape, long tick);

  /**
   * Returns the start time of this {@code IAction} in ticks.
   *
   * @return a long representing the start time of this action.
   */
  long getStartTime();

  /**
   * Returns the end time of this {@code IAction} in ticks.
   *
   * @return a long representing the end time of this action.
   */
  long getEndTime();

  /**
   * Determines if this {@code IAction} is currently happening at the given tick.
   *
   * @param tick Tick to test happening-ness with
   * @return true if this {@code IAction} begins before and ends after the given tick, false
   *         otherwise
   * @throws IllegalArgumentException if given a negative tick
   */
  boolean isHappening(long tick);

  /**
   * Returns a deep copy of this {@code IAction}.
   *
   * @return Copy of this {@code IAction}
   */
  IAction getCopy();

  /**
   * Returns a string representation of this {@code IAction}.
   *
   * @return String representing the {@code IAction}
   */
  @Override
  String toString();

}
