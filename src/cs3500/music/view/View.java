package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * The view of a music editor.
 */
public interface View {

  /**
   * Runs the given view and displays/plays the music of a piece.
   */
  void run();

  /**
   * Sets the read-only view model of this view.
   *
   * @param model The view model that will be used for data to display/play.
   */
  void setModel(ViewModel model);

  /**
   * Moves the current beat position to the left if possible.
   */
  void moveLeft();

  /**
   * Move the current beat position to the right if possible.
   */
  void moveRight();

  /**
   * Moves to the beginning of the piece.
   */
  void start();

  /**
   * Moves to the end of the piece.
   */
  void end();

  /**
   * Plays or pauses the piece based on whether the piece is playing.
   */
  void play();

  /**
   * Sets the key handler that listens for user keystrokes.
   *
   * @param k The keylistener to be used.
   */
  void setKeys(KeyListener k);

  /**
   * Sets the mouse handler that listens for user mouse input.
   *
   * @param m The mousehandler to be used.
   */
  void setMouse(MouseListener m);

  /**
   * Sets the current beat to the given number.
   *
   * @param current The beat to move to.
   */
  void setCurrentBeat(Long current);

  /**
   * Gets the beat that is currently playing.
   *
   * @return The current beat.
   */
  Long getCurrentBeat();

  /**
   * Initializes the piece in the case that user input has made a change to the model.
   */
  void initialize();

  /**
   * Says whether the piece is playing or not.
   *
   * @return Whether the piece is playing.
   */
  boolean getIsPlaying();


}
