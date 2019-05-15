package org.mannfeldt.gameoflife.view;

import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;
import org.mannfeldt.gameoflife.model.Cell;
import org.mannfeldt.gameoflife.presenter.GameOfLifeActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameOfLifeViewSwing implements GameOfLifeView {
  private JButton buttonPlay;
  private JButton buttonReset;
  private JPanel panelGrid;
  private JLabel labelGeneration;
  private Image imageBoard;
  private Graphics graphicsBoard;
  private GameOfLifeActionListener listener;
  private int rowCount;
  private int colCount;
  private final Color CELL_COLOR = new Color(15, 15, 15);
  private final Color BACKGROUND_COLOR = new Color(240, 240, 240);
  private final Color GRID_LINE_COLOR = Color.LIGHT_GRAY;

  public GameOfLifeViewSwing(final int gridSize) {
    this.rowCount = gridSize * ApplicationConfiguration.HEIGHT_RATIO;
    this.colCount = gridSize * ApplicationConfiguration.WIDTH_RATIO;

    initiateComponents();
  }

  public void setListener(final GameOfLifeActionListener listener) {
    this.listener = listener;
  }

  private void initiateComponents() {

    JFrame frame = new JFrame();
    panelGrid = new JPanel();
    buttonPlay = new JButton("Play");
    buttonReset = new JButton("Reset");
    JPanel contentPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    labelGeneration = new JLabel("Generation 0");
    LayoutManager layout = new BorderLayout();

    frame.setContentPane(contentPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    contentPanel.setLayout(layout);
    contentPanel.add(panelGrid, BorderLayout.CENTER);
    buttonPanel.add(buttonPlay);
    buttonPanel.add(buttonReset);
    contentPanel.add(buttonPanel, BorderLayout.PAGE_END);
    if (ApplicationConfiguration.isFeatureToggleShowGenerationNumber()) {
      buttonPanel.add(labelGeneration);
    }
    frame.setSize(1680, 1040);
    frame.setVisible(true);
    imageBoard = panelGrid.createImage(panelGrid.getWidth(), panelGrid.getHeight());
    graphicsBoard = imageBoard.getGraphics();
    panelGrid.setBackground(BACKGROUND_COLOR);
    initActionListeners();
  }

  private void updateGenerationLabel(final int number) {
    labelGeneration.setText("Generation " + number);
  }

  private void initActionListeners() {
    panelGrid.addMouseListener(
        new MouseAdapter() {
          public void mouseClicked(MouseEvent evt) {
            mouseAction(evt);
          }
        });
    panelGrid.addMouseMotionListener(
        new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent evt) {
            mouseAction(evt);
          }
        });
    buttonReset.addActionListener(e -> listener.reset());
    buttonPlay.addActionListener(e -> listener.pauseWasToggled());
  }

  private void mouseAction(final MouseEvent evt) {
    int row = rowCount * evt.getY() / panelGrid.getHeight();
    int column = colCount * evt.getX() / panelGrid.getWidth();
    listener.cellWasToggled(row, column, SwingUtilities.isLeftMouseButton(evt));
  }

  public void setPlay(final boolean running) {
    buttonPlay.setText(running ? "Pause" : "Play");
  }

  public void render(final Cell[][] grid, final int generation) {
    graphicsBoard.setColor(panelGrid.getBackground());
    graphicsBoard.fillRect(0, 0, panelGrid.getWidth(), panelGrid.getHeight());

    drawCells(grid);
    drawGridLines();

    panelGrid.getGraphics().drawImage(imageBoard, 0, 0, panelGrid);
    if (ApplicationConfiguration.isFeatureToggleShowGenerationNumber()) {
      updateGenerationLabel(generation);
    }
  }

  private void drawCells(final Cell[][] grid) {
    graphicsBoard.setColor(CELL_COLOR);
    for (int row = 0; row < rowCount; row++) {
      for (int col = 0; col < colCount; col++) {
        if (grid[row][col].isAlive()) {
          int x = col * panelGrid.getWidth() / colCount;
          int y = row * panelGrid.getHeight() / rowCount;
          graphicsBoard.fillRect(
              x, y, panelGrid.getWidth() / colCount, panelGrid.getHeight() / rowCount);
        }
      }
    }
  }

  private void drawGridLines() {
    graphicsBoard.setColor(GRID_LINE_COLOR);
    for (int col = 1; col < colCount; col++) {
      int x = col * panelGrid.getWidth() / colCount;
      graphicsBoard.drawLine(x, 0, x, panelGrid.getHeight());
    }
    for (int row = 1; row < rowCount; row++) {
      int y = row * panelGrid.getHeight() / rowCount;
      graphicsBoard.drawLine(0, y, panelGrid.getWidth(), y);
    }
  }
}
