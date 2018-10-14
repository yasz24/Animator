package cs3500.animator.view;

/**
 * Holds the labels and action commands for every button in the lower
 * panel of the animation. Makes it extremely easy to add a new button.
 */
public enum ControlButton {
  // Rewinds the animation to the beginning.
  REWIND("Rewind", "Rewind Button"),

  // Saves the animation to a SVG.
  SVG("Save", "SVG Button"),

  // Brings up the shape selection dialog.
  SELECT("Select shapes", "Select"),

  // Brings up the instructions dialog.
  INSTRUCT("Controls", "Instructions");

  private final String actionCommand;
  private final String label;

  /**
   * Constructor for the Control Button enum.
   */
  ControlButton(String label, String actionCommand) {
    this.actionCommand = actionCommand;
    this.label = label;
  }

  /**
   * Returns the displayed text on the button.
   *
   * @return the label
   */
  public String getLabel() {
    return this.label;
  }

  /**
   * Returns a {@code String} representing the action command sent when the button
   * is clicked.
   *
   * @return string with an actioncommand in it
   */
  public String getActionCommand() {
    return this.actionCommand;
  }
}
