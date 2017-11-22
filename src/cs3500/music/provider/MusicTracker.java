package cs3500.music.provider;

/**
 * This interface keeps track of how far into a piece of music the program is.
 */
public interface MusicTracker {
  /**
   * To return the current position in a the piece of music being played.
   */
  long getCurrentPosition();

  /**
   * Changes the position of where we are in the piece of music being played.
   */
  void changePosition(long n);

}
