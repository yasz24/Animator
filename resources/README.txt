~~~~~~~~~~~~~~~~~~
~~~~~~MODEL~~~~~~~
~~~~~~~~~~~~~~~~~~
Our model interface is IAnimatorModel, which provides basic functionality such as
adding and retrieving IActors and IActions, as well as providing the state of the model
at a given tick.

Our IActor
The IActor's two main components are their IShape and list of IActions
The IActor can update itself, control its visibility, and apply IActions to itself

Our IVisitableActor extends IActor
The IVisitableActor serves to hold all visitor methods for actors.

Our IShape
The IShape interface defines some shape such that can return a list of vertices to define itself
It can also have IActions applied to it

Our IVisitableShape extends IShape
The IVisitableShape serves to hold all visitor methods for shapes.

Our IAction
The IAction interface represents a transformation of some shape that occurs over a period of time,
beginning with a start tick and ending on an end tick.

Our IVisitableAction extends IAction
The IVisitableAction interface serves to hold all visitor methods for actions.

~~~~~~~~~~~~~~~~~~
~~~~~~VIEW~~~~~~~~
~~~~~~~~~~~~~~~~~~
Our IView
The IView interface gives the controller the ability to output data from the model.
It does this through two sub interfaces currently.
To add a new IView, just extend/implement one of the existing view classes/interfaces,
then add cases to the createController() method in EasyAnimator.java and create() method in
AnimationViewCreator.java.

Our GraphicalView interface takes the actors from the model once, but then is updated multiple
times and draws the updated actors to the screen.

Our SvgView interface takes the actors from the model once, then renders them to an svg file.

Our HybridView interface contains both an IView and SvgView. It renders the actors to the IView,
and when the user specifies, outputs the actors to a text file.
xml package:
The xml package includes a number of classes for representing the xml in an svg file

Our ITag
The ITag interface represents an xml tag. It has a name, recursive ITags within it, and IAttributes
within it also. It can recursively output itself and it's subtags.

Our IAttribute
The IAttribute interface represents one particular attribute of in an ITag.

~~~~~~~~~~~~~~~~~~
~~~~CONTROLLER~~~~
~~~~~~~~~~~~~~~~~~
Our IAnimationController
The IAnimationController interface is parameterized over any model type, and controls the entire
execution of the model and outputting to view. It handles exceptions within the model.

Our GraphicsAnimController
The GraphicsAnimController class controls models that output to screen. The main difference between
it and a TextAnimController is it calls the outputActors method on the model many times per second.

Our TextAnimController
The TextAnimController works with AppendableViews to output the model to screen. It only needs to
output once, unlike the GraphicsAnimController.

Our HybridAnimController
The HybridAnimController interface is similar to and extends the GraphicsAnimController, but
provides useful functionality to the end user. Namely, one can:
- Adjust the speed of the animation through the up-down arrow keys
- Pause the animation
- Rewind the animation
- Loop the animation
- Export the animation to an SVG
   - Export the animation to a looping SVG



Some design choices
We decided that we would have one unifying IView interface that defaults to unsupported methods
because we wanted to bring all the views under one interface, instead of segregating.
We decided that the ticks per second/looping state in the animation should be independent
of the ticks per second and looping of the outputted svg, since the user would likely want
to loop to examine the animation without actually outputting the animation looping.
We decided to put the buttons from the bottom of our window in a single enum for the view, but not
for the controller since the view and controller shouldn't be very coupled.

An image (description) of the UI can be found in 'interface.png'.
All features that were assigned in previous homeworks should function, including buttons, pausing, playing, increasing speed, and hybrid view.

To add our IView implementations to your code, you will need to implement all our shape interfaces so that the IView can see your shapes as ours.
You may also need to implement our color and position interfaces.

Changelog:
CHANGES 04/15-17/18
IAnimatorModel.java:
- Changed uses of IAction, IActor, IShape to IVisitableAction, IVisitableActor, IVisitableShape,
  so that client could use visitor methods.

IActor.java:
- Moved accept() to IVisitableActor.java
- Changed to use visitable-versions of IShape (IVisitableShape) and IAction (IVisitableAction)

AbstractActor.java:
- Deleted, functionality moved to subclasses and IVisitableActor.java

IVisitableActor.java:
- Changed to extend IActor.java
- Added getVisibleCopy() method

IColor.java:
- Created so that clients could re-implement without knowing about Color.java

IEllipse.java:
- Created so that clients could re-implement for visitors

IRectangle.java:
- Created so that clients could re-implement for visitors

IPosition.java:
- Created so that clients could re-implement

IAction.java:
- Moved accept() method to IVisitableAction.java

IVisitableAction.java:
- Changed to inherit from IAction
- Created getVisitableCopy() method
- Changed uses of IAction, IShape to IVisitableAction, IVisitableShape

IColorAction.java:
- Created so that clients could re-implement for visitors

IMoveAction.java:
- Created so that clients could re-implement for visitors

IScaleAction.java:
- Created so that clients could re-implement for visitors



CHANGE 03/30/18 IActor.java: Added toggleVisibility() method to override time-based visibility
CHANGE 03/30/18 IView.java: Added resetFocus() method to allow swing based IView to listen to
                            keyboard events
CHANGE 03/30/18 IView.java: Added addKeyListener() method to allow swing based IViews to listen to
                            keyboard events and react to them accordingly.
CHANGE 03/30/18 IView.java: Added shapeSelectionPanel() method to create an Option Pane that
                            enables the user to save the animation in svg format if they wanted to.
                            Only relevant to Hybrid Views.
CHANGE 03/30/18 IView.java: Added writeSVG() method that writes the animation based off of the given
                            parameters(actors, looping boolean-flag, tps) to the Appendable object
                            passed in.
CHANGE 4/01/18 IAnimatorModel.java:  HashMap -> LinkedHashMap in getActorCopies(), because it needs
                                     to be ordered


02/24/17
We essentially re-created the model from scratch, using ideas from both of our models
Namely, we
- Brought over the visitor pattern for comparing compatibility with IActions (see compatibleWith
  function in IAction)
- Brought over the visitor pattern for applying IActions to IActors (see applyTo function in
  IAction)
- Decided to use a ShapeType enum to determine how to draw the lines between vertices returned by
  getVertices
- Added more visitor patterns, including for compatibility in the IAction interface.
- Took out the added visitor pattern methods to separate interfaces (see IVisitableAction,
  IVisitableActor, IVisitableShape)