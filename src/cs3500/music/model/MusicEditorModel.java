package cs3500.music.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import cs3500.music.util.CompositionBuilder;

/**
 * The MusicEditorModel, an implementation of the MusicEditorOperations interface parameterized
 * over a Note.  The MusicEditorModel is represented via a Map of integers and Lists of Notes.
 * Notes are added to a list of notes before they are put into the map.  The model has
 * a stored lowest and highest note.
 */
public class MusicEditorModel implements MusicEditorOperations<Note> {
  private List<Note> notes;
  private Map<Long, List<Note>> nom;
  private Note lowest;
  private Note highest;
  private int tempo;

  /**
   * Constructs a MusicEditorModel.  A standard MusicEditorModel is initialized with an
   * empty list of notes and a lower and upper bound of C3 and C4 respectively.
   */
  public MusicEditorModel() {
    notes = new ArrayList<>();
    lowest = new Note(Tone.C, 3);
    highest = new Note(Tone.C, 4);
    tempo = 10000;
  }

  /**
   * Constructs a model with a tempo determined by the user.4
   *
   * @param notes The notes of the song.
   * @param tempo The tempo of the piece.
   */
  public MusicEditorModel(List<Note> notes, int tempo) {
    this.notes = notes;
    this.tempo = tempo;
  }

  /**
   * Constructs a MusicEditorModel given a list of notes.
   *
   * @param notes The notes of the piece to use.
   */
  public MusicEditorModel(List<Note> notes) {
    Objects.requireNonNull(notes, "Input notes must be non-null");
    this.notes = notes;
    findBounds();
  }

  public static final class Builder implements CompositionBuilder<MusicEditorOperations> {
    List<Note> buildnotes = new ArrayList<>();
    int tempo = 140;

    @Override
    public MusicEditorOperations<Note> build() {

      return new MusicEditorModel(buildnotes, tempo);
    }

    @Override
    public CompositionBuilder<MusicEditorOperations> setTempo(int tempo) {
      if (tempo < 1) {
        throw new IllegalArgumentException("Tempo must be greater than 0");
      } else {
        this.tempo = tempo;
        return this;
      }
    }

    @Override
    public CompositionBuilder<MusicEditorOperations> addNote(int start, int end, int instrument,
                                                             int pitch, int volume) {
      Tone tone = Tone.C;
      for (Tone t : Tone.values()) {
        if (pitch % 12 == t.getValue()) {
          tone = t;
        }
      }
      Note n = new Note(tone, pitch / 12, start, end - start, instrument, volume);
      this.buildnotes.add(n);
      return this;
    }

  }

  @Override
  public Note getLowest() {
    findBounds();
    return lowest;
  }

  @Override
  public Note getHighest() {
    findBounds();
    return highest;
  }

  @Override
  public void addNote(Note note) {
    notes.add(note);
  }

  @Override
  public void removeNote(Note note) {
    if (notes.contains(note)) {
      notes.remove(note);
    }
  }

  @Override
  public void combineConsecutively(List<Note> other) {
    if (notes.size() != 0) {
      for (Note n : other) {
        this.addNote(n.changePos(getLength()));
      }
    } else {
      notes = other;
    }
  }

  @Override
  public void combineSimultaneously(List<Note> other) {
    for (Note n : other) {
      this.addNote(n);
    }
  }

  @Override
  public List<Note> getNotes() {
    return this.notes;
  }

  @Override
  public int getTempo() {
    return tempo;
  }


  /**
   * Initializes the notes on measure map.
   */
  private void initializeNom() {
    nom = new TreeMap<>();
    int l = getLength();

    for (long i = 0; i <= l + 1; i++) {
      nom.put(i, new ArrayList<>());
    }

    for (Note n : this.notes) {
      for (long e = n.getPosition(); e < n.getPosition() + n.getDuration(); e++) {
        List<Note> temp = nom.get(e);
        if (!temp.contains(new Note(n.getTone(), n.getOctave()))) {
          nom.get(e).add(new Note(n.getTone(), n.getOctave()));
        }
      }
    }
  }


  @Override
  public Map<Long, List<Note>> getNotesOnMeasure() {
    initializeNom();
    return nom;
  }


  /**
   * Checks the notes of the piece and changes the bounds of the piece to match its content.
   */
  private void findBounds() {
    if (notes.size() == 0) {
      lowest = new Note(Tone.C, 3);
      highest = new Note(Tone.C, 4);
    } else {
      Note templ = notes.get(0);
      Note temph = notes.get(0);
      for (Note n : notes) {
        if (n.translate() > temph.translate()) {
          temph = n;
        }
        if (n.translate() < templ.translate()) {
          templ = n;
        }
      }
      lowest = new Note(templ.getTone(), templ.getOctave());
      highest = new Note(temph.getTone(), temph.getOctave());
    }
  }


  @Override
  public int getLength() {
    int l = 0;
    for (Note n : notes) {
      if (n.getPosition() + n.getDuration() - 1 > l) {
        l = n.getPosition() + n.getDuration() - 1;
      }
    }
    return l;
  }
}
