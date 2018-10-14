package cs3500.animator;

import cs3500.animator.model.IAnimatorModel;

/**
 * Inserted methods that were needed to add {@code RotRectangle}s, {@code RotEllipse}s and the
 * {@code RotationAction}s to our model. This interface extends the TweenModelBuilder interface.
 * @param <T> The interface of the animator model being built.
 *
 * <p>Change 04/24/18: created this interface.
 */
public interface ModelBuilderRotLayer<T> extends TweenModelBuilder<T> {
  /**
   * Add a new oval to the model with the given specifications.
   *
   * @param name        the unique name given to this {@code Actor}
   * @param cx          the x-coordinate of the center of the oval
   * @param cy          the y-coordinate of the center of the oval
   * @param xRadius     the x-radius of the oval
   * @param yRadius     the y-radius of the oval
   * @param red         the red component of the color of the oval
   * @param green       the green component of the color of the oval
   * @param blue        the blue component of the color of the oval
   * @param startOfLife the time tick at which this oval appears
   * @param endOfLife   the time tick at which this oval disappears
   * @param layer       the layer the oval should be on
   * @param angle       the angle at which the ellipse is oriented.
   * @return the builder object
   */
  TweenModelBuilder<T> addOval(
          String name,
          float cx, float cy,
          float xRadius, float yRadius,
          float red, float green, float blue,
          int startOfLife, int endOfLife, int layer, int angle);

  /**
   * Add a new rectangle to the model with the given specifications.
   *
   * @param name        the unique name given to this {@code Actor}
   * @param lx          the minimum x-coordinate of a corner of the
   *                    rectangle
   * @param ly          the minimum y-coordinate of a corner of the
   *                    rectangle
   * @param width       the width of the rectangle
   * @param height      the height of the rectangle
   * @param red         the red component of the color of the rectangle
   * @param green       the green component of the color of the rectangle
   * @param blue        the blue component of the color of the rectangle
   * @param startOfLife the time tick at which this rectangle appears
   * @param endOfLife   the time tick at which this rectangle disappears
   * @param layer       the layer the rectangle should be on
   * @param angle       the angle at which this rectangle is oriented.
   * @return reference to {@code this}, for chaining
   */
  TweenModelBuilder<T> addRectangle(
          String name,
          float lx, float ly,
          float width, float height,
          float red, float green, float blue,
          int startOfLife, int endOfLife, int layer, int angle);


  TweenModelBuilder<IAnimatorModel> addRotationChange(String name,
                                                      float fromAngle,
                                                      float toAngle, int start, int end);
}
