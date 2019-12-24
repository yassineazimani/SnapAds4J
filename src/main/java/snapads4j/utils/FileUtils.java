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

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FileUtils {

    /**
     * Test equality between width and height. It's useful to check ratio 1:1.
     *
     * @param f File
     * @return boolean
     * @throws IOException
     */
    public static boolean testEqualityWidthHeight(File f) throws IOException {
        boolean result = false;
        if (f != null) {
            BufferedImage bimg = ImageIO.read(f);
            result = bimg.getWidth() == bimg.getHeight();
        }
        return result;
    }// testEqualityWidthHeight()

    /**
     * Get dimension of a file (must be an image or a video)
     *
     * @param f file
     * @return Mapping Dimension (keys : width and height)
     * @throws IOException
     */
    public static Map<String, Integer> getDimensionFile(File f) throws IOException {
        Map<String, Integer> result = new HashMap<>();
        if (f != null) {
            BufferedImage bimg = ImageIO.read(f);
            result.put("width", bimg.getWidth());
            result.put("height", bimg.getHeight());
        } else {
            result.put("width", 0);
            result.put("height", 0);
        }
        return result;
    }// getDimensionFile()

    /**
     * Get extension file
     *
     * @param file file
     * @return extension file
     */
    public static String getExtensionFile(File file) {
        String extension = "";
        if (file != null) {
            String name = file.getName();
            if (name.contains(".")) {
                extension = name.substring(name.lastIndexOf(".") + 1);
            }
        }
        return extension;
    }// getExtensionFile()

    /**
     * Get filename without extension
     *
     * @param file file
     * @return extension file
     */
    public static String getFilenameWithoutExtension(File file) {
        String extension = "";
        if (file != null) {
            String name = file.getName();
            if (name.contains(".")) {
                extension = name.substring(0, name.lastIndexOf("."));
            }
        }
        return extension;
    }// getExtensionFile()

    public Optional<File> getFileFromResources(String fileName, String fileNameTmp) {
        if (StringUtils.isNotEmpty(fileName)) {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException("File not found");
            } else {
                try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                     OutputStream outStream = new FileOutputStream(fileNameTmp)) {
                    byte[] buffer = new byte[inputStream.available()];
                    inputStream.read(buffer);
                    File targetFile = new File(fileNameTmp);
                    outStream.write(buffer);
                    return Optional.of(targetFile);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Impossible to read File");
                }
            }
        }
        return Optional.empty();
    }// getFileFromResources()

    public static boolean deleteFile(String fileName) {
        return new File(fileName).delete();
    }// deleteFile()

    /**
     * Get length of large media (bytes)
     *
     * @param chunks
     * @return length
     */
    public static long getLengthLargeMedia(List<File> chunks) {
        long total = 0L;
        if (CollectionUtils.isNotEmpty(chunks)) {
            for (File chunk : chunks) {
                total += chunk.length();
            }
        }
        return total;
    }// getLengthLargeMedia()

}// FileUtils
