package cs3500.music.controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;
import cs3500.music.view.GUIViewOperations;
import cs3500.music.view.ViewModel;

/**
 * The controller of the music editor that is currently responsible for handling user input.
 */
public class GUIController implements ControllerOperations {
  private GUIViewOperations view;
  private MusicEditorOperations model;

  /**
   * Creates a controller that is linked to a model and view.  Sets the viewmodel of the view
   * to be the model and then configures the keyboard controls of the view.
   */
  public GUIController(GUIViewOperations view, MusicEditorOperations model) {
    this.view = view;
    this.model = model;
    configureKeyboard();
    configureMouse();
  }

  @Override
  public void run() {
    view.setModel(new ViewModel(model));
    view.run();
  }

  /**
   * Checks whether the given y value is in the range for a white key to be activated.
   *
   * @param y The vertical position.
   * @return Whether the position is within the White keys.
   */
  private boolean withinWhite(int y) {
    return y < view.getPianoLength() + view.getScoreHeight() + 33 &&
            // 33 is the width of the top border of the window (On windows 8)
            y > view.getBlackLength() + view.getScoreHeight() + 33;

  }

  /**
   * Checks whether the given y value is in the range for a black key to be activated.
   *
   * @param y The vertical position.
   * @return Whether the position is within the black keys.
   */
  private boolean withinBlack(int y) {
    return y < view.getBlackLength() + view.getScoreHeight() + 33;
  }

  /**
   * Adds a note based on a given x and y value of a mouse click.
   *
   * @param x The horizontal position of the click.
   * @param y The vertical position of the click.
   */
  private void addNote(int x, int y) {
    Tone tone = null;
    Integer octave = null;
    boolean white = withinWhite(y);
    boolean black = withinBlack(y);
    int octavepos = x - view.getMargin() - 8; //8 is the width of the left border of the window.
    int notepos = 0;
    int position = ((int) view.getPosition());
    if (octavepos >= 0) {
      //Finds which octave is being played.
      while (octavepos % (view.getPianoSize() * 7) != 0) {
        notepos++;
        octavepos--;
      }
      octave = octavepos / (view.getPianoSize() * 7);
      //Finds which tone is being added.
      if (notepos <= view.getPianoSize() && white) {
        tone = Tone.C;
      }
      else if (notepos <= view.getPianoSize() + view.getPianoSize() / 2 && black) {
        tone = Tone.C_SHARP;
      }
      else if (notepos <= 2 * view.getPianoSize() && white) {
        tone = Tone.D;
      }
      else if (notepos <= 2 * view.getPianoSize() + view.getPianoSize() / 2 && black) {
        tone = Tone.D_SHARP;
      }
      else if (notepos <= 3 * view.getPianoSize() && white) {
        tone = Tone.E;
      }
      else if (notepos <= 4 * view.getPianoSize() && white) {
        tone = Tone.F;
      }
      else if (notepos <= 4 * view.getPianoSize() + view.getPianoSize() / 2 && black) {
        tone = Tone.F_SHARP;
      }
      else if (notepos <= 5 * view.getPianoSize() && white) {
        tone = Tone.G;
      }
      else if (notepos <= 5 * view.getPianoSize() + view.getPianoSize() / 2 && black) {
        tone = Tone.G_SHARP;
      }
      else if (notepos <= 6 * view.getPianoSize() && white) {
        tone = Tone.A;
      }
      else if (notepos <= 6 * view.getPianoSize() + view.getPianoSize() / 2 && black) {
        tone = Tone.A_SHARP;
      }
      else if (notepos <= 7 * view.getPianoSize() && white) {
        tone = Tone.B;
      }
      if (tone != null && octave != null) {
        model.addNote(new Note(tone, octave, position, 1, 1, 75));
        view.moveRight();
      }
    }
  }

  /**
   * Configures the mouse handling of the controller for the view.
   */
  private void configureMouse() {
    Map<Integer, Runnable> released = new HashMap<>();
    Mouse mouse = new Mouse();
    Runnable pianoUsed = new Runnable() {
      @Override
      public void run() {
        int x = Math.round(mouse.getX());
        int y = Math.round(mouse.getY());
        if (!view.getIsPlaying()) {
          addNote(x, y);
          view.initialize();
        }

      }
    };
    released.put(MouseEvent.BUTTON1, pianoUsed);
    mouse.setActions(released);
    view.setMouse(mouse);
  }


  /**
   * Configures the keyboard controls for the handling of user input.
   */
  private void configureKeyboard() {
    Map<Integer, Runnable> pressed = new HashMap<>();

    pressed.put(KeyEvent.VK_LEFT, () -> view.moveLeft());
    pressed.put(KeyEvent.VK_RIGHT, () -> view.moveRight());
    pressed.put(KeyEvent.VK_SPACE, () -> view.play());
    pressed.put(KeyEvent.VK_HOME, () -> view.start());
    pressed.put(KeyEvent.VK_END, () -> view.end());

    KeyListener keyboard = new Keyboard(new HashMap<>(), pressed, new HashMap<>());
    view.setKeys(keyboard);
  }

}
