package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import cs3500.music.model.Note;

/**
 * A midi view that is able play music of a given piece.
 */
public class MidiView implements View {
  private Sequencer sequencer;
  private ViewModel model;
  boolean isPlaying;
  long beat;

  /**
   * Constructs a standard midi view that uses the standard Java sequencer.
   */
  public MidiView() {
    try {
      this.sequencer = MidiSystem.getSequencer();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
    isPlaying = false;
    beat = 0;
  }

  /**
   * Constructs a midi view with a given sequencer.  Used for testing the view.
   *
   * @param sequencer The sequencer to play the music
   */
  public MidiView(Sequencer sequencer) {
    this.sequencer = sequencer;
  }

  @Override
  public void initialize() {
    Sequence piece = null;
    try {
      sequencer.open();
      piece = new Sequence(Sequence.PPQ, 1);
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }

    Track track = piece.createTrack();
    List<Note> notes = model.getNotes();
    for (Note n : notes) {
      try {
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                n.translate(), n.getVolume()), n.getPosition()));
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

      try {
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF, n.getInstrument() - 1,
                n.translate(), n.getVolume()), n.getPosition() + n.getDuration() - 1));
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
    }
    sequencer.setTempoInMPQ(model.getTempo());
    try {
      sequencer.setSequence(piece);
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
    sequencer.setTickPosition(beat);
  }

  @Override
  public boolean getIsPlaying() {
    return sequencer.isRunning();
  }

  @Override
  public void setCurrentBeat(Long current) {

    sequencer.setTickPosition(current);
  }

  @Override
  public void run() {
    initialize();
  }

  @Override
  public void setModel(ViewModel model) {
    this.model = model;
  }

  @Override
  public void moveLeft() {
    if (sequencer.getTickPosition() != 0) {
      sequencer.setTickPosition(sequencer.getTickPosition() - 1);
    }
    updateBeat();
  }

  @Override
  public void moveRight() {
    if (sequencer.getTickPosition() != model.getLength()) {
      sequencer.setTickPosition(sequencer.getTickPosition() + 1);
    }
    updateBeat();
  }

  @Override
  public void start() {
    sequencer.setTickPosition(0);
    sequencer.stop();
    updateBeat();
  }

  @Override
  public void end() {
    sequencer.setTickPosition(model.getLength());
    sequencer.stop();
    updateBeat();
  }

  @Override
  public void play() {
    if (isPlaying) {
      sequencer.stop();
      updateBeat();
    } else {
      sequencer.start();
      sequencer.setTempoInMPQ(model.getTempo());
    }
    isPlaying = !isPlaying;
  }

  @Override
  public void setKeys(KeyListener k) {
    /**
     * Keylistener not used in this view.
     */
  }

  @Override
  public void setMouse(MouseListener m) {
    /**
     * Mouse listener not needed.
     */
  }

  @Override
  public Long getCurrentBeat() {
    return sequencer.getTickPosition();
  }

  /**
   * Updates the current beat tracker.
   */
  private void updateBeat() {
    beat = sequencer.getTickPosition();
  }
}
