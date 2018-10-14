package cs3500.animator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.panel.AnimationPanel;

/**
 * Represents an implementation of {@link GraphicalView} that uses the Swing library to draw
 * actors to the computer screen. Extends JFrame as to be drawable.
 *
 * <p>Change 04/16/18: Changed HashMap to LinkedHashMap.
 *
 * <p>Change 04/16/18: Changed IActor to IVisitableActor.
 */
public class SwingView extends JFrame implements GraphicalView {

  // The panel that the SwingView draws to
  protected AnimationPanel animationPanel;

  /**
   * Creates a new SwingView with default parameters.
   */
  public SwingView() {
    super();
    this.setTitle("Animations!");
    this.setSize(1200, 1200);
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    //using a borderlayout with drawing panel in center.
    this.setLayout(new BorderLayout());
    animationPanel = new AnimationPanel();
    animationPanel.setPreferredSize(new Dimension(1200,1200));

    //adding the animation panel to the frame and wrapping it in a scrollPane.
    this.add(animationPanel,BorderLayout.CENTER);
    JScrollPane scrollPane = new JScrollPane(animationPanel);
    scrollPane.setPreferredSize(new Dimension(1200, 1200));
    this.getContentPane().add(scrollPane);
    this.pack();
  }

  @Override
  public void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    List<IVisitableActor> actorsList = new ArrayList<>(actors.values());
    animationPanel.setActors(actorsList);
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void outputActors() {
    this.repaint();
  }

}
