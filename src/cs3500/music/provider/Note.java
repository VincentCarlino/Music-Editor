package cs3500.music.provider;

public interface Note {

  /**
   * Checks if this note is longer than other note.
   * @param other another note being compared to.
   * @return a boolean.
   */
  public boolean longerNote(Note other);

  /**
   * Gets the length of the note.
   * @return an integer
   */
  int getDuration();

  /**
   * Gets the beat of the note.
   * @return an integer
   */
  int getBeat();

  /**
   * Returns the octave of the note.
   * @return an octave
   */
  Octave getOctave();

  /**
   * Returns the volume of the note.
   * @return an int
   */
  int getVolume();

  /**
   * Returns the numerical value of a note.
   * @return an int
   */
  int getNumericalValue();

  /**
   * Checks if the given note has the given values.
   * @param startTime an int representing the start time
   * @param pitch     a Pitch enum
   * @param octave    an octave enum
   * @return a boolean
   */
  boolean sameValues(int startTime, Pitch pitch, Octave octave);

  /**
   * Tells if this note is higher than the given note.
   * @param highestNote a Note
   * @return a boolean
   */
  boolean ishigher(Note highestNote);

  /**
   * Gets a note's pitch.
   * @return a pitch
   */
  Pitch getPitch();

  /**
   * Returns the integer representation of the instrument of the Note.
   * @return an int
   */
  int getInstrument();

  /**
   * Tells if this note is lower than the given note.
   * @param lowestNote a Note
   * @return a boolean
   */
  boolean isLower(Note lowestNote);

  /**
   * Tells if this note is currently playing.
   * @param beat an integer
   * @return a boolean
   */
  boolean isPlaying(int beat);
}
