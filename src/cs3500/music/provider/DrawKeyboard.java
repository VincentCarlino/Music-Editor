package cs3500.music.provider;


import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This keyboard class creates the keyboard to be drawn on the GUI using a hashmap.
 */
public class DrawKeyboard extends JPanel implements KeyboardView, ActionListener {
  private Map<Integer, Rectangle> keyMap = new HashMap<>();
  private ViewModel model;
  private MusicTracker tracker;
  private Timer timer = new Timer(29, this);

  /**
   * Maps note numerical values to Rectangles representing keys on a keyboard.
   */
  public DrawKeyboard() {
    timer.start();
    int whitesDrawn = 0;
    for (int i = 0; i < 120; i++) {
      if (sharp(i)) {
        //black keys
        Rectangle current = new Rectangle(40 * (whitesDrawn) + 80, 20, 20, 300);
        keyMap.put(i, current);
      } else {
        //white keys
        Rectangle current = new Rectangle(40 * (whitesDrawn) + 90, 20, 40, 600);
        keyMap.put(i, current);
        whitesDrawn = whitesDrawn + 1;
      }
    }
  }

  /**
   * Sets this model to the given ViewModel.
   * @param model the given ViewModel
   */
  @Override
  public void setViewModel(ViewModel model) {
    this.model = model;
  }

  /**
   * Sets this tracker to the given tracker.
   * @param tracker the given tracker that keeps track of
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    this.tracker = tracker;
  }

  /**
   * Adds the given listener to this KeyListener.
   * @param listener the given tracker that keeps track of
   */
  @Override
  public void setKeyListener(KeyListener listener) {
    this.addKeyListener(listener);
  }

  /**
   * Adds the given mouselistener to the keyboard.
   * @param mouseListener a given mouselistener
   */
  @Override
  public void setMouseListener(MouseListener mouseListener) {
    this.addMouseListener(mouseListener);
  }

  @Override
  public void scroll() {
    //do nothing
  }

  /**
   * Initializes the visibility to true so that we can see the GUI.
   */
  @Override
  public void initialize() {
    this.setVisible(true);
  }

  /**
   * Draws the notes in this music to the GUI.
   * @param g the graphics
   */
  @Override
  public void paintComponent(Graphics g) {
    this.requestFocus();

    if (model != null) {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0, 0, 4000, 800);

      List<Integer> listKeys = new ArrayList<>();
      listKeys.addAll(keyMap.keySet());

      int currentBeat = (int) tracker.getCurrentPosition();
      List<Note> notesPlaying = model.getNotesPlayingAtBeat(currentBeat);
      List<Integer> notesPlayingInts = new ArrayList<>();
      notesPlaying.forEach(n -> notesPlayingInts.add(n.getNumericalValue()));

      //draw white keys
      for (int i = 0; i < 120; i++) {
        Rectangle current = keyMap.get(i);
        int x = current.x;
        int y = current.y;
        int width = current.width;
        int height = current.height;

        if (notesPlayingInts.contains(i) && !sharp(i)) {
          g.setColor(Color.BLACK);
          g.fillRect(x, y, width, height);
          g.setColor(new Color(248, i * 2, 86));
          g.fillRect(x, y, width, height);
        } else if (!sharp(i)) {
          g.setColor(Color.BLACK);
          g.fillRect(x, y, width, height);
          g.setColor(Color.WHITE);
          g.fillRect(x - 1, y - 1, width - 2, height - 1);
        }
      }

      //draw black keys
      for (int i = 0; i < 120; i++) {
        Rectangle current = keyMap.get(i);
        int x = current.x;
        int y = current.y;
        int width = current.width;
        int height = current.height;

        if (notesPlayingInts.contains(i) && sharp(i)) {
          g.setColor(new Color(i, 88, 207));
          g.fillRect(x, y, width, height);
        } else if (sharp(i)) {
          g.setColor(Color.BLACK);
          g.fillRect(x, y, width, height);
        }
      }
    }
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

  /**
   * Constantly repaints the GUI to keep the red line and keys updated.
   * @param e an Action Event
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == timer) {
      repaint();
    }
  }

  /**
   * Gets the keyMap out of the keyboard.
   * @return a map
   */
  @Override
  public Map<Integer, Rectangle> getMap() {
    return this.keyMap;
  }
}
