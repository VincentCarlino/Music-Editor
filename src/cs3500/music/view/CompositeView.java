package cs3500.music.view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * A composite view that runs both a gui view and a midi view.
 */
public class CompositeView extends JFrame implements GUIViewOperations {
  GUIViewOperations gui;
  View midi;
  ViewModel model;
  boolean isPlaying;


  /**
   * Constructs a composite view with given views to use.
   *
   * @param gui  The gui to be used by this view.
   * @param midi The midi view to be used by this view.
   */
  public CompositeView(GUIViewOperations gui, View midi) {
    this.gui = gui;
    this.midi = midi;
    isPlaying = false;
  }


  @Override
  public void run() {
    gui.run();
    midi.run();
  }

  @Override
  public void setModel(ViewModel model) {
    gui.setModel(model);
    midi.setModel(model);
  }

  @Override
  public void moveLeft() {
    gui.moveLeft();
    midi.moveLeft();
  }

  @Override
  public void moveRight() {
    gui.moveRight();
    midi.moveRight();
  }

  @Override
  public void start() {
    gui.start();
    midi.start();
  }

  @Override
  public void end() {
    gui.end();
    midi.end();
  }

  @Override
  public void play() {
    gui.play();
    midi.play();
    isPlaying = !isPlaying;
  }

  @Override
  public void setKeys(KeyListener k) {
    addKeyListener(k);
    gui.setKeys(k);
  }

  @Override
  public void setMouse(MouseListener m) {
    gui.setMouse(m);
  }


  @Override
  public void setCurrentBeat(Long current) {
    midi.setCurrentBeat(current);
  }

  @Override
  public Long getCurrentBeat() {
    return midi.getCurrentBeat();
  }

  @Override
  public void initialize() {
    gui.initialize();
    midi.initialize();
  }

  @Override
  public boolean getIsPlaying() {
    return gui.getIsPlaying();
  }

  @Override
  public void redraw() {

    gui.redraw();
  }

  @Override
  public JPanel getPiano() {

    return gui.getPiano();
  }

  @Override
  public int getPianoSize() {

    return gui.getPianoSize();
  }

  @Override
  public int getMargin() {

    return gui.getMargin();
  }

  @Override
  public long getPosition() {

    return gui.getPosition();
  }

  @Override
  public int getPianoLength() {

    return gui.getPianoLength();
  }

  @Override
  public int getBlackLength() {

    return gui.getBlackLength();
  }

  @Override
  public int getScoreHeight() {

    return gui.getScoreHeight();
  }

  @Override
  public int getWhiteWidth() {
    return gui.getWhiteWidth();
  }

  @Override
  public int getBlackWidth() {
    return gui.getBlackWidth();
  }

  @Override
  public int getBlackWhiteOffset() {
    return gui.getBlackWhiteOffset();
  }
}
