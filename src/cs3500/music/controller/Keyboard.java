package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * The keyboard listener that is used to control various operations in the view of the
 * music editor.
 */
public class Keyboard implements KeyListener {
  private Map<Character, Runnable> typed;
  private Map<Integer, Runnable> pressed;
  private Map<Integer, Runnable> released;


  /**
   * Creates the keyboard listener based on maps of how keys are manipulated.
   *
   * @param typed    The typed keys.
   * @param pressed  The pressed keys.
   * @param released The released keys.
   */
  public Keyboard(Map<Character, Runnable> typed, Map<Integer, Runnable> pressed,
                  Map<Integer, Runnable> released) {
    this.typed = typed;
    this.pressed = pressed;
    this.released = released;
  }


  @Override
  public void keyTyped(KeyEvent e) {
    if (typed.containsKey(e.getKeyChar())) {
      typed.get(e.getKeyChar()).run();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (pressed.containsKey(e.getKeyCode())) {
      pressed.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (released.containsKey(e.getKeyCode())) {
      released.get(e.getKeyCode()).run();
    }
  }
}
