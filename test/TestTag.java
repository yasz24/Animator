import org.junit.Before;
import org.junit.Test;

import cs3500.animator.view.xml.Attribute;
import cs3500.animator.view.xml.ITag;
import cs3500.animator.view.xml.Tag;

import static org.junit.Assert.assertEquals;

/**
 * Tests classes in the {@link cs3500.animator.view.xml} package.
 */
public class TestTag {

  ITag tag;

  @Before
  public void initialize() {
    tag = new Tag();
  }

  @Test
  public void testTags() {
    assertEquals("Empty tag should be empty", "", tag.toXml());
    tag.setName("name");

    assertEquals("Adding name", "<name/>", tag.toXml());

    tag.addAttribute("test", "1");
    assertEquals("Adding attribute test", "<name test=\"1\"/>", tag.toXml());

    tag.addAttribute("test2", "2");
    assertEquals("Adding second attribute",
            "<name test=\"1\" test2=\"2\"/>", tag.toXml());

    ITag subTag = new Tag();
    subTag.setName("subtag");
    subTag.addAttribute("subattr", "sub1");
    tag.addSubTag(subTag);
    assertEquals("Adding subtag",
            "<name test=\"1\" test2=\"2\">\n"
            + "<subtag subattr=\"sub1\"/>\n"
            + "</name>", tag.toXml());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testAddAttrNameNull() {
    tag.addAttribute(null, "1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAttrValNull() {
    tag.addAttribute("test", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAttrNameEmpty() {
    tag.addAttribute("", "1");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddAttrValEmpty() {
    tag.addAttribute("test", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameNull() {
    tag.setName(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetAttrNameNull() {
    Attribute attr = new Attribute(null, "blah");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetAttrValNull() {
    Attribute attr = new Attribute("blah", null);
  }

}
