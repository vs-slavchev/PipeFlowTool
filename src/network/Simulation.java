package network;

import javafx.scene.canvas.GraphicsContext;
import object.Component;
import object.ComponentWithImage;
import object.Pipe;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Manages all the objects in the network.
 */
public class Simulation {

    private ArrayList<Component> objects = new ArrayList<>();

    public void addComponent(Component obj) {
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
     * Moves all objects, or only the selected ones.
     */
    public void moveObjects(final int x, final int y) {
        Stream<Component> toMove = objects.stream();
        boolean anyComponentSelected = objects.stream().anyMatch(Component::isSelected);
        if (anyComponentSelected) {
            toMove = objects.stream().filter(Component::isSelected);
        }
        toMove.forEach(obj -> obj.translate(x, y));
    }

    public Optional<Component> getObject(int x, int y) {
        return objects.stream().filter(obj -> obj.isClicked(x, y)).findFirst();
    }


    /**
     * Checks if the supplied component overlaps with another object.
     * @param componentToTest the component to be tested
     */
    public boolean doesOverlap(ComponentWithImage componentToTest) {
        return objects.stream().anyMatch(obj -> obj.overlaps(componentToTest));
    }

    public void deselectAll() {
        objects.forEach(obj -> obj.setSelected(false));
    }

    public boolean deleteSelected() {
        return objects.removeIf(Component::isSelected);
    }
}
