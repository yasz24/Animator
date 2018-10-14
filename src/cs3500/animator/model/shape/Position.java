package cs3500.animator.model.shape;

/**
 * Represents a position on a 2-D surface as an x-y coordinate. Can be mutated. Overrides
 * equality and hashcode.
 */
public class Position implements IPosition {
  // x and y coordinates of the position.
  private double x;
  private double y;

  /**
   * Creates a new {@code Position} with given x and y coordinates.
   *
   * @param x x-coordinate of the position
   * @param y y-coordinate of the position
   */
  public Position(double x, double y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Creates a new {@code Position} with the same coordinates as the given {@code IPosition}.
   *
   * @param p Position to make a new position from
   */
  public Position(IPosition p) {
    this.x = p.getX();
    this.y = p.getY();
  }

  @Override
  public double getX() {
    return this.x;
  }

  @Override
  public void setX(double otherX) {
    this.x = otherX;
  }

  @Override
  public double getY() {
    return this.y;
  }

  @Override
  public void setY(double otherY) {
    this.y = otherY;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Position)) {
      return false;
    } else {
      Position p = (Position) other;
      return Math.abs(p.x - this.x) < .001 && Math.abs(p.y - this.y) < .001;
    }
  }

  @Override
  public int hashCode() {
    return Double.hashCode(this.x) * Double.hashCode(this.y);
  }

  @Override
  public String toString() {
    return "(" + this.x + "," + this.y + ")";
  }
}

