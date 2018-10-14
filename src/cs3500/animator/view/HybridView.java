package cs3500.animator.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import cs3500.animator.model.actor.IVisitableActor;

/**
 * This view essentially combines operations from the {@code GraphicalView} and
 * {@code AppendableView}, in that it produces a swing based animation as well as has capability of
 * letting the user to save the animation in SVG format. The methods in this interface facilitate
 * interactivity with the user through the medium of key events and button clicks.
 */
public interface HybridView extends IView {
  @Override
  void resetFocus();

  @Override
  void addKeyListener(KeyListener listener);

  @Override
  void addActionListener(ActionListener listener);

  @Override
  void showShapeSelectionPane();

  @Override
  void instructionsPopup();

  @Override
  void saveSvgPopup(LinkedHashMap<String, IVisitableActor> actorCopies);

//  @Override
//  JFrame getFrame();

  @Override
  void writeSVG(Appendable ap, int tps, boolean looping,
                LinkedHashMap<String, IVisitableActor> actorHashMap);
}
