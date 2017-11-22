package cs3500.music.provider;

import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Map;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.JScrollBar;

/**
 * A skeleton Frame (i.e., a window) in Swing.
 */
public class GuiViewFrame extends javax.swing.JFrame implements GuiView, KeyboardView {
  private MusicTracker tracker;
  private ViewModel model;
  private final DrawNotes notePanel;
  private final DrawKeyboard drawKeyboardPanel;
  private JScrollPane scrollPane;
  private Timer timer;


  /**
   * Creates new GuiView.
   */
  public GuiViewFrame() {
    GridLayout myLayout = new GridLayout(2, 1);
    this.setPreferredSize(new Dimension(3150, 1500));
    this.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    this.setLayout(myLayout);

    this.notePanel = new DrawNotes();
    this.drawKeyboardPanel = new DrawKeyboard();

    JViewport notePort = new JViewport();
    notePort.setView(notePanel);
    notePort.setPreferredSize(new Dimension(1500, 700));

    scrollPane = new JScrollPane();
    scrollPane.setViewport(notePort);
    scrollPane.setHorizontalScrollBar(new JScrollBar(0));
    scrollPane.setVerticalScrollBar(new JScrollBar(1));
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    this.add(scrollPane);
    this.add(drawKeyboardPanel);
    this.pack();

    timer = new Timer();
    scroll();

  }

  /**
   * Sets the given tracker to this model and the note and keyboard panels.
   *
   * @param tracker to track where we are in the music being played
   */
  @Override
  public void setTracker(MusicTracker tracker) {
    this.tracker = tracker;
    notePanel.setTracker(tracker);
    drawKeyboardPanel.setTracker(tracker);
  }

  /**
   * Sets the visibility to true so we can see the GUI.
   */
  @Override
  public void initialize() {
    this.setVisible(true);
    drawKeyboardPanel.initialize();
    notePanel.initialize();
  }

  /**
   * Sets this model and the note and keyboard panels to the given model.
   *
   * @param model the given ViewModel
   */
  @Override
  public void setViewModel(ViewModel model) {
    notePanel.setPreferredSize(new Dimension(model.getFinalBeat() * 30 + 80, 300));
    notePanel.setViewModel(model);
    drawKeyboardPanel.setViewModel(model);
  }


  /**
   * Sets the given listener to this model and the note and keyboard panels.
   *
   * @param listener a new KeyListener
   */
  @Override
  public void setKeyListener(KeyListener listener) {
    notePanel.setKeyListener(listener);
    drawKeyboardPanel.setKeyListener(listener);
  }

  /**
   * Sets the mouseListener of the view frame, the note panel and the keyboard panel to
   * the given mouseListener.
   */
  @Override
  public void setMouseListener(MouseListener mouseListener) {
    notePanel.setMouseListener(mouseListener);
    drawKeyboardPanel.setMouseListener(mouseListener);
  }

  /**
   * Scrolls the view when the Tracker is no longer on screen.
   */
  public void scroll() {
    timer.scheduleAtFixedRate(new TimerTask() {

      @Override
      public void run() {
        long current = scrollPane.getHorizontalScrollBar().getValue();
        if (tracker != null && tracker.getCurrentPosition() * 30 > current
                + scrollPane.getWidth()) {
          scrollPane.getHorizontalScrollBar().setValue((int) current + scrollPane.getWidth() / 2);
        }
      }
    }, 15, 15);
  }


  /**
   * Gets the map from the keyboard panel.
   *
   * @return a map
   */
  public Map<Integer, Rectangle> getMap() {
    return drawKeyboardPanel.getMap();
  }

}
