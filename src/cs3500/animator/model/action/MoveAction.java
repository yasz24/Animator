package cs3500.animator.model.action;

import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.model.shape.Position;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Represents a transformation that moves an {@code IShape}. Given a start and ending tick,
 * {@code MoveAction} interpolates the position of the given shape to be somewhere between the
 * starting and ending position, based on the given current tick.
 */
public class MoveAction extends AbstractAction implements IMoveAction {
  /**
   * IPosition that the applied-upon {@code IShape} begins at.
   */
  private final IPosition startPosition;

  /**
   * IPosition that the applied-upon {@code IShape} ends at.
   */
  private final IPosition endPosition;

  /**
   * Creates a new {@code MoveAction} with the specified start and end times and positions.
   *
   * @param startTime     start time of the move as a long in ticks
   * @param endTime       end time of the move as a long in ticks
   * @param startPosition the start position of the move
   * @param endPosition   the end position of the move
   * @throws IllegalArgumentException if start or end positions are null.
   */
  public MoveAction(long startTime, long endTime, IPosition startPosition, IPosition endPosition)
          throws IllegalArgumentException {

    super(startTime, endTime);

    if ((startPosition == null) || (endPosition == null)) {
      throw new IllegalArgumentException("Start or End position for move cannot be null.");
    }

    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  @Override
  public void transformShape(IVisitableShape shape, long tick) {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape to transform");
    }

    // Interpolate between the initial and final values
    double newX = linInterpolation(tick, this.startPosition.getX(), this.endPosition.getX());
    double newY = linInterpolation(tick, this.startPosition.getY(), this.endPosition.getY());

    shape.setPosition(new Position(newX, newY));
  }

  @Override
  public boolean compatibleMoveTransformation(MoveAction other) {
    return !this.simultaneousWith(other);
  }

  @Override
  protected boolean compatibleAbstractAction(AbstractAction other) {
    return other.compatibleMoveTransformation(this);
  }

  @Override
  public IAction getCopy() {
    return new MoveAction(this.startTime, this.endTime,
            this.startPosition, this.endPosition);
  }

  @Override
  public void accept(ModelVisitor v) {
    if (v == null) {
      throw new IllegalArgumentException("Given a null visitor");
    }
    v.visit(this);
  }

  @Override
  public IPosition getStartPosition() {
    return new Position(startPosition);
  }

  @Override
  public IPosition getEndPosition() {
    return new Position(endPosition);
  }

  @Override
  public IVisitableAction getVisitableCopy() {
    return new MoveAction(this.startTime, this.endTime,
            this.startPosition, this.endPosition);
  }
}
