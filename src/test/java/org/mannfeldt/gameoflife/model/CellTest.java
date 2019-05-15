package org.mannfeldt.gameoflife.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

class CellTest {

  private static final Cell.CellState DEAD = Cell.CellState.DEAD;
  private static final Cell.CellState ALIVE = Cell.CellState.ALIVE;

  @ParameterizedTest(name = "{index}: {0} cell is killed successfully")
  @EnumSource(Cell.CellState.class)
  void kill(final Cell.CellState state) {
    Cell cell = new Cell(state);

    cell.kill();

    assertEquals(DEAD, cell.getState());
  }

  @ParameterizedTest(name = "{index}: {0} cell is birthed successfully")
  @EnumSource(Cell.CellState.class)
  void birth(final Cell.CellState state) {
    Cell cell = new Cell(state);

    cell.birth();

    assertEquals(ALIVE, cell.getState());
  }

  @ParameterizedTest(name = "{index}: {0} state is read successfully")
  @EnumSource(Cell.CellState.class)
  void getState(final Cell.CellState state) {
    Cell cell = new Cell(state);

    assertEquals(state, cell.getState());
  }

  @ParameterizedTest(name = "{index}: {0} is interpreted correctly through isAlive()")
  @EnumSource(Cell.CellState.class)
  void isAlive(final Cell.CellState state) {
    Cell cell = new Cell(state);

    assertEquals(state.equals(ALIVE), cell.isAlive());
  }

  private static Stream<Arguments> provideDataForNextCellState() {
    return Stream.of(
        Arguments.of(-1, ALIVE, DEAD),
        Arguments.of(0, ALIVE, DEAD),
        Arguments.of(1, ALIVE, DEAD),
        Arguments.of(2, ALIVE, ALIVE),
        Arguments.of(2, DEAD, DEAD),
        Arguments.of(3, ALIVE, ALIVE),
        Arguments.of(3, DEAD, ALIVE),
        Arguments.of(4, ALIVE, DEAD),
        Arguments.of(5, ALIVE, DEAD),
        Arguments.of(6, ALIVE, DEAD),
        Arguments.of(7, ALIVE, DEAD),
        Arguments.of(8, ALIVE, DEAD),
        Arguments.of(9, ALIVE, DEAD));
  }

  @ParameterizedTest(name = "{index}: {0} neighbors changes state {1} to {2}")
  @MethodSource("provideDataForNextCellState")
  void setNextState(
      final int livingNeighbors, final Cell.CellState currentState, final Cell.CellState expected) {
    Cell cell = new Cell(currentState);

    cell.setNextState(livingNeighbors);

    assertEquals(expected, cell.getState());
  }
}
