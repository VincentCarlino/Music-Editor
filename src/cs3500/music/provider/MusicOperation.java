package cs3500.music.provider;

import java.util.List;

/**
 * Operations needed for a piece of music.
 */
public interface MusicOperation {

  /**
   * Returns the tempo of a piece of music.
   * @return an int
   */
  public int getTempo();

  /**
   * Adds a note to the music.
   * @param note a Note
   */
  public void addNote(Note note);

  /**
   * Adds a Note to the music piece.
   * @param startTime a Start beat
   * @param duration  a duration int
   * @param pitch     a Pitch enum
   * @param octave    an Octave enum
   */
  void addNote(int startTime, int duration, Pitch pitch, Octave octave);


  /**
   * Removes a note from the music if given the correct start value.
   * @param startTime A start beat
   * @param pitch     A pitch from the enum
   * @param octave    An octave from 1 to 10
   */
  public void removeNote(int startTime, Pitch pitch, Octave octave);

  /**
   * Gets all the notes in the music.
   * @return A List of Notes
   */
  public List<Note> getNotes();

  /**
   * Gets all the notes at the specified beat.
   * @param beat A beat in the piece
   * @return A List of Notes
   */
  public List<Note> getNotesAtBeat(int beat);

  /**
   * Adds the given model to the end of this model.
   * @param model another MusicOperation
   */
  public void appendModels(MusicOperation model);

  /**
   * Merges the given model to this model.
   * @param model another MusicOperation
   */
  public void mergeModels(MusicOperation model);

  /**
   * Creates a String view of the current state of the music.
   * @return A full string view of the model
   */
  public String getState();

  /**
   * Returns the last beat of this music.
   * @return an integer
   */
  public int getFinalBeat();

  /**
   * Gets the lowest pitched Note in the music.
   * @return a Note
   */
  public Note getLowestNote();

  /**
   * Gets the highest pitched Note in the music.
   * @return a Note
   */
  public Note getHighestNote();

  /**
   * Returns a list of all notes being played at the given beat.
   * @param i a beat in the piece of music
   * @return A list of Notes
   */
  public List getNotesPlayingAtBeat(int i);
}
