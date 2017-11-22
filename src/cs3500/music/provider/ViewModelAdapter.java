package cs3500.music.provider;

import java.util.List;

/**
 * Adapts a MusicModel to be an immutable ViewModel.
 */
public class ViewModelAdapter implements ViewModel {
  private MusicOperation model;

  /**
   * Sets this model to the given model.
   * @param model a MusicModel to be used by the ViewModel
   */
  public ViewModelAdapter(MusicOperation model) {
    this.model = model;
  }

  /**
   * Gets the tempo from the model.
   * @return an int
   */
  @Override
  public int getTempo() {
    return model.getTempo();
  }

  /**
   * Returns all the notes in the model.
   * @return a list of notes
   */
  @Override
  public List<Note> getNotes() {
    return model.getNotes();
  }

  /**
   * Returns all the notes played at a given beat.
   * @param beat A beat in the piece
   * @return a list of notes
   */
  @Override
  public List<Note> getNotesAtBeat(int beat) {
    return model.getNotesAtBeat(beat);
  }

  /**
   * Returns a string representation of the model.
   * @return a String
   */
  @Override
  public String getState() {
    return model.getState();
  }

  /**
   * Gets the final beat of all the notes in the model.
   * @return an int
   */
  @Override
  public int getFinalBeat() {
    return model.getFinalBeat();
  }

  /**
   * Returns the lowest note in the model.
   * @return a Note
   */
  @Override
  public Note getLowestNote() {
    return model.getLowestNote();
  }

  /**
   * Returns the highest note in the model.
   * @return a Note
   */
  @Override
  public Note getHighestNote() {
    return model.getHighestNote();
  }

  /**
   * Returns all the notes playing at the given beat.
   * @param beat a given beat in the piece of music.
   * @return a List of notes
   */
  @Override
  public List getNotesPlayingAtBeat(int beat) {
    return model.getNotesPlayingAtBeat(beat);
  }
}
