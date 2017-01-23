package utility;

import file.ImageManager;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Controls the type of the cursor and its visual representation.
 */
public class CursorManager {

    private static CursorType cursorType = CursorType.POINTER;
    private static Scene scene;

    public static void setScene(Scene scene) {
        CursorManager.scene = scene;
    }

    /**
     * Returns the correct imageName for the cursor depending on the cursor type.
     *
     * @return
     */
    public static Cursor createCursorImage() {
        String imageSize;
        if (cursorType == CursorType.POINTER) {
            return new ImageCursor();
        } else if (cursorType == CursorType.PIPE) {
            return Cursor.CROSSHAIR;
        } else if (cursorType == CursorType.DELETE) {
            imageSize = "32";
        } else {
            imageSize = "64";
        }

        String cursorImageName = cursorType.toString().toLowerCase() + imageSize;
        Image cursorImage = ImageManager.getImage(cursorImageName);
        return new ImageCursor(cursorImage,
                cursorImage.getWidth() / 2,
                cursorImage.getHeight() / 2);
    }

    public static CursorType getCursorType() {
        return cursorType;
    }

    public static void setCursorType(CursorType cursorType) {
        CursorManager.cursorType = cursorType;
        scene.setCursor(createCursorImage());
    }

    public enum CursorType {POINTER, DELETE, PIPE, PUMP, SPLITTER, MERGER, SINK}
}
