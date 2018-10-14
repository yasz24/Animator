package cs3500.animator.model.action;

import cs3500.animator.model.shape.IVisitableShape;

/**
 * Implementation of the {@code IVisitableAction} interface. Used for the visitor pattern to
 * determine compatibility of two {@code IVisitableActions}, hides the internal compatibility
 * methods from the interface so that the user cannot call them.
 *
 * <p>Change 04/24/18: added compatibleRotateTransformationMethod().
 */
public abstract class AbstractAction implements IVisitableAction {

  /**
   * Tick representing when the action begins.
   */
  protected long startTime;

  /**
   * Tick representing when the action ends.
   */
  protected long endTime;

  /**
   * Creates a new AbstractAction, validating the given start and end times.
   *
   * @param startTime Non-negative long value that is smaller than endTime
   * @param endTime   Non-negative long value that is greater than startTime
   * @throws IllegalArgumentException if given a startTime that is after endTime &lt; 0
   */
  protected AbstractAction(long startTime, long endTime) {
    // Assign the start time.
    if (startTime >= 0) {
      this.startTime = startTime;
    } else {
      throw new IllegalArgumentException("Start time cannot be negative.");
    }

    // Assign the end time.
    if (endTime >= startTime) {
      this.endTime = endTime;
    } else {
      throw new IllegalArgumentException("End time cannot be before start time.");
    }
  }

  /**
   * Checks if this {@code IAction} occurs at the same time as another {@code IAction},
   * usually for compatibility.
   *
   * @param other {@code IAction} to compare active interval with
   * @return true if this {@code IAction} occurs in the same time interval as the other, false
   *         otherwise.
   */
  protected boolean simultaneousWith(IAction other) {
    return (this.getStartTime() <= other.getEndTime()
            && this.getStartTime() >= other.getStartTime())
            || (other.getStartTime() >= this.getStartTime()
            && other.getStartTime() <= this.getEndTime());

  }

  /**
   * Applies the transformation held in this {@code IAction} to the given {@link IVisitableShape}.
   *
   * @param shape {@code AbstractShape} to apply transformation to
   * @param tick  Used for interpolation of the transformation
   */
  @Override
  public abstract void transformShape(IVisitableShape shape, long tick);


  /**
   * Determines if this {@code AbstractAction} is compatible with the given {@code ColorAction}.
   * Intended to be overridden, returns true by default.
   *
   * @param other {@code ColorAction} to compare compatibility with
   * @return true if this {@code AbstractAction} is compatible with the given {@code ColorAction},
   *         false otherwise
   */
  protected boolean compatibleColorTransformation(ColorAction other) {
    return true;
  }

  /**
   * Determines if this {@code AbstractAction} is compatible with the given {@code ColorAction}.
   * Intended to be overridden, returns true by default.
   *
   * @param other {@code ColorAction} to compare compatibility with
   * @return true if this {@code AbstractAction} is compatible with the given {@code ColorAction},
   *         false otherwise
   */
  protected boolean compatibleScaleTransformation(ScaleAction other) {
    return true;
  }

  /**
   * Determines if this {@code AbstractAction} is compatible with the given {@code ColorAction}.
   * Intended to be overridden, returns true by default.
   *
   * @param other {@code ColorAction} to compare compatibility with
   * @return true if this {@code AbstractAction} is compatible with the given {@code ColorAction},
   *         false otherwise
   */
  protected boolean compatibleMoveTransformation(MoveAction other) {
    return true;
  }


  /**
   * Determines if this {@code AbstractAction} is compatible with the given {@code RotateAction}.
   * Intended to be overridden, returns true by default.
   *
   * @param other {@code RotateAction} to compare compatibility with
   * @return true if this {@code AbstractAction} is compatible with the given {@code RotateAction},
   *         false otherwise
   */
  protected boolean compatibleRotateTransformation(RotateAction other) {
    return true;
  }

  /**
   * Interpolates some value between the given start and and final values, according to where the
   * given tick falls between the start and final tick held in state.
   *
   * @param tick       Tick to use for interpolation
   * @param initialVal Initial value to interpolate from
   * @param finalVal   Final value to interpolate to
   * @return Some value in [initialVal, finalVal] based on where tick falls
   *         in [initialTick, finalTick]
   * @throws IllegalArgumentException if given a tick not between the start and end time of this
   *                                  {@code AbstractAction}
   */
  protected double linInterpolation(long tick, double initialVal, double finalVal) {
    if (tick < startTime) {
      tick = startTime;
    }
    if (tick > endTime) {
      tick = endTime;
    }
    return (finalVal - initialVal)
            * (((double) tick - this.startTime) / (this.endTime - this.startTime))
            + initialVal;
  }

  /**
   * Purpose is for the visitor pattern used to determine {@code AbstractAction} compatibility.
   *
   * @param other {@code AbstractAction} to compare compatibility with
   * @return True if the given {@code AbstractAction} is compatible with this, false otherwise
   */
  protected abstract boolean compatibleAbstractAction(AbstractAction other);

  @Override
  public long getStartTime() {
    return this.startTime;
  }

  @Override
  public long getEndTime() {
    return this.endTime;
  }

  @Override
  public int compareTo(IAction other) {
    if (other == null) {
      throw new IllegalArgumentException("Given null IAction to compare to");
    }
    if ((this.startTime == other.getStartTime())) {
      return (int) (this.endTime - other.getEndTime());
    }
    return (int) (this.startTime - other.getStartTime());
  }

  @Override
  public boolean compatibleWith(IVisitableAction other) {
    // The one instanceof we are allowing ourselves, the purpose of this is to hide
    // all the compatible*X*Transformation from the interface, so that the client doesn't
    // ever see them.
    if (!(other instanceof AbstractAction)) {
      return true;
    }

    // Safe cast, since we just tested that other is an AbstractAction
    AbstractAction newOther = (AbstractAction) other;

    return newOther.compatibleAbstractAction(this);
  }

  @Override
  public void applyTo(IVisitableShape shape, long tick) {
    if (shape == null) {
      throw new IllegalArgumentException("Given a null shape");
    }
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }
    shape.acceptVisitableAction(this, tick);
  }

  @Override
  public boolean isHappening(long tick) {
    if (tick < 0) {
      throw new IllegalArgumentException("Given a tick less than zero");
    }
    return (startTime <= tick) && (tick <= endTime);
  }
}
