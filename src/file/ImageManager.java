package file;

import utility.PipeToolFatalError;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private final static String directory = "images/";
    private final static String extension = ".png";
    private static Map<String, Image> images = new HashMap<>();

    public static void initImages() {
        loadImage("pump");
    }

    private static Image loadImage(String fileName) {
        BufferedImage img = null;
        String imagePath = directory + fileName + extension;
        try {
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            PipeToolFatalError.show(imagePath + " missing!");
        }
        images.put(fileName, img);
        return img;
    }

    public static Image getImage(String s) {
        if (!images.containsKey(s)) {
            PipeToolFatalError.show("Image was not loaded. name=\"" + s + "\"");
        }
        return images.get(s);
    }
}
