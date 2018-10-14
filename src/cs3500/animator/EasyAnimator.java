package cs3500.animator;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import javax.swing.JOptionPane;
import javax.swing.JDialog;
import javax.swing.WindowConstants;
import javax.swing.JFrame;

import cs3500.animator.view.ViewType;

/**
 * This class holds the main method and is the entry point for the program, where the command-line
 * arguments are processed and the entire program is set into motion. The default tick-rate is
 * set to 1 tick per second.
 */
public final class EasyAnimator {

  // Name of the file to read animation data from
  private static String inputFile;

  // Type of view to present
  private static ViewType viewType;

  // Name of the file to output to, if there is one
  private static String outputFile;

  // Ticks per second to use for outputting
  private static int tps;

  // Map of commandline arguments to the effects they have
  private static Map<String, Function<String, Void>> argMap;

  // Manager for the animator
  private static IAnimatorManager manager;

  /**
   * Entrypoint for the program, taking in commandline arguments.
   *
   * @param args Commandline arguments
   */
  public static void main(String[] args) {
    inputFile = null;
    viewType = null;
    tps = 1;
    outputFile = "out";

    initArgMap();

    //Building a string from command-line arguments.
    StringBuilder sb = new StringBuilder();
    for (String s : args) {
      sb.append(s).append(" ");
    }
    String commandLineArguments = sb.toString();

    //parsing the command-line arguments using a scanner
    Scanner sc = new Scanner(commandLineArguments);
    while (sc.hasNext()) {
      String currentSet = sc.next();
      if (!sc.hasNext()) {
        EasyAnimator.inputError("argument", "none provided");
        break;
      }
      String param = sc.next();
      try {
        argMap.get(currentSet).apply(param);
      } catch (NullPointerException e) {
        EasyAnimator.inputError(currentSet, param);
      }
    }


    // Make sure we were given an input and view
    if (inputFile == null || viewType == null) {
      EasyAnimator.inputError("argument", "no viewtype and/or input file provided");
    }

    if (viewType.getType().startsWith("prov")) {
      EasyAnimator.manager = new ProvAnimatorManager(viewType, tps, inputFile, outputFile);
    } else {
      EasyAnimator.manager = new MyAnimatorManager(viewType, tps, inputFile, outputFile);
    }

    manager.start();
  }

  /**
   * This is where one would add more commandline arguments. (Or, they could extend
   * {@code EasyAnimator}, override this function, call it in the overridden version,
   * then add their own params).
   */
  private static void initArgMap() {
    argMap = new HashMap<>();

    argMap.put("-if", (a) -> {
      if (argMap.containsKey(a)) {
        EasyAnimator.inputError("argument", a);
      } else {
        EasyAnimator.inputFile = a;
      }
      return null;
    });

    argMap.put("-o", (a) -> {
      if (argMap.containsKey(a)) {
        EasyAnimator.inputError("argument", a);
      } else {
        EasyAnimator.outputFile = a;
      }
      return null;
    });

    argMap.put("-iv", (a) -> {
      try {
        EasyAnimator.viewType = ViewType.validateType(a);
        // throws IllegalArgumentException if param doesnt correspond
        // to an actual ViewType
      } catch (IllegalArgumentException e) {
        EasyAnimator.inputError("parameter", a);
      }
      return null;
    });

    argMap.put("-speed", (a) -> {
      if (argMap.containsKey(a)) {
        EasyAnimator.inputError("parameter", a);
      } else {
        int tps_;
        try {
          tps_ = Integer.parseInt(a);
        } catch (NumberFormatException e) {
          EasyAnimator.inputError("parameter", a);
          return null;
        }
        EasyAnimator.tps = tps_;
      }
      return null;
    });
  }

  /**
   * Creates an error message dialog with the given message, then ends the program when okay
   * is clicked. Not part of the view because there may not be a view when this is called.
   *
   * @param whatsInvalid Noun 'what' is actually the invalid input type
   * @param msg          Message to create option pane with
   */
  public static void inputError(String whatsInvalid, String msg) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    JOptionPane p = new JOptionPane("Invalid " + whatsInvalid + ": " + msg);
    JDialog d = p.createDialog(frame, "Error");
    d.setLocation(500, 500);
    d.setVisible(true);
    System.exit(-1);
  }
}

