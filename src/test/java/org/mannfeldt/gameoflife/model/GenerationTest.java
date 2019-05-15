package org.mannfeldt.gameoflife.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class GenerationTest {

  private static final Cell.CellState DEAD = Cell.CellState.DEAD;
  private static final Cell.CellState ALIVE = Cell.CellState.ALIVE;

  @DisplayName("grid is initiated correctly")
  @Test
  void getGrid() {
    Generation generation = new Generation(10);
    Cell[][] grid = generation.getGrid();
    assertEquals(10* ApplicationConfiguration.HEIGHT_RATIO, grid.length);
  }

  @DisplayName("Generation number increases when evolving generation")
  @Test
  void getGenerationNumber() {
    Generation generation = new Generation(2);
    assertEquals(0, generation.getGenerationNumber());
    generation.evolve();
    assertEquals(1, generation.getGenerationNumber());
  }

  @DisplayName("Grid is initiated correctly")
  @Test
  void gridConstructor() {
    Cell[][] initialGrid = {
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Generation generation = new Generation(initialGrid);
    assertEquals(initialGrid, generation.getGrid());
  }

  @DisplayName("All cells are killed by ClearGrid()")
  @Test
  void clearGrid() {
    Cell[][] input = {
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
    };
    Cell[][] expectedGrid = {
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Generation generation = new Generation(input);
    Generation expected = new Generation(expectedGrid);

    generation.clearGrid();

    assertEquals(expected.toString(), generation.toString());
  }

  private static Stream<Arguments> provideDataForEvolve() {

    Cell[][] input1 = {
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Cell[][] expected1 = {
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Cell[][] input2 = {
      {new Cell(ALIVE), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Cell[][] expected2 = {
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };

    Cell[][] input3 = {
      {new Cell(ALIVE), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD)},
    };
    Cell[][] expected3 = {
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
    };

    Cell[][] input4 = {
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
    };
    Cell[][] expected4 = {
      {new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
    };
    Cell[][] input5 = {
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD)},
    };
    Cell[][] expected5 = {
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
    };
    Cell[][] bigGrid = {
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(DEAD), new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
    };
    Cell[][] bigGridExpected = {
      {new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD), new Cell(DEAD), new Cell(DEAD)},
      {new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(ALIVE), new Cell(ALIVE), new Cell(DEAD), new Cell(ALIVE)},
      {new Cell(DEAD), new Cell(DEAD), new Cell(ALIVE), new Cell(DEAD), new Cell(DEAD)},
    };

    return Stream.of(
        Arguments.of(new Generation(input1), new Generation(expected1)),
        Arguments.of(new Generation(input2), new Generation(expected2)),
        Arguments.of(new Generation(input3), new Generation(expected3)),
        Arguments.of(new Generation(input4), new Generation(expected4)),
        Arguments.of(new Generation(input5), new Generation(expected5)),
        Arguments.of(new Generation(bigGrid), new Generation(bigGridExpected)));
  }

  @DisplayName("Evolving generation successfully")
  @ParameterizedTest()
  @MethodSource("provideDataForEvolve")
  void evolve(final Generation input, final Generation expected) {
    input.evolve();

    assertEquals(expected.toString(), input.toString());
  }
}
