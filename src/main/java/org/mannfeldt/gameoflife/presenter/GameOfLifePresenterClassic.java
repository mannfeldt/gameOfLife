package org.mannfeldt.gameoflife.presenter;

import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;
import org.mannfeldt.gameoflife.model.Generation;
import org.mannfeldt.gameoflife.view.GameOfLifeView;
import java.util.Timer;
import java.util.TimerTask;

public class GameOfLifePresenterClassic implements GameOfLifePresenter, GameOfLifeActionListener {

  private final GameOfLifeView view;
  private Generation generation;
  private boolean running;

  public GameOfLifePresenterClassic(final GameOfLifeView view, final Generation generation) {
    this.generation = generation;
    this.view = view;
    view.setListener(this);
    running = true;
    tick();
    running = false;
  }

  public void cellWasToggled(final int row, final int col, final boolean isBirth) {
    if (generation.isWithinGrid(row, col)) {
      if (isBirth) {
        generation.getGrid()[row][col].birth();
      } else {
        generation.getGrid()[row][col].kill();
      }
      view.render(generation.getGrid(), generation.getGenerationNumber());
    }
  }

  public void pauseWasToggled() {
    running = !running;
    view.setPlay(running);
  }

  public void reset() {
    generation.clearGrid();
    running = true;
    view.setPlay(true);
    view.render(generation.getGrid(), generation.getGenerationNumber());
  }

  public void start() {
    TimerTask task =
        new TimerTask() {
          @Override
          public void run() {
            tick();
          }
        };
    new Timer().schedule(task, 0, 1000 / ApplicationConfiguration.getSimulationFPS());
  }

  public boolean isRunning() {
    return running;
  }

  private void tick() {
    if (isRunning()) {
      generation.evolve();
      view.render(generation.getGrid(), generation.getGenerationNumber());
    }
  }
}
