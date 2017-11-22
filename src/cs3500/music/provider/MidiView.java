package cs3500.music.provider;

/**
 * Represents the MidiView to play the music in the midi sequencer.
 */
public interface MidiView extends View, MusicTracker {

  /**
   * Stops the music in the midiview.
   */
  void pause();

  /**
   * Starts the music in the midiview.
   */
  void start();

}
