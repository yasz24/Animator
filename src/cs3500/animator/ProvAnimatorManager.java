package cs3500.animator;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import cs3500.animator.controller.IAnimationController;
import cs3500.animator.controller.adapters.ProvControllerImitate;
import cs3500.animator.controller.adapters.ProvControllerTextualImitate;
import cs3500.animator.model.AnimatorModelWithToString;
import cs3500.animator.model.EzAnimatorOpsAdapter;
import cs3500.animator.model.IAnimatorModel;
import cs3500.animator.provider.view.HybridView;
import cs3500.animator.provider.view.SvgAnimationView;
import cs3500.animator.provider.view.TextualView;
import cs3500.animator.provider.view.VisualView;
import cs3500.animator.view.ViewType;

/**
 * Manages the models, views, and controllers for our providers'
 * implementations. Creates the model, view, and controller from given viewtype, ticks per second,
 * input file, and output file.
 */
public class ProvAnimatorManager implements IAnimatorManager {

  // our model
  private IAnimatorModel model;

  // Our controller wrapped to imitate our providers'
  private IAnimationController controller;

  // Ticks per second to run at
  private int tps;
  private ViewType viewType;
  private String outputFile;
  private String inputFile;
  private OutputStreamWriter fileWriter;

  /**
   * Creates a provider animator manager with given arguments.
   *
   * @param viewType   Viewtype to use
   * @param tps        ticks per second to run at
   * @param inputFile  file to read from
   * @param outputFile file to output to, if any
   */
  public ProvAnimatorManager(ViewType viewType, int tps, String inputFile, String outputFile) {
    this.tps = tps;
    this.viewType = viewType;
    this.outputFile = outputFile;
    this.inputFile = inputFile;
    this.createModel();
    this.createViewController();
  }

  /**
   * Creates a new controller based on stored viewType, model, and IView.
   */
  private void createViewController() {
    if (outputFile.equals("out")) {
      this.fileWriter = new OutputStreamWriter(System.out);
    } else {
      try {
        this.fileWriter = new FileWriter(outputFile);
      } catch (IOException e) {
        // ruh roh
        System.exit(-1);
      }
    }

    switch (this.viewType) {
      case PROVIDER:
        this.controller =
                new ProvControllerTextualImitate(
                        new HybridView(this.tps),
                        new EzAnimatorOpsAdapter(this.model),
                        this.fileWriter);
        break;
      case PROVIDER_VISUAL:
        this.controller =
                new ProvControllerImitate(
                        new VisualView(this.tps),
                        new EzAnimatorOpsAdapter(this.model));
        break;
      case PROVIDER_TEXTUAL:
        this.controller =
                new ProvControllerTextualImitate(
                        new TextualView(this.tps),
                        new EzAnimatorOpsAdapter(this.model),
                        fileWriter);
        break;
      case PROVIDER_SVG:
        this.controller =
                new ProvControllerTextualImitate(
                        new SvgAnimationView(this.tps),
                        new EzAnimatorOpsAdapter(this.model),
                        fileWriter);
        break;
      default:
        throw new IllegalArgumentException("Invalid viewtype given");
    }
  }

  /**
   * Creates the model.
   *
   * @return a model based on read input file.
   */
  private void createModel() {
    AnimationFileReader reader = new AnimationFileReader();
    try {
      this.model = reader.readFile(inputFile, new AnimatorModelWithToString.Builder());
    } catch (FileNotFoundException e) {
      EasyAnimator.inputError("argument", "input file not found");
      System.exit(-1);
    }
  }

  @Override
  public void start() {
    this.controller.start();

    try {
      this.fileWriter.close();
    } catch (IOException e) {
      EasyAnimator.inputError("file writer", "Failed to write to file");
    }
  }
}
