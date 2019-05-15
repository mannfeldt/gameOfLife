package org.mannfeldt.gameoflife.model;

public class Cell {
  public enum CellState {
    DEAD('□'),
    ALIVE('■');
    private final char symbol;

    CellState(char symbol) {
      this.symbol = symbol;
    }

    char getSymbol() {
      return symbol;
    }
  }

  private CellState state;

  protected Cell(final CellState state) {
    this.state = state;
  }

  public void kill() {
    state = CellState.DEAD;
  }

  public void birth() {
    state = CellState.ALIVE;
  }

  public boolean isAlive() {
    return state.equals(CellState.ALIVE);
  }

  protected char getSymbol() {
    return state.getSymbol();
  }

  protected CellState getState() {
    return state;
  }

  protected void setNextState(final int livingNeighbors) {
    if (livingNeighbors == 3) state = CellState.ALIVE;
    else if (livingNeighbors != 2) state = CellState.DEAD;
  }
}
