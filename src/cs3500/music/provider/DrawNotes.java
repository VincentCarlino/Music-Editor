package cs3500.music.provider;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;
import javax.swing.JPanel;

/**
 * Draws the notes in the model to the GUI.
 */
public class DrawNotes extends JPanel implements GuiView, ActionListener {
  private ViewModel model;
  private MusicTracker tracker;
  private static final int CELL_WIDTH = 120;
  private Timer timer = new Timer(29, this);

  /**
   * Initializes a DrawNote and starts its timer.
   **/
  DrawNotes() {
    timer.start();
  }

  /**
   * sets this model to the given model.
   *
   * @param model the given ViewModel
   */
  @Override
  public void setViewModel(ViewModel model) {
    this.model = model;
  }

  /**
   * Sets this tracker to the given tracker.
   *
   * @param tracker the given tracker
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    this.tracker = tracker;
  }

  /**
   * Adds the given KeyListener to the class.
   *
   * @param listener the given KeyListener
   */
  @Override
  public void setKeyListener(KeyListener listener) {
    this.addKeyListener(listener);
  }

  /**
   * Adds the given mouseListener to the Notes.
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
   * Sets the visibility true so that the GUI is visible.
   */
  @Override
  public void initialize() {
    this.setVisible(true);
  }

  @Override
  public Map<Integer, Rectangle> getMap() {
    return null;
  }

  /**
   * This method draws the notes, grid, pitches, keys and red line to the GUI.
   *
   * @param g the Graphics
   */
  @Override
  public void paintComponent(Graphics g) {

    if (model != null) {
      g.setColor(Color.LIGHT_GRAY);
      g.fillRect(0, 0, 500000, 800);

      g.setColor(Color.BLACK);

      Note highest = model.getHighestNote();
      Note lowest = model.getLowestNote();
      int lastBeat = model.getFinalBeat();

      int ordinalHighestOctave = highest.getOctave().ordinal();
      int ordinalHighestPitch = highest.getPitch().ordinal();
      int ordinalLowOctave = lowest.getOctave().ordinal();
      int ordinalLowPitch = lowest.getPitch().ordinal();

      int from = ordinalLowOctave * 12 + ordinalLowPitch;
      int to = ordinalHighestOctave * 12 + ordinalHighestPitch;
      List<String> allNotes = this.allNotes().subList(from, to + 1);


      // draws the grid
      for (int i = 0; i < allNotes.size(); i++) {
        for (int j = 0; j < (lastBeat / 4); j++) {
          g.setColor(Color.BLACK);
          g.fillRect(j * CELL_WIDTH + 75, i * 13 + 30, CELL_WIDTH, 13);
          g.setColor(Color.WHITE);
          g.fillRect(j * CELL_WIDTH + 77, i * 13 + 32, CELL_WIDTH - 3, 13 - 3);
        }
      }

      //draws the beats
      for (int i = 0; i < model.getFinalBeat(); i += 4) {
        g.setColor(Color.BLACK);
        int x = 30;
        if (i == 0) {
          x = 75;
          g.drawString(Integer.toString(i), x, 29);

        } else {
          g.drawString(Integer.toString(i), i * x + 75, 29);
        }
      }

      //draws all notes
      for (int i = 0; i < lastBeat; i++) {
        List<Note> notesAtBeat = model.getNotesPlayingAtBeat(i);
        for (int n = 0; n < notesAtBeat.size(); n++) {
          Note current = notesAtBeat.get(n);

          if (current.getBeat() == i) {
            g.setColor(Color.BLACK);
            g.fillRect(i * 30 + 75, (to - current.getNumericalValue()) * 13
                    + 32, 27, 11);
          } else {
            g.setColor(new Color(250, (current.getNumericalValue() + 100) % 220,
                    (current.getNumericalValue() + 80)));
            g.fillRect(i * 30 + 75 - 3, (to - current.getNumericalValue()) * 13
                    + 32, 30, 11);
          }
        }
      }

      //draws the pitches on left hand side
      for (int i = 0; i < allNotes.size(); i++) {
        g.setColor(Color.BLACK);

        int x = 20;
        int y = 13;

        if (allNotes.get(i).contains("B")) {
          g.setColor(Color.BLACK);
          g.fillRect(75, (y * (i + 1) + 30),
                  CELL_WIDTH * (model.getFinalBeat() / 4), 4);
        }

        if (i == 0) {
          g.drawString(allNotes.get(i), x, y * allNotes.size() + 32);
        } else {
          g.drawString(allNotes.get(i), x, (y * allNotes.size() + 32) - y * i);
          g.fillRect(75, (y * (i + 1) + 30),
                  CELL_WIDTH * (model.getFinalBeat() / 4), 4);
        }
      }

      //draw red line
      if (tracker != null) {
        g.setColor(Color.RED);

        if (tracker.getCurrentPosition() <= 0
                || tracker.getCurrentPosition() > model.getFinalBeat()) {
          g.fillRect(75, 32, 4,
                  allNotes.size() * 13);
        } else {
          g.fillRect(75 + 30 * (int) (tracker.getCurrentPosition()), 31, 4,
                  allNotes.size() * 13);
        }
      }
    }
  }

  /**
   * Gets a List of all possible notes as strings.
   *
   * @return a List of String
   */
  private List<String> allNotes() {
    List<String> allNotes = new ArrayList<String>();

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

  /**
   * Gets pitch given an integer.
   *
   * @param i an integer
   * @return a Pitch
   */
  private Pitch getPitch(int i) {
    return Pitch.values()[i];
  }

  /**
   * Gets Octave given an integer.
   *
   * @param i an integer
   * @return an Octave
   */
  private Octave getOctave(int i) {
    return Octave.values()[i];
  }

  /**
   * Repaints the GUI continuously.
   *
   * @param e an ActionEvent
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == timer) {
      repaint();
    }
  }

  /**
   * Creates a list of all the notes as strings.
   *
   * @return a List of Strings to represent notes
   */
  List<String> getAllNotes() {
    return this.allNotes();
  }
}
