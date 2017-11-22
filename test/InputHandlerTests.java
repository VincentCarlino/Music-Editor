import org.junit.Test;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cs3500.music.controller.Keyboard;
import cs3500.music.controller.Mouse;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the input handlers.
 */
public class InputHandlerTests {

  @Test
  public void testMouse() {
    Appendable result = new StringBuilder();
    Map<Integer, Runnable> inputs = new HashMap<>();
    inputs.put(MouseEvent.BUTTON1, () -> {
      try {
        result.append("This should appear.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    MouseListener mouse = new Mouse(inputs);
    assertEquals("", result.toString());
    mouse.mouseReleased(new MouseEvent(new Component() {
    }, 0, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON1));
    //The listener contains the key so it should run its action.
    assertEquals("This should appear.", result.toString());
    mouse.mouseReleased(new MouseEvent(new Component() {
    }, 0, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON2));
    //The listener does not contain this key, nothing is changed.
    assertEquals("This should appear.", result.toString());

  }

  @Test
  public void testKeyboard() {
    Appendable result = new StringBuilder();
    Map<Character, Runnable> typed = new HashMap<>();
    Map<Integer, Runnable> pressed = new HashMap<>();
    Map<Integer, Runnable> released = new HashMap<>();
    typed.put('c', () -> {
      try {
        result.append("typed runs \n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    pressed.put(KeyEvent.VK_SPACE, () -> {
      try {
        result.append("pressed runs \n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    released.put(KeyEvent.VK_RIGHT, () -> {
      try {
        result.append("released runs \n");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    KeyListener keyboard = new Keyboard(typed, pressed, released);
    assertEquals("", result.toString());
    keyboard.keyTyped(new KeyEvent(new Component() {
    }, 0, 0, 0, 0, 'c'));
    assertEquals("typed runs \n", result.toString());
    keyboard.keyPressed(new KeyEvent(new Component() {
    }, 0, 0, 0, KeyEvent.VK_SPACE));
    assertEquals("typed runs \npressed runs \n", result.toString());
    keyboard.keyReleased(new KeyEvent(new Component() {
    }, 0, 0, 0, KeyEvent.VK_RIGHT));
    assertEquals("typed runs \npressed runs \nreleased runs \n", result.toString());
    keyboard.keyReleased(new KeyEvent(new Component() {
    }, 0, 0, 0, KeyEvent.VK_LEFT));
    assertEquals("typed runs \npressed runs \nreleased runs \n", result.toString());

  }
}
