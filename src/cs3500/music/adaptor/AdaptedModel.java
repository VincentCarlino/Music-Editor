package cs3500.music.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.provider.MusicOperation;
import cs3500.music.provider.Note;
import cs3500.music.provider.Octave;
import cs3500.music.provider.Pitch;

/**
 * An adapted model that converts our model to the model given by the provider.
 */
public class AdaptedModel implements MusicOperation {
  MusicEditorOperations<cs3500.music.model.Note> adaptee;
  Map<Integer, List<Note>> notesonbeat;

  /**
   * Constructs the adapted model.
   * @param adaptee The model to convert.
   */
  public AdaptedModel(MusicEditorOperations<cs3500.music.model.Note> adaptee) {
    this.adaptee = adaptee;
  }

  /**
   * Initializes the notes on beat map.
   */
  private void initializeNOB() {
    notesonbeat = new HashMap<>();
    for (int i = 0; i <= getFinalBeat(); i++) {
      notesonbeat.put(i, new ArrayList<>());
    }
    for (Note n: getNotes()) {
      notesonbeat.get(n.getBeat()).add(n);
    }
  }

  @Override
  public int getTempo() {
    return adaptee.getTempo();
  }

  @Override
  public void addNote(Note note) {
    adaptee.addNote(new cs3500.music.model.Note(ToneAdaptor.adaptFrom(note.getPitch()),
            OctaveAdaptor.adaptFrom(note.getOctave()), note.getBeat(),
            note.getDuration(), note.getInstrument(), note.getVolume()));
  }

  @Override
  public void addNote(int startTime, int duration, Pitch pitch, Octave octave) {
    adaptee.addNote(new cs3500.music.model.Note(ToneAdaptor.adaptFrom(pitch),
            OctaveAdaptor.adaptFrom(octave), startTime, duration));
  }

  @Override
  public void removeNote(int startTime, Pitch pitch, Octave octave) {
    /**
     * Removing notes note needed.
     */
  }

  @Override
  public List<Note> getNotes() {
    List<Note> result = new ArrayList<>();
    for (cs3500.music.model.Note n: adaptee.getNotes()) {
      result.add(new AdaptedNote(n));
    }
    return result;
  }

  @Override
  public List<Note> getNotesAtBeat(int beat) {
    return notesonbeat.get(beat);
  }

  @Override
  public void appendModels(MusicOperation model) {
    /**
     * Not needed to make view work.
     */
  }

  @Override
  public void mergeModels(MusicOperation model) {
    /**
     * Not need to make view work.
     */
  }

  @Override
  public String getState() {
    return "";
  }

  @Override
  public int getFinalBeat() {
    return adaptee.getLength();
  }

  @Override
  public Note getLowestNote() {
    return new AdaptedNote(adaptee.getLowest());
  }

  @Override
  public Note getHighestNote() {
    return new AdaptedNote(adaptee.getHighest());
  }

  @Override
  public List getNotesPlayingAtBeat(int i) {
    List<Note> result = new ArrayList<>();

    for (Note n: getNotes()) {
      if (n.isPlaying(i)) {
        result.add(n);
      }
    }
    return result;
  }
}
