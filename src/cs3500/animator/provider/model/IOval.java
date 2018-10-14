package cs3500.animator.provider.model;

/**
 * Created by Yash Shetty and Jack Steilberg because our providers failed to respond
 * in a timely manner after multiple emails.
 *
 * Represents an oval.
 *
 * <p>Change 04/16/18: Created this interface.
 */
public interface IOval extends IAShape{

  @Override
  IPosn getCenter();

  @Override
  IMyColor getColor();

  @Override
  double getyRadius();

  @Override
  double getxRadius();
}
