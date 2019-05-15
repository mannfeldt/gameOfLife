package org.mannfeldt.gameoflife;

import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;
import org.mannfeldt.gameoflife.model.Generation;
import org.mannfeldt.gameoflife.presenter.GameOfLifePresenter;
import org.mannfeldt.gameoflife.presenter.GameOfLifePresenterClassic;
import org.mannfeldt.gameoflife.view.GameOfLifeView;
import org.mannfeldt.gameoflife.view.GameOfLifeViewSwing;

/** @author Emil Mannfeldt. The game of life. https://sv.wikipedia.org/wiki/Game_of_Life */
public class GameOfLife {

  private GameOfLife(final int gridSize) {
    final GameOfLifeView view = new GameOfLifeViewSwing(gridSize);
    final Generation generation = new Generation(gridSize);
    final GameOfLifePresenter presenter = new GameOfLifePresenterClassic(view, generation);
    presenter.start();
  }

  public static void main(String[] args) {
    ApplicationConfiguration.loadProperties(ApplicationConfiguration.PROPERTY_FILE_NAME);
    new GameOfLife(ApplicationConfiguration.getGridSize());
  }
}
