package cs3500.music.provider;

/**
 * Represents a Simple note.
 */
public class SimpleNote implements Note {
  private final Pitch pitch;
  private final Octave octave;
  private final int start;
  private final int duration;
  private final int instrument;
  private final int volume;

  /**
   * A SimpleNote constructor.
   * @param pitch      a Pitch enum
   * @param octave     an Octave enum
   * @param start      a start integer
   * @param duration   a duration integer
   * @param instrument an instrument
   * @param volume     the volume
   */
  public SimpleNote(Pitch pitch, Octave octave, int start, int duration,
                    int instrument, int volume) {
    if (start < 0 || duration < 0) {
      throw new IllegalArgumentException("Incorrect Parameters");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.start = start;
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
  }

  /**
   * A SimpleNote convenience constructor.
   * @param pitch a Pitch from the Pitch enum
   * @param octave an Octave from the Octave enum
   * @param start a Start beat
   * @param duration the length of the note
   */
  // change: added convenience constructor
  public SimpleNote(Pitch pitch, Octave octave, int start, int duration) {
    this(pitch, octave, start, duration, 11, 100);
  }

  /**
   * Takes in a note and compares it to this note, returning the longer Note.
   * @param other another note being compared to.
   * @return a boolean
   */
  @Override
  public boolean longerNote(Note other) {
    return this.duration <= other.getDuration();
  }

  /**
   * Returns this note represented as a String.
   * @return a String
   */
  @Override
  public String toString() {
    return this.pitch.getValue() + this.octave.getValue();
  }

  /**
   * Returns the duration of this note.
   * @return an int
   */
  @Override
  public int getDuration() {
    return this.duration;
  }

  /**
   * Returns the location of where this note starts.
   * @return an int
   */
  @Override
  public int getBeat() {
    return this.start;
  }

  /**
   * Returns the octave of this note.
   * @return an octave
   */
  @Override
  public Octave getOctave() {
    return this.octave;
  }

  /**
   * Returns the volume of this note.
   * @return an int
   */
  @Override
  public int getVolume() {
    return this.volume;
  }

  /**
   * returns the Numerical value of this note.
   * @return an int
   */
  @Override
  public int getNumericalValue() {
    return octave.ordinal() * 12 + pitch.ordinal();
  }

  /**
   * Returns the pitch of this note.
   * @return a pitch
   */
  @Override
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Returns the instrument of this note.
   * @return an int
   */
  @Override
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Returns the lowest note, either this note or the given note.
   * @param lowestNote a Note
   * @return a boolean
   */
  @Override
  // changed so it uses calculations based off Numbericalvalue
  public boolean isLower(Note lowestNote) {
    if (lowestNote == null) {
      return true;
    } else {
      return this.getNumericalValue() < lowestNote.getNumericalValue();
    }
  }

  /**
   * Is given a beat and checks if this note is playing at that beat.
   * @param beat an integer
   * @return a boolean
   */
  @Override
  public boolean isPlaying(int beat) {
    int endBeat = this.getBeat() + this.duration - 1;
    return this.start <= beat && beat <= endBeat;
  }

  /**
   * Checks if the given values are the same as the values for this note.
   * @param startTime an int representing the start time
   * @param pitch     a Pitch enum
   * @param octave    an octave enum
   * @return a boolean
   */
  @Override
  public boolean sameValues(int startTime, Pitch pitch, Octave octave) {
    return this.start == startTime && this.pitch == pitch && this.octave == octave;
  }

  /**
   * Checks if this note is higher than the given note.
   * @param highestNote a Note
   * @return a boolean
   */
  @Override
  // changed so it uses calculations based off Numbericalvalue
  public boolean ishigher(Note highestNote) {
    if (highestNote == null) {
      return true;
    } else {
      return this.getNumericalValue() > highestNote.getNumericalValue();
    }
  }
}
