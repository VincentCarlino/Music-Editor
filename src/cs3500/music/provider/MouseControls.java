package cs3500.music.provider;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

/**
 * Allows the view to check when the user clicks on a key with the mouse.
 */
public class MouseControls implements MouseListener {
  private MusicOperation model;
  private View view;
  private MusicTracker tracker;

  /**
   * Initializes model, view and tracker so w ecan get information from them.
   * @param model a MusicOperation
   * @param view a View
   * @param tracker a MusicTracker
   */
  public MouseControls(MusicOperation model, View view, MusicTracker tracker) {
    this.model = model;
    this.view = view;
    this.tracker = tracker;
  }

  /**
   * Pass the x y and model into a new method which adds the note to the model.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    onClickAddNote(e.getX(), e.getY(), view.getMap());
  }

  /**
   * Adds a note to the model if a key is clicked with the mouse.
   * @param x the x coordinate of the mouse click
   * @param y the y coordinate of the mouse click
   * @param keyMap the map of keys from the keyboard view
   */
  private void onClickAddNote(int x, int y, Map<Integer, Rectangle> keyMap) {

    for (int key : keyMap.keySet()) {
      Rectangle rectangle = keyMap.get(key);

      if (key == 119 && clickCheck(x,y, rectangle)) {
        model.addNote((int)tracker.getCurrentPosition(), 1, Pitch.B, Octave.TEN);
        moveTracker();
        break;
      }

      Rectangle nextRect = keyMap.get(key + 1);

      if (sharp(key) && clickCheck(x, y, rectangle)) {
        model.addNote((int) tracker.getCurrentPosition(), 1,
                getPitch(key % 12), getOctave(key / 12));
        moveTracker();
        break;
      } else if (!sharp(key) && clickCheck(x, y, nextRect) && clickCheck(x, y, rectangle)) {
        model.addNote((int) tracker.getCurrentPosition(), 1,
                getPitch((key + 1) % 12), getOctave((key + 1) / 12));
        moveTracker();
        break;
      } else if (!sharp(key) && clickCheck(x, y, rectangle)) {
        model.addNote((int) tracker.getCurrentPosition(), 1,
                getPitch(key % 12), getOctave(key / 12));
        moveTracker();
        break;
      }
    }
  }

  /**
   * Checks if the click is inside the x and y coordinates of a given rectangle.
   */
  private boolean clickCheck(int x, int y, Rectangle rectangle) {
    if (rectangle == null) {
      return false;
    } else {
      return x >= rectangle.getX() && x <= rectangle.getX() + rectangle.getWidth()
              && y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight();
    }
  }

  /**
   * Moves the tracker one position forward.
   */
  private void moveTracker() {
    tracker.changePosition(tracker.getCurrentPosition() + 1);
  }


  @Override
  public void mousePressed(MouseEvent e) {
    //Do nothing.
  }


  @Override
  public void mouseReleased(MouseEvent e) {
    //Do nothing.
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    //Do nothing.
  }

  @Override
  public void mouseExited(MouseEvent e) {
    //Do nothing.
  }

  /**
   * Gets pitch given an integer.
   * @param i an integer
   * @return a Pitch
   */
  private Pitch getPitch(int i) {
    return Pitch.values()[i];
  }

  /**
   * Gets Octave given an integer.
   * @param i an integer
   * @return an Octave
   */
  private Octave getOctave(int i) {
    return Octave.values()[i];
  }

  /**
   * Checks if the given integer is a sharp note value.
   * @param i an int that represents the pitch of a note
   * @return a boolean
   */
  private boolean sharp(int i) {
    int pitch = i % 12;
    return pitch == 1 || pitch == 3 || pitch == 6 || pitch == 8 || pitch == 10;
  }
}
