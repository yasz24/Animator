package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.*;

import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.changeEventListener.IChangeListener;

/**
 * Represents a method of outputting the state of the model, whether to screen or to a file.
 * Is given an ordered map of the actors in the model and outputs it. All methods here
 * default to throwing an {@link UnsupportedOperationException} because some
 * sub-classes/sub-interfaces do not implement all methods, depending on whether they are
 * of graphical or appendable method of output.
 *
 * <p>Change 03/30/18: added resetFocus() method to allow swing based IViews to listen to keyboard
 * events.
 *
 * <p>Change 03/30/18: added addKeyListener() method to allow swing based IViews to listen to
 * keyboard events and react to them accordingly.
 *
 * <p>Change 03/30/18: added addActionListener() method to allow swing based IViews to listen to
 * Action events such as button clicks and checkboxes, and react to those action events accordingly.
 *
 * <p>Change 03/30/18: added shapeSelectionPanel() method to create an Option Pane that enables the
 * user to save the animation in svg format if they wanted to. Only relevant to Hybrid Views.
 *
 * <p>Change 03/30/18: added writeSVG() method that writes the animation based off of the given
 * parameters(actors, looping boolean-flag, tps) to the Appendable object passed in.
 *
 * <p>CHANGE 04/16/18: Changed IActor to IVisitableActor in all signatures.
 *
 * <p>Change 04/16/18: Changed IActor to IVisitableActor.
 *
 * <p>Change 04/16/18: Changed HashMap to LinkedHashMap.
 *
 * <p>Change 04/21/18: added the addChangeListener method() to support the slider.
 *
 * <p>Change 04/21/18: added the getSlider() method.
 */
public interface IView {
  /**
   * Make the view visible, if it is an {@code GraphicalView}. This is usually called
   * after the view is constructed.
   *
   * @throws UnsupportedOperationException unless overridden
   */
  default void makeVisible() {
    throw new UnsupportedOperationException("Method makeVisible() is unsupported");
  }

  /**
   * Outputs the {@code IActor}s held in state using the method implemented in the subclass.
   *
   * @throws IOException if the method of outputting
   */
  default void outputActors() throws IOException {
    throw new UnsupportedOperationException("Method outputActors() is unsupported");
  }

  /**
   * Sets the actors to be drawn on the Animation Panel.
   *
   * @param actors The Map of Actors retrieved from the model.
   */
  default void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    throw new UnsupportedOperationException("Method setActors() is unsupported");
  }

  /**
   * Reset the focus on the appropriate part of the view that has the keyboard listener attached
   * to it, so that keyboard events will still flow through.
   */
  default void resetFocus() {
    throw new UnsupportedOperationException("Method resetFocus() is unsupported");
  }

  /**
   * This is to force the view to have a method to set up the keyboard.
   * The name has been chosen deliberately. This is the same method signature to
   * add a key listener in Java Swing. The method is only relevant to the hybrid view
   * which can interact with user inputs such as key strokes.
   *
   * @param listener The keyboard listeners that listens to the users keyboard inputs
   */
  default void addKeyListener(KeyListener listener) {
    throw new UnsupportedOperationException("Method addKeyListner() is unsupported");
  }

  /**
   * This is to force the view to have a method to set up actions for buttons.
   * All the buttons must be given this action listener. The method is only relevant to the hybrid
   * view which will respond to the various buttons clicked by the user.
   * <br>Thus our Swing-based implementation of this interface will already have such a
   * method.
   *
   * @param listener The action listener.
   */
  default void addActionListener(ActionListener listener) {
    throw new UnsupportedOperationException("Method addActionListener() is unsupported");
  }

  /**
   * This methods makes the shapeSelectionPane visible, allowing the user to select and dis-select
   * Actors that are visible in the animation. Only relevant to a HybridView.
   */
  default void showShapeSelectionPane() {
    throw new UnsupportedOperationException("Method setShapeSelectionPanel() is unsupported");
  }

//  /**
//   * The method returns the frame on which the view is rendered. Only relevant to Graphical Views
//   * and Hybrid Views.
//   *
//   * @return the frame held by the {@code IView}
//   */
//  default JFrame getFrame() {
//    throw new UnsupportedOperationException("Method getFrame() is unsupported");
//  }

  /**
   * The method writes the animation in SVG format to the given appendable. Only relevant to
   * the Hybrid View.
   *
   * @param ap       the appendable that is to be written to.
   * @param tps      the ticks per second of the animation
   * @param looping  whether or not the svg animation should loop.
   * @param actorMap the map of actors that the svg view needs to render.
   */
  default void writeSVG(Appendable ap, int tps, boolean looping,
                        LinkedHashMap<String, IVisitableActor> actorMap) {
    throw new UnsupportedOperationException("Method writeSVG() is unsupported");
  }


  /**
   * Brings up a dialog that presents the option to save the given actorCopies
   * as an SVG file.
   *
   * @param actorCopies Actors to save
   */
  default void saveSvgPopup(LinkedHashMap<String, IVisitableActor> actorCopies) {
    throw new UnsupportedOperationException("Method saveSvgPopup() is unsupported");
  }

  /**
   * Brings up a text window that displays instructions for how to use the program.
   */
  default void instructionsPopup() {
    throw new UnsupportedOperationException("Method instructionsPopup() is unsupported");
  }

  /**
   * Gets the slider that enables the feature of scrubbing in IViews. Only Relevant to
   * ScrubbableViews.
   * @return the JSlider that lets the user scrub the animation.
   */
  default JSlider getSlider() {
    throw new UnsupportedOperationException("This IView does not support scrubbing");
  }

  /**
   * Add a changeListener to this IView so that it can listen to ChangeEvents. Only relevant to the
   * ScrubbableView where the slider listens to Change Events.
   * @param listener the IChangeListener that lets the Slider listen to Change Events.
   */
  default void addChangeListener(IChangeListener listener) {
    throw new UnsupportedOperationException("View does not support listening to Change Events");
  }
}
