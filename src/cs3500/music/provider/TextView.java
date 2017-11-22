package cs3500.music.provider;

import java.awt.Rectangle;
import java.util.Map;

/**
 * The textview class implements the view and outputs a String representation of the music model.
 */
public class TextView implements View {
  private ViewModel model;
  private MusicTracker tracker;

  /**
   * A default constructor for the text view.
   */
  public TextView() {
    //nothing here.
  }

  public TextView(ViewModel model, MusicTracker tracker) {
    this.model = model;
    this.tracker = tracker;
  }

  /**
   * Sets this model to the given Model.
   * @param model the given ViewModel
   */
  @Override
  public void setViewModel(ViewModel model) {
    this.model = model;
  }

  /**
   * Sets this tracker to the given tracker.
   * @param tracker the given tracker
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    this.tracker = tracker;
  }

  /**
   * Outputs the current game state of the model, i.e. outputs a string representation of the
   * model.
   */
  @Override
  public void initialize() {
    //Do nothing.
  }

  /**
   * There is no map to return.
   * @return a map
   */
  @Override
  public Map<Integer, Rectangle> getMap() {
    return null;
  }
}
