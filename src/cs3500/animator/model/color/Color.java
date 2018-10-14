package cs3500.animator.model.color;

/**
 * Represents a color, using red, green, blue, and alpha values. Can be mutated. Also possibly
 * able to store alpha values.
 */
public class Color implements IColor {

  // Red, green, blue, and alpha values
  private float r;
  private float g;
  private float b;
  private float a;

  /**
   * Initializes this Color with given r, g, b, and a values. Given values must be between
   * 0 and 1.
   *
   * @param r float between 0 and 1 to represent the proportion of red in the color
   * @param g float between 0 and 1 to represent the proportion of green in the color
   * @param b float between 0 and 1 to represent the proportion of blue in the color
   * @param a float between 0 and 1 to represent the alpha (transparency) of the color
   */
  public Color(float r, float g, float b, float a) {
    validateRGBA(r, g, b, a);

    this.r = r;
    this.g = g;
    this.b = b;
    this.a = a;
  }

  /**
   * Initializes this Color with given r, g, and b values. Given values must be between
   * 0 and 1. Defaults {@code a} value to 1. Wasn't complex enough to justify a builder
   * pattern.
   *
   * @param r float between 0 and 1 to represent the proportion of red in the color;
   * @param g float between 0 and 1 to represent the proportion of green in the color;
   * @param b float between 0 and 1 to represent the proportion of blue in the color;
   */
  public Color(float r, float g, float b) {
    this(r, g, b, 1.f);
  }

  /**
   * Copy constructor- Creates a new color from the given color.
   *
   * @param c {@code Color} to construct new color from;
   */
  public Color(IColor c) {
    this.r = c.getR();
    this.g = c.getG();
    this.b = c.getB();
    this.a = c.getA();
  }


  /**
   * Modifies the color values for this {@code Color}.
   *
   * @param newR New float red value for the {@code Color}
   * @param newG New float green value for the {@code Color}
   * @param newB New float blue value for the {@code Color}
   * @param newA New float alpha value for the {@code Color}
   */
  @Override
  public void mutateColor(float newR, float newG, float newB, float newA) {
    validateRGBA(newR, newG, newB, newA);

    this.r = newR;
    this.g = newG;
    this.b = newB;
    this.a = newA;
  }

  /**
   * Ensures that the given red, green, blue, and alpha values are between 0 and 1.
   *
   * @param r red value
   * @param g green value
   * @param b blue value
   * @param a alpha value
   */
  private void validateRGBA(float r, float g, float b, float a) {
    if (!validatefloat(r)) {
      throw new IllegalArgumentException("Invalid red value given, must be between 0.0 and 1.0");
    }

    if (!validatefloat(g)) {
      throw new IllegalArgumentException("Invalid green value given, must be between 0.0 and 1.0");
    }

    if (!validatefloat(b)) {
      throw new IllegalArgumentException("Invalid blue value given, must be between 0.0 and 1.0");
    }

    if (!validatefloat(a)) {
      throw new IllegalArgumentException("Invalid alpha value given, must be between 0.0 and 1.0");
    }
  }

  /**
   * Ensures that the given float is between 0 and 1.
   *
   * @param f float value to check
   * @return True if the value is between 0 and 1, false otherwise
   */
  private boolean validatefloat(float f) {
    return (f >= 0.f && f <= 1.f);
  }

  /**
   * Returns the red value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  @Override
  public float getR() {
    return this.r;
  }

  /**
   * Returns the green value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  @Override
  public float getG() {
    return this.g;
  }

  /**
   * Returns the blue value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  @Override
  public float getB() {
    return this.b;
  }

  /**
   * Returns the alpha value of this {@code Color}.
   *
   * @return float between 0 and 1
   */
  @Override
  public float getA() {
    return this.a;
  }

  /**
   * Returns a text representation of the color.
   *
   * @return String containing the color's values in the form (r,g,b)
   */
  @Override
  public String toString() {
    return "(" + Float.toString(r) + "," + Float.toString(g) + "," + Float.toString(b) + ")";
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Color)) {
      return false;
    } else {
      Color c = (Color) other;
      return (c.getR() == this.getR() &&
              (c.getG() == this.getG()) &&
              (c.getB() == this.getB()));
    }
  }


  @Override
  public int hashCode() {
    return Float.hashCode(this.r) * Float.hashCode(this.b) * Float.hashCode(this.g);
  }
}

