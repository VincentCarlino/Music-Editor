package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.model.Note;

/**
 * The console view of a music piece.  It displays the notes of a music piece vertically.
 */
public class ConsoleView implements View {
  private Map<Integer, List<Note>> piece;
  private ViewModel model;
  private Appendable ap;

  /**
   * Constructs a console view that sends directly to system.out.
   */
  public ConsoleView() {
    this.ap = System.out;
  }

  /**
   * Given an appendable as an output path, constructs a console view.
   *
   * @param ap The output path of the view.
   */
  public ConsoleView(Appendable ap) {
    this.ap = ap;
  }

  @Override
  public void run() {
    initialize();
    String result = topLineHelper();
    int l = model.getLength();
    for (int i = 0; i <= l; i++) {
      result += lineNumber(i, String.valueOf(l).length());
      for (List<Note> list : piece.values()) {
        if (noteStart(list, i)) {
          result += "  X  ";
        } else if (noteExtension(list, i)) {
          result += "  |  ";
        } else {
          result += "     ";
        }
      }
      result += "\n";
    }

    try {
      ap.append(result);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  @Override
  public void setModel(ViewModel model) {
    this.model = model;
  }

  @Override
  public void moveLeft() {
    /**
     * Method not needed for now.
     */
  }

  @Override
  public void moveRight() {
    /**
     * Method not needed for now.
     */

  }

  @Override
  public void start() {
    /**
     * Unused in console.
     */
  }

  @Override
  public void end() {
    /**
     * Unused in console.
     */
  }

  @Override
  public void play() {
    /**
     * Method not needed for now.
     */
  }

  @Override
  public void setKeys(KeyListener k) {
    /**
     * Key listener not used in this view.
     */
  }

  @Override
  public void setMouse(MouseListener m) {
    /**
     * Mouse listener not needed.
     */
  }


  @Override
  public void setCurrentBeat(Long current) {
    /**
     * Current beat not used in this view.
     */
  }

  @Override
  public Long getCurrentBeat() {
    return Long.valueOf(0);
  }


  /**
   * Initializes the piece to prepare it to show any changes that were made to the
   * list of notes.  First finds the bounds of the piece, then reinitializes the map of notes
   * according to note number.
   */
  @Override
  public void initialize() {
    piece = new TreeMap<>();
    for (int i = model.getLowest().translate(); i <= model.getHighest().translate(); i++) {
      piece.put(i, new ArrayList<>());
    }
    for (Note n : model.getNotes()) {
      piece.get(n.translate()).add(n);
    }
  }

  @Override
  public boolean getIsPlaying() {
    return false;
  }

  /**
   * Creates the top line to be used in the getState function.
   *
   * @return The top line of the getState function.
   */
  private String topLineHelper() {
    String result = " ";
    for (int i = 0; i < String.valueOf(model.getLength()).length(); i++) {
      result += " ";
    }
    Note current = model.getLowest();
    for (int i = model.getLowest().translate(); i < model.getHighest().translate() + 1; i++) {
      switch (current.toString().length()) {
        case 2:
          result += "  " + current.toString() + " ";
          break;
        case 3:
          result += " " + current.toString() + " ";
          break;
        case 4:
          result += current.toString() + " ";
          break;
        default:
          result += current.toString();
          break;
      }
      current = current.next();
    }
    result += "\n";
    return result;
  }

  /**
   * Generates the string of line number based on the length of the largest line number.
   *
   * @param number  The number to generate a string for.
   * @param spacing The length that the string must be.
   * @return The string for the line number.
   */
  private String lineNumber(int number, int spacing) {
    int len = String.valueOf(number).length();
    String result = "";
    int diff = spacing - len;
    for (int i = 0; i <= diff; i++) {
      result += " ";
    }
    result += number;
    return result;
  }

  /**
   * Checks to see if there is the extension of a note in the given beat for a given note list.
   * Is used as a helper for the getState method.
   *
   * @param notes      The list of notes to check.
   * @param beatNumber The beat number to check.
   * @return Whether a note extension exists.
   */
  private boolean noteExtension(List<Note> notes, int beatNumber) {
    boolean result = false;
    for (Note n : notes) {
      if (beatNumber > n.getPosition() && beatNumber < (n.getPosition() + n.getDuration())) {
        result = true;
      }
    }
    return result;
  }

  /**
   * Checks to see if there is the start of a note in the given beat for a given note list.
   * Is used as a helper for the getState method.
   *
   * @param notes      The list of notes to check.
   * @param beatNumber The beat number to check.
   * @return Whether a note start exists.
   */
  private boolean noteStart(List<Note> notes, int beatNumber) {
    boolean result = false;
    for (Note n : notes) {
      if (beatNumber == n.getPosition()) {
        result = true;
      }
    }
    return result;
  }
}
