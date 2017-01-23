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
        simulation.setUpFactory();
    }

    public CanvasPanel(Canvas canvas, Simulation sim) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setTextAlign(TextAlignment.CENTER);
        graphicsContext.setTextBaseline(VPos.CENTER);
        addEventHandlers();

        simulation = sim;
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setSimulation(Simulation sim) {
        this.simulation = sim;
        this.simulation.setUpFactory();
        redraw();
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

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            if (CursorManager.getCursorType() == CursorType.PIPE) {
                if (!simulation.startPlottingPipe(mouseEvent)) {
                    simulation.addPipeJoin(dragOrigin);
                }
            } else if (CursorManager.getCursorType() == CursorType.POINTER) {
                Optional<Component> found = simulation.getObject(dragOrigin);
                if (found.isPresent()) {
                    // if there is a component - move only it
                    simulation.deselectAll();
                    found.get().setSelected(true);
                }
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
        Point clickLocation = new Point((int) t.getX(), (int) t.getY());

        if (isClick) {
            if (t.getButton() == MouseButton.PRIMARY) {
                simulation.deselectAll();

                if (CursorManager.getCursorType() == CursorType.POINTER) {
                    simulation.showPropertiesOnLocation(clickLocation);
                } else if (CursorManager.getCursorType() == CursorType.DELETE) {
                    simulation.deleteOnLocation(clickLocation);
                } else if (CursorManager.getCursorType() != CursorType.PIPE) {
                    // cursor is a component type
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
        simulation.deselectAll();
        redraw();
    }

    public boolean deleteSelectedComponents() {
        boolean anyDeleted = simulation.deleteSelected();
        redraw();
        return anyDeleted;
    }
}
