package cs3500.animator.view;

/**
 * Provides an easy interface to add additional view types. Simply add a new entry to
 * {@code ViewType} and add cases to the corresponding switch statements.
 */
public enum ViewType {
  VISUAL("visual"), TEXT("text"), INTERACTIVE("interactive"), INTERACT_SLIDER("interact_slider"),
  SVG("svg"), PROVIDER("provider"), PROVIDER_VISUAL("provider_visual"),
  PROVIDER_TEXTUAL("provider_textual"), PROVIDER_SVG("provider_svg");

  private final String type;

  ViewType(String type) {
    this.type = type;
  }

  /**
   * Returns the type string of this {@code ViewType}.
   *
   * @return string representing the type of this {@code ViewType}
   */
  public String getType() {
    return this.type;
  }

  public static ViewType validateType(String str) {
    return ViewType.valueOf(str.trim().toUpperCase());
  }
}
