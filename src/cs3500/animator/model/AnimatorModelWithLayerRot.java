package cs3500.animator.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

import cs3500.animator.ModelBuilderRotLayer;
import cs3500.animator.TweenModelBuilder;
import cs3500.animator.model.action.ColorAction;
import cs3500.animator.model.action.MoveAction;
import cs3500.animator.model.action.RotateAction;
import cs3500.animator.model.action.ScaleAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.actor.LayeredActor;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.shape.IEllipse;
import cs3500.animator.model.shape.IPosition;
import cs3500.animator.model.shape.IRectangle;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.model.shape.Position;
import cs3500.animator.model.shape.RotEllipse;
import cs3500.animator.model.shape.RotRectangle;
import cs3500.animator.view.visitors.AbstVisitor;
import cs3500.animator.view.visitors.ModelVisitor;

/**
 * Used to overshadow builder class from BasicAnimatorModel, replacing Actors with
 * LayeredActors, Rectangles with RotRectangles, and Ellipses with RotEllipses.
 */
public class AnimatorModelWithLayerRot extends BasicAnimatorModel {

  /**
   * A builder class that allows the user to build AnimatorModelWithLayerRot's.
   * Allows chaining of multiple commands to quickly build a functioning model.
   */
  public static final class Builder implements ModelBuilderRotLayer<IAnimatorModel> {

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
      return this.addOval(
              name, cx, cy, xRadius, yRadius, red, green, blue, startOfLife, endOfLife, 0, 0);
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addOval(String name, float cx, float cy, float xRadius,
                                                     float yRadius, float red, float green,
                                                     float blue, int startOfLife, int endOfLife,
                                                     int layer, int angle) {
      LayeredActor a = new LayeredActor(new RotEllipse(cx, cy, xRadius, yRadius, new Color(red, green, blue),
              angle),
              new ArrayList<>(), startOfLife, endOfLife, name, layer);

      actors.put(name, a);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(String name, float lx, float ly,
                                                          float width, float height, float red,
                                                          float green, float blue, int startOfLife,
                                                          int endOfLife) {
      return this.addRectangle(
                      name, lx, ly, width, height,
              red, green, blue, startOfLife, endOfLife, 0, 0);
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRectangle(String name, float lx, float ly,
                                                          float width, float height, float red,
                                                          float green, float blue, int startOfLife,
                                                          int endOfLife, int layer, int angle) {
      LayeredActor a = new LayeredActor(new RotRectangle(lx, ly, width, height, new Color(red, green, blue),
              angle),
              new ArrayList<>(), startOfLife, endOfLife, name, layer);

      actors.put(name, a);

      return this;
    }

    @Override
    public TweenModelBuilder<IAnimatorModel> addRotationChange(String name,
                                                               float fromAngle,
                                                               float toAngle,
                                                               int startTime,
                                                               int endTime) {
      if (actors.get(name) != null) {

        // Create a visitor that obtains the center of the actor's shape
        ModelVisitor<IPosition> vis = new AbstVisitor<IPosition>() {
          private IPosition result = null;

          @Override
          public void visit(IVisitableShape shape) {
            shape.accept(this);
          }

          @Override
          public void visit(IRectangle rect) {
            // The position of a rectangle is its min-corner so we need to add
            // half the width and height to get the center
            this.result =
                    new Position(
                            rect.getPosition().getX() + (0.5 * rect.getWidth()),
                            rect.getPosition().getY() + (0.5 * rect.getHeight()));
          }

          @Override
          public void visit(IEllipse ellipse) {
            // The position of an ellipse is its center
            this.result = ellipse.getPosition();
          }

          @Override
          public IPosition getResult() {
            if (this.result == null) {
              throw new IllegalStateException("Failed to visit and obtain position");
            }
            return this.result;
          }
        };

        // Rotation should be about the axis the shape is at when the rotation starts.
        actors.get(name).update(startTime);
        vis.visit(actors.get(name).getVisitableCopy().getShape());
        actors.get(name).update(1);

        actors.get(name).addAction(new RotateAction(startTime, endTime,
                fromAngle, toAngle, vis.getResult()));
        return this;
      } else {
        throw new IllegalArgumentException("The shape that the move is to be performed "
                + "on does not exist");
      }
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
        actors.get(name).addAction(new ScaleAction(
                startTime, endTime, fromSx, fromSy, toSx, toSy));
        return this;
      } else {
        throw new IllegalArgumentException("The shape that the move is to be performed "
                + "on does not exist");
      }
    }

    @Override
    public IAnimatorModel build() {
      IAnimatorModel model = new AnimatorModelWithLayerRot();

      // Sort the actors by their layers. This also leaves ones on the same layer in place
      Collection<IVisitableActor> sorted =
              actors.values()
                      .stream()
                      .sorted().collect(Collectors.toList());
      // Build the model
      for (IVisitableActor s : sorted) {
        model.addActor(s);
      }
      return model;
    }
  }
}
