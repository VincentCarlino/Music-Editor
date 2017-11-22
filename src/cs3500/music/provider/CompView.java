package cs3500.music.provider;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.Rectangle;
import java.util.Map;

/**
 * Represents a combined view which has both a midiview and a guiView.
 */
public class CompView implements CombinedView {
  private GuiView guiView;
  private MidiView midiView;

  /**
   * A constructor to initalize the views and set the guiView tracker to the midiView.
   *
   * @param guiView  the GUIview, a view
   * @param midiView the midiView, a view
   */
  public CompView(GuiView guiView, MidiViewImpl midiView) {
    this.guiView = guiView;
    this.midiView = midiView;
    guiView.setTracker(midiView);
  }

  /**
   * Sets the current view's model to the given model.
   *
   * @param model the given model
   */
  @Override
  public void setViewModel(ViewModel model) {
    guiView.setViewModel(model);
    midiView.setViewModel(model);
  }

  /**
   * Inherited tracker setter, is not used.
   *
   * @param tracker the given tracker
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    //does nothing
  }

  /**
   * Initializes the midi view and the gui view.
   */
  @Override
  public void initialize() {
    midiView.initialize();
    guiView.initialize();
  }

  /**
   * Gets the map out of the guiView.
   *
   * @return a map.
   */
  @Override
  public Map<Integer, Rectangle> getMap() {
    return guiView.getMap();
  }

  /**
   * Stops the music from playing.
   */
  @Override
  public void pause() {
    midiView.pause();
  }

  /**
   * Starts the music in the midiview.
   */
  @Override
  public void start() {
    midiView.start();
  }

  /**
   * Gets the current position of the tracker in the midi view.
   *
   * @return a long
   */
  @Override
  public long getCurrentPosition() {
    return midiView.getCurrentPosition();
  }

  /**
   * Sets the tracker to the new given position in the midi view.
   *
   * @param n a long
   */
  @Override
  public void changePosition(long n) {
    midiView.changePosition(n);
  }

  /**
   * Sets the GUIview's key listener to the given key listener.
   *
   * @param listener a new KeyListener
   */
  @Override
  public void setKeyListener(KeyListener listener) {
    guiView.setKeyListener(listener);
  }

  /**
   * Sets the guiView's mouse listener to the given listener.
   *
   * @param mouseListener a given mouselistener
   */
  @Override
  public void setMouseListener(MouseListener mouseListener) {
    guiView.setMouseListener(mouseListener);
  }

  @Override
  public void scroll() {
    //do nothing
  }
}
