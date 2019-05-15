package org.mannfeldt.gameoflife.view;

import org.mannfeldt.gameoflife.model.Cell;
import org.mannfeldt.gameoflife.presenter.GameOfLifeActionListener;

/** View interface for Game of life */
public interface GameOfLifeView {

  /**
   * Renders state
   *
   * @param grid game of life state
   * @param generation generation number
   */
  void render(final Cell[][] grid, final int generation);

  /**
   * Handles pause action
   *
   * @param running true if state is running
   */
  void setPlay(final boolean running);

  /**
   * Hook for action listener
   *
   * @param listener action listener
   */
  void setListener(final GameOfLifeActionListener listener);
}
