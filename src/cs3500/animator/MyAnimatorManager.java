package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.controller.GraphicsAnimController;
import cs3500.animator.controller.HybridAnimController;
import cs3500.animator.controller.HybridControllerWithSlider;
import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.TextAnimController;
import cs3500.animator.model.AnimatorModelWithLayerRot;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.view.AnimationViewCreator;
import cs3500.animator.view.IView;
import cs3500.animator.view.ViewType;

/**
 * Manages the models, views, and controllers for Yash Shetty and Jack Steilberg's
 * implementations. Creates the model, view, and controller from given viewtype, ticks per second,
 * input file, and output file.
 *
 * <p>Change: added case INTERACT_SLIDER: in createController() method.
 */
public class MyAnimatorManager implements IAnimatorManager {

  // Our model
  private IAnimatorModel model;

  // Our controller
  private IAnimationController controller;

  // Our view
  private IView view;

  // Ticks per second, view type, input file, output file
  private int tps;
  private ViewType viewType;
  private String outputFile;
  private String inputFile;
  private FileWriter fileWriter = null;

  /**
   * Creates a new MyAnimatorManager with given parameters. Does not start it.
   *
   * @param viewType   The viewtype to use
   * @param tps        ticks per second for conversion and rendering
   * @param inputFile  file to read in
   * @param outputFile file to output to
   */
  public MyAnimatorManager(ViewType viewType, int tps, String inputFile, String outputFile) {
    this.tps = tps;
    this.viewType = viewType;
    this.outputFile = outputFile;
    this.inputFile = inputFile;
    this.createModel();
    this.createView();
    this.createController();
  }

  /**
   * Creates the view, based on the stored model.
   */
  private void createView() {
    if (outputFile.equals("out")) {
      this.view = AnimationViewCreator.create(viewType, System.out, model.getActorCopies(),
              tps);
    } else {
      try {
        this.fileWriter = new FileWriter(outputFile);
        this.view = AnimationViewCreator.create(viewType, this.fileWriter, model.getActorCopies(),
                tps);
      } catch (IOException e) {
        e.printStackTrace();
        // ruh roh
        System.exit(-1);
      }
    }
  }

  /**
   * Creates a new controller based on stored viewType, model, and IView.
   */
  private void createController() {
    switch (this.viewType) {
      case VISUAL:
        this.controller = new GraphicsAnimController(model, view, this.tps);
        break;
      case TEXT:
        this.controller = new TextAnimController(model, view);
        break;
      case INTERACTIVE:
        this.controller = new HybridAnimController(model, view, this.tps);
        break;
      case INTERACT_SLIDER:
        this.controller = new HybridControllerWithSlider(model, view, this.tps);
        break;
      case SVG:
        this.controller = new TextAnimController(model, view);
        break;
      default:
        throw new IllegalArgumentException("Invalid viewtype given");
    }
  }


  /**
   * Creates the model.
   */
  private void createModel() {
    LayerRotAnimFileReader reader = new LayerRotAnimFileReader();
    try {
      this.model = reader.readFile(inputFile, new AnimatorModelWithLayerRot.Builder());
    } catch (FileNotFoundException e) {
      EasyAnimator.inputError("argument", "input file not found");
      System.exit(-1);
    }
  }

  @Override
  public void start() {
    this.controller.start();

    //ensures that the channel to the fileWriter is closed and all the text is printed.
    if (this.fileWriter != null) {
      try {
        this.fileWriter.close();
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(-1);
      }
    }
  }
}
