package cs3500.music.adaptor;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import cs3500.music.controller.ControllerOperations;
import cs3500.music.controller.Keyboard;
import cs3500.music.controller.Mouse;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.provider.CombinedView;
import cs3500.music.provider.MusicOperation;
import cs3500.music.provider.MusicTracker;
import cs3500.music.provider.MusicTrackerImpl;
import cs3500.music.provider.Octave;
import cs3500.music.provider.Pitch;
import cs3500.music.provider.ViewModelAdapter;

/**
 * The adapted controller that controls the functions of the providers view while being tied
 * to our model.
 */
public class AdaptedController implements ControllerOperations {
  MusicEditorOperations model;
  MusicOperation adapted;
  MusicTracker tracker;
  CombinedView view;
  Timer timer;

  /**
   * Constructs the adapted controller and initializes the necessary fields.
   *
   * @param model The model of the controller.
   * @param view  The provider view.
   */
  public AdaptedController(MusicEditorOperations model, CombinedView view) {
    this.model = model;
    this.adapted = new AdaptedModel(model);
    this.view = view;
    view.setViewModel(new ViewModelAdapter(adapted));
    this.tracker = new MusicTrackerImpl();
    this.timer = new Timer(model.getTempo() / 1000, (e) ->
            tracker.changePosition(view.getCurrentPosition()));
  }

  /**
   * Adds a note to the model based on the x and y positions of the mouse click.
   *
   * @param x The x position.
   * @param y The y position.
   */
  private void addNote(int x, int y) {
    Map<Integer, Rectangle> keys = view.getMap();
    for (int key : keys.keySet()) {
      Rectangle rectangle = keys.get(key);

      if (key == 119 && isKey(x, y, rectangle)) {
        adapted.addNote((int) tracker.getCurrentPosition(), 1, Pitch.B, Octave.TEN);
        update();
        break;
      }

      Rectangle nextRect = keys.get(key + 1);

      if (isSharp(key) && isKey(x, y, rectangle)) {
        adapted.addNote((int) tracker.getCurrentPosition(), 1,
                keyToPitch(key), keyToOctave(key / 12));
        update();
        break;
      } else if (!isSharp(key) && isKey(x, y, nextRect) && isKey(x, y, rectangle)) {
        adapted.addNote((int) tracker.getCurrentPosition(), 1,
                keyToPitch(key + 1), keyToOctave((key + 1) / 12));
        update();
        break;
      } else if (!isSharp(key) && isKey(x, y, rectangle)) {
        adapted.addNote((int) tracker.getCurrentPosition(), 1,
                keyToPitch(key), keyToOctave(key / 12));
        update();
        break;
      }
    }
  }

  /**
   * Checks to see if the x and y positions are in the key given by a given rectangle.
   *
   * @param x The horizontal coordinate
   * @param y The vertical coordinate
   * @param r The key to check
   * @return Whether the position is in the rectangle.
   */
  private boolean isKey(int x, int y, Rectangle r) {
    if (r == null) {
      return false;
    } else {
      return x >= r.getX() && x <= r.getX() + r.getWidth()
              && y >= r.getY() && y <= r.getY() + r.getHeight();
    }
  }

  /**
   * Checks if the key is sharp.
   *
   * @param i The key
   * @return Is it sharp?
   */
  private boolean isSharp(int i) {
    return i % 12 == 1 || i % 12 == 3 || i % 12 == 6 || i % 12 == 8 || i % 12 == 10;
  }

  /**
   * Determines what pitch the given position is.
   *
   * @param i the position
   * @return the pitch of the key
   */
  private Pitch keyToPitch(int i) {
    int find = i % 12;
    return Pitch.values()[find];
  }

  /**
   * Determines what octave the given position is.
   *
   * @param i the position
   * @return the octave of the key
   */
  private Octave keyToOctave(int i) {
    return Octave.values()[i];
  }

  /**
   * Updates the tracker by moving it forward 1.
   */
  private void update() {
    tracker.changePosition(tracker.getCurrentPosition() + 1);
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
        addNote(x, y);
      }
    };
    released.put(MouseEvent.BUTTON1, pianoUsed);
    mouse.setActions(released);
    view.setMouseListener(mouse);
  }

  /**
   * Configures the keyboard controls for the handling of user input.
   */
  private void configureKeyboard() {
    Map<Integer, Runnable> pressed = new HashMap<>();

    pressed.put(KeyEvent.VK_LEFT, () -> tracker.changePosition(tracker.getCurrentPosition() - 1));
    pressed.put(KeyEvent.VK_RIGHT, () -> tracker.changePosition(tracker.getCurrentPosition() - 1));

    pressed.put(KeyEvent.VK_SPACE, new Runnable() {
      @Override
      public void run() {
        if (view instanceof CombinedView) {
          ((CombinedView) view).pause();
          tracker.changePosition(view.getCurrentPosition());
        }
      }
    });
    pressed.put(KeyEvent.VK_HOME, () -> tracker.changePosition(0));
    pressed.put(KeyEvent.VK_END, () -> tracker.changePosition(adapted.getFinalBeat()));

    KeyListener keyboard = new Keyboard(new HashMap<>(), pressed, new HashMap<>());
    view.setKeyListener(keyboard);
  }

  @Override
  public void run() {
    configureMouse();
    configureKeyboard();
    view.initialize();
  }
}
