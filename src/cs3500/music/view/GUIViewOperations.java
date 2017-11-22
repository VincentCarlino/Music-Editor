package cs3500.music.view;

import javax.swing.JPanel;

/**
 * The operations needed for a GUIView.
 */
public interface GUIViewOperations extends View {

  /**
   * Redraws this view to update it.
   */
  void redraw();

  /**
   * Gets the piano panel of this view.
   *
   * @return The piano panel.
   */
  JPanel getPiano();

  /**
   * Gets the length of a piano key.
   *
   * @return The length of a white key on the piano.
   */
  int getPianoSize();

  /**
   * Gets the margin of the components of the view.
   *
   * @return The margins of this view.
   */
  int getMargin();

  /**
   * Gets the current beat that is stored in the view.
   *
   * @return The current beat.
   */
  long getPosition();

  /**
   * Gets the length of a white piano key. Used for determining whether a key has been clicked.
   *
   * @return The length of a white piano key.
   */
  int getPianoLength();


  /**
   * Gets the length of a black piano key. Used for determining whether a key has been clicked.
   *
   * @return The length of a black piano key.
   */
  int getBlackLength();

  /**
   * Gets the heignt of the score. Used for helping determine mouse position.
   *
   * @return The height of the score area.
   */
  int getScoreHeight();

  /**
   * Gets the width of a white piano key.
   * @return The width of the white key.
   */
  int getWhiteWidth();

  /**
   * Gets the width of a black piano key.
   * @return The width of the black key.
   */
  int getBlackWidth();

  /**
   * Gets the space between the first white key and the first black key.
   * @return The space between the white and black key.
   */
  int getBlackWhiteOffset();

}
