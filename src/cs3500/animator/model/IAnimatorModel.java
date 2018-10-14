package cs3500.animator.model;

import java.util.LinkedHashMap;

import cs3500.animator.TweenModelBuilder;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;

/**
 * Represents a structure that holds and manages a collection of {@link IVisitableActor}s and
 * {@link IVisitableAction}s.
 * <br>To use, create an instance of {@code IAnimatorModel}, obtain the {@code IActor}s using
 * the {@code getActorCopies()} method, and call updateActors once per tick. The actor copies
 * obtained earlier will automatically be updated.
 *
 * <p>CHANGE 4/01/18: HashMap -&gt; LinkedHashMap in getActorCopies(),
 * because it needs to be ordered.
 *
 * <p>CHANGE 4/16/18: Changed instances of IAction, IActor, IShape to IVisitableAction,
 * IVisitableActor, IVisitableShape.
 */
public interface IAnimatorModel {

  /**
   * Adds a new Actor to the internal representation of {@code IActor}s.
   *
   * @param actor new IVisitableActor to add to the {@code IAnimatorModel}
   */
  void addActor(IVisitableActor actor) throws IllegalArgumentException;

  /**
   * Returns a map of all the copies of actors in this {@code IAnimatorModel}.
   * These actors will automatically update when the model is updated.
   *
   * @return list of actors
   */
  LinkedHashMap<String, IVisitableActor> getActorCopies();

  /**
   * Updates the actors stored in this model to given time tick.
   *
   * @param tick time to update actors to
   */
  void updateActors(long tick);

  /**
   * Adds an action to the actor named by name.
   *
   * @param name   name of the actor to add the action to
   * @param action action to add ot the named actor
   */
  void addActionByName(String name, IVisitableAction action);

  /**
   * Provides a string representation of this {@code IAnimatorModel}.
   *
   * @return String containing some representation of the model
   */
  @Override
  String toString();

  /**
   * Creates a builder object that can then be used to construct a AnimatorModel.
   *
   * @return the Builder object that can be used to
   */
  static TweenModelBuilder<IAnimatorModel> builder() {
    return new BasicAnimatorModel.Builder();
  }
}
