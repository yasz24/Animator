package cs3500.animator.view.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.visitors.ModelVisitor;
import cs3500.animator.view.visitors.SwingViewVisitor;

/**
 * This class represents the Panel on which the shapes being drawn are displayed.
 * It essentially loops through given actors and renders them.
 * This panel is to be placed on a class extending JFrame.
 */
public class AnimationPanel extends JPanel implements IAnimationPanel {

  /**
   * Actors to be rendered.
   */
  private List<IVisitableActor> actors;

  /**
   * Create a new {@code AnimationPanel} with no {@code IVisitableActor}s.
   */
  public AnimationPanel() {
    this.actors = new ArrayList<>();
  }

  @Override
  public void setActors(List<IVisitableActor> list) {
    if (list == null) {
      throw new IllegalArgumentException("Given a null list");
    }
    this.actors = list;
  }

  @Override
  protected void paintComponent(Graphics g) {
    //ensures all functionality of the paint component method in the super class is still
    //implemented.
    super.paintComponent(g);

    Graphics2D g2d =  (Graphics2D) g;

    ModelVisitor<Graphics2D> visitor = new SwingViewVisitor(g2d);

    // Loop through all the actors and render them if they're visible.
    for (IActor a : actors) {
      if (a.isVisible()) {
        visitor.visit(a.getShape());
      }
    }
  }
}
