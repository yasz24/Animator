package cs3500.animator.view;

import java.awt.BorderLayout;
import java.util.LinkedHashMap;

import javax.swing.JSlider;

import cs3500.animator.model.action.IAction;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.changeEventListener.IChangeListener;

/**
 * This class represents an interactive view with an added feature: a Slider. The slider, allows
 * users to fast-forward and rewind the animation as they please.
 */
public class InteractiveViewWithSlider extends InteractiveView implements ScrubbableView {

  // The slider the user uses
  private JSlider slider;

  /**
   * Create a new {@code InteractiveView} with the given map of actors, allowing them to be
   * rendered to the screen.
   *
   * @param actorHashMap Map of actors to be rendered to the screen.
   */
  public InteractiveViewWithSlider(LinkedHashMap<String, IVisitableActor> actorHashMap) {
    super(actorHashMap);

    slider = new JSlider();
    slider.setMinimum(0);
    slider.setMaximum((int)this.getLastActionTime(actorHashMap));
    slider.setValue(1);
    this.swingView.add(slider, BorderLayout.NORTH);
    this.swingView.pack();
  }

  @Override
  public void addChangeListener(IChangeListener listener) {
    this.slider.addChangeListener(listener);
  }

  @Override
  public JSlider getSlider() {
    return slider;
  }

  /**
   * Gets the very last tick when any action in this animation occurs.
   *
   * @return very last tick of the animation.
   */
  private long getLastActionTime(LinkedHashMap<String, IVisitableActor> actorMap) {
    long endTime = 0;

    for (String name : actorMap.keySet()) {
      IActor curAct = actorMap.get(name);

      // Find the last action time
      for (IAction action : curAct.getActions()) {
        if (action.getEndTime() > endTime) {
          endTime = action.getEndTime();
        }
      }

      // Find the last actor disappear time
      if (curAct.getDisappearTime() > endTime) {
        endTime = curAct.getDisappearTime();
      }
    }
    return endTime;
  }
}
