package cs3500.music.view;

/**
 * A factory of music views.
 */
public class ViewFactory {

  /**
   * Creates a music view based on the input string.
   *
   * @param input The type of view to return.
   * @return The view that is asked for.
   */
  public static View create(String input) {
    switch (input) {
      case "composite":
        return new CompositeView(new GUIView(), new MidiView());
      case "console":
        return new ConsoleView();
      case "midi":
        return new MidiView();
      case "visual":
        return new GUIView();
      default:
        throw new IllegalArgumentException("Invalid view type.");
    }

  }
}
