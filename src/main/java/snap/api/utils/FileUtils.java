package snap.api.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

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
	Map<String, Integer> result = new HashMap<String, Integer>();
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
     * @param f file
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
     * @param f file
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
}// FileUtils
