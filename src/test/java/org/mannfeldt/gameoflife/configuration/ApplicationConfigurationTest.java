package org.mannfeldt.gameoflife.configuration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationConfigurationTest {

  @DisplayName("FPS property found")
  @Test
  void getSimulationFPSFromFile() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/application.properties"));
    assertEquals(12, ApplicationConfiguration.getSimulationFPS());
  }

  @DisplayName("Get default FPS value if property value is invalid")
  @Test
  void getSimulationFPSDefault() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/errorformat.properties"));

    assertEquals(25, ApplicationConfiguration.getSimulationFPS());
  }

  @DisplayName("feature toggle property found")
  @Test
  void isFeatureToggleShowGenerationNumber() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/application.properties"));
    assertTrue(ApplicationConfiguration.isFeatureToggleShowGenerationNumber());
  }

  @DisplayName("Grid size property found")
  @Test
  void getGridSize() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/application.properties"));
    assertEquals(20, ApplicationConfiguration.getGridSize());
  }

  @DisplayName("Get default Grid size value if property value is 0")
  @Test
  void getGridSizeZero() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/gridsizezero.properties"));
    assertEquals(6, ApplicationConfiguration.getGridSize());
  }

  @DisplayName("Get default Grid size value if property value is invalid format")
  @Test
  void getGridSizeInvalidFormat() {
    ApplicationConfiguration.loadProperties(
        ApplicationConfigurationTest.class.getResourceAsStream("/errorformat.properties"));
    assertEquals(6, ApplicationConfiguration.getGridSize());
  }
}
