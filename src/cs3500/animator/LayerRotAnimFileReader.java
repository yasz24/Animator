package cs3500.animator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Adds layering and rotation functionality to the AnimationFileReader.
 */
public class LayerRotAnimFileReader extends AnimationFileReader {

  // If true, the file reader looks for layering information
  private boolean doLayering = false;

  /**
   * Read the animation file and use the builder to build a model.
   *
   * @param fileName the path of the file to be read
   * @param builder  the builder used to build the model
   * @param <T>      the type of model
   * @return the model
   * @throws FileNotFoundException  if the specified file cannot be read
   * @throws InputMismatchException if some data value is not of the expected
   *                                type
   * @throws IllegalStateException  if an illegal token is read from the file
   */
  public <T> T readFile(String fileName, ModelBuilderRotLayer<T> builder) throws
          FileNotFoundException, IllegalStateException, InputMismatchException {
    Scanner sc;

    sc = new Scanner(new FileInputStream(fileName));

    while (sc.hasNext()) {
      String cmd = sc.next();

      switch (cmd) {
        case "rectangle":
          RectangleInfo_ rinfo = readRectangleInfo(sc);
          builder.addRectangle(
                  rinfo.getName(),
                  rinfo.getX(), rinfo.getY(),
                  rinfo.getWidth(), rinfo.getHeight(),
                  rinfo.getR(), rinfo.getG(), rinfo.getB(),
                  rinfo.getStart(), rinfo.getEnd(), rinfo.getLayer(), 0);
          break;
        case "oval":
          OvalInfo_ cinfo = readOvalInfo(sc);
          builder.addOval(
                  cinfo.getName(),
                  cinfo.getX(), cinfo.getY(),
                  cinfo.getXRadius(), cinfo.getYRadius(),
                  cinfo.getR(), cinfo.getG(), cinfo.getB(),
                  cinfo.getStart(), cinfo.getEnd(), cinfo.getLayer(), 0);
          break;
        case "move":
          MoveInfo minfo = readMoveInfo(sc);
          builder.addMove(
                  minfo.getName(),
                  minfo.getFromX(),
                  minfo.getFromY(),
                  minfo.getToX(),
                  minfo.getToY(),
                  minfo.getStart(),
                  minfo.getEnd());
          break;
        case "change-color":
          ChangeColorInfo colorInfo = readChangeColorInfo(sc);
          builder.addColorChange(colorInfo.name,
                  colorInfo.getFromR(),
                  colorInfo.getFromG(),
                  colorInfo.getFromB(),
                  colorInfo.getToR(),
                  colorInfo.getToG(),
                  colorInfo.getToB(),
                  colorInfo.getStart(),
                  colorInfo.getEnd());
          break;
        case "scale":
          ScaleByInfo scaleByInfo = readScaleByInfo(sc);
          builder.addScaleToChange(scaleByInfo.name,
                  scaleByInfo.getFromXScale(),
                  scaleByInfo.getFromYScale(),
                  scaleByInfo.getToXScale(),
                  scaleByInfo.getToYScale(),
                  scaleByInfo.getStart(),
                  scaleByInfo.getEnd());
          break;
        case "rotate":
          RotateByInfo rotateByInfo = readRotateByInfo(sc);
          builder.addRotationChange(rotateByInfo.name,
                  rotateByInfo.getFromAngle(),
                  rotateByInfo.getToAngle(),
                  rotateByInfo.getStart(),
                  rotateByInfo.getEnd());
        case "+layering":
          this.doLayering = true;
          break;
        default:
          throw new IllegalStateException("Unidentified token " + cmd + " "
                  + "read from file");

      }
    }
    return builder.build();
  }

  private RectangleInfo_ readRectangleInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    RectangleInfo_ info = new RectangleInfo_();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "min-x":
          info.setX(sc.nextFloat());
          break;
        case "min-y":
          info.setY(sc.nextFloat());
          break;
        case "width":
          info.setWidth(sc.nextFloat());
          break;
        case "height":
          info.setHeight(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        case "layer":
          info.setLayer(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "rectangle");
      }
    }

    return info;
  }

  private OvalInfo_ readOvalInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    OvalInfo_ info = new OvalInfo_();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "center-x":
          info.setX(sc.nextFloat());
          break;
        case "center-y":
          info.setY(sc.nextFloat());
          break;
        case "x-radius":
          info.setXRadius(sc.nextFloat());
          break;
        case "y-radius":
          info.setYRadius(sc.nextFloat());
          break;
        case "color":
          info.setR(sc.nextFloat());
          info.setG(sc.nextFloat());
          info.setB(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        case "layer":
          info.setLayer(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "oval");
      }
    }

    return info;
  }

  class ShapeInfo_ extends ShapeInfo {
    private int layer = 0;

    ShapeInfo_() {
      super();
      if (doLayering) {
        valueFlags.put("layer", false);
      }
    }

    void setLayer(int layer) {
      this.layer = layer;
      valueFlags.replace("layer", true);
    }

    int getLayer() {
      return layer;
    }
  }

  class RectangleInfo_ extends ShapeInfo_ {
    private float x;
    private float y;
    private float width;
    private float height;

    RectangleInfo_() {
      super();
      valueFlags.put("x", false);
      valueFlags.put("y", false);
      valueFlags.put("width", false);
      valueFlags.put("height", false);
    }

    void setX(float x) {
      this.x = x;
      valueFlags.replace("x", true);
    }

    void setY(float y) {
      this.y = y;
      valueFlags.replace("y", true);
    }

    void setWidth(float width) {
      this.width = width;
      valueFlags.replace("width", true);
    }

    void setHeight(float height) {
      this.height = height;
      valueFlags.replace("height", true);
    }

    float getX() {
      return x;
    }

    float getY() {
      return y;
    }

    float getWidth() {
      return width;
    }

    float getHeight() {
      return height;
    }
  }

  /**
   * Had to recreate ovalinfo because couldn't double extend.
   */
  class OvalInfo_ extends ShapeInfo_ {
    private float cx;
    private float cy;
    private float xradius;
    private float yradius;

    OvalInfo_() {
      super();
      valueFlags.put("cx", false);
      valueFlags.put("cy", false);
      valueFlags.put("xradius", false);
      valueFlags.put("yradius", false);
    }

    void setX(float x) {
      this.cx = x;
      valueFlags.replace("cx", true);
    }

    void setY(float y) {
      this.cy = y;
      valueFlags.replace("cy", true);
    }

    void setXRadius(float radius) {
      this.xradius = radius;
      valueFlags.replace("xradius", true);
    }

    void setYRadius(float radius) {
      this.yradius = radius;
      valueFlags.replace("yradius", true);
    }

    float getX() {
      return cx;
    }

    float getY() {
      return cy;
    }

    float getXRadius() {
      return xradius;
    }

    float getYRadius() {
      return yradius;
    }
  }

  /**
   * New class for rotation information. Stores the name, beginning angle,
   * ending angle, start time, and end time.
   */
  class RotateByInfo extends Inputable {
    protected String name;
    private float fromAngle;
    private float toAngle;
    private int start;
    private int end;

    /**
     * Create a new RotateByInfo and initialize value flags.
     */
    RotateByInfo() {
      super();

      valueFlags.put("name", false);
      valueFlags.put("fromang", false);
      valueFlags.put("toang", false);
      valueFlags.put("start", false);
      valueFlags.put("end", false);
    }

    /**
     * Get the initial angle for the rotation.
     *
     * @return float containing initial angle, in degrees, positive is clockwise
     */
    public float getFromAngle() {
      return fromAngle;
    }

    /**
     * Set the initial angle for the rotation.
     *
     * @param fromAngle angle to rotate from.
     */
    public void setFromAngle(float fromAngle) {
      this.fromAngle = fromAngle;
      valueFlags.replace("fromang", true);
    }

    /**
     * Get the final angle for the rotation.
     *
     * @return float containing final angle, in degrees, positive is clockwise
     */
    public float getToAngle() {
      return toAngle;
    }

    /**
     * Set the final angle for the rotation.
     *
     * @param toAngle angle to rotate to.
     */
    public void setToAngle(float toAngle) {
      this.toAngle = toAngle;
      valueFlags.replace("toang", true);
    }

    /**
     * Get the starting tick of the animation.
     *
     * @return int containing starting tick of rotation
     */
    public int getStart() {
      return start;
    }

    /**
     * Set the starting tick of the rotation.
     *
     * @param start starting tick of the rotation
     */
    public void setStart(int start) {
      this.start = start;
      valueFlags.replace("start", true);
    }

    /**
     * Get the tick the rotation ends on.
     *
     * @return tick containing ending of rotation
     */
    public int getEnd() {
      return end;
    }

    /**
     * Ser the tick the rotation ends on.
     *
     * @param end tick the rotation ends on
     */
    public void setEnd(int end) {
      this.end = end;
      valueFlags.replace("end", true);
    }

    /**
     * Set the name of the actor the rotation applies to.
     *
     * @param name string containing aforementioned name
     */
    public void setName(String name) {
      this.name = name;
      valueFlags.replace("name", true);
    }
  }

  /**
   * Reads the rotation info of a rotation command into a RotateByInfo object.
   *
   * @param sc scanner to read from
   * @return RotateByInfo object containing parsed information about the rotation
   * @throws IllegalStateException if given an invalid parameter in the rotation command
   * @throws InputMismatchException  if there was an unexpected argument to a parameter
   */
  protected RotateByInfo readRotateByInfo(Scanner sc) throws
          IllegalStateException, InputMismatchException {
    RotateByInfo info = new RotateByInfo();

    while (!info.isAllInitialized()) {
      String command = sc.next();
      switch (command) {
        case "rotateto":
          info.setFromAngle(sc.nextFloat());
          info.setToAngle(sc.nextFloat());
          break;
        case "name":
          info.setName(sc.next());
          break;
        case "from":
          info.setStart(sc.nextInt());
          break;
        case "to":
          info.setEnd(sc.nextInt());
          break;
        default:
          throw new IllegalStateException("Invalid attribute " + command + " for "
                  + "rotate-to");
      }
    }
    return info;
  }
}
