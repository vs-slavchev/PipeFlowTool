package utility;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import network.Simulation;
import object.Component;
import network.NetworkFactory;
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
        simulation.drawPipes(graphicsContext);
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

        redraw();
    }

    private void mouseDragged(MouseEvent t) {
        if (t.getButton() == MouseButton.PRIMARY) {
            int horizontalDelta = (int) (t.getX() - dragOrigin.getX());
            int verticalDelta = (int) (t.getY() - dragOrigin.getY());
            dragOrigin.setLocation(t.getX(), t.getY());
            isClick = false;

            simulation.moveObjects(horizontalDelta, verticalDelta);
        }
        redraw();
    }

    private void mouseReleased(MouseEvent t) {
        int cursorX = (int) t.getX();
        int cursorY = (int) t.getY();

        if (isClick) {
            if (t.getButton() == MouseButton.PRIMARY) { /* TODO: check cursor type; add deleting
                                                    with CursorManager.getCursorType() ? */
                simulation.deselectAll();

                // try to select an object, if found - show properties
                Optional<Component> selected = simulation.getObject(cursorX, cursorY);
                if (selected.isPresent()) {
                    selected.get().showPropertiesDialog();
                } else {
                    Component created = NetworkFactory.createNetworkObject(cursorX, cursorY);

                    if (simulation.doesOverlap(created)) {
                        AlertDialog.showInvalidInputAlert(
                                "This spot overlaps with an existing object.");
                        return;
                    }
                    CursorManager.setCursorType(CursorType.POINTER);
                    simulation.add(created);
                }
            }

            if (t.getButton() == MouseButton.SECONDARY) {
                Optional<Component> selected = simulation.getObject(cursorX, cursorY);
                if (selected.isPresent()) {
                    selected.get().toggleSelected();
                }
            }
        }
        redraw();
    }

    public boolean deleteSelectedObjects() {
        boolean anyDeleted = simulation.deleteSelected();
        redraw();
        return anyDeleted;
    }
}
