package org.mannfeldt.gameoflife.presenter;

/** Listener handles callback actions from UI */
public interface GameOfLifeActionListener {
  /**
   * Toggles the state of specified cell
   *
   * @param row cell row index
   * @param col cell column index
   * @param isBirth true if cell should become alive else it dies
   */
  void cellWasToggled(final int row, final int col, final boolean isBirth);

  /** Toggles the simulations pause state */
  void pauseWasToggled();

  /** Resets the simulation */
  void reset();
}
