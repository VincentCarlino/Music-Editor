package cs3500.music.provider;


import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;

/**
 * This class allows different views to be made based on different String input.
 */
public class MusicViewCreator {

  /**
   * Creates a new view based on the string input.
   * @param arg the different types of views that can be drawn
   * @return a View
   */
  public static View create(String arg) {
    switch (arg) {
      case "visual":
        return new GuiViewFrame();
      case "console":
        return new TextView();
      case "combined":
        try {
          return new CompView(new GuiViewFrame(), new MidiViewImpl(MidiSystem.getSequencer()));
        } catch (MidiUnavailableException e) {
          throw new IllegalArgumentException("No MIDI found");
        }
      case "midi":
        try {
          return new MidiViewImpl(MidiSystem.getSequencer());
        } catch (MidiUnavailableException e) {
          throw new IllegalArgumentException("No MIDI found");
        }
      default:
        throw new IllegalArgumentException("This view type is not supported.");
    }
  }
}
