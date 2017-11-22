package cs3500.music.provider;

/**
 * Represents the different pitches a note can have.
 */
public enum Pitch {
  C("C"), CSHARP("C#"), D("D"), DSHARP("D#"), E("E"), F("F"), FSHARP("F#"),
  G("G"), GSHARP("G#"), A("A"), ASHARP("A#"), B("B");

  private String value;

  Pitch(String value) {
    this.value = value;
  }

  /**
   * Gives the value of the Pitch as a String.
   * @return a String
   */
  public String getValue() {
    return this.value;
  }


}
