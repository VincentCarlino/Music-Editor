package cs3500.music.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

import cs3500.music.model.Note;
import cs3500.music.model.Tone;

/**
 * A gui view that is able display music of a given piece.
 */
public class GUIView extends JFrame implements GUIViewOperations {
  protected ViewModel model;
  Map<Long, List<Note>> nom;
  private JPanel mainpanel;
  private JPanel scorepanel;
  private JPanel pianopanel;
  JScrollPane scrollPane;
  Timer timer;
  long beat;


  protected static final int NOTE_SIZE = 10; //The size of each note cell
  protected static final int MARGIN = NOTE_SIZE * 3; //The margin of the score and piano
  protected static final int PIANO_SIZE = 12; //The width of a white piano key
  protected static final int PIANO_LENGTH = PIANO_SIZE * 9; //The length of a key
  protected static final int BLACK_LENGTH = PIANO_LENGTH / 2; //The length of a black key

  /**
   * Creates the view and initializes all panels.
   */
  public GUIView() {
    super();
    this.scorepanel = new MusicPanel();
    this.mainpanel = new JPanel();
    this.pianopanel = new PianoPanel();
    this.beat = 0;
  }

  @Override
  public void moveLeft() {
    if (beat > 0) {
      if (!currentBeatIsVisible()) {
        int scrollpos = scrollPane.getHorizontalScrollBar().getValue();
        int scrollwidth = scrollPane.getHorizontalScrollBar().getWidth();
        scrollPane.getHorizontalScrollBar().setValue(scrollpos - scrollwidth);
      }
      beat--;
      repaint();
    }
  }

  @Override
  public void moveRight() {
    if (beat <= model.getLength()) {
      if (!currentBeatIsVisible()) {
        int scrollpos = scrollPane.getHorizontalScrollBar().getValue();
        int scrollwidth = scrollPane.getHorizontalScrollBar().getWidth();
        scrollPane.getHorizontalScrollBar().setValue(scrollpos + scrollwidth);
      }
      beat++;
      repaint();
    } else {
      timer.stop();
    }
  }

  /**
   * Checks if the current beat is within the visible area of the score.
   *
   * @return Whether the current beat can be seen.
   */
  private boolean currentBeatIsVisible() {
    int beatLoc = NOTE_SIZE * (int) beat + MARGIN;
    return beatLoc >= scrollPane.getHorizontalScrollBar().getValue() && beatLoc <=
            scrollPane.getHorizontalScrollBar().getValue() + scrollPane.
                    getHorizontalScrollBar().getWidth();
  }

  @Override
  public void start() {
    setCurrentBeat((long) 0);
    timer.stop();
    repaint();
  }

  @Override
  public void end() {
    setCurrentBeat((long) model.getLength());
    timer.stop();
    repaint();
  }

  @Override
  public void play() {
    if (timer.isRunning()) {
      timer.stop();
    } else {
      timer.start();
    }
  }


  @Override
  public void redraw() {
    repaint();
  }

  @Override
  public JPanel getPiano() {
    return pianopanel;
  }

  @Override
  public int getPianoSize() {
    return PIANO_SIZE;
  }

  @Override
  public int getMargin() {
    return MARGIN;
  }

  @Override
  public int getPianoLength() {
    return PIANO_LENGTH;
  }

  @Override
  public int getBlackLength() {
    return BLACK_LENGTH;
  }

  @Override
  public int getScoreHeight() {
    return scrollPane.getHeight();
  }

  @Override
  public int getWhiteWidth() {
    return PIANO_SIZE;
  }

  @Override
  public int getBlackWidth() {
    return PIANO_SIZE / 2;
  }

  @Override
  public int getBlackWhiteOffset() {
    return PIANO_SIZE - PIANO_SIZE / 4;
  }

  @Override
  public long getPosition() {
    return beat;
  }

  @Override
  public void run() {
    this.setTitle("Music Visual");
    this.setLayout(new BorderLayout());
    mainpanel.setLayout(new BorderLayout());
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.scorepanel.setPreferredSize(new Dimension((model.getLength() * NOTE_SIZE) +
            (4 * NOTE_SIZE) + MARGIN, (4 * NOTE_SIZE) +
            (model.getHighest().translate() - model.getLowest().translate()) *
                    NOTE_SIZE + MARGIN));
    this.pianopanel.setPreferredSize(new Dimension(MARGIN * 2 + 70 * PIANO_SIZE,
            100 + MARGIN / 2));
    this.mainpanel.add(pianopanel, BorderLayout.CENTER);
    this.mainpanel.add(scorepanel, BorderLayout.NORTH);
    this.scrollPane = new JScrollPane(scorepanel);
    this.add(scrollPane, BorderLayout.NORTH);
    this.add(pianopanel, BorderLayout.CENTER);
    this.setMinimumSize(this.getPreferredSize());
    this.pack();
    this.setVisible(true);
    timer = new Timer(model.getTempo() / 1000, (e) -> moveRight());
  }

  @Override
  public void setKeys(KeyListener k) {
    this.addKeyListener(k);
  }

  @Override
  public void setMouse(MouseListener m) {
    this.addMouseListener(m);
  }


  @Override
  public void setCurrentBeat(Long current) {
    beat = current;
    repaint();
  }

  @Override
  public Long getCurrentBeat() {
    return beat;
  }

  @Override
  public void initialize() {
    this.scorepanel.setPreferredSize(new Dimension((model.getLength() * NOTE_SIZE) +
            (4 * NOTE_SIZE) + MARGIN, (4 * NOTE_SIZE) +
            (model.getHighest().translate() - model.getLowest().translate()) *
                    NOTE_SIZE + MARGIN));
    nom = model.getNotesOnMeasure();
    scorepanel.revalidate();
    repaint();
  }

  @Override
  public boolean getIsPlaying() {
    return timer.isRunning();
  }

  @Override
  public void setModel(ViewModel model) {
    this.model = model;
    nom = model.getNotesOnMeasure();
  }


  /**
   * A MusicPanel is the panel of the gui view that displays the notes of the song in sequence.
   */
  class MusicPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      this.drawValuesAndMeasures(g);
      this.drawNotes(g);
      this.drawScrubber(g);
    }

    /**
     * Draws all note names and measure numbers of the song.
     *
     * @param g Graphics.
     */
    private void drawValuesAndMeasures(Graphics g) {
      g.setColor(Color.DARK_GRAY);
      int row = ((model.getHighest().translate() - model.getLowest().translate()) *
              NOTE_SIZE) + NOTE_SIZE * (3 / 2) + MARGIN;
      Note current = model.getLowest();
      for (int i = model.getLowest().translate(); i <= model.getHighest().translate(); i++) {
        g.drawString(current.toString(), NOTE_SIZE, row);
        row -= NOTE_SIZE;
        current = current.next();
      }
      for (int y = 0; y <= model.getHighest().translate() - model.getLowest().translate(); y++) {
        for (int x = 0; x < model.getLength(); x++) {
          if (x % 4 == 0) {
            g.drawRect(MARGIN + (NOTE_SIZE * x), MARGIN + (NOTE_SIZE * y), NOTE_SIZE * 4,
                    NOTE_SIZE);
            g.drawString(Integer.toString(x), MARGIN - 2 + NOTE_SIZE * x,
                    MARGIN * (3 / 2) - 2);
          }
        }
      }
    }

    /**
     * Draws all notes in the piece.
     *
     * @param g Graphics.
     */
    private void drawNotes(Graphics g) {

      int note = 0;
      for (int i = model.getHighest().translate(); i >= model.getLowest().translate(); i--) {
        for (Note n : model.getNotes()) {
          if (i == n.translate()) {
            g.setColor(new Color(241, 55, 107));
            g.fillRect(MARGIN + NOTE_SIZE * n.getPosition(), MARGIN + NOTE_SIZE * note,
                    NOTE_SIZE, NOTE_SIZE);

            for (int e = 1; e < n.getDuration(); e++) {
              g.setColor(new Color(0xFFFA00));
              g.fillRect(MARGIN + NOTE_SIZE * (n.getPosition() + e), MARGIN + NOTE_SIZE * note,
                      NOTE_SIZE, NOTE_SIZE);
            }
          }
        }

        note++;
      }
    }

    /**
     * Draws the scrubber/red line the tracks the current beat.
     *
     * @param g Graphics.
     */
    private void drawScrubber(Graphics g) {
      g.setColor(new Color(44, 180, 102));
      g.fillRect(NOTE_SIZE * (int) beat + MARGIN, MARGIN,
              2, NOTE_SIZE * (model.getHighest().translate() - model.getLowest().translate()
                      + 1));
    }
  }

  /**
   * A PianoPanel displays the piano keys in the gui view.
   */
  class PianoPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      drawWhiteKeys(g);
      drawBlackKeys(g);
    }

    /**
     * Checks to see if a given tone is sharp.
     *
     * @param t The tone to check.
     * @return Whether the tone is sharp.
     */
    private boolean isSharp(Tone t) {
      return t.equals(Tone.A_SHARP) ||
              t.equals(Tone.C_SHARP) ||
              t.equals(Tone.D_SHARP) ||
              t.equals(Tone.F_SHARP) ||
              t.equals(Tone.G_SHARP);
    }

    /**
     * Draws all of the white keys of the piano and highlights the keys that are currently playing.
     *
     * @param g Graphics.
     */
    private void drawWhiteKeys(Graphics g) {
      int octave = 0;
      Tone current = Tone.C;
      int pos = 0;
      g.setColor(Color.black);

      for (int i = 0; i < 120; i++) {
        if (!this.isSharp(current)) {
          if (nom.get(beat).contains(new Note(current, octave))) {
            g.setColor(new Color(249, 222, 0));
            g.fillRect(MARGIN + (pos * PIANO_SIZE), 0, PIANO_SIZE, PIANO_LENGTH);
            g.setColor(Color.BLACK);
            g.drawRect(MARGIN + (pos * PIANO_SIZE), 0, PIANO_SIZE, PIANO_LENGTH);
            pos++;
          } else {
            g.drawRect(MARGIN + (pos * PIANO_SIZE), 0, PIANO_SIZE, PIANO_LENGTH);
            pos++;
          }
        }
        if (current == Tone.B) {
          octave++;
        }
        current = current.next();
      }
    }

    /**
     * Draws all of the black keys of the piano and highlights the keys that are currently playing.
     *
     * @param g Graphics.
     */
    private void drawBlackKeys(Graphics g) {
      int octave = 0;
      Tone current = Tone.C;
      g.setColor(Color.black);

      for (int i = 0; i < 120; i++) {
        if (this.isSharp(current)) {
          if (nom.get(beat).contains(new Note(current, octave))) {
            g.setColor(new Color(249, 224, 78));
          }
          switch (current) {
            case C_SHARP:
              g.fillRect(MARGIN + (octave * PIANO_SIZE * 7) + (PIANO_SIZE - PIANO_SIZE / 4),
                      0, PIANO_SIZE / 2, BLACK_LENGTH);
              break;
            case D_SHARP:
              g.fillRect(MARGIN + (octave * PIANO_SIZE * 7) + (2 * PIANO_SIZE - PIANO_SIZE / 4),
                      0, PIANO_SIZE / 2, BLACK_LENGTH);
              break;
            case F_SHARP:
              g.fillRect(MARGIN + (octave * PIANO_SIZE * 7) + (4 * PIANO_SIZE - PIANO_SIZE / 4),
                      0, PIANO_SIZE / 2, BLACK_LENGTH);
              break;
            case G_SHARP:
              g.fillRect(MARGIN + (octave * PIANO_SIZE * 7) + (5 * PIANO_SIZE - PIANO_SIZE / 4),
                      0, PIANO_SIZE / 2, BLACK_LENGTH);
              break;
            case A_SHARP:
              g.fillRect(MARGIN + (octave * PIANO_SIZE * 7) + (6 * PIANO_SIZE - PIANO_SIZE / 4),
                      0, PIANO_SIZE / 2, BLACK_LENGTH);
              break;
            default:
              break;
          }
        }
        current = current.next();
        g.setColor(Color.BLACK);
        if (current == Tone.B) {
          octave++;
        }
      }
    }
  }
}
