package cs3500.music.view;

import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Note;

/**
 * A read-only viewmodel that is used as a source of data for a view.
 */
public class ViewModel {
  MusicEditorOperations<Note> model;

  /**
   * Constructs a view model based on the model given by the user.
   *
   * @param model The model to read from.
   */
  public ViewModel(MusicEditorOperations model) {
    this.model = model;
  }

  /**
   * Gets the read-only version of the list of notes from the model.
   *
   * @return The list of notes from the model.
   */
  public List<Note> getNotes() {
    return model.getNotes();
  }

  /**
   * Gets the lowest note of the model.
   *
   * @return The lowest note.
   */
  public Note getLowest() {
    return model.getLowest();
  }


  /**
   * Gets the highest note of the model.
   *
   * @return The highest note.
   */
  public Note getHighest() {
    return model.getHighest();
  }

  /**
   * Gets the tempo of the model.
   *
   * @return the tempo.
   */
  public int getTempo() {
    return model.getTempo();
  }

  /**
   * Gets the length of the model.
   *
   * @return The length
   */
  public int getLength() {
    return model.getLength();
  }

  /**
   * Gets the notes of the song as a map based on what measure they are playing in.
   *
   * @return The map of notes to their given beats.
   */
  public Map<Long, List<Note>> getNotesOnMeasure() {
    return model.getNotesOnMeasure();
  }
}
