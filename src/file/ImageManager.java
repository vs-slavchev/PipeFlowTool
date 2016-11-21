package file;

import javafx.scene.image.Image;
import utility.PipeToolFatalError;

import java.util.HashMap;
import java.util.Map;

/**
 * A static class responsible for loading, storing and retrieving images.
 */
public class ImageManager {

    private final static String extension = ".png";
    private static Map<String, Image> images = new HashMap<>();

    public static void initializeImages() {
        loadImage("pump");
    }

    private static Image loadImage(String imageName) {
        Image img = null;
        String fileName = imageName + extension;
        try {
            img = new Image("file:images/" + fileName);
        } catch (Exception e) {
            PipeToolFatalError.show(fileName + " missing!");
        }
        images.put(imageName, img);
        return img;
    }

    /**
     * Get an already loaded image by its name.
     *
     * @param s the name of the image without extension
     * @return the image
     */
    public static Image getImage(String s) {
        if (!images.containsKey(s)) {
            PipeToolFatalError.show("Image was not loaded. name=\"" + s + "\"");
        }
        return images.get(s);
    }
}
