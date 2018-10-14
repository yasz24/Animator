package cs3500.animator.view.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the {@link ITag} interface, adding variables to store the tag's name, attributes,
 * and subtags. Implements a recursive toXml() method that prints the {@code Tag}.
 */
public class Tag implements ITag {
  /**
   * Name of the {@code Tag}.
   */
  private String name;

  /**
   * List of {@link Attribute}s held in state.
   */
  private final List<Attribute> attributes;

  /**
   * List of {@code ITag}s contained within this {@code Tag}.
   */
  private final List<ITag> subTags;

  /**
   * Create a new tag with no name, no attributes, and no subtags.
   */
  public Tag() {
    this.name = "";
    this.attributes = new ArrayList<>();
    subTags = new ArrayList<>();
  }

  @Override
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Given a null name");
    }
    this.name = name;
  }

  @Override
  public void addSubTag(ITag tag) {
    if (tag == null) {
      throw new IllegalArgumentException("Given a null tag to add");
    }
    this.subTags.add(tag);
  }

  @Override
  public void addAttribute(String name, String value) {
    if (name == null) {
      throw new IllegalArgumentException("Given a null attribute name");
    }
    if (value == null) {
      throw new IllegalArgumentException("Given a null attribute value");
    }
    if (name.isEmpty()) {
      throw new IllegalArgumentException("Name cannot be empty");
    }
    if (value.isEmpty()) {
      throw new IllegalArgumentException("Value cannot be empty");
    }
    this.attributes.add(new Attribute(name, value));
  }

  public List<Attribute> getAttributes() {
    return this.attributes;
  }

  @Override
  public List<ITag> getSubTags() {
    return this.subTags;
  }

  @Override
  public String toXml() {
    if (this.subTags.isEmpty() && this.attributes.isEmpty() && this.name.isEmpty()) {
      return "";
    } else {
      // Start with opening the tag
      StringBuilder sb = new StringBuilder("<");
      sb.append(this.name);

      // Add all the attributes
      for (Attribute attr : this.attributes) {
        sb.append(" ");
        sb.append(attr.toXml());
      }

      // If simple tag then it just ends
      if (this.subTags.isEmpty()) {
        sb.append("/>");
      } else {
        sb.append(">");

        // If not then add all the subtags
        for (ITag subTag : this.subTags) {
          sb.append("\n");
          sb.append(subTag.toXml());
        }

        sb.append("\n</").append(name).append(">");
      }
      return sb.toString();
    }
  }
}
