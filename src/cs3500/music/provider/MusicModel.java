package cs3500.music.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.music.util.CompositionBuilder;

/**
 * The music model takes in a HashMap of notes and a tempo and creates a representation of the
 * music given.
 */
public class MusicModel implements MusicOperation {
  private HashMap<Integer, List<Note>> notes;
  private int tempo;

  /**
   * A constructor for MusicModel, the tempo is initialized to 90000.
   */
  public MusicModel() {
    this(90000);
  }

  /**
   * A constructor for MusicModel, the notes are represented as a Hashmap and the tempo is
   * initialized to itself.
   */
  public MusicModel(int tempo) {
    this.notes = new HashMap<>();
    this.tempo = tempo;
  }

  private List<String> allnotes = this.allNotes();

  /**
   * Returns the tempo of the current piece in the model.
   * @return Returns the tempo.
   */
  @Override
  public int getTempo() {
    return tempo;
  }


  /**
   * This method takes in a note and adds it to a List of Notes.
   * @param note a Note
   */
  // I changed this method to just take in a single note instead of Note and startTime
  @Override
  public void addNote(Note note) {

    List<Note> notes = new ArrayList<>();
    notes.add(note);

    if (this.notes.get(note.getBeat()) == null) {
      this.notes.put(note.getBeat(), notes);
    } else {
      List<Note> oldList = this.notes.remove(note.getBeat());
      oldList.addAll(notes);
      this.notes.put(note.getBeat(), oldList);
    }
  }


  /**
   * Adds a note to the music.
   * @param startTime an integer
   * @param duration  an integer
   * @param pitch     a Pitch enum
   * @param octave    an Octave enum
   */
  @Override
  public void addNote(int startTime, int duration, Pitch pitch, Octave octave) {
    List<Note> notes = new ArrayList<>();
    Note newNote = new SimpleNote(pitch, octave, startTime, duration);
    notes.add(newNote);

    if (this.notes.get(startTime) == null) {
      this.notes.put(startTime, notes);
    } else {
      List<Note> oldList = this.notes.remove(startTime);
      oldList.addAll(notes);
      this.notes.put(startTime, oldList);
    }

  }


  /**
   * Removes a note from the list of notes in the model.
   * @param startTime A start beat
   * @param pitch     A pitch from the enum
   * @param octave    An octave from 1 to 10
   */
  @Override
  public void removeNote(int startTime, Pitch pitch, Octave octave) {
    List<Note> notes = this.notes.remove(startTime);

    if (notes == null) {
      throw new IllegalArgumentException("This beat does exist.");
    }

    for (int i = 0; i < notes.size(); i++) {
      Note note = notes.get(i);

      if (note.sameValues(startTime, pitch, octave)) {
        notes.remove(i);
        this.notes.put(startTime, notes);
      } else {
        throw new IllegalArgumentException("Cant remove this note.");
      }
    }

  }

  /**
   * This method outputs all the notes in a piece at all beats.
   * @return A list of Notes is returned.
   */
  @Override
  public List<Note> getNotes() {
    ArrayList<Note> listAllNotes = new ArrayList<>();

    ArrayList<Integer> keys = new ArrayList<>(this.notes.keySet());

    for (int i = 0; i < keys.size(); i++) {
      int currentKey = keys.get(i);

      List<Note> current = this.getNotesAtBeat(currentKey);

      listAllNotes.addAll(current);
    }
    return listAllNotes;
  }

  /**
   * This method is given a beat and it returns all the notes played at that beat.
   * @param beat A beat in the piece
   * @return A list of all notes in the piece that are played at the given beat.
   */
  @Override
  public List<Note> getNotesAtBeat(int beat) {
    ArrayList<Note> listAtBeat = new ArrayList<>();

    List<Note> current = notes.get(beat);

    if (current == null) {
      this.notes.put(beat, listAtBeat);
      return notes.get(beat);
    } else {
      listAtBeat.addAll(current);
      return listAtBeat;
    }
  }

  /**
   * Adds the notes in a given model to this model at the end of the current piece.
   * @param model another MusicOperation model.
   */
  @Override
  public void appendModels(MusicOperation model) {
    int length = this.getFinalBeat();

    List<Note> currentNotes = model.getNotes();

    for (int x = 0; x < currentNotes.size(); x++) {
      Note current = currentNotes.get(x);
      Note newNote = new SimpleNote(current.getPitch(), current.getOctave(),
              current.getBeat() + length, current.getDuration());
      this.addNote(newNote);
    }
  }

  /**
   * Combines two models together by adding the notes of the given model to this model.
   * @param model another MusicOperation model.
   */
  @Override
  public void mergeModels(MusicOperation model) {
    int thisLength = this.getFinalBeat();
    int otherLength = model.getFinalBeat();
    int length;

    if (thisLength >= otherLength) {
      length = thisLength;
    } else {
      length = otherLength;
    }

    for (int i = 0; i < length; i++) {
      List<Note> currentNotes = model.getNotesAtBeat(i);

      for (int x = 0; x < currentNotes.size(); x++) {
        Note currentNote = currentNotes.get(x);
        this.addNote(currentNote);

      }

    }

  }

  /**
   * This method outputs a String view of the current model showing all the notes in the model
   * at their respective position and for their respective lengths.
   * @return A string representation of the model.
   */
  @Override
  public String getState() {
    StringBuilder builder = new StringBuilder();
    StringBuilder listPitches = listAllPitches(this.getFinalBeat());
    int length = this.getFinalBeat();

    builder.append(listPitches);

    for (int i = 0; i < length; i++) {
      builder.append("\n" + padded(i, length) + notesToString(getNotesPlayingAtBeat(i), i));
    }
    return builder.toString();
  }


  /**
   * Pads the number for each column.
   * @param i      an integer
   * @param length the length of the piece
   * @return a String
   */
  private String padded(int i, int length) {
    String current = Integer.toString(i);
    int size = Integer.toString(length).length();
    current = String.format("%" + size + "s", current);
    return current;
  }


  /**
   * Creates a string for the given beat.
   * @param notesPlayingAtBeat a list of notes at this beat.
   * @param beat               an integer
   * @return a String
   */
  private String notesToString(List<Note> notesPlayingAtBeat, int beat) {
    if (notesPlayingAtBeat.size() == 0) {
      return "";
    }
    String padding = "     ";
    String onBeat = "  x  ";
    String onDuration = "  |  ";

    Note highest = this.getHighestNote();
    Note lowest = this.getLowestNote();
    int ordinalHighestOctave = highest.getOctave().ordinal();
    int ordinalHighestPitch = highest.getPitch().ordinal();
    int ordinalLowOctave = lowest.getOctave().ordinal();
    int ordinalLowPitch = lowest.getPitch().ordinal();
    int from = ordinalLowOctave * 12 + ordinalLowPitch;
    int to = ordinalHighestOctave * 12 + ordinalHighestPitch;

    List<String> listOfNotes = this.allnotes.subList(from, to + 1);
    int allNotesSize = listOfNotes.size();

    StringBuilder builder = new StringBuilder();
    List<String> newList = new ArrayList<>();

    for (int x = 0; x < allNotesSize; x++) {
      String currentNote = listOfNotes.get(x);

      for (int i = 0; i < notesPlayingAtBeat.size(); i++) {
        Note playingNote = notesPlayingAtBeat.get(i);
        String playingString =
                playingNote.getPitch().getValue() + playingNote.getOctave().getValue();

        if (playingString.equals(currentNote) && playingNote.getBeat() == beat) {
          newList.add(onBeat);
          break;
        } else if (playingString.equals(currentNote)) {
          newList.add(onDuration);
          break;
        } else if (i == notesPlayingAtBeat.size() - 1) {
          newList.add(padding);
        }
      }
    }

    for (int c = 0; c < newList.size(); c++) {
      builder.append(newList.get(c).toString());
    }

    return builder.toString();

  }


  /**
   * A List of of pitches which goes at the top of the string view.
   * @param length length of the piece of music
   * @return a StringBuilder appendable
   */
  private StringBuilder listAllPitches(int length) {
    StringBuilder builder = new StringBuilder();

    int padding = Integer.toString(length).length();
    String spaces = "";
    spaces = String.format("%" + (padding) + "s", spaces);
    builder.append(spaces);

    Note highest = this.getHighestNote();
    Note lowest = this.getLowestNote();
    int ordinalHighestOctave = highest.getOctave().ordinal();
    int ordinalHighestPitch = highest.getPitch().ordinal();
    int ordinalLowOctave = lowest.getOctave().ordinal();
    int ordinalLowPitch = lowest.getPitch().ordinal();
    int from = ordinalLowOctave * 12 + ordinalLowPitch;
    int to = ordinalHighestOctave * 12 + ordinalHighestPitch;
    List<String> allNotes = this.allnotes.subList(from, to + 1);

    for (int i = 0; i < allNotes.size(); i++) {
      String current = allNotes.get(i);

      if (current.length() == 2) {
        builder.append("  " + current + " ");
      } else if (current.length() == 3) {
        builder.append(" " + current + " ");
      } else if (current.length() == 4) {
        builder.append(" " + current);
      } else {
        builder.append(current);
      }
    }
    return builder;
  }

  /**
   * Gets a list of notes playing at the given beat.
   * @param beat an integer
   * @return a List
   */
  @Override
  public List getNotesPlayingAtBeat(int beat) {
    List<Note> notes = this.getNotes();
    ArrayList<Note> notesAtBeat = new ArrayList<>();

    for (int i = 0; i < notes.size(); i++) {
      Note current = notes.get(i);

      if (current.isPlaying(beat)) {
        notesAtBeat.add(current);
      }
    }

    return notesAtBeat;
  }

  /**
   * Gets the final length of this music.
   * @return an integer
   */
  @Override
  public int getFinalBeat() {
    List<Note> currentNotes = this.getNotes();
    int size = currentNotes.size();
    int farthestBeat = 0;

    for (int i = 0; i < size; i++) {
      Note currentNote = currentNotes.get(i);
      int currentEnd = currentNote.getBeat() + currentNote.getDuration();

      if (currentEnd > farthestBeat) {
        farthestBeat = currentEnd;
      }
    }
    return farthestBeat;
  }

  /**
   * Gets the highest note of this music.
   * @return a Note
   */
  @Override
  public Note getHighestNote() {
    List<Note> currentNotes = this.getNotes();
    int size = currentNotes.size();
    Note highestNote = null;

    for (int i = 0; i < size; i++) {
      Note currentNote = currentNotes.get(i);

      if (currentNote.ishigher(highestNote)) {
        highestNote = currentNote;
      }
    }
    if (highestNote == null) {
      throw new IllegalArgumentException("No Notes To Compare");
    }
    return highestNote;
  }

  /**
   * Gets the lowest note of this music.
   * @return a Note
   */
  @Override
  public Note getLowestNote() {
    List<Note> currentNotes = this.getNotes();
    int size = currentNotes.size();
    Note lowestNote = null;

    for (int i = 0; i < size; i++) {
      Note currentNote = currentNotes.get(i);

      if (currentNote.isLower(lowestNote)) {
        lowestNote = currentNote;
      }
    }
    if (lowestNote == null) {
      throw new IllegalArgumentException("No Notes To Compare");
    }
    return lowestNote;
  }

  /**
   * Gets a List of all possible notes as strings.
   * @return a List of String
   */
  private List<String> allNotes() {
    List allNotes = new ArrayList();

    for (int i = 0; i < 10; i++) {
      Octave octave = getOctave(i);

      for (int x = 0; x < 12; x++) {
        Pitch pitch = getPitch(x);

        String note = pitch.getValue() + octave.getValue();
        allNotes.add(note);
      }
    }
    return allNotes;
  }


  // changed to use values instead of switch.
  /**
   * Gets pitch given an integer.
   * @param i an integer
   * @return a Pitch
   */
  private Pitch getPitch(int i) {
    return Pitch.values()[i];
  }

  // changed to use values instead of switch.
  /**
   * Gets Octave given an integer.
   * @param i an integer
   * @return an Octave
   */
  private Octave getOctave(int i) {
    return Octave.values()[i];
  }


  /**
   * Builds compositions.
   */
  public static final class Builder implements CompositionBuilder<MusicOperation> {
    private int tempo = 90000;
    private List<Note> listNotes = new ArrayList<>();

    /**
     * Constructs an actual composition, given the notes that have been added.
     * @return an operation
     */
    @Override
    public MusicOperation build() {
      MusicOperation m = new MusicModel(this.tempo);
      listNotes.forEach(n -> m.addNote(n));
      return m;
    }

    /**
     * Sets the tempo of the piece.
     * @param tempo The speed, in microseconds per beat
     * @return a MusicOperation
     */
    @Override
    public CompositionBuilder<MusicOperation> setTempo(int tempo) {
      this.tempo = tempo;
      return this;
    }

    /**
     * Adds a new note to the piece.
     * @param start The start time of the note, in beats
     * @param end The end time of the note, in beats
     * @param instrument The instrument number (to be interpreted by MIDI)
     * @param pitch The pitch (in the range [0, 127], where 60 represents C4,
     *              the middle-C on a piano)
     * @param volume The volume (in the range [0, 127])
     * @return A MusicOperation
     */
    @Override
    public CompositionBuilder<MusicOperation> addNote(int start, int end, int instrument,
                                                      int pitch, int volume) {
      this.listNotes.add(new SimpleNote(Pitch.values()[(pitch) % 12],
              Octave.values()[(pitch / 12) - 2 ], start, end - start, instrument, volume));
      return this;
    }
  }
}














