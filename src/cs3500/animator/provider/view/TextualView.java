package cs3500.animator.provider.view;

import java.awt.event.ActionListener;

import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;

/**
 * Represents the animation as text that can be outputted to a file.
 */
public class TextualView implements IView<String, IAShape, IAAnimation> {

  private int tickRate;

  public TextualView(int tickRate) {
    this.tickRate = tickRate;
  }

  @Override
  public void quit() {
    // Unnecessary but defined by interface.
  }

  @Override
  public void togglePause(boolean toggle) {
    // Unnecessary but defined by interface.
  }

  @Override
  public void toggleLoop(String toggle) {
    // Unnecessary but defined by interface.
  }

  @Override
  public void reset() {
    // Unnecessary but defined by interface.
  }

  @Override
  public void addListener(ActionListener k) {
    //Do Nothing
  }

  @Override
  public int currentShape() {
    return 0;
  }

  @Override
  public void increment() {
    // Not needed for this instance.
  }

  @Override
  public double convertTicks(int tick) {
    return tick / this.tickRate;
  }

  @Override
  public String render(EasyAnimatorOperations<IAShape, IAAnimation> model) {
    String shape = "Shapes: \n";
    for (IAShape shap : model.getShapes()) {
      String temp = shap.toString();
      if (temp.contains("rectangle")) {
        temp = temp.replace("Lower-left corner", "Min-Corner");
      }
      temp = renderHelp(temp, true);
      shape = shape + temp + "\n\n";

    }

    for (IAAnimation anim : model.getAnimations()) {

      String temp = anim.toString();
      temp = renderHelp(temp, false);
      shape = shape + temp + "\n";

    }

    return shape;
  }

  private String renderHelp(String s, boolean flag) {

    int appTick;
    int disTick;

    if (flag) {

      appTick = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.indexOf("Disappears") - 1));
      disTick = Integer.parseInt(s.substring(s.lastIndexOf("=") + 1));

    } else {

      appTick = Integer.parseInt(s.substring(s.indexOf("=") + 1, s.lastIndexOf("t=") - 4));
      disTick = Integer.parseInt(s.substring(s.lastIndexOf("=") + 1));

    }

    double appTime = convertTicks(appTick);
    double disTime = convertTicks(disTick);
    String first = s.substring(0, s.indexOf("="));
    String second = s.substring(s.indexOf("="), s.lastIndexOf("="));
    String third = s.substring(s.lastIndexOf("="));
    second = second.replace(Integer.toString(appTick), Double.toString(appTime));
    third = third.replace(Integer.toString(disTick), Double.toString(disTime));
    return first + second + third;

  }
}
