package cs3500.music.provider;

import java.awt.Rectangle;
import java.util.Map;

/**
 * Controls all the methods we want to use for all the different views we have.
 */
public interface View {

  /**
   * Sets this model to the given model.
   * @param model the given model
   */
  void setViewModel(ViewModel model);

  /**
   * Sets this tracker to the given tracker.
   * @param tracker the given tracker
   */
  void setTracker(MusicTracker tracker);

  /**
   * Displays the view in specified format.
   */
  void initialize();

  /**
   * Returns the map in the View.
   */
  Map<Integer, Rectangle> getMap();

}
