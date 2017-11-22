package cs3500.music;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.adaptor.AdaptedController;
import cs3500.music.adaptor.AdaptedModel;
import cs3500.music.model.MusicEditorModel;
import cs3500.music.model.MusicEditorOperations;
import cs3500.music.provider.CombinedView;
import cs3500.music.provider.MusicViewCreator;
import cs3500.music.provider.View;
import cs3500.music.provider.ViewModelAdapter;
import cs3500.music.util.CompositionBuilder;
import cs3500.music.util.MusicReader;

/**
 * The ProviderEditor than runs a view given by the provider.
 */
public class ProviderEditor {


  /**
   * The main method of the provider editor.  Takes in a file to read and a type of view to use
   * and executes the music editor as designed by the provider.
   *
   * @param args The file to read and the view to use
   * @throws FileNotFoundException    When the given file is not found.
   * @throws InvalidMidiDataException If the midi player somehow malfunctions.
   */
  public static void main(String[] args) throws InvalidMidiDataException, FileNotFoundException {
    CompositionBuilder<MusicEditorOperations> builder = new MusicEditorModel.Builder();
    MusicEditorOperations model = MusicReader.parseFile(new FileReader(args[0]), builder);
    View view = MusicViewCreator.create(args[1]);
    if (view instanceof CombinedView) {
      new AdaptedController(model, (CombinedView) view).run();
    }
    else {
      view.setViewModel(new ViewModelAdapter(new AdaptedModel(model)));
      view.initialize();
    }
  }
}

