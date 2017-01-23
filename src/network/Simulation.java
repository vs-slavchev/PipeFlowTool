package network;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import object.Component;
import object.ComponentWithImage;
import object.Pipe;
import utility.AlertDialog;
import utility.CursorManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Manages all the objects in the network.
 */
public class Simulation implements java.io.Serializable{
    private static final long serialVersionUID = -2724135797181166853L;
    private ArrayList<Component> objects = new ArrayList<>();
    private Set<Component> objectsToRemove = new HashSet<>();


    private transient ComponentFactory factory = new ComponentFactory(this);

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

    public Optional<Component> getObject(final Point clickLocation) {
        return objects.stream().filter(obj -> obj.isClicked(clickLocation)).findFirst();
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

    /**
     * Deletes the selected components
     * @return true if any components have been deleted
     */
    public boolean deleteSelected() {
        objects.stream()
                .filter(Component::isSelected)
                .forEach(component -> deleteComponentFromSimulation(component));

        boolean anyRemoved = objects.removeAll(objectsToRemove);
        objectsToRemove.clear();
        return anyRemoved;
    }

    public void startPlottingPipe(MouseEvent event) {
        Optional<Component> selected = getObject(new Point((int) event.getX(), (int) event.getY()));
        if (selected.isPresent()) {
            factory.startPipe(selected.get());
        }
    }

    public void showPropertiesOnLocation(Point clickLocation) {
        // try to select an object, if found - show properties
        Optional<Component> clicked = getObject(clickLocation);
        if (clicked.isPresent()) {
            clicked.get().showPropertiesDialog();
        }
    }

    public void deleteOnLocation(Point clickLocation) {
        Optional<Component> clicked = getObject(clickLocation);
        if (clicked.isPresent()) {
            deleteComponentFromSimulation(clicked.get());
        }

        objects.removeAll(objectsToRemove);
        objectsToRemove.clear();
    }

    private void deleteComponentFromSimulation(Component toRemove) {
        objectsToRemove.add(toRemove);
        // make components having the deleted one as their next one not point at it
        objects.stream()
                .filter(component -> component.getNext() == toRemove
                            || toRemove.getNext() == component)
                .forEach(component -> {
                    component.setNext(null);
                    if (component instanceof Pipe) {
                        deleteComponentFromSimulation(component);
                    }
                });
    }

    public void createComponentOnLocation(Point clickLocation) {
        ComponentWithImage created = factory.createComponent(
                CursorManager.getCursorType());
        created.setCenterPosition(clickLocation);

        if (doesOverlap(created)) {
            AlertDialog.showInvalidInputAlert(
                    "This spot overlaps with an existing object.");
            return;
        }
        CursorManager.setCursorType(CursorManager.CursorType.POINTER);
        addComponent(created);
    }


    public void toggleSelectedOnLocation(Point clickLocation) {
        Optional<Component> clicked = getObject(clickLocation);
        if (clicked.isPresent()) {
            clicked.get().toggleSelected();
        }
    }


    public void finishPipeOnLocation(Point clickLocation) {
        Optional<Component> clicked = getObject(clickLocation);
        if (clicked.isPresent()) {
            Pipe created = factory.finishPipe(clicked.get());
            if (created != null) {
                addComponent(created);
                // start update to represent current values
                created.getInput().update(created.getInput().getFlow());
            }
        } else {
            factory.stopBuildingPipe();
        }
    }

    public Stream<Component> getPipesPointingToComponent(Component pointedTo) {
        return objects.stream()
                .filter(pipe -> pipe.getNext() == pointedTo);
    }
}
