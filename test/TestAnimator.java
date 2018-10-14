import org.junit.Before;
import org.junit.Test;


import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import cs3500.animator.model.action.IVisitableAction;
import cs3500.animator.model.actor.IVisitableActor;
import cs3500.animator.model.color.IColor;
import cs3500.animator.model.shape.IVisitableShape;
import cs3500.animator.view.ViewType;
import cs3500.animator.controller.GraphicsAnimController;
import cs3500.animator.controller.HybridAnimController;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.TextAnimController;
import cs3500.animator.model.BasicAnimatorModel;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.model.action.ColorAction;
import cs3500.animator.model.action.IAction;
import cs3500.animator.model.action.MoveAction;
import cs3500.animator.model.action.ScaleAction;
import cs3500.animator.model.actor.Actor;
import cs3500.animator.model.actor.IActor;
import cs3500.animator.model.color.Color;
import cs3500.animator.model.shape.Ellipse;
import cs3500.animator.model.shape.Position;
import cs3500.animator.model.shape.Rectangle;
import cs3500.animator.view.AnimationViewCreator;
import cs3500.animator.view.AppendableView;
import cs3500.animator.view.IView;
import cs3500.animator.view.InteractiveView;
import cs3500.animator.view.LoopingSvgView;
import cs3500.animator.view.SvgView;
import cs3500.animator.view.SwingView;
import cs3500.animator.view.TextView;
import cs3500.animator.view.buttonlistener.ButtonListener;
import cs3500.animator.view.buttonlistener.IButtonListener;
import cs3500.animator.view.keyboardlistener.IKeyBoardListener;
import cs3500.animator.view.keyboardlistener.KeyBoardListener;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests all the animator classes. Uses multiple methods
 */
public class TestAnimator {


  private IAnimatorModel animModel;
  private IAnimatorModel model1;
  private IVisitableShape rectangleR;
  private IVisitableShape ovalC;

  private IVisitableActor rectActor;
  private IVisitableActor ovalActor;

  /**
   * Sets up a basic animator.
   */
  @Before
  public void initialize() {
    IColor red = new Color(1f, 0f, 0f);
    IColor green = new Color(0f, 1f, 0f);
    IColor blue = new Color(0f, 0f, 1f);


    animModel = new BasicAnimatorModel();
    model1 = new BasicAnimatorModel();

    rectangleR = new Rectangle(200d, 200d, 50d, 100d, red);
    ovalC = new Ellipse(500, 100, 60, 30, blue);

    IVisitableAction moveRect = new MoveAction(1, 50,
            new Position(200, 200),
            new Position(300, 350));

    IVisitableAction scaleRect = new ScaleAction(51, 90,
            50d, 100d, 200d, -20d);

    IVisitableAction rectColor = new ColorAction(50, 80, red, blue);

    IVisitableAction moveCirc = new MoveAction(10, 50,
            new Position(500, 100),
            new Position(60, 30));


    List<IVisitableAction> rectActions = new ArrayList<>();
    List<IVisitableAction> ovalActions = new ArrayList<>();

    rectActions.add(moveRect);
    rectActions.add(scaleRect);
    rectActions.add(rectColor);

    ovalActions.add(moveCirc);

    rectActor = new Actor(rectangleR, rectActions, 1, 100, "R");
    ovalActor = new Actor(ovalC, ovalActions, 6, 100, "C");

    animModel.addActor(rectActor);
    animModel.addActor(ovalActor);

    model1.addActor(rectActor);
  }

  @Test
  public void testSvgViewOutput() throws IOException {
    Appendable ap = new StringBuilder();

    AppendableView t = new SvgView(ap, 10);

    t.setActors(this.animModel.getActorCopies());
    t.outputActors();
    assertEquals("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" fill=\"rgb"
            + "(255,0,0)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"4900.0ms\" from=\"200.0\" to="
            + "\"300.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"100.0ms\" dur=\"4900.0ms\" from=\"20"
            + "0.0\" to=\"350.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"5000.0ms\" dur=\"3000.0ms\" from=\"rgb(255,0"
            + ",0)\" to=\"rgb(0,0,255)\" fill=\"freeze\" attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"5100.0ms\" dur=\"3900.0ms\" from=\"5"
            + "0.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"5100.0ms\" dur=\"3900.0ms\" from=\"100.0\""
            + " to=\"-20.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"100.0ms\" dur=\""
            + "100.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"10000.0ms\""
            + " dur=\"100.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" fill=\"rgb(0,0"
            + ",255)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"4000.0ms\" from=\"500."
            + "0\" to=\"60.0\" fill=\"freeze\" attributeName=\"cx\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"1000.0ms\" dur=\"4000.0ms\" from=\"100.0\""
            + " to=\"30.0\" fill=\"freeze\" attributeName=\"cy\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"600.0ms\" dur=\""
            + "100.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"10000.0ms\""
            + " dur=\"100.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</ellipse>\n"
            + "</svg>", ap.toString());
  }

  @Test
  public void testTextOutput() throws IOException {
    Appendable ap = new StringBuilder();

    AppendableView t = new TextView(ap, 10);

    t.setActors(this.animModel.getActorCopies());
    t.outputActors();
    assertEquals("Shapes:\n"
            + "Name: R\n"
            + "Type: rectangle\n"
            + "Lower-left corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=0.1s\n"
            + "Disappears at t=10.0s\n\n"
            + "Name: C\n"
            + "Type: oval\n"
            + "Center: (500.0,100.0), X-Radius: 60.0, Y-Radius: 30.0, Color: (0.0,0.0,1.0)\n"
            + "Appears at t=0.6s\n"
            + "Disappears at t=10.0s\n\n"
            + "Shape R moves from (200.0,200.0) to (300.0,350.0) from t=0.1s to t=5.0s\n"
            + "Shape C moves from (500.0,100.0) to (60.0,30.0) from t=1.0s to t=5.0s\n"
            + "Shape R changes color from (1.0,0.0,0.0) to (0.0,0.0,1.0) from t=5.0s to t=8.0s\n"
            + "Shape R scales from Width: 50.0, Height: 100.0 to Width: 200.0, Height: -20.0 from "
            + "t=5.1s to t=9.0s\n", ap.toString());
  }

  @Test
  public void testAnimEquality() {
    IVisitableAction anim1 =
            new ColorAction( 0, 100,
                    new Color(1.f, 1.f, 1.f),
                    new Color(0.f, 0.f, 0.f));
    IVisitableAction anim2 = new ScaleAction(0, 100,
            150, 150, 200, 200);

    assertTrue(anim1.compatibleWith(anim2));

    // An animation should never be compatible with itself
    assertFalse(anim1.compatibleWith(anim1));


    IActor a = this.ovalActor.getCopy();
    this.ovalActor.update(30);
    assertNotEquals(a.toString(), ovalActor.toString());
  }

  // Test adding a null actor
  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    animModel = new BasicAnimatorModel();
    animModel.addActor(null);
  }

  // Test for start tick being higher than end tick
  @Test(expected = IllegalArgumentException.class)
  public void testBadEventTime() {
    IAction bad = new ScaleAction(100, 1,
            10, 100, 50, 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullTransformation() {
    IAction bad = new MoveAction(10, 30, null, new Position(10, 10));
  }

  @Test
  public void testTextAnimController() {
    DummyView dummyViewAppendable = new DummyView();
    assertEquals(0, dummyViewAppendable.getActors().size());
    IAnimationController<IAnimatorModel> textController =
            new TextAnimController(animModel, dummyViewAppendable);

    textController.start();
    assertEquals(2, dummyViewAppendable.getActors().size());
  }

  @Test
  public void testGraphicsAnimController() {
    DummyView dummyViewGraphical = new DummyView();
    assertEquals(0, dummyViewGraphical.getActors().size());
    IAnimationController<IAnimatorModel> graphicsController =
            new GraphicsAnimController(animModel, dummyViewGraphical, 1);

    graphicsController.start();
    assertEquals(2, dummyViewGraphical.getActors().size());
  }

  @Test
  public void testGetPositionForShape() {
    assertEquals(new Position(200, 200), rectangleR.getPosition());
    assertEquals(new Position(500, 100), ovalC.getPosition());
  }

  @Test
  public void testGetColorShapes() {
    assertEquals(1f, rectangleR.getColor().getR(), 0.01);
    assertEquals(0f, rectangleR.getColor().getB(), 0.01);
    assertEquals(0f, rectangleR.getColor().getG(), 0.01);
  }

  @Test
  public void testToStringShapes() {
    assertEquals("Type: rectangle\n"
            + "Lower-left corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)",
            rectangleR.toString());
    assertEquals("Type: oval\n"
                    + "Center: (500.0,100.0), X radius: 60.0, Y radius: 30.0, Color: (0.0,0.0,1.0)",
            ovalC.toString());

  }

  @Test
  public void testSetAndOutPutActors() throws IOException {
    Appendable ap = new StringBuilder();
    LinkedHashMap<String, IVisitableActor> actorHashMap = new LinkedHashMap<>();
    actorHashMap.put("rect", rectActor);
    IView textView = new TextView(ap, 1);
    try {
      textView.setActors(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null list of actors", e.getMessage());
    }
    textView.setActors(actorHashMap);
    assertEquals("", ap.toString());
    textView.outputActors();
    assertEquals("Shapes:\n"
            + "Name: R\n"
            + "Type: rectangle\n"
            + "Lower-left corner: (200.0,200.0), Width: 50.0, Height: 100.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=1.0s\n"
            + "Disappears at t=100.0s\n\n"
            + "Shape R moves from (200.0,200.0) to (300.0,350.0) from t=1.0s to t=50.0s\n"
            + "Shape R changes color from (1.0,0.0,0.0) to (0.0,0.0,1.0) from t=50.0s to t=80.0s\n"
            + "Shape R scales from Width: 50.0, Height: 100.0 to Width: 200.0, Height: -20.0 from "
            + "t=51.0s to t=90.0s\n", ap.toString());
  }

  @Test
  public void testAnimViewCreator() {
    assertEquals(false,
            AnimationViewCreator.create(
                    ViewType.VISUAL, new StringBuilder(), model1.getActorCopies(), 1)
                    instanceof TextView);
    assertEquals(true,
            AnimationViewCreator.create(
                    ViewType.VISUAL, new StringBuilder(), model1.getActorCopies(),1)
                    instanceof SwingView);
    assertEquals(true,
            AnimationViewCreator.create(
                    ViewType.TEXT, new StringBuilder(), model1.getActorCopies(),1)
                    instanceof TextView);
    assertEquals(false,
            AnimationViewCreator.create(
                    ViewType.TEXT, new StringBuilder(), model1.getActorCopies(),1)
                    instanceof SvgView);
    assertEquals(true,
            AnimationViewCreator.create(
                    ViewType.SVG, new StringBuilder(), model1.getActorCopies(),1)
                    instanceof SvgView);
    assertEquals(false,
            AnimationViewCreator.create(
                    ViewType.VISUAL, new StringBuilder(), model1.getActorCopies(),1)
                    instanceof TextView);
    try {
      AnimationViewCreator.create(null, new StringBuilder(), model1.getActorCopies(),
              1);
    } catch (IllegalArgumentException e) {
      assertEquals("Given a null viewtype", e.getMessage());
    }
  }

  @Test
  public void testBuilder() {
    BasicAnimatorModel.Builder builder = new BasicAnimatorModel.Builder();
    builder.addRectangle("Rect", 20, 40, 50, 70, 1, 0, 0, 1, 50);
    builder.addOval("IOval", 20, 60, 50, 70, 0, 1, 0, 1, 70);
    builder.addMove("Rect", 20, 40, 50, 60, 20, 30);
    builder.addColorChange("IOval", 0, 1, 0, 1, 0, 0, 50, 70);
    builder.addScaleToChange("Rect", 50, 70, 40, 50, 30, 40);
    IAnimatorModel model3 = builder.build();

    assertEquals("Shapes:\n"
            + "Name: Rect\n"
            + "Type: rectangle\n"
            + "Lower-left corner: (20.0,40.0), Width: 50.0, Height: 70.0, Color: (1.0,0.0,0.0)\n"
            + "Appears at t=1\n"
            + "Disappears at t=50\n"
            + "Name: IOval\n"
            + "Type: oval\n"
            + "Center: (20.0,60.0), X radius: 50.0, Y radius: 70.0, Color: (0.0,1.0,0.0)\n"
            + "Appears at t=1\n"
            + "Disappears at t=70", model3.toString().substring(0, 279));
  }


  @Test
  public void testUpdateActor() {
    rectActor.update(30);
    assertEquals(new Position(259.18367, 288.77551),
            rectActor.getCopy().getShape().getPosition());
    rectActor.update(40);
    assertEquals(new Position(279.59183,319.38775),
            rectActor.getCopy().getShape().getPosition());

    ovalActor.update(20);
    assertEquals(new Position(390, 82.5),
            ovalActor.getCopy().getShape().getPosition());
    ovalActor.update(50);
    assertEquals(new Position(60, 30),
            ovalActor.getCopy().getShape().getPosition());
  }

  //tests that the Hybrid view works correctly with timer events
  @Test
  public void testHybridViewControllerTimerEvents() {
    IView view = new InteractiveView(animModel.getActorCopies());
    int tps = 50;
    HybridAnimController hybrid = new HybridAnimController(animModel, view, tps);
    Timer timer = new Timer(1000 / tps, hybrid);
    timer.setActionCommand("Timer");
    ActionEvent e = new ActionEvent(timer, 1, "Timer");
    assertEquals(new Position(200, 200),
            animModel.getActorCopies().get("R").getShape().getPosition());

    //test the actionPerformed() method to see if the controller is doing what it is supposed to
    // and everything is wired up correctly.

    //increases tick to 1.
    hybrid.actionPerformed(e);

    //increases tick to 2
    hybrid.actionPerformed(e);

    assertEquals(new Position(204.08163, 206.12244),
            animModel.getActorCopies().get("R").getShape().getPosition());
  }

  //Test the wiring up of the check boxes for shape selection, done by the HybridController, to
  // ensure that they listen to the appropriate actions and have
  // the desired effect on the animation.
  @Test
  public void testConfigureButtonListenerMethodShapeSelection() {
    JCheckBox rectBox = new JCheckBox("R");
    rectBox.setActionCommand("R");
    rectBox.setSelected(true);

    //map of code snippet to run when checkBox is clicked
    Map<String, Runnable> buttonClickedActions = new HashMap<>();
    buttonClickedActions.put("R", () -> animModel.getActorCopies().get("R").toggleVisibility());

    //configuring button listener
    IButtonListener listener = new ButtonListener();
    listener.setButtonClickedActionMap(buttonClickedActions);

    //actor should be visible at tick: 1 + appearTime.
    animModel.getActorCopies().get("R")
            .update(animModel.getActorCopies().get("R").getAppearTime() + 1);

    //check box hasn't been unchecked so shape is visible.
    assertEquals(true, animModel.getActorCopies().get("R").isVisible());

    //creating action event that simulates a checkBox click.
    ActionEvent e = new ActionEvent(rectBox, 1, "R");

    //should uncheck the check box
    listener.actionPerformed(e);

    //test to see if the shape is no more visible.
    assertEquals(false, animModel.getActorCopies().get("R").isVisible());
  }


  //Hitting the rewind button should
  @Test
  public void testConfigureButtonListenerRewindButton() {
    JButton rewind = new JButton("Rewind");
    rewind.setActionCommand("Rewind Button");

    //map of code snippet to run when Rewind button is clicked
    Map<String, Runnable> buttonClickedActions = new HashMap<>();
    buttonClickedActions.put("Rewind Button", () -> animModel.updateActors(1));

    //configure button listener
    IButtonListener listener = new ButtonListener();
    listener.setButtonClickedActionMap(buttonClickedActions);

    //rectActor updated to its state on tick 20.
    animModel.getActorCopies().get("R")
            .update(20);
    assertEquals(new Position(238.77551, 258.16326),
            animModel.getActorCopies().get("R").getShape().getPosition());

    //action event that simulates the clicking of the rewind button
    ActionEvent e = new ActionEvent(rewind, 1, "Rewind Button");

    //clicks the rewind button
    listener.actionPerformed(e);

    //model should be updated to its state on tick: 1. So should rectActor.
    assertEquals(new Position(200, 200), animModel.getActorCopies().get("R").getShape()
            .getPosition());
  }


  @Test
  public void testSVGWithLooping() {
    Appendable ap = new StringBuilder();
    LinkedHashMap<String, IVisitableActor> actorHashMap = new LinkedHashMap<>();
    actorHashMap.put("rect", rectActor);
    IView loopingSVGView = new LoopingSvgView(ap, 1);
    try {
      loopingSVGView.setActors(null);
    } catch (IllegalArgumentException e) {
      assertEquals("Given null map of actors", e.getMessage());
    }
    loopingSVGView.setActors(actorHashMap);
    assertEquals("", ap.toString());
    try {
      loopingSVGView.outputActors();
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect>\n"
            + "<animate id=\"__base__\" begin=\"0;__base__.end\" dur=\"100000.0ms\" attributeName="
            + "\"visibility\" from=\"hidden\" to=\"hidden\"/>\n"
            + "</rect>\n"
            + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" fill"
            + "=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms\""
            + " from=\"200.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms"
            + "\" from=\"200.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms\" "
            + "from=\"50.0\" to=\"50.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms\""
            + " from=\"100.0\" to=\"100.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms\" "
            + "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"1000.0ms\""
            + " from=\"hidden\" to=\"hidden\" fill=\"freeze\" attributeName=\"visibility\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+1000.0ms\" dur=\"49000.0ms\""
            + " from=\"200.0\" to=\"300.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+1000.0ms\" dur=\"49000.0ms\""
            + " from=\"200.0\" to=\"350.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+50000.0ms\" dur=\"30000.0ms\""
            + " from=\"rgb(255,0,0)\" to=\"rgb(0,0,255)\" fill=\"freeze\" "
            + "attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+51000.0ms\" dur=\"39000.0ms\""
            + " from=\"50.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+51000.0ms\" dur=\"39000.0ms\""
            + " from=\"100.0\" to=\"-20.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__."
            + "begin+1000.0ms\" dur=\"1000.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__.begin+"
            + "100000.0ms\" dur=\"1000.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "</svg>", ap.toString());
  }

  //This is the method that gets called when the SVG button is hit.
  @Test
  public void testWriteSVGMethod() {
    Appendable ap = new StringBuilder();
    Appendable ap1 = new StringBuilder();
    LinkedHashMap<String, IVisitableActor> actorHashMap = animModel.getActorCopies();
    IView view = new InteractiveView(actorHashMap);
    IView view1 = new InteractiveView(actorHashMap);
    view.writeSVG(ap, 25, false, actorHashMap);

    //svg output for 25 tps with looping checkbox unchecked.
    assertEquals("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" "
            + "fill=\"rgb(255,0,0)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"40.0ms\" dur=\"1960.0ms\" from=\"200.0\" "
            + "to=\"300.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"40.0ms\" dur=\"1960.0ms\" from=\"200.0\" "
            + "to=\"350.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"2000.0ms\" dur=\"1200.0ms\" from=\"rgb(25"
            + "5,0,0)\" to=\"rgb(0,0,255)\" fill=\"freeze\" attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"2040.0ms\" dur=\"1560.0ms\" from=\"50.0\""
            + " to=\"200.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"2040.0ms\" dur=\"1560.0ms\" from=\"100.0\""
            + " to=\"-20.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"40.0ms\" dur=\""
            + "40.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"4000.0ms\" du"
            + "r=\"40.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" fill=\"rgb(0"
            + ",0,255)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"400.0ms\" dur=\"1600.0ms\" from=\"500.0\" "
            + "to=\"60.0\" fill=\"freeze\" attributeName=\"cx\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"400.0ms\" dur=\"1600.0ms\" from=\"100.0\" "
            + "to=\"30.0\" fill=\"freeze\" attributeName=\"cy\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"240.0ms\" "
            + "dur=\"40.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"4000.0ms\" "
            + "dur=\"40.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</ellipse>\n"
            + "</svg>", ap.toString());


    view1.writeSVG(ap1, 25, true, actorHashMap);
    //svg for 25 tps with with looping checkbox checked.
    assertEquals("<svg version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">\n"
            + "<rect>\n"
            + "<animate id=\"__base__\" begin=\"0;__base__.end\" dur=\"4000.0ms\" attribu"
            + "teName=\"visibility\" from=\"hidden\" to=\"hidden\"/>\n"
            + "</rect>\n"
            + "<rect id=\"R\" x=\"200.0\" y=\"200.0\" width=\"50.0\" height=\"100.0\" fill=\""
            + "rgb(255,"
            + "0,0)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"200.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"200.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"50.0\" to=\"50.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"100.0\" to=\"100.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"rgb(255,0,0)\" to=\"rgb(255,0,0)\" fill=\"freeze\" attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"hidden\" to=\"hidden\" fill=\"freeze\" attributeName=\"visibility\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+40.0ms\" dur=\"1960.0ms\" "
            + "from=\"200.0\" to=\"300.0\" fill=\"freeze\" attributeName=\"x\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+40.0ms\" dur=\"1960.0ms\" "
            + "from=\"200.0\" to=\"350.0\" fill=\"freeze\" attributeName=\"y\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+2000.0ms\" dur=\"1200.0ms\""
            + " from=\"rgb(255,0,0)\" to=\"rgb(0,0,255)\" fill=\"freeze\" attributeName=\"fil"
            + "l\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+2040.0ms\" dur=\"1560.0ms\""
            + " from=\"50.0\" to=\"200.0\" fill=\"freeze\" attributeName=\"width\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+2040.0ms\" dur=\"1560.0ms\" "
            + "from=\"100.0\" to=\"-20.0\" fill=\"freeze\" attributeName=\"height\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__.begin+"
            + "40.0ms\" dur=\"40.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__.beg"
            + "in+4000.0ms\" dur=\"40.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</rect>\n"
            + "<ellipse id=\"C\" cx=\"500.0\" cy=\"100.0\" rx=\"60.0\" ry=\"30.0\" fill=\"rgb("
            + "0,0,255)\" visibility=\"hidden\">\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" f"
            + "rom=\"500.0\" to=\"500.0\" fill=\"freeze\" attributeName=\"cx\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" f"
            + "rom=\"100.0\" to=\"100.0\" fill=\"freeze\" attributeName=\"cy\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"60.0\" to=\"60.0\" fill=\"freeze\" attributeName=\"rx\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"30.0\" to=\"30.0\" fill=\"freeze\" attributeName=\"ry\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\" "
            + "from=\"rgb(0,0,255)\" to=\"rgb(0,0,255)\" fill=\"freeze\" attributeName=\"fill\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+0.0ms\" dur=\"40.0ms\""
            + " from=\"hidden\" to=\"hidden\" fill=\"freeze\" attributeName=\"visibility\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+400.0ms\" dur=\"1600.0ms\""
            + " from=\"500.0\" to=\"60.0\" fill=\"freeze\" attributeName=\"cx\"/>\n"
            + "<animate attributeType=\"xml\" begin=\"__base__.begin+400.0ms\" dur=\"1600.0ms\" "
            + "from=\"100.0\" to=\"30.0\" fill=\"freeze\" attributeName=\"cy\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__."
            + "begin+240.0ms\" dur=\"40.0ms\" from=\"hidden\" to=\"visible\" fill=\"freeze\"/>\n"
            + "<animate attributeName=\"visibility\" attributeType=\"xml\" begin=\"__base__."
            + "begin+4000.0ms\" dur=\"40.0ms\" from=\"visible\" to=\"hidden\" fill=\"freeze\"/>\n"
            + "</ellipse>\n"
            + "</svg>", ap1.toString());
  }

  @Test
  public void testSpaceKeyEvent() {
    JButton b = new JButton();
    KeyEvent e = new KeyEvent(b, 1, 0, 0, KeyEvent.VK_SPACE, KeyEvent.CHAR_UNDEFINED);
    IView view = new InteractiveView(animModel.getActorCopies());
    DummyController controller = new DummyController(animModel, view, 5);
    IKeyBoardListener listener = new KeyBoardListener();

    Map<Integer, Runnable> map = new HashMap<>();

    map.put(KeyEvent.VK_SPACE, () -> {
      controller.stopTimer();
    });

    listener.setPressedKeyMap(map);
    view.addKeyListener(listener);


    assertEquals(false, controller.getTimer().isRunning());

    listener.keyPressed(e);

    assertEquals(true, controller.getTimer().isRunning());

  }


  /*
   * Test Should create a JOption pane with a message "Invalid Input File." because the
   * name of the input file isn't specified.
   *
  @Test
  public void testMain() {
    String[] input1 = {"-if", "-iv", "svg", "-speed", "100", "-o", "toh-output.svg"};
    EasyAnimator.main(input1);
  }
  */


  /*
   * Test Should create a JOption pane with a message "Invalid Input File." because the
   * name of the type of view isn't specified.
   *
  @Test
  public void testMain2() {
    String[] input1 = {"-if", "big-bang-big-crunch.txt", "-iv", "-speed", "100", "-o",
    "toh-output.svg"};
    EasyAnimator.main(input1);
  }
  */

  /*
   * Test Should create a JOption pane with a message "Invalid Input File." because the
   * name of the type of view isn't specified.
   *
  @Test
  public void testMain3() {
    String[] input1 = {"-if", "big-bang-big-crunch.txt", "-speed", "100", "-o", "toh-output.svg"};
    EasyAnimator.main(input1);
  }
  */

  /*
   * Test prints a text output of the animation with a speed of 100 tps to System.out, the default
   * output destination.
   *
  @Test
  public void testMain4() {
    String[] input1 = {"-if", "big-bang-big-crunch.txt", "-iv", "text" "-speed", "100", };
    EasyAnimator.main(input1);
  }
  */

  /*
   * Test prints an xml formatted output of the animation with a speed of 1 tps(default tick-rate)
   * to System.out, the default output destination.
   *
  @Test
  public void testMain4() {
    String[] input1 = {"-if", "big-bang-big-crunch.txt", "-iv", "svg"};
    EasyAnimator.main(input1);
  }
  */
}
