package cs3500.animator.view.xml;

import java.util.List;

import cs3500.animator.view.SvgView;

/**
 * Represents a tag in XML. A tag contains {@link IAttribute}s and other {@code Tag}s. The purpose
 * of this class is to be able to easily build an internal representation of the xml for
 * {@link SvgView}, so that outputting it is easy.
 */
public interface ITag {

  /**
   * Sets the name of this {@code ITag}. The name is the part that comes right after the
   * tag opens<br>
   * e.g., in <br>
   * {@code <rect> </rect>},<br>
   *   "rect" is the name.
   *
   * @param name String containing name to set this {@code ITag}'s name
   * @throws IllegalArgumentException if given a null name
   */
  void setName(String name);

  /**
   * Adds the given tag as a sub-tag to this tag. This means the new tag is contained within
   * this tag. e.g. in {@code <svg> <rect> </rect> </svg>}, {@code rect} is a subtag of {@code svg}.
   *
   * @param tag Tag to add as a subtag
   * @throws IllegalArgumentException if given a null tag
   */
  void addSubTag(ITag tag);

  /**
   * Adds a new {@link Attribute} to the {@code ITag} with the specified name and value.
   *
   * @param name Name of the attribute
   * @param value Value of the attribute
   * @throws IllegalArgumentException if either of name or value are null
   */
  void addAttribute(String name, String value);

  /**
   * Returns a {@link List} of all the {@code Attributes} contained in this {@code Tag}.
   *
   * @return list of {@code Attributes}
   */
  List<Attribute> getAttributes();

  /**
   * Returns a {@link List} of all the subtags contained in this {@code Tag}.
   *
   * @return list of {@code Tag}s
   */
  List<ITag> getSubTags();

  /**
   * Recursively turns this {@code ITag}, all its attributed, and all its sub tags
   * into a string that holds the XML tag the {@code ITag} represents.
   *
   * @return String containing {@code ITag} as an xml tag
   */
  String toXml();
}
