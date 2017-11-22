package cs3500.music.provider;

/**
 * To represent the 10 different possible octaves for a note.
 */
public enum Octave {
  ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"),
  SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10");

  private String value;

  Octave(String value) {
    this.value = value;
  }

  /**
   * Gives the value of the Octave as a String.
   * @return a String
   */
  public String getValue() {
    return this.value;
  }

}
