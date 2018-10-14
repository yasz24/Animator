package cs3500.animator.view.xml;

/**
 * Represents an attribute that is contained within a tag declaration.
 * e.g., in
 * <br>{@code <rect width="300">}
 * <br> the 'width="300"' part is an attribute.
 */
public interface IAttribute {
  /**
   * Turns this {@code IAttribute} into a string that holds the XML the {@code IAttribute}
   * represents.
   *
   * @return String containing {@code IAttribute} as an xml attribute
   */
  String toXml();

  /**
   * Returns the name of this {@code IAttribute}.
   *
   * @return the name of this {@code IAttribute}
   */
  String getName();

  /**
   * Returns the value of this {@code IAttribute}.
   *
   * @return the value of this {@code IAttribute}.
   */
  String getValue();
}
