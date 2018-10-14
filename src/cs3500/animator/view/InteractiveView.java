package cs3500.animator.view;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * Represents a view that has buttons to pause, rewind, loop, display instructions, and
 * select/deselect shapes. Interacts with a controller who implements the actual functionality
 * of said buttons and updates the model accordingly.
 *
 * <p>CHANGE 04/21/18: changed the access modifiers for all instance variables from private to
 * protected.
 */
public class InteractiveView extends SwingView implements HybridView {
  /**
   * Actual view to output to.
   */
  protected final SwingView swingView;

  /**
   * The buttons on the button panel of this interactive view.
   */
  protected final List<JButton> controlButtons;

  /**
   * Checkbox to cause animation to loop.
   */
  protected JCheckBox loopingCheckBox;

  /**
   * Check boxes to select/deselect shapes in the animation.
   */
  protected final List<JCheckBox> checkBoxes;

  /**
   * Pane holding the checkboxes from right above.
   */
  protected final JScrollPane shapePane;

  /**
   * Create a new {@code InteractiveView} with the given map of actors, allowing them to be
   * rendered to the screen.
   *
   * @param actorHashMap Map of actors to be rendered to the screen.
   */
  public InteractiveView(LinkedHashMap<String, IVisitableActor> actorHashMap) {
    controlButtons = new ArrayList<>();

    this.swingView = new SwingView();

    // Create the panel for the button.
    JPanel buttonPanel = makeButtonPanel();

    // Add the button panel to the frame.
    this.swingView.add(buttonPanel, BorderLayout.SOUTH);

    // Add shape selection panel, make it a 3 by n grid.
    JPanel shapePanel = new JPanel(new GridLayout(actorHashMap.size() / 3 + 1,3));

    // Loop through all the shape selection check boxes.
    this.checkBoxes = new ArrayList<>();
    for (String k : actorHashMap.keySet()) {
      JCheckBox shapeCheckBox = new JCheckBox(k);
      shapeCheckBox.setActionCommand(k);
      shapeCheckBox.setSelected(true);
      this.checkBoxes.add(shapeCheckBox);
      shapePanel.add(shapeCheckBox);
    }
    // Make the panel big enough for the checkboxes.
    shapePanel.setPreferredSize(shapePanel.getPreferredSize());
    shapePanel.validate();

    this.shapePane = new JScrollPane(shapePanel);

    // Making a dimension that adjusts to the number of checkboxes but also has a minimum size,
    // so that the title doesn't get cut off.
    Dimension scrollPaneDim = shapePanel.getPreferredSize();
    scrollPaneDim.setSize(Math.max(scrollPaneDim.getWidth() + 30, 320), 500);
    this.shapePane.setPreferredSize(scrollPaneDim);

    this.shapePane.setVisible(true);
    this.swingView.pack();
  }

  /**
   * Adds the rewind, save, loop, and controls buttons to a panel and returns said panel.
   *
   * @return {@code JPanel} containing multiple buttons.
   */
  private JPanel makeButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());

    for (ControlButton b : ControlButton.values()) {
      JButton button = this.makeButton(b.getLabel(), b.getActionCommand());
      this.controlButtons.add(button);
      buttonPanel.add(button);
    }

    // Set up the loop button, adding its command/name
    this.loopingCheckBox = new JCheckBox("Loop animation");
    this.loopingCheckBox.setActionCommand("Looping");
    buttonPanel.add(this.loopingCheckBox);

    return buttonPanel;
  }

  /**
   * Create a flat, nice looking button whose label is the given text and whose
   * {@link java.awt.event.ActionEvent} is the given command.
   *
   * @param text Label for the button
   * @param command Command for the button to send
   * @return newly created button
   */
  private JButton makeButton(String text, String command) {
    JButton button = new JButton(text);
    button.setActionCommand(command);
    button.setForeground(Color.BLACK);
    button.setBackground(Color.WHITE);
    Border line = new LineBorder(Color.BLACK);
    Border margin = new EmptyBorder(5, 15, 5, 15);
    Border compound = new CompoundBorder(line, margin);
    button.setBorder(compound);
    return button;
  }

  @Override
  public void resetFocus() {
    this.swingView.setFocusable(true);
    this.swingView.requestFocus();
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    this.swingView.addKeyListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    for (JButton b : this.controlButtons) {
      b.addActionListener(listener);
    }

    this.loopingCheckBox.addActionListener(listener);
    for (JCheckBox checkBox : this.checkBoxes) {
      checkBox.addActionListener(listener);
    }
  }

  @Override
  public void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    this.swingView.setActors(actors);
  }

  @Override
  public void makeVisible() {
    this.swingView.makeVisible();
  }

  @Override
  public void outputActors() {
    this.swingView.outputActors();
  }

  @Override
  public void writeSVG(Appendable ap, int tps, boolean looping,
                       LinkedHashMap<String, IVisitableActor> actorMap) {
    // Conditionally create a normal or looping SvgView depending on what the user
    // selects.
    IView svg;
    if (looping) {
      svg = new LoopingSvgView(ap, tps);
    }
    else {
      svg = new SvgView(ap, tps);
    }
    svg.setActors(actorMap);
    try {
      svg.outputActors();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void instructionsPopup() {
    JOptionPane.showMessageDialog(this.swingView,
            "SPACE BAR: Start/Pause/Resume Animation\n"
                    + "UP-ARROW KEY: Increase Speed\n"
                    + "DOWN-ARROW KEY: Decrease Speed\n",
            "  Controls",
            JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void saveSvgPopup(LinkedHashMap<String, IVisitableActor> actorCopies) {
    //creating components to place onto the JOptionPane pop-up.
    JTextField outputFile = new JTextField();
    JTextField tps = new JTextField();
    JCheckBox loopBack = new JCheckBox("Loopback?");
    Object[] components = {"Output file name:", outputFile, "Ticks-per-second:", tps, loopBack};

    int option =
            JOptionPane.showConfirmDialog(this.swingView, components, "Save animation to SVG",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    String file = outputFile.getText();
    String ticks = tps.getText();

    int tpsInt;
    try {
      tpsInt = Integer.parseInt(ticks);
    } catch (NumberFormatException e) {
      tpsInt = -1;
    }

    if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
      return;
    }

    // Get rid of invalid file names, namely ones containing ?:/\"*<>|
    while (file.matches(".*([:|<>?*\"/\\\\]).*") || tpsInt <= 0) {
      // Display the text "invalid file name"
      ArrayList<Object> newComps = new ArrayList<>(Arrays.asList(components));
      newComps.add("Error: Invalid file name or ticks per second");

      //Do it again
      option = JOptionPane.showConfirmDialog(
              this.swingView,
              newComps.toArray(),
              "Save animation to SVG",
              JOptionPane.OK_CANCEL_OPTION,
              JOptionPane.PLAIN_MESSAGE);

      if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
        return;
      }
      file = outputFile.getText();
      ticks = tps.getText();

      try {
        tpsInt = Integer.parseInt(ticks);
      } catch (NumberFormatException e) {
        tpsInt = -1;
      }

    }
    //using the user input to write to the SVG file that the user wants the output to be printed to.
    FileWriter fileWriter = null;
    if (option == JOptionPane.OK_OPTION) {
      try {
        fileWriter = new FileWriter(outputFile.getText());
        this.writeSVG(fileWriter, Integer.parseInt(tps.getText()), loopBack.isSelected(),
                actorCopies);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    //closing the file writer if there was a valid output file name provided.
    if (fileWriter != null) {
      try {
        fileWriter.close();
      } catch (IOException e) {
        System.exit(-1);
      }
    }
  }

  @Override
  public void showShapeSelectionPane() {
    JOptionPane.showMessageDialog(this.swingView, this.shapePane, " Select shapes to display",
            JOptionPane.PLAIN_MESSAGE);
  }

}
