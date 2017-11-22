package cs3500.music.provider;

/**
 * This class implements the music tracker interface and manages the position of the player in a
 * piece of music. The position is initialized to 0 at the beginning.
 */
public class MusicTrackerImpl implements MusicTracker {
  private long position = 0;

  /**
   * Returns the position of the player in the piece of music being played.
   * @return the current position in a piece of music.
   */
  @Override
  public long getCurrentPosition() {
    return this.position;
  }

  /**
   * Changes the position of the player to the given position n.
   * @param n the new position that will be used for the piece of music being played.
   */
  @Override
  public void changePosition(long n) {
    this.position = n;
  }

}
