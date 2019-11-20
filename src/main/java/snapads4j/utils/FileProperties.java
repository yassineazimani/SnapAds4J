/*
 * Copyright 2019 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.utils;

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
