package network;

import javafx.scene.canvas.GraphicsContext;
import object.Component;
import object.ComponentWithImage;
import object.Pipe;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages all the objects in the network.
 */
public class Simulation {

    private ArrayList<Component> objects = new ArrayList<>();

    public void add(Component obj) {
        if (!objects.contains(obj)) {
            objects.add(obj);
        }
    }

    public void drawAllObjects(GraphicsContext gc) {
        objects.stream().filter(obj -> obj instanceof Pipe).forEach(obj -> obj.draw(gc));
        objects.stream().filter(obj -> !(obj instanceof Pipe)).forEach(obj -> obj.draw(gc));
    }

    /**
     * Use for moving all objects or making it look like we are moving the viewport.
     */
    public void moveObjects(final int x, final int y) {
        boolean anyComponentSelected = objects.stream().anyMatch(Component::isSelected);
        if (anyComponentSelected) {
            objects.stream()
                    .filter(Component::isSelected)
                    .forEach(obj -> obj.translate(x, y));
        } else {
            objects.forEach(obj -> obj.translate(x, y));
        }
    }

    public Optional<Component> getObject(int x, int y) {
        return objects.stream().filter(obj -> obj.isClicked(x, y)).findFirst();
    }

    public boolean doesOverlap(ComponentWithImage currentObject) {
        return objects.stream().anyMatch(obj -> obj.overlaps(currentObject));
    }

    public void deselectAll() {
        objects.forEach(obj -> obj.setSelected(false));
    }

    public boolean deleteSelected() {
        return objects.removeIf(Component::isSelected);
    }
}
