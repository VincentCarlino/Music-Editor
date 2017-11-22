package cs3500.music.provider;

import java.util.List;

/**
 * Takes the model and adapts it to only have immutable fields.
 */
public interface ViewModel {
  /**
   * Returns the tempo of the music.
   * @return an int
   */
  public int getTempo();

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
   * Returns all the notes being played at the given beat.
   * @param i a given beat
   * @return a list of notes
   */
  public List getNotesPlayingAtBeat(int i);

}
