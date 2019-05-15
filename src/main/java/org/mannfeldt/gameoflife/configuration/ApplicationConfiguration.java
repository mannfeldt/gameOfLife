package org.mannfeldt.gameoflife.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfiguration {
  private static Properties properties;
  private static final String GRID_SIZE_KEY = "grid_size";
  private static final String FEATURE_TOGGLE_SHOW_GENERATION_NUMBER_KEY =
      "feature_toggle_show_generation_number";
  private static final int GRID_SIZE_DEFAULT = 6;
  private static final String SIMULATION_FPS_KEY = "simulation_speed";
  private static final int SIMULATION_FPS_DEFAULT = 25;
  public static final String PROPERTY_FILE_NAME = "./application.properties";
  public static final int HEIGHT_RATIO = 13;
  public static final int WIDTH_RATIO = 21;

  public static void loadProperties(final InputStream stream) {
    properties = new Properties();
    try (stream) {
      if (stream == null) {
        throw new IOException();
      }
      properties.load(stream);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static void loadProperties(final String filename) {
    try (FileInputStream stream = new FileInputStream(filename)) {
      loadProperties(stream);
    } catch (IOException ex) {
      properties = new Properties();
    }
  }

  public static int getGridSize() {
    try {
      int gridSize = Integer.parseInt(properties.getProperty(GRID_SIZE_KEY));
      if (gridSize < 1) {
        return GRID_SIZE_DEFAULT;
      }
      return gridSize;
    } catch (NumberFormatException e) {
      return GRID_SIZE_DEFAULT;
    }
  }

  public static boolean isFeatureToggleShowGenerationNumber() {
    return Boolean.parseBoolean(properties.getProperty(FEATURE_TOGGLE_SHOW_GENERATION_NUMBER_KEY));
  }

  public static int getSimulationFPS() {
    try {
      return Integer.parseInt(properties.getProperty(SIMULATION_FPS_KEY));
    } catch (NumberFormatException e) {
      return SIMULATION_FPS_DEFAULT;
    }
  }
}
