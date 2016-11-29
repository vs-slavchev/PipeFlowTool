package utility;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import network.NetworkManager;
import object.NetworkObject;
import network.NetworkFactory;

import java.util.Optional;

/**
 * Responsible for the canvas: drawing, event handling.
 */
public class CanvasPanel {

    private Canvas canvas;
    private GraphicsContext graphicsContext;

    private NetworkManager networkManager;
    private Point dragOrigin;
    private boolean isClick;

    public CanvasPanel(Canvas canvas) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        addEventHandlers();

        networkManager = new NetworkManager();
    }

    /**
     * Call after each event to render the now current state.
     */
    public void redraw() {
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        //draw stuff
        graphicsContext.setFill(Color.LIGHTGRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Color.PURPLE);
        graphicsContext.fillText("ello bois", 100, 200);

        networkManager.drawAllObjects(graphicsContext);
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

            networkManager.moveObjects(horizontalDelta, verticalDelta);
        }

        redraw();
    }

    private void mouseReleased(MouseEvent t) {
        int cursorX = (int) t.getX();
        int cursorY = (int) t.getY();

        if (isClick) {
            if (t.getButton() == MouseButton.PRIMARY) {
                networkManager.deselectAll();

                // try to select an object, if there is none - create one
                Optional<NetworkObject> selected = networkManager.getObject(cursorX, cursorY);
                NetworkObject currentObject = selected.orElse(
                        NetworkFactory.createNetworkObject(cursorX, cursorY));

                if (networkManager.doesOverlap(currentObject)) {
                    AlertDialog.showInvalidInputAlert(
                            "This spot overlaps with an existing object.");
                    return;
                }

                networkManager.add(currentObject);
                currentObject.showPropertiesDialog();
            }

            if (t.getButton() == MouseButton.SECONDARY) {
                Optional<NetworkObject> selected = networkManager.getObject(cursorX, cursorY);
                if (selected.isPresent()) {
                    selected.get().toggleSelected();
                }
            }
        }
        redraw();
    }

    public void deleteSelected() {
        networkManager.deleteSelected();
        redraw();
    }
}
