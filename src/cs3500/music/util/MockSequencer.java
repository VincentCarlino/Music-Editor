package cs3500.music.util;

import javax.sound.midi.ControllerEventListener;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.sound.midi.Transmitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A mock midi sequencer that is used to test the midi view.
 */
public class MockSequencer implements Sequencer {
  Appendable result;

  /**
   * Creates a mock midi sequencer that appends a sequence to the given result.
   *
   * @param result The output path of the result of the sequence
   */
  public MockSequencer(Appendable result) {
    this.result = result;
  }


  @Override
  public void setSequence(Sequence sequence) throws InvalidMidiDataException {
    for (Track t : sequence.getTracks()) {
      for (int i = 0; i < t.size() - 1; i++) {
        String line = "";
        switch (t.get(i).getMessage().getStatus()) {
          case 144:
            line += "NOTE " + ((ShortMessage) t.get(i).getMessage()).getData1() + " ON ";
            break;
          case 128:
            line += "NOTE " + ((ShortMessage) t.get(i).getMessage()).getData1() + " OFF ";
            break;
          default:
            break;
        }
        line += "\n";
        try {
          result.append(line);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void setSequence(InputStream stream) throws IOException, InvalidMidiDataException {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public Sequence getSequence() {
    return null;
  }

  @Override
  public void start() {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */

  }

  @Override
  public void stop() {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */

  }

  @Override
  public boolean isRunning() {
    return false;
  }

  @Override
  public void startRecording() {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public void stopRecording() {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public boolean isRecording() {
    return false;
  }

  @Override
  public void recordEnable(Track track, int channel) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public void recordDisable(Track track) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public float getTempoInBPM() {
    return 0;
  }

  @Override
  public void setTempoInBPM(float bpm) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public float getTempoInMPQ() {
    return 0;
  }

  @Override
  public void setTempoInMPQ(float mpq) {
    try {
      result.append("Tempo " + mpq + "\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void setTempoFactor(float factor) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public float getTempoFactor() {
    return 0;
  }

  @Override
  public long getTickLength() {
    return 0;
  }

  @Override
  public long getTickPosition() {
    return 0;
  }

  @Override
  public void setTickPosition(long tick) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public long getMicrosecondLength() {
    return 0;
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public void close() {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }

  @Override
  public void setMicrosecondPosition(long microseconds) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public void setMasterSyncMode(SyncMode sync) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public SyncMode getMasterSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getMasterSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setSlaveSyncMode(SyncMode sync) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public SyncMode getSlaveSyncMode() {
    return null;
  }

  @Override
  public SyncMode[] getSlaveSyncModes() {
    return new SyncMode[0];
  }

  @Override
  public void setTrackMute(int track, boolean mute) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public boolean getTrackMute(int track) {
    return false;
  }

  @Override
  public void setTrackSolo(int track, boolean solo) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public boolean getTrackSolo(int track) {
    return false;
  }

  @Override
  public boolean addMetaEventListener(MetaEventListener listener) {
    return false;
  }

  @Override
  public void removeMetaEventListener(MetaEventListener listener) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public int[] addControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public int[] removeControllerEventListener(ControllerEventListener listener, int[] controllers) {
    return new int[0];
  }

  @Override
  public void setLoopStartPoint(long tick) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public long getLoopStartPoint() {
    return 0;
  }

  @Override
  public void setLoopEndPoint(long tick) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public long getLoopEndPoint() {
    return 0;
  }

  @Override
  public void setLoopCount(int count) {
    /**
     * Method not needed for testing midi view but necessary for implementing a sequencer.
     */
  }

  @Override
  public int getLoopCount() {
    return 0;
  }
}
