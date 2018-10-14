package cs3500.animator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import cs3500.animator.TweenModelBuilder;
import cs3500.animator.model.action.ColorActionWithToString;
import cs3500.animator.model.action.MoveActionWithToString;
import cs3500.animator.model.action.ScaleActionWithToString;
import cs3500.animator.model.actor.Actor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Position;
import cs3500.animator.model.shape.Rectangle;

/**
 * The same as a BasicAnimatorModel, but with an overwritten builder that uses
 * the to-Stringable versions of each action. This is to provide compatibility with
 * the provider's TextView.
 */
public class AnimatorModelWithToString extends BasicAnimatorModel {
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
        actors.get(name).addAction(new MoveActionWithToString(startTime, endTime,
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
        actors.get(name).addAction(new ColorActionWithToString(startTime, endTime,
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
        actors.get(name).addAction(new ScaleActionWithToString(
                startTime, endTime, fromSx, fromSy, toSx, toSy));
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
