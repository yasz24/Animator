package cs3500.animator.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.provider.model.EasyAnimatorOperations;
import cs3500.animator.provider.model.IAAnimation;
import cs3500.animator.provider.model.IAShape;
import cs3500.animator.view.visitors.IAAnimationAdaptVistor;
import cs3500.animator.view.visitors.IAShapeAdapterVisitor;
import cs3500.animator.view.visitors.ModelVisitor;


/**
 * Wraps an IAnimatorModel and adapts it to behave like an EasyAnimatorOperations. The way
 * this works is that internally the model being used is our own IAnimatorModel, but this class
 * implements their model interface. So, whenever the controller(in the provider's code also their
 * View) queries EasyAnimatorOperations for shapes and animations, it expects IAShapes and
 * IAAnimations. But our internal model only has IActors and IActions. So we wrap these in
 * adapters that implement IAShape and IAAnimations respectively and send them back like that, when
 * queried.
 */
public class EzAnimatorOpsAdapter
        implements EasyAnimatorOperations<IAShape, IAAnimation> {
  /**
   * Our model that we're wrapping.
   */
  private IAnimatorModel ourModel;

  /**
   * boolean flag indicating whether the animation has started.
   */
  private boolean hasAnimationStarted;

  /**
   * The current tick of this animation.
   */
  private int tick;

  /**
   * Constructor for the EzAnimatorOpsAdpater.
   *
   * @param model our IAnimatorModel that needs to be adapted to an EasyAnimatorOperations.
   */
  public EzAnimatorOpsAdapter(IAnimatorModel model) {
    ourModel = model;
    hasAnimationStarted = true;
    tick = 0;
  }

  @Override
  public List<IAShape> getShapes() {
    List<IAShape> iaShapeList = new ArrayList<>();
    Map<String, IVisitableActor> actors = ourModel.getActorCopies();
    for (String s : actors.keySet()) {
      iaShapeList.add(this.makeIAShape(actors.get(s)));
    }
    return iaShapeList;
  }

  @Override
  public List<IAAnimation> getAnimations() {
    List<IAAnimation> allAnimations = new ArrayList<>();
    Map<String, IVisitableActor> actors = ourModel.getActorCopies();
    for (String s : actors.keySet()) {
      List<IVisitableAction> actorActions = actors.get(s).getActions();
      for (IVisitableAction a : actorActions) {
        // Use visitor to get the IAAnimations
        IVisitableActor actor = actors.get(s);
        ModelVisitor<IAAnimation> animationVisitor = new IAAnimationAdaptVistor(actor);
        animationVisitor.visit(a);
        allAnimations.add(animationVisitor.getResult());
      }
    }
    return allAnimations;
  }

  @Override
  public boolean animationStarted() {
    return this.hasAnimationStarted;
  }

  @Override
  public int currentTick() {
    return this.tick;
  }

  @Override
  public void updateTick(double x) {
    this.tick += x;
  }

  @Override
  public void startAnimator() {
    this.hasAnimationStarted = true;
    this.tick = 0;
  }

  @Override
  public void addShape(IAShape shape) {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public void removeShape(IAShape shape) {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public void addAnimation(IAAnimation anim) {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public void removeAnimation(IAAnimation anim) {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public String getAnimatorState() {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public ArrayList<IAShape> getUpdatedShapes() {
    ArrayList<IAShape> iaShapes = new ArrayList<>();
    Map<String, IVisitableActor> actorMap = ourModel.getActorCopies();
    for (String s : actorMap.keySet()) {
      iaShapes.add(this.makeIAShape(actorMap.get(s)));
    }
    return iaShapes;
  }

  @Override
  public int getTotal() {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public void deAnimate(String name) {
    throw new UnsupportedOperationException("Unused method");
  }

  @Override
  public void reset() {
    ourModel.updateActors(0);
    this.hasAnimationStarted = false;
    this.tick = 0;
  }

  @Override
  public Map<IAShape, Boolean> getMap() {
    Map<IAShape, Boolean> iaShapeBooleanMap = new LinkedHashMap<>();
    Map<String, IVisitableActor> actorMap = ourModel.getActorCopies();
    for (String s : actorMap.keySet()) {
      IVisitableActor ourActor = actorMap.get(s);
      iaShapeBooleanMap.put(this.makeIAShape(ourActor), ourActor.isVisible());
    }
    return iaShapeBooleanMap;
  }

  @Override
  public int getEndTick() {
    int lastTick = 0;
    Map<String, IVisitableActor> actorMap = ourModel.getActorCopies();
    for (String s : actorMap.keySet()) {
      if (lastTick < actorMap.get(s).getDisappearTime()) {
        lastTick = (int) actorMap.get(s).getDisappearTime();
      }
    }
    return lastTick;
  }

  @Override
  public void toggleShape(int i) {
    ArrayList<IActor> actors = new ArrayList<>();
    Map<String, IVisitableActor> actorMap = ourModel.getActorCopies();
    for (String s : actorMap.keySet()) {
      actors.add(actorMap.get(s));
    }
    actors.get(i).toggleVisibility();
  }

  /**
   * Wraps a given IVisitableActor, in the appropriate AShapeAdapter, using a
   * IAShapeAdapterVisitor object.
   *
   * @param actor the IVisitableActor to be adapted.
   * @return an IAShape.
   */
  private IAShape makeIAShape(IVisitableActor actor) {
    ModelVisitor<IAShape> shapeVisitor = new IAShapeAdapterVisitor(actor);
    shapeVisitor.visit(actor);
    return shapeVisitor.getResult();
  }
}
