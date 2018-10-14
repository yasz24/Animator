package cs3500.animator.model.actor;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Currently empty, but would contain methods similar to {@link IVisitableAction} if more
 * {@code IActor}s are implemented. Therefore, I went ahead and made the interface, even though
 * it is currently empty.
 *
 * <p>Change 04/16/18: Moved accept() from IActor to IVisitableActor.
 *
 * <p>Change 04/16/18: Created getVisitableCopy() method.
 *
 * <p>Change 04/25/18: Made extend Comparable, so that layers can be compared.
 */
public interface IVisitableActor extends IActor, Comparable<IVisitableActor> {

  /**
   * Returns a visitable copy of this {@code IVisitableActor}.
   *
   * @return copy of this actor
   */
  IVisitableActor getVisitableCopy();

  /**
   * Accepts the given {@link ModelVisitor}, then calls visit on it from one of {@code IActor}'s
   * subclasses, thus allowing {@code ModelVisitor} to access non-interface methods using
   * polymorphism.
   *
   * @param v ModelVisitor to accept
   * @throws IllegalArgumentException if given a null visitor
   */
  void accept(ModelVisitor v);

  /**
   * Compare this {@code IVisitableActor} to another, based on layer.
   *
   * @param other actor to compare layer to
   * @return 1 if this actor has a greater layer, -1 if smaller, 0 if same
   */
  @Override
  default int compareTo(IVisitableActor other) {
    throw new UnsupportedOperationException("Compare not implemented");
  }
}