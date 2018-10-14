package cs3500.animator.view;

import java.io.IOException;
import java.util.LinkedHashMap;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * This interface offers functionality that lets the user output the animation as plain text as well
 * as in SVG format.
 *
 * <p>Change 04/16/18: Changed HashMap to LinkedHashMap.
 *
 * <p>Change 04/16/18: Changed IActor to IVisitableActor.
 */
public interface AppendableView extends IView {

  @Override
  void setActors(LinkedHashMap<String, IVisitableActor> actors);

  @Override
  void outputActors() throws IOException;
}
