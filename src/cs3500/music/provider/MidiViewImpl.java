package cs3500.music.provider;

import java.awt.Rectangle;
import java.util.List;
import java.util.Map;
import java.util.Objects;


import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 * A skeleton for MIDI playback.
 */
public class MidiViewImpl implements MidiView {
  private ViewModel model;
  private Sequencer sequencer;
  private int resolution = 60;


  /**
   * Sets this model's sequencer to the given Sequencer.
   * @param sequencer the given sequencer
   */
  public MidiViewImpl(Sequencer sequencer) {
    Objects.requireNonNull(sequencer);
    this.sequencer = sequencer;
  }

  /**
   * Sets this view's ViewModel to the given model.
   * @param model a ViewModel
   */
  public void setViewModel(ViewModel model) {
    this.model = model;
  }

  /**
   * Sets this tracker to the given tracker.
   * @param tracker the given tracker
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    // do nothing
  }

  /**
   * Adds the notes to the sequencer and starts the sequencer.
   */
  public void initialize() {
    Sequence sequence;
    try {
      sequence = new Sequence(Sequence.PPQ, resolution);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException("Could not initialize sequencer");
    }
    Track track = sequence.createTrack();
    List<Note> notes = model.getNotes();
    for (Note n : notes) {
      try {
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON, n.getInstrument() - 1,
                n.getNumericalValue() + (24), n.getVolume()), n.getBeat() * resolution));
        track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,
                n.getInstrument() - 1,
                n.getNumericalValue() + (24), n.getVolume()), n.getBeat() * resolution
                + n.getDuration() * resolution));
      } catch (InvalidMidiDataException e) {
        throw new RuntimeException("Could not add note to track");
      }
    }
    try {
      sequencer.setSequence(sequence);
    } catch (InvalidMidiDataException e) {
      throw new RuntimeException("Could not set sequence");
    }
    try {
      sequencer.open();
    } catch (MidiUnavailableException e) {
      //do nothing
    }
    sequencer.start();
    sequencer.setTempoInBPM(60000000 / model.getTempo());
  }

  /**
   * There is no map to return.
   * @return null
   */
  @Override
  public Map<Integer, Rectangle> getMap() {
    return null;
  }

  /**
   * Returns the current position in the midi View.
   * @return a long.
   */
  @Override
  public long getCurrentPosition() {
    return sequencer.getTickPosition() / this.resolution;
  }

  /**
   * Changes the position to the given position.
   * @param n a long
   */
  @Override
  public void changePosition(long n) {
    sequencer.setTickPosition(n * resolution);
    sequencer.setTempoInBPM(60000000 / model.getTempo());
  }

  /**
   * Stops the sequencer and the music.
   */
  @Override
  public void pause() {
    sequencer.stop();
    sequencer.setTempoInBPM(60000000 / model.getTempo());
  }

  /**
   * Starts the sequencer and the music.
   */
  @Override
  public void start() {
    sequencer.start();
    sequencer.setTempoInBPM(60000000 / model.getTempo());
  }
}