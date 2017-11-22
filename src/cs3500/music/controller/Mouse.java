package cs3500.music.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

/**
 * The mouse object that handles mouse input of the music editor.
 */
public class Mouse implements MouseListener {
  Map<Integer, Runnable> actions;
  int x;
  int y;

  /**
   * Produces a mouse listener with no actions.
   */
  public Mouse() {
    actions = new HashMap<>();
  }

  /**
   * Produces a mouse listener with actions determined by the user.
   *
   * @param actions The actions to be run based on user input.
   */
  public Mouse(Map<Integer, Runnable> actions) {
    this.actions = actions;
  }

  /**
   * Sets the actions that this mouse handler can do.
   *
   * @param actions The actions of the handler.
   */
  public void setActions(Map<Integer, Runnable> actions) {
    this.actions = actions;
  }

  /**
   * Gets the y position of the mouse.
   *
   * @return The y position last taken by the mouse.
   */
  public int getY() {
    return y;
  }

  /**
   * Gets the x position of the mouse.
   *
   * @return The x position last taken by the mouse.
   */
  public int getX() {
    return x;

  }

  @Override
  public void mouseClicked(MouseEvent e) {
    /**
     * Not needed.
     */
  }

  @Override
  public void mousePressed(MouseEvent e) {
    /**
     * Not needed.
     */
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    this.x = e.getX();
    this.y = e.getY();
    if (actions.containsKey(e.getButton())) {
      actions.get(e.getButton()).run();
    }
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    /**
     * Not needed.
     */
  }

  @Override
  public void mouseExited(MouseEvent e) {
    /**
     * Not needed.
     */
  }
}
