package cs3500.animator.model.action;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a MoveAction with a toString that is compatible with the provider's
 * text renderer. We need this because their text renderer uses string comprehension
 * on the toString() of the model to generate the text representation.
 */
public class MoveActionWithToString extends MoveAction {

  /**
   * Creates a new {@code MoveAction} with the specified start and end times and positions.
   *
   * @param startTime     start time of the move as a long in ticks
   * @param endTime       end time of the move as a long in ticks
   * @param startPosition the start position of the move
   * @param endPosition   the end position of the move
   * @throws IllegalArgumentException if start or end positions are null.
   */
  public MoveActionWithToString(long startTime, long endTime, IPosition startPosition, IPosition endPosition) throws IllegalArgumentException {
    super(startTime, endTime, startPosition, endPosition);
  }

  @Override
  public String toString() {
    return "moves from " + this.getStartPosition().toString() + " to " +
            this.getEndPosition().toString() +
            " from t=" + this.startTime + " to t=" + this.endTime;
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null visitor");
    }
    v.visit(this);
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new MoveActionWithToString(this.startTime, this.endTime,
            this.getStartPosition(), this.getEndPosition());
  }

  @Override
  public IAction getCopy() {
    return this.getVisitableCopy();
  }
}
