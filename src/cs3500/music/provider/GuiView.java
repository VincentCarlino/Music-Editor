package cs3500.music.provider;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * This interface represents the GUI for the View.
 */
public interface GuiView extends View {
  /**
   * Sets the keyListener to the given KeyListener.
   * @param listener a new KeyListener
   */
  void setKeyListener(KeyListener listener);

  /**
   * Sets the mouseListener to the given MouseListener.
   * @param mouseListener a new mouseListener
   */
  void setMouseListener(MouseListener mouseListener);

  /**
   * Scrolls the view when the Tracker is no longer on screen.
   */
  void scroll();
}
