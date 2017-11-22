package cs3500.music.model;

import java.util.Objects;

/**
 * A representation of a note in a piece of music.  A note has a tone, an octave, a position,
 * and a duration. A note can not exceed the octave of 11, cannot have a position of less
 * than 0, and cannot have a length of anything less than 1.
 */
public class Note {
  private Tone tone;
  private int octave;
  private int position;
  private int duration;
  private int instrument;
  private int volume;

  /**
   * Constructs a note with a standard position and duration of 0 and 1 respectively.  This
   * note is typically used for the sake of constructing the upper and lower bounds of a
   * music piece.
   *
   * @param tone   The tone of the note
   * @param octave The octave of the note.
   */
  public Note(Tone tone, int octave) {
    if (octave > 12 || octave < 0) {
      throw new IllegalArgumentException("Invalid Note");
    } else {
      this.tone = tone;
      this.octave = octave;
      this.position = 0;
      this.duration = 1;
    }
  }

  /**
   * Constructs a note based on the user's given tone, octave, position, instrument, and duration.
   *
   * @param tone     The tone of the note
   * @param octave   The octave of the note
   * @param position The position of the note
   * @param duration The duration of the note
   */
  public Note(Tone tone, int octave, int position, int duration) {
    if (octave > 12 || octave < 0 || position < 0 || duration < 1) {
      throw new IllegalArgumentException("Invalid Note");
    } else {
      this.tone = tone;
      this.octave = octave;
      this.position = position;
      this.duration = duration;
    }
  }

  /**
   * Gets the instrument of this note.
   *
   * @return The instrument of this note.
   */
  public int getInstrument() {
    return instrument;
  }

  /**
   * Gets the volume of this note.
   *
   * @return The volume of this note.
   */
  public int getVolume() {
    return volume;
  }

  /**
   * Constructs a note based on the user's given tone, octave, position, instrument, volume, and
   * duration.
   *
   * @param tone       The tone of the note
   * @param octave     The octave of the note
   * @param position   The position of the note
   * @param duration   The duration of the note
   * @param instrument The instrument that plays this note
   * @param volume     The loudness of the note
   */
  public Note(Tone tone, int octave, int position, int duration, int instrument, int volume) {
    if (octave > 12 || octave < 0 || position < 0 || instrument < 0) {
      throw new IllegalArgumentException("Invalid Note");
    } else {
      this.tone = tone;
      this.octave = octave;
      this.position = position;
      this.duration = duration;
      this.instrument = instrument;
      this.volume = volume;
    }
  }

  /**
   * Changes the note's position.  (Returns a new note with changed values)
   *
   * @param amount The amount of time to change the position by.
   * @return The new note with the new position.
   */
  public Note changePos(int amount) {
    return new Note(tone, octave, this.position + amount, this.duration);
  }


  /**
   * Calculates the value of the note based on its octave and tone.
   *
   * @return The value of the note.
   */
  public int translate() {
    return 12 * octave + tone.getValue();
  }

  /**
   * Gets the tone of this note.
   *
   * @return The tone of this note.
   */
  public Tone getTone() {
    return tone;
  }

  /**
   * Gets the octave of this note.
   *
   * @return The octave of this note.
   */
  public int getOctave() {
    return octave;
  }

  /**
   * Gets the position of this note.
   *
   * @return The position of this note.
   */
  public int getPosition() {
    return position;
  }

  /**
   * Gets the duration of this note.
   *
   * @return The duration of this note.
   */
  public int getDuration() {
    return duration;
  }


  /**
   * Gets the note the follows this note.
   *
   * @return The next note.
   */
  public Note next() {
    Tone[] tones = Tone.values();

    if (tone == Tone.B) {
      return new Note(Tone.C, octave + 1);
    } else {
      return new Note(tone.next(), octave);
    }
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Note)) {
      return false;
    } else {
      Note that = (Note) o;
      return tone == that.getTone() &&
              octave == that.getOctave() &&
              duration == that.getDuration() &&
              position == that.getPosition();
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(tone, octave, position, duration);
  }

  @Override
  public String toString() {
    return tone.getString() + octave;
  }


}


