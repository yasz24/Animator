package cs3500.animator.provider.model;

import cs3500.animator.model.shape.IPosition;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * This interface extends from IAAnimation. The method stipulates the overriding of the end location
 * method in the implementing classes.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IMove extends IAAnimation {

  @Override
  IPosn endLocation();
}
