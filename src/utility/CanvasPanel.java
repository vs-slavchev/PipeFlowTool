package utility;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import network.Point;
import network.Simulation;
import object.Component;
import object.ComponentWithImage;
import network.ComponentFactory;
import object.Pipe;
import utility.CursorManager.CursorType;

import java.util.Optional;

/**
 * Responsible for the canvas: drawing, event handling.
 */
public class CanvasPanel {

    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private Simulation simulation;
    private Point dragOrigin;
    private boolean isClick;

    public CanvasPanel(Canvas canvas) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        addEventHandlers();

        simulation = new Simulation();
    }

    /**
     * Call after each event to render the now current state.
     */
    public void redraw() {
        // prepare background
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        simulation.drawAllObjects(graphicsContext);
    }

    private void addEventHandlers() {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, this::mouseDragged);
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, this::mousePressed);
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, this::mouseReleased);
    }

    private void mousePressed(MouseEvent t) {
        dragOrigin = new Point((int) t.getX(), (int) t.getY());
        isClick = true;

        if (t.getButton() == MouseButton.PRIMARY
                && CursorManager.getCursorType() == CursorType.PIPE) {
            Optional<Component> selected = simulation.getObject((int) t.getX(), (int) t.getY());
            if (selected.isPresent()) {
                ComponentFactory.startPipe(selected.get());
            }
        }

        redraw();
    }

    private void mouseDragged(MouseEvent t) {
        if (t.getButton() == MouseButton.PRIMARY) {
            int horizontalDelta = (int) (t.getX() - dragOrigin.getX());
            int verticalDelta = (int) (t.getY() - dragOrigin.getY());
            dragOrigin.setLocation(t.getX(), t.getY());
            isClick = false;

            if (CursorManager.getCursorType() == CursorType.POINTER) {
                simulation.moveObjects(horizontalDelta, verticalDelta);
            }
        }
        redraw();
    }

    private void mouseReleased(MouseEvent t) {
        int cursorX = (int) t.getX();
        int cursorY = (int) t.getY();

        if (isClick) {
            if (t.getButton() == MouseButton.PRIMARY) {
                simulation.deselectAll();

                if (CursorManager.getCursorType() == CursorType.POINTER) {
                    // try to select an object, if found - show properties
                    Optional<Component> selected = simulation.getObject(cursorX, cursorY);
                    if (selected.isPresent()) {
                        selected.get().showPropertiesDialog();
                    }
                } else if (CursorManager.getCursorType() == CursorType.DELETE) { //move to simulatio
                    Optional<Component> selected = simulation.getObject(cursorX, cursorY);
                    if (selected.isPresent()) {
                        selected.get().setSelected(true);
                        simulation.deleteSelected();
                    }
                } else {// move to simulation
                    ComponentWithImage created = ComponentFactory.createComponent(
                            CursorManager.getCursorType());
                    created.setCenterPosition(cursorX, cursorY);

                    if (simulation.doesOverlap(created)) {
                        AlertDialog.showInvalidInputAlert(
                                "This spot overlaps with an existing object.");
                        return;
                    }
                    CursorManager.setCursorType(CursorType.POINTER);
                    simulation.addComponent(created);
                }

            }

            if (t.getButton() == MouseButton.SECONDARY) {
                Optional<Component> selected = simulation.getObject(cursorX, cursorY);
                if (selected.isPresent()) {
                    selected.get().toggleSelected();
                }
            }
        } else {
            if (t.getButton() == MouseButton.PRIMARY
                    && CursorManager.getCursorType() == CursorType.PIPE) {
                Optional<Component> selected = simulation.getObject((int) t.getX(), (int) t.getY());
                if (selected.isPresent()) {
                    Pipe created = ComponentFactory.finishPipe(selected.get());
                    if (created != null) {
                        simulation.addComponent(created);
                    }
                } else {
                    ComponentFactory.stopBuildingPipe();
                }
            }
        }
        redraw();
    }

    public boolean deleteSelectedComponents() {
        boolean anyDeleted = simulation.deleteSelected();
        redraw();
        return anyDeleted;
    }
}
