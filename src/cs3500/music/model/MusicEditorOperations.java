package cs3500.music.model;

import java.util.List;
import java.util.Map;

/**
 * The interface that defines the operations necessary to run as a Music Editor Model
 * parameterized over the type T.  A MusicEditor should have the cabability to add and remove a T,
 * combine two pieces to play
 * simultaneously or consecutively, and be able to show the piece as it will be played.
 *
 * @param <T> The parameter of what is being added to the editor.
 */
public interface MusicEditorOperations<T> {

  /**
   * Adds a T to the piece.
   *
   * @param note The note/T to be added.
   */
  void addNote(T note);

  /**
   * Removes the T from the music editor.
   *
   * @param note The note to be removed.
   */
  void removeNote(T note);

  /**
   * Combines the piece with another piece consecutively.  The piece given will play
   * immediately after the current piece.
   *
   * @param other The piece to append to the end of this piece.
   */
  void combineConsecutively(List<T> other);

  /**
   * Combines this piece with another piece simultaneously.  The piece given will play at the
   * same time as the current piece.
   *
   * @param other The piece to append to the beginning of this piece.
   */
  void combineSimultaneously(List<T> other);

  /**
   * Gets the notes of the song as a map based on what measure they are playing in.
   *
   * @return The map of notes to their given beats.
   */
  Map<Long, List<T>> getNotesOnMeasure();

  /**
   * Gets the lowest note of the model.
   *
   * @return The lowest note.
   */
  T getLowest();

  /**
   * Gets the highest note of the model.
   *
   * @return The highest note.
   */
  T getHighest();

  /**
   * Gets the notes of this song.
   *
   * @return The notes of the song.
   */
  List<T> getNotes();

  /**
   * Gets the tempo of the model.
   *
   * @return the tempo.
   */
  int getTempo();

  /**
   * Gets the length of the model.
   *
   * @return The length
   */
  int getLength();
}
