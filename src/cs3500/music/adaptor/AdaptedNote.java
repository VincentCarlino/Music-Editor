package cs3500.music.adaptor;

import cs3500.music.provider.Note;
import cs3500.music.provider.Octave;
import cs3500.music.provider.Pitch;

/**
 * An adapted note that converts our note to the note given by the provider.
 */
public class AdaptedNote implements Note {
  cs3500.music.model.Note adaptee;

  /**
   * Constructs the adapted note.
   * @param adaptee The note to convert.
   */
  public AdaptedNote(cs3500.music.model.Note adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public boolean longerNote(Note other) {
    return getDuration() > other.getDuration();
  }

  @Override
  public int getDuration() {
    return adaptee.getDuration();
  }

  @Override
  public int getBeat() {
    return adaptee.getPosition();
  }

  @Override
  public Octave getOctave() {
    return OctaveAdaptor.adaptTo(adaptee.getOctave());
  }

  @Override
  public int getVolume() {
    return adaptee.getVolume();
  }

  @Override
  public int getNumericalValue() {
    return adaptee.getOctave() * 12 + adaptee.getTone().getValue();
  }

  @Override
  public boolean sameValues(int startTime, Pitch pitch, Octave octave) {
    return this.getBeat() == startTime &&
            this.getPitch() == pitch &&
            this.getOctave() == octave;
  }

  @Override
  public boolean ishigher(Note highestNote) {
    return getNumericalValue() > highestNote.getNumericalValue();
  }

  @Override
  public Pitch getPitch() {
    return ToneAdaptor.adaptTo(adaptee.getTone());
  }

  @Override
  public int getInstrument() {
    return adaptee.getInstrument();
  }

  @Override
  public boolean isLower(Note lowestNote) {
    return getNumericalValue() < lowestNote.getNumericalValue();
  }

  @Override
  public boolean isPlaying(int beat) {
    return beat >= getBeat() && beat < getBeat() + getDuration();
  }
}
