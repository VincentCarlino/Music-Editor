package cs3500.music.adaptor;

import cs3500.music.model.Tone;
import cs3500.music.provider.Pitch;

/**
 * The tone adaptor class that allows conversions between our representation of tones and
 * the provider representation of pitches.
 */
public class ToneAdaptor {

  /**
   * Converts the given tone into a provider pitch.
   * @param t The tone to convert.
   * @return The tone as a provider pitch.
   */
  public static Pitch adaptTo(Tone t) {
    switch (t) {
      case C:
        return Pitch.C;
      case C_SHARP:
        return Pitch.CSHARP;
      case D:
        return Pitch.D;
      case D_SHARP:
        return Pitch.DSHARP;
      case E:
        return Pitch.E;
      case F:
        return Pitch.F;
      case F_SHARP:
        return Pitch.FSHARP;
      case G:
        return Pitch.G;
      case G_SHARP:
        return Pitch.GSHARP;
      case A:
        return Pitch.A;
      case A_SHARP:
        return Pitch.ASHARP;
      default:
        return Pitch.B;
    }
  }

  /**
   * Converts the given provider pitch to a tone.
   * @param p The provider pitch.
   * @return Our representation of a tone.
   */
  public static Tone adaptFrom(Pitch p) {
    switch (p) {
      case C:
        return Tone.C;
      case CSHARP:
        return Tone.C_SHARP;
      case D:
        return Tone.D;
      case DSHARP:
        return Tone.D_SHARP;
      case E:
        return Tone.E;
      case F:
        return Tone.F;
      case FSHARP:
        return Tone.F_SHARP;
      case G:
        return Tone.G;
      case GSHARP:
        return Tone.G_SHARP;
      case A:
        return Tone.A;
      case ASHARP:
        return Tone.A_SHARP;
      default:
        return Tone.B;
    }
  }

}
