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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Read the properties files.
 *
 * @author Yassine
 */
public class FileProperties {

    private static final Logger LOGGER = LogManager.getLogger(FileProperties.class);

    /**
     * Get properties from file properties
     *
     * @param propertiesFilePath Name of properties file
     * @return properties
     * @throws IOException
     */
    public Properties getProperties(String propertiesFilePath) throws IOException{
        Properties properties = new Properties();
        String PROPERTIES_FILE_PATH = StringUtils.isEmpty(propertiesFilePath) ? "application.properties" : propertiesFilePath;
        try (InputStream inputStream =
                     getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE_PATH)) {
            if(inputStream != null)
                properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Impossible to get {}", propertiesFilePath, e);
            throw e;
        }
        return properties;
    } // getProperties()

    /**
     * Get properties from application.properties
     *
     * @return properties
     */
    public Properties getProperties() throws IOException{
        return getProperties(null);
    }// getProperties()

} // FileProperties
