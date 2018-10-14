package cs3500.animator.view;

import java.util.LinkedHashMap;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * Creates an {@code IView} according to the specified viewType. Current options for viewtype are
 * "visual", "text", "interactive", "interact_slider" and "svg".
 * <p>Change 04/24/18: added case: INTERACT_SLIDER.
 */
public class AnimationViewCreator {

  /**
   * Static class so no instantiating.
   */
  private AnimationViewCreator() {
  }

  /**
   * This factory method creates the view of the appropriate kind based on {@code viewType}
   * parameter passed in.
   *
   * @param viewType     The input viewType passed.
   * @param ap           the appendable object that the view corresponds to.
   * @param actorHashMap the map of all actors in the animation
   * @param tps          number of ticks per second to output at
   * @return The appropriate IView corresponding to the {@code viewType}
   * @throws IllegalArgumentException If a valid view cannot be created.
   */
  public static IView create(ViewType viewType, Appendable ap,
                             LinkedHashMap<String, IVisitableActor> actorHashMap, int tps)
          throws IllegalArgumentException {

    if (viewType == null) {
      throw new IllegalArgumentException("Given a null viewtype");
    }

    switch (viewType) {
      case VISUAL:
        return new SwingView();
      case TEXT:
        return new TextView(ap, tps);
      case INTERACTIVE:
        return new InteractiveView(actorHashMap);
      case INTERACT_SLIDER:
        return new InteractiveViewWithSlider(actorHashMap);
      case SVG:
        return new SvgView(ap, tps);
      default:
        throw new IllegalArgumentException("Invalid view type specified");
    }
  }
}
