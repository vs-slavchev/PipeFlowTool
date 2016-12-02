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

    public enum CursorType {POINTER, DELETE, PIPE, PUMP, SPLITTER, MERGER, SINK}

    private static CursorType cursorType;
    private static Scene scene;

    public static void setScene(Scene scene) {
        CursorManager.scene = scene;
    }

    public static void setCursorType(CursorType cursorType) {
        CursorManager.cursorType = cursorType;
        scene.setCursor(getCursor());
    }

    public static Cursor getCursor() {
        if (cursorType == CursorType.POINTER) {
            return new ImageCursor();
        } else {
            String cursorImageName = cursorType.toString().toLowerCase() + "64";
            Image cursorImage = ImageManager.getImage(cursorImageName);
            return new ImageCursor(cursorImage,
                    cursorImage.getWidth() / 2,
                    cursorImage.getHeight() / 2);
        }
    }

    public CursorType getCursorType() {
        return cursorType;
    }
}
