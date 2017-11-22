import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MockSequencer;
import cs3500.music.util.MusicReader;
import cs3500.music.view.MidiView;
import cs3500.music.view.View;
import cs3500.music.view.ViewModel;

import org.junit.Test;

import javax.sound.midi.Sequencer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Tests for the midi view.
 */
public class MidiTest {

  @Test
  public void testOutput() {
    Appendable build = new StringBuilder();
    Sequencer mock = new MockSequencer(build);
    View view = new MidiView(mock);
    List<Note> notes = new ArrayList<>();
    notes.add(new Note(Tone.C, 0, 0, 2, 1, 1));
    notes.add(new Note(Tone.C_SHARP, 6, 10, 2, 1, 1));
    MusicEditorOperations model = new MusicEditorModel(notes, 140);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("Tempo 140.0\n" +
            "NOTE 0 ON \n" +
            "NOTE 0 OFF \n" +
            "NOTE 73 ON \n" +
            "NOTE 73 OFF \n", build.toString());
  }

  @Test
  public void testMaryOutput() throws FileNotFoundException {

    CompositionBuilder<MusicEditorOperations> builder = new MusicEditorModel.Builder();
    MusicEditorOperations model =
            MusicReader.parseFile(new FileReader("mary-little-lamb.txt"), builder);
    Appendable build = new StringBuilder();
    Sequencer mock = new MockSequencer(build);
    View view = new MidiView(mock);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("Tempo 200000.0\n" +
            "NOTE 64 ON \n" +
            "NOTE 55 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 60 ON \n" +
            "NOTE 60 OFF \n" +
            "NOTE 55 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 64 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 62 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 64 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 64 OFF \n" +
            "NOTE 67 ON \n" +
            "NOTE 67 OFF \n" +
            "NOTE 67 ON \n" +
            "NOTE 67 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 60 ON \n" +
            "NOTE 60 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 62 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 64 OFF \n" +
            "NOTE 55 ON \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 62 OFF \n" +
            "NOTE 64 ON \n" +
            "NOTE 64 OFF \n" +
            "NOTE 62 ON \n" +
            "NOTE 55 OFF \n" +
            "NOTE 62 OFF \n" +
            "NOTE 52 ON \n" +
            "NOTE 60 ON \n" +
            "NOTE 52 OFF \n" +
            "NOTE 60 OFF \n", build.toString());
  }

}
