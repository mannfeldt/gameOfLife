package org.mannfeldt.gameoflife.model;

import org.mannfeldt.gameoflife.configuration.ApplicationConfiguration;

public class Generation {
  enum Direction {
    NORTH_WEST(-1, -1),
    NORTH(0, -1),
    NORTH_EAST(1, -1),
    EAST(1, 0),
    WEST(-1, 0),
    SOUTH_WEST(-1, 1),
    SOUTH(0, 1),
    SOUTH_EAST(1, 1);

    final int x;
    final int y;

    Direction(final int x, final int y) {
      this.x = x;
      this.y = y;
    }
  }

  private Cell[][] grid;
  private int generationNumber;

  public Generation(final int gridSize) {
    final int rows = gridSize * ApplicationConfiguration.HEIGHT_RATIO;
    final int columns = gridSize * ApplicationConfiguration.WIDTH_RATIO;
    Cell[][] initialGrid = new Cell[rows][columns];
    for (int row = 0; row < initialGrid.length; row++) {
      for (int col = 0; col < initialGrid[row].length; col++) {
        initialGrid[row][col] = new Cell(Cell.CellState.DEAD);
      }
    }
    init(initialGrid);
  }

  private void init(final Cell[][] grid) {
    this.grid = grid;
    generationNumber = 0;
  }

  public Generation(final Cell[][] grid) {
    init(grid);
  }

  public Cell[][] getGrid() {
    return grid;
  }

  private int getRowCount() {
    return grid.length;
  }

  private int getColumnCount() {
    return grid[0].length;
  }

  public int getGenerationNumber() {
    return generationNumber;
  }

  public void evolve() {
    Cell[][] nextGrid = getGridCopy();
    for (int row = 0; row < grid.length; row++) {
      for (int col = 0; col < grid[row].length; col++) {
        final int livingNeighbors = getAliveNeighbors(row, col);
        nextGrid[row][col].setNextState(livingNeighbors);
      }
    }
    grid = nextGrid;
    generationNumber++;
  }

  public void clearGrid() {
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        cell.kill();
      }
    }
  }

  public String toString() {
    final String newline = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder(getRowCount() * (getColumnCount() + newline.length()));
    for (Cell[] row : grid) {
      for (Cell cell : row) {
        sb.append(cell.getSymbol());
      }
      sb.append(newline);
    }
    return sb.toString();
  }

  private Cell[][] getGridCopy() {
    Cell[][] gridCopy = new Cell[grid.length][];
    for (int row = 0; row < grid.length; row++) {
      gridCopy[row] = new Cell[grid[row].length];
      for (int col = 0; col < grid[row].length; col++) {
        gridCopy[row][col] = new Cell(grid[row][col].getState());
      }
    }
    return gridCopy;
  }

  private boolean cellIsAlive(final int row, final int col) {
    return isWithinGrid(row, col) && grid[row][col].isAlive();
  }

  public boolean isWithinGrid(final int row, final int col) {
    return row >= 0 && col >= 0 && row < getRowCount() && col < getColumnCount();
  }

  private int getAliveNeighbors(final int row, final int col) {
    int livingNeighbors = 0;
    for (Direction direction : Direction.values()) {
      if (cellIsAlive(row + direction.x, col + direction.y)) {
        livingNeighbors++;
      }
    }
    return livingNeighbors;
  }
}
