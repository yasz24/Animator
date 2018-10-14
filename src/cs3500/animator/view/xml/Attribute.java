package cs3500.animator.view.xml;

/**
 * Concrete implementation of IAttribute. Has a name and value.<br>
 * e.g, in the tag<br>
 * {@code <rect width="5.0"> </rect>},<br>
 * {@code rect} is a tag with an attribute named "width" and a value "5.0".
 */
public class Attribute implements IAttribute {
  /**
   * Name of the attribute.
   */
  private final String name;
  /**
   * Value of the attribute.
   */
  private final String value;

  /**
   * Create a new {@code Attribute} with given name and value.
   *
   * @param name  Name of the {@code Attribute}
   * @param value Value of the {@code Attribute}
   * @throws IllegalArgumentException if either of name or value are null
   */
  public Attribute(String name, String value) {
    if (name == null) {
      throw new IllegalArgumentException("Given null name");
    }
    if (value == null) {
      throw new IllegalArgumentException("Given null value");
    }
    this.name = name;
    this.value = value;
  }

  @Override
  public String toXml() {
    return this.name + "=\"" + this.value + "\"";
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String getValue() {
    return this.value;
  }
}
