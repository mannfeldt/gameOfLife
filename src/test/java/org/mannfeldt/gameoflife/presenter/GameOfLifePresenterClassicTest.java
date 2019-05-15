package org.mannfeldt.gameoflife.presenter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;
import org.mannfeldt.gameoflife.model.Generation;
import org.mannfeldt.gameoflife.view.GameOfLifeView;
import org.mannfeldt.gameoflife.view.GameOfLifeViewSwing;

import static org.junit.jupiter.api.Assertions.*;

class GameOfLifePresenterClassicTest {
  private GameOfLifeView view;
  private GameOfLifePresenterClassic presenter;
  private Generation generation;

  @BeforeEach
  void setUp() {
    ApplicationConfiguration.loadProperties("");
    generation = new Generation(10);
    view = new GameOfLifeViewSwing(10);
    presenter = new GameOfLifePresenterClassic(view, generation);
  }

  @DisplayName("Cell comes alive when action to live is called")
  @Test
  void cellWasToggledOn() {
    int row = 2, col = 2;
    generation.getGrid()[row][col].kill();

    presenter.cellWasToggled(row, col, true);

    assertTrue(generation.getGrid()[row][col].isAlive());
  }

  @DisplayName("Cell dies when action to kill is called")
  @Test
  void cellWasToggledOff() {
    int row = 2, col = 2;
    generation.getGrid()[row][col].birth();

    presenter.cellWasToggled(row, col, false);

    assertFalse(generation.getGrid()[row][col].isAlive());
  }

  @DisplayName("Nothing is affected if cell coordinate is out of bounds")
  @Test
  void cellWasToggledOutOfBounds() {
    String expected = generation.toString();

    presenter.cellWasToggled(-1, 1, true);
    presenter.cellWasToggled(1, -1, true);
    presenter.cellWasToggled(1, 1000, true);
    presenter.cellWasToggled(1000, 1, true);

    String actual = generation.toString();

    assertEquals(expected, actual);
  }

  @DisplayName("Simulation is paused when pause action is called")
  @Test
  void pauseWasToggled() {
    boolean before = presenter.isRunning();

    presenter.pauseWasToggled();

    assertNotEquals(before, presenter.isRunning());
  }

  @DisplayName("Generation is cleared when reset action is called")
  @Test
  void reset() {
    int row = 2, col = 2;
    generation.getGrid()[row][col].birth();

    presenter.reset();

    assertFalse(generation.getGrid()[row][col].isAlive());
  }

  @DisplayName("Generation is evolving on tick")
  @Test
  void tick() {
    Generation firstGen = new Generation(10);

    new GameOfLifePresenterClassic(view, firstGen);

    assertEquals(1, firstGen.getGenerationNumber());
  }
}
