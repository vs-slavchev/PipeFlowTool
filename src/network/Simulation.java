package network;

import javafx.scene.canvas.GraphicsContext;
import object.ComponentWithImage;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages all the objects in the network.
 */
public class Simulation {

    private ArrayList<ComponentWithImage> objects = new ArrayList<>();

    public void add(ComponentWithImage obj) {
        if (!objects.contains(obj)) {
            objects.add(obj);
        }
    }

    public void drawPipes(GraphicsContext gc) {

    }

    public void drawAllObjects(GraphicsContext gc) {
        objects.forEach(obj -> obj.draw(gc));
    }

    /**
     * Use for moving all objects or making it look like we are moving the viewport.
     */
    public void moveObjects(final int x, final int y) {
        if (objects.stream().anyMatch(ComponentWithImage::isSelected)) {
            objects.stream()
                    .filter(ComponentWithImage::isSelected)
                    .forEach(obj -> obj.translate(x, y));
        } else {
            objects.forEach(obj -> obj.translate(x, y));
        }
    }

    public Optional<ComponentWithImage> getObject(int x, int y) {
        return objects.stream().filter(obj -> obj.isClicked(x, y)).findFirst();
    }

    public boolean doesOverlap(ComponentWithImage currentObject) {
        return objects.stream().anyMatch(obj -> obj.collidesWith(currentObject));
    }

    public void deselectAll() {
        objects.forEach(obj -> obj.setSelected(false));
    }

    public boolean deleteSelected() {
        return objects.removeIf(ComponentWithImage::isSelected);
    }
}
