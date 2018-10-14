package cs3500.animator.provider.model;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * Represents a rectangle.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IRectangle extends IAShape {

  @Override
  IPosn getLLC();

  @Override
  IMyColor getColor();

  @Override
  double getHeight();

  @Override
  double getWidth();

  @Override
  String getName();
}
