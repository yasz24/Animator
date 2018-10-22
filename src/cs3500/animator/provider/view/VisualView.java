package cs3500.animator.provider.view;

import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;

import javax.swing.JFrame;

import java.awt.Button;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * Represents the animation as an actual rendering.
 */
public class VisualView extends JFrame implements IView<Void, IAShape, IAAnimation> {

  private int tickRate;
  private boolean shouldLoop;
  //the animation panel is called draw.
  private Draw o;
  private ArrayList<String> buttonNames;
  private ArrayList<Button> buttons;
  private int currentShape;

  /**
   * Constructor. Creates an instance of the visual cs3500.animator.provider.view.
   *
   * @param tickRate ticks per second
   */
  public VisualView(int tickRate) {
    super("Animations");
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.tickRate = tickRate;
    this.shouldLoop = false;
    this.setVisible(true);
    this.setLayout(new BorderLayout());
    this.buttonNames = new ArrayList<>();
    buttonNames.add("Quit");
    buttonNames.add("Pause");
    buttonNames.add("Resume");
    buttonNames.add("Loop on");
    buttonNames.add("Loop off");
    buttonNames.add("Reset");
    buttonNames.add("Remove Shape");
    this.buttons = new ArrayList<>();

  }

  @Override
  public Void render(EasyAnimatorOperations<IAShape, IAAnimation> model) {

    int x = 900;
    int y = 20;
    for (String s : this.buttonNames) {

      Button b = new Button(s);
      b.setActionCommand(s);
      b.setBounds(x, y, 75, 20);

      y += 25;

      this.add(b);
      this.buttons.add(b);

    }

    String z = "Current Shape: " + model.getShapes().get(this.currentShape).getName();
    Button b = new Button(z);
    b.setActionCommand(z);
    b.setBounds(25, 20, 200, 20);
    this.add(b);
    this.o = new Draw(model, this.tickRate);
    o.setSize(1000, 1000);
    this.add(o);
    o.drawing();
    return null;

  }

  @Override
  public double convertTicks(int tick) {
    return tick / this.tickRate;
  }

  @Override
  public void quit() {

    this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

  }

  @Override
  public void togglePause(boolean toggle) {

    this.o.pause(toggle);

  }

  @Override
  public void toggleLoop(String toggle) {

    if (toggle.equals("On")) {

      this.shouldLoop = true;
      this.o.updateShouldLoop(this.shouldLoop);

    } else if (toggle.equals("Off")) {

      this.shouldLoop = false;
      this.o.updateShouldLoop(this.shouldLoop);

    } else {

      throw new IllegalArgumentException("Options are On/Off");

    }

  }

  @Override
  public void reset() {

    this.o.reset();
  }

  @Override
  public void addListener(ActionListener k) {

    for (Button b : this.buttons) {

      b.addActionListener(k);

    }

  }

  @Override
  public int currentShape() {
    return this.currentShape;
  }

  @Override
  public void increment() {

    this.currentShape += 1;

  }

}
