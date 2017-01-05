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
import utility.CursorManager.CursorType;

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

    private void mousePressed(MouseEvent mouseEvent) {
        dragOrigin = new Point((int) mouseEvent.getX(), (int) mouseEvent.getY());
        isClick = true;

        if (mouseEvent.getButton() == MouseButton.PRIMARY
                && CursorManager.getCursorType() == CursorType.PIPE) {
            simulation.startPlottingPipe(mouseEvent);
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
        Point clickLocation = new Point((int) t.getX(), (int) t.getY());

        if (isClick) {
            if (t.getButton() == MouseButton.PRIMARY) {
                simulation.deselectAll();

                if (CursorManager.getCursorType() == CursorType.POINTER) {
                    simulation.showPropertiesOnLocation(clickLocation);
                } else if (CursorManager.getCursorType() == CursorType.DELETE) {
                    simulation.deleteOnLocation(clickLocation);
                } else {
                    simulation.createComponentOnLocation(clickLocation);
                }

            } else if (t.getButton() == MouseButton.SECONDARY) {
                simulation.toggleSelectedOnLocation(clickLocation);
            }
        } else {
            if (t.getButton() == MouseButton.PRIMARY
                    && CursorManager.getCursorType() == CursorType.PIPE) {
                simulation.finishPipeOnLocation(clickLocation);
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
