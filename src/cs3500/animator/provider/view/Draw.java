package cs3500.animator.provider.view;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;

/**
 * Class the handles the creation of the visuals.
 */
public class Draw extends JPanel implements ActionListener {

  private EasyAnimatorOperations<IAShape, IAAnimation> model;
  private Timer t;
  private int tickPause;
  private boolean shouldLoop;
  private int tickSpeed;

  /**
   * Creates a Draw.
   *
   * @param model     cs3500.animator.provider.model to be drawn
   * @param tickSpeed ticks/second
   */
  public Draw(EasyAnimatorOperations<IAShape, IAAnimation> model, int tickSpeed) {
    super();
    this.model = model;
    this.t = new Timer(1000 / tickSpeed, this);
    this.tickPause = 1;
    this.shouldLoop = false;
    this.tickSpeed = tickSpeed;
  }

  /**
   * Calls the repaint method.
   */
  public void drawing() {

    this.repaint();

  }

  @Override
  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    this.t.start();
    for (IAShape a : this.model.getUpdatedShapes()) {

      for (IAShape b : this.model.getShapes()) {
        if (b.getName().equals(a.getName()) && this.model.getMap().get(b)) {
          a.updateDrawing(g);
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {

    if (model.currentTick() == model.getEndTick() && this.shouldLoop) {

      this.model.reset();
      this.reset();

    }

    for (IAAnimation a : this.model.getAnimations()) {

      if (a.getStart() <= model.currentTick() && a.getEnd() >= model.currentTick()) {

        IAShape shapeToUpdate = null;
        for (IAShape shap : model.getUpdatedShapes()) {

          if (shap.getName().equals(a.getAffected().getName())) {

            shapeToUpdate = shap;

          }

        }

        if (shapeToUpdate == null) {

          throw new IllegalArgumentException("Something went wrong");

        }

        a.update(a.getAffected(), model.currentTick(), shapeToUpdate);

      }
    }
    this.repaint();
    model.updateTick(this.tickPause);
  }

  /**
   * Pauses the animation, stopping all animations in progress where they stand.
   *
   * @param toggle Event that causes animation to pause
   */
  public void pause(boolean toggle) {

    if (toggle) {

      this.tickPause = 0;

    } else {

      this.tickPause = 1;

    }

  }

  /**
   * Resets the animation to its initial state.
   */
  public void reset() {

    this.t = new Timer(1000 / this.tickSpeed, this);

  }

  /**
   * Updates the boolean dependent on whether the user wants the animation to loopback.
   *
   * @param toggle the boolean value for whether the animation should loop
   */
  public void updateShouldLoop(boolean toggle) {

    this.shouldLoop = toggle;

  }
}
