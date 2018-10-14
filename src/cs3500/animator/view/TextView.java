package cs3500.animator.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.view.visitors.ModelVisitor;
import cs3500.animator.view.visitors.TextViewVisitor;

/**
 * Outputs a human-readable reformatting of the animation. Starts by listing the shapes in the
 * model, then lists every action that is performed by order of starting time.
 */
public class TextView implements AppendableView {

  // Appendable to output to
  private final Appendable out;

  // Map of actors to output to text
  private LinkedHashMap<String, IVisitableActor> actors;

  // Ticks per second to output at, used for unit conversion
  private final int tps;

  /**
   * Creates a {@code Textview} that outputs to the given {@link Appendable}.
   *
   * @param out {@code Appendable} to output to
   * @param tps number of ticks per second in the model (used for converting ticks to seconds)
   * @throws IllegalArgumentException if tps less than 1 or out is null
   */
  public TextView(Appendable out, int tps) {
    if (out == null) {
      throw new IllegalArgumentException("Given null out Appendable");
    }
    if (tps <= 0) {
      throw new IllegalArgumentException("Given too small a tps");
    }
    this.tps = tps;
    this.out = out;
  }

  @Override
  public void outputActors() throws IOException {
    if (actors == null) {
      throw new IllegalArgumentException("Given a null map of actors");
    }
    this.out.append("Shapes:\n");


    List<Tuple> actions = new ArrayList<>();
    ModelVisitor<String> visitor = new TextViewVisitor(this.tps);
    for (String key : actors.keySet()) {
      IVisitableActor actor = actors.get(key);

      // Store all the actor's actions for printing at bottom of fileN
      for (IVisitableAction action : actor.getActions()) {
        actions.add(new Tuple(actor.getName(), action));
      }

      visitor.visit(actor);
    }
    this.out.append(visitor.getResult());

    Collections.sort(actions);

    // Output all the actions at the bottom of the file, sorted by starting time
    for (Tuple tup : actions) {
      this.out.append("Shape ").append(tup.getName()).append(" ");
      ModelVisitor<String> actionVisitor = new TextViewVisitor(this.tps);
      actionVisitor.visit(tup.getAction());
      this.out.append(actionVisitor.getResult());
    }
  }

  @Override
  public void setActors(LinkedHashMap<String, IVisitableActor> actors) {
    if (actors == null) {
      throw new IllegalArgumentException("Given a null list of actors");
    }
    this.actors = actors;
  }

  @Override
  public void makeVisible() {
    throw new UnsupportedOperationException("Unsupported operation");
  }

  /**
   * Represents an {@code IAction} paired with the name of the {@code IActor} it is linked to.
   * Used for sorting and outputting {@code IAction}s.
   */
  private static class Tuple implements Comparable<Tuple> {
    /**
     * Name of the {@code IAction}.
     */
    private final String name;

    /**
     * {@code IVisitableAction} linked to the given name.
     */
    private final IVisitableAction action;

    /**
     * Create a {@code Tuple} with the given {@code IActor} name and {@code IAction}.
     *
     * @param name   name of the {@code IActor} linked to given action
     * @param action {@code IAction} linked tot given name
     * @throws IllegalArgumentException if given null name or actionx
     */
    protected Tuple(String name, IVisitableAction action) {
      if (name == null) {
        throw new IllegalArgumentException("Given null name");
      }
      if (action == null) {
        throw new IllegalArgumentException("Given null action");
      }
      this.name = name;
      this.action = action;
    }

    /**
     * Returns the name of the {@code IActor} linked to the given {@code IAction} held in state.
     *
     * @return String containing the name of an {@code IActor}
     */
    protected String getName() {
      return this.name;
    }

    /**
     * Returns the {@code IVisitableAction} linked to the name held in state.
     *
     * @return {@code IVisitableAction} that is linked to the name in state
     */
    protected IVisitableAction getAction() {
      return this.action;
    }

    /**
     * A {@code Tuple} is ordered by the starting time of its {@code IVisitableAction}.
     * This lets us easily sort a list of {@code Tuples} in a way that can be outputted to a file.
     *
     * @param o {@code Tuple} to compare this {@code Tuple} with
     * @return result of comparing the {@code IVisitableAction}s held in state of both
     *         {@code Tuple}s
     */
    @Override
    public int compareTo(Tuple o) {
      return this.action.compareTo(o.action);
    }
  }
}
