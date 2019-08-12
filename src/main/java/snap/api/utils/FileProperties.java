package snap.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read the properties files.
 *
 * @author Yassine
 */
public class FileProperties {

  private final String PROPERTIES_FILE_PATH = "application.properties";

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
      e.printStackTrace();
    }
    return properties;
  } // getProperties()
} // FileProperties
