package cs3500.music.adaptor;

import cs3500.music.provider.Octave;

/**
 * The octave adaptor class that allows conversions between our representation of octaves and
 * the provider representation.
 */
public class OctaveAdaptor {

  /**
   * Converts the given integer into the octave of the provider.
   * @param i The int to convert.
   * @return The equal octave.
   */
  public static Octave adaptTo(int i) {
    switch (i) {
      case 0:
        return Octave.ONE;
      case 1:
        return Octave.TWO;
      case 2:
        return Octave.THREE;
      case 3:
        return Octave.FOUR;
      case 4:
        return Octave.FIVE;
      case 5:
        return Octave.SIX;
      case 6:
        return Octave.SEVEN;
      case 7:
        return Octave.EIGHT;
      case 8:
        return Octave.NINE;
      case 9:
        return Octave.TEN;
      default:
        return Octave.TEN;
    }
  }

  /**
   * Converts the given provider octave into in integer.
   * @param o The octave to convert.
   * @return The octave as an int.
   */
  public static int adaptFrom(Octave o) {
    return o.ordinal();
  }
}
