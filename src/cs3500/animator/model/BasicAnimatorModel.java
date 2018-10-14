package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cs3500.animator.TweenModelBuilder;
import cs3500.animator.model.action.ColorAction;
import cs3500.animator.model.action.ColorActionWithToString;
import cs3500.animator.model.action.IAction;
import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.action.MoveAction;
import cs3500.animator.model.action.MoveActionWithToString;
import cs3500.animator.model.action.ScaleAction;
import cs3500.animator.model.actor.Actor;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Position;
import cs3500.animator.model.shape.Rectangle;

/**
 * Implements basic functionality of an {@link IAnimatorModel}, adding a {@link HashMap} of
 * actors that maintains the order of which {@link IVisitableActor}s were added to it. Holds a list
 * of {@link IVisitableAction}s that act on the held {@code IVisitableActor}s.
 */
public class BasicAnimatorModel implements IAnimatorModel {

  /**
   * Holds all the actors in the model, attached to their names.
   */
  protected LinkedHashMap<String, IVisitableActor> actors;

  /**
   * All the actions defined in this animation.
   */
  protected List<IVisitableAction> actions;

  /**
   * Sets up the actions, actors, and tps for this {@code BasicAnimatorModel}.
   */
  public BasicAnimatorModel() {
    this.actors = new LinkedHashMap<>();
    this.actions = new ArrayList<>();
  }

  /**
   * Adds a new {@code IActor} to the animation. If called, the client must call getActorCopies()
   * again to store the updated {@code HashMap} of actors.
   *
   * @param newActor new actor to add
   * @throws IllegalArgumentException if given actor or its actions are null
   */
  @Override
  public void addActor(IVisitableActor newActor) throws IllegalArgumentException {
    if (newActor == null) {
      throw new IllegalArgumentException("Given a null actor to add!");
    }
    if (newActor.getActions() == null) {
      throw new IllegalArgumentException("New actor has null actions");
    }

    this.actions.addAll(newActor.getActions());
    this.actors.put(newActor.getName(), newActor);
  }

  @Override
  public LinkedHashMap<String, IVisitableActor> getActorCopies() {
    // Create a new HashMap and return it
    LinkedHashMap<String, IVisitableActor> ans = new LinkedHashMap<>();

    for (String k : this.actors.keySet()) {
      ans.put(k, this.actors.get(k).getVisitableCopy());
    }

    return ans;
  }

  @Override
  public void updateActors(long tick) {
    // Loop through all the actors and update them
    for (IActor a : this.actors.values()) {
      a.update(tick);
    }
  }

  @Override
  public void addActionByName(String name, IVisitableAction action) {
    if (name == null) {
      throw new IllegalArgumentException("Given a null name");
    }
    this.actors.get(name).addAction(action);
  }

  @Override
  public String toString() {
    if (this.actors.isEmpty()) {
      return "No shapes";
    }

    String ans = "Shapes:\n";
    for (IActor a : this.actors.values()) {
      ans += a.toString() + "\n";
    }

    ans += "\n\n";

    Collections.sort(this.actions);

    for (IAction e : actions) {
      for (IActor a : this.actors.values()) {
        if (a.getActions().contains(e)) {
          ans += a.getName() + " ";
        }
      }
      ans += e.toString();

      ans += "\n";
    }

    ans = ans.substring(0, ans.length() - 1);
    return ans;
  }

  /**
   * A builder class that allows the user to build IAnimatorModels. Allows chaining of multiple
   * commands to quickly build a functioning model.
   */
  public static final class Builder implements TweenModelBuilder<IAnimatorModel> {

    /**
     * Holds the actors that will be given to the model.
     */
    private HashMap<String, IVisitableActor> actors;

    /**
     * Create a new builder with no {@code IActor}s added.
     */
    public Builder() {

      actors = new LinkedHashMap<>();
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addOval(String name, float cx, float cy, float xRadius,
                                                     float yRadius, float red, float green,
                                                     float blue, int startOfLife, int endOfLife) {
      actors.put(name, new Actor(new Ellipse(cx, cy, xRadius, yRadius, new Color(red, green, blue)),
              new ArrayList<>(), startOfLife, endOfLife, name));

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(String name, float lx, float ly,
                                                          float width, float height, float red,
                                                          float green, float blue, int startOfLife,
                                                          int endOfLife) {
      actors.put(name, new Actor(new Rectangle(lx, ly, width, height, new Color(red, green, blue)),
              new ArrayList<>(), startOfLife, endOfLife, name));

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addMove(String name, float moveFromX, float moveFromY,
                                                     float moveToX, float moveToY, int startTime,
                                                     int endTime) {
      if (actors.get(name) != null) {
        actors.get(name).addAction(new MoveAction(startTime, endTime,
                new Position(moveFromX, moveFromY), new Position(moveToX, moveToY)));
        return this;
      } else {
        throw new IllegalArgumentException("The shape that the move is to be performed "
                + "on does not exist");
      }
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addColorChange(String name, float oldR, float oldG,
                                                            float oldB, float newR,
                                                            float newG, float newB, int startTime,
                                                            int endTime) {
      if (actors.get(name) != null) {
        actors.get(name).addAction(new ColorAction(startTime, endTime,
                new Color(oldR, oldG, oldB), new Color(newR, newG, newB)));
        return this;
      } else {
        throw new IllegalArgumentException("The shape that the move is to be performed "
                + "on does not exist");
      }
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addScaleToChange(String name, float fromSx,
                                                              float fromSy, float toSx, float toSy,
                                                              int startTime, int endTime) {
      if (actors.get(name) != null) {
        actors.get(name).addAction(new ScaleAction(startTime, endTime, fromSx, fromSy, toSx, toSy));
        return this;
      } else {
        throw new IllegalArgumentException("The shape that the move is to be performed "
                + "on does not exist");
      }
    }

    @Override
    public IAnimatorModel build() {
      IAnimatorModel model = new BasicAnimatorModel();
      // Build the model
      for (String s : actors.keySet()) {
        model.addActor(actors.get(s));
      }
      return model;
    }
  }
}
