package snap.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Read the properties files.
 *
 * @author Yassine
 */
public class FileProperties {

  private final String PROPERTIES_FILE_PATH = "application.properties";

  private static final Logger LOGGER = LogManager.getLogger(FileProperties.class);

  /**
   * Get properties from application.properties
   *
   * @return properties
   */
  public Properties getProperties() {
    Properties properties = new Properties();
    try (InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
      properties.load(inputStream);
    } catch (IOException e) {
      LOGGER.error("Impossible to get properties", e);
    }
    return properties;
  } // getProperties()
} // FileProperties
