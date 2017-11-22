import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.model.Note;
import cs3500.music.model.Tone;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.View;
import cs3500.music.view.ViewModel;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * tests for the music editor model.
 */
public class MusicModelTest {

  @Test
  public void testNotes() {
    Note a = new Note(Tone.C, 0, 0, 1);
    Note b = new Note(Tone.C_SHARP, 0, 0, 1);
    assertEquals("C0", a.toString());
    assertEquals("C#0", b.toString());
    assertEquals(a.next(), b);
    Note c = new Note(Tone.C, 1, 0, 1);
    Note d = new Note(Tone.B, 0, 0, 1);
    assertEquals(d.next(), c);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNote1() {
    Note a = new Note(Tone.C, -1, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNote2() {
    Note a = new Note(Tone.C, 13, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNote3() {
    Note a = new Note(Tone.C, 1, -1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNote4() {
    Note a = new Note(Tone.C, 1, 0, 0);
  }

  @Test
  public void testPrint1() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 1, 0, 1));
    model.addNote(new Note(Tone.E, 1, 3, 7));
    model.addNote(new Note(Tone.C, 1, 3, 7));
    model.addNote(new Note(Tone.G, 1, 3, 7));
    model.addNote(new Note(Tone.D_SHARP, 1, 3, 7));
    model.addNote(new Note(Tone.C, 2, 0, 1));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C1  C#1   D1  D#1   E1   F1  F#1   G1  G#1   A1  A#1   B1   C2 \n" +
            " 0  X                                                           X  \n" +
            " 1                                                                 \n" +
            " 2                                                                 \n" +
            " 3  X              X    X              X                           \n" +
            " 4  |              |    |              |                           \n" +
            " 5  |              |    |              |                           \n" +
            " 6  |              |    |              |                           \n" +
            " 7  |              |    |              |                           \n" +
            " 8  |              |    |              |                           \n" +
            " 9  |              |    |              |                           \n",
        build.toString());
  }

  @Test
  public void testPrint2() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 0, 0, 6));
    model.addNote(new Note(Tone.C, 0, 4, 2));
    model.addNote(new Note(Tone.C, 1, 4, 4));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1  |                                                              \n" +
            " 2  |                                                              \n" +
            " 3  |                                                              \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n",
        build.toString());
  }

  @Test
  public void testPrint3() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 0, 0, 1));
    model.addNote(new Note(Tone.C, 0, 4, 2));
    model.addNote(new Note(Tone.C_SHARP, 0, 1, 1));
    model.addNote(new Note(Tone.D, 0, 2, 1));
    model.addNote(new Note(Tone.D_SHARP, 0, 3, 1));
    model.addNote(new Note(Tone.C, 1, 4, 4));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1       X                                                         \n" +
            " 2            X                                                    \n" +
            " 3                 X                                               \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n",
        build.toString());
  }

  @Test
  public void testRemove() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 0, 0, 1));
    model.addNote(new Note(Tone.C, 0, 4, 2));
    model.addNote(new Note(Tone.C_SHARP, 0, 1, 1));
    model.addNote(new Note(Tone.D, 0, 2, 1));
    model.addNote(new Note(Tone.D_SHARP, 0, 3, 1));
    model.addNote(new Note(Tone.C, 1, 4, 4));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1       X                                                         \n" +
            " 2            X                                                    \n" +
            " 3                 X                                               \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n",
        build.toString());
    model.removeNote(new Note(Tone.D, 0, 2, 1));
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1       X                                                         \n" +
            " 2            X                                                    \n" +
            " 3                 X                                               \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n" +
            "    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1       X                                                         \n" +
            " 2                                                                 \n" +
            " 3                 X                                               \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n",
        build.toString());
  }


  @Test
  public void testCombineCons() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 0, 0, 1));
    model.addNote(new Note(Tone.C, 0, 4, 2));
    model.addNote(new Note(Tone.C_SHARP, 0, 1, 1));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0 \n" +
        " 0  X       \n" +
        " 1       X  \n" +
        " 2          \n" +
        " 3          \n" +
        " 4  X       \n" +
        " 5  |       \n", build.toString());
    List<Note> adding = new ArrayList<>();
    adding.add(new Note(Tone.D, 0, 2, 1));
    adding.add(new Note(Tone.D_SHARP, 0, 3, 1));
    adding.add(new Note(Tone.C, 1, 4, 4));
    model.combineConsecutively(adding);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0 \n" +
            " 0  X       \n" +
            " 1       X  \n" +
            " 2          \n" +
            " 3          \n" +
            " 4  X       \n" +
            " 5  |       \n" +
            "     C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            "  0  X                                                              \n" +
            "  1       X                                                         \n" +
            "  2                                                                 \n" +
            "  3                                                                 \n" +
            "  4  X                                                              \n" +
            "  5  |                                                              \n" +
            "  6                                                                 \n" +
            "  7            X                                                    \n" +
            "  8                                                                 \n" +
            "  9                                                                 \n" +
            " 10                 X                                               \n" +
            " 11                                                                 \n" +
            " 12                                                                 \n" +
            " 13                                                                 \n" +
            " 14                                                              X  \n" +
            " 15                                                              |  \n" +
            " 16                                                              |  \n" +
            " 17                                                              |  \n",
        build.toString());

  }

  @Test
  public void testCombineSimul() {
    MusicEditorOperations model = new MusicEditorModel();
    model.addNote(new Note(Tone.C, 0, 0, 1));
    model.addNote(new Note(Tone.C, 0, 4, 2));
    model.addNote(new Note(Tone.C_SHARP, 0, 1, 1));
    Appendable build = new StringBuilder();
    View view = new ConsoleView(build);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0 \n" +
        " 0  X       \n" +
        " 1       X  \n" +
        " 2          \n" +
        " 3          \n" +
        " 4  X       \n" +
        " 5  |       \n", build.toString());
    List<Note> adding = new ArrayList<>();
    adding.add(new Note(Tone.D, 0, 2, 1));
    adding.add(new Note(Tone.D_SHARP, 0, 3, 1));
    adding.add(new Note(Tone.C, 1, 4, 4));
    model.combineSimultaneously(adding);
    view.setModel(new ViewModel(model));
    view.run();
    assertEquals("    C0  C#0 \n" +
            " 0  X       \n" +
            " 1       X  \n" +
            " 2          \n" +
            " 3          \n" +
            " 4  X       \n" +
            " 5  |       \n" +
            "    C0  C#0   D0  D#0   E0   F0  F#0   G0  G#0   A0  A#0   B0   C1 \n" +
            " 0  X                                                              \n" +
            " 1       X                                                         \n" +
            " 2            X                                                    \n" +
            " 3                 X                                               \n" +
            " 4  X                                                           X  \n" +
            " 5  |                                                           |  \n" +
            " 6                                                              |  \n" +
            " 7                                                              |  \n",
        build.toString());

  }

}
