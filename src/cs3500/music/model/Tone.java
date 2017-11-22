package cs3500.music.model;

/**
 * Tones in music theory.  There are 12 of them. Each of them has a value to represent it.
 */
public enum Tone {

  C("C", 0), C_SHARP("C#", 1), D("D", 2), D_SHARP("D#", 3), E("E", 4), F("F", 5),
  F_SHARP("F#", 6), G("G", 7), G_SHARP("G#", 8), A("A", 9), A_SHARP("A#", 10), B("B", 11);

  private final String string;
  private final int value;

  Tone(String string, int value) {
    this.string = string;
    this.value = value;
  }

  private static Tone[] tones = values();

  /**
   * Gets the next tone after the current tone
   *
   * @return The next tone.
   */
  public Tone next() {
    return tones[(this.ordinal() + 1) % tones.length];
  }

  /**
   * Gets this tone as a string.
   *
   * @return The tone as a string.
   */
  public String getString() {
    return string;
  }

  /**
   * Gets the value of the tone.
   *
   * @return The value of the tone
   */
  public int getValue() {
    return value;
  }
}
