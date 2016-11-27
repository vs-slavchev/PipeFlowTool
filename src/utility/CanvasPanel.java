package utility;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import object.NetworkManager;
import object.NetworkObject;
import object.Pump;

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
        int horizontalDelta = (int) (t.getX() - dragOrigin.getX());
        int verticalDelta = (int) (t.getY() - dragOrigin.getY());
        dragOrigin.setLocation(t.getX(), t.getY());
        isClick = false;

        networkManager.moveObjects(horizontalDelta, verticalDelta);

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
                NetworkObject currentObject = selected.orElse(new Pump(cursorX, cursorY));

                if (networkManager.doesOverlap(currentObject)) {
                    showInvalidInputAlert("This spot overlaps with an existing object.");
                    return;
                }

                networkManager.add(currentObject);

                Optional<String> properties = showPropertiesInputDialog();
                setObjectFlowAndCapacity(currentObject, properties.orElse("10"));
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

    /**
     * Set valid values for the flow and capacity of a network object from an input string.
     * @param object
     * @param input
     */
    public void setObjectFlowAndCapacity(NetworkObject object, String input) {
        int flow = Values.DEFAULT_FLOW_INPUT;
        int capacity = flow;
        try {
            flow = extractTextInputData(input, 0);
            capacity = extractTextInputData(input, 1);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            showInvalidInputAlert("The values were not numbers.\n" +
                    "Default values are assigned.");
        } finally {
            object.setFlow(flow);
            object.setCapacity(capacity);
        }
    }

    /**
     * Get the integer values out of the input string.
     *
     * @param string   the input string
     * @param position index in the values array: 0th is left of '/'; 1st is to the right
     * @return the flow or capacity value as an int
     */
    private int extractTextInputData(String string, int position)
            throws NumberFormatException, IndexOutOfBoundsException {
        if (string.contains("/")) {
            String[] numbers = string.split("/");
            return Integer.parseInt(numbers[position]);
        } else {
            return Integer.parseInt(string);
        }
    }

    /**
     * Shows an alert box saying that the expected numbers were not input properly.
     */
    private void showInvalidInputAlert(String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid input");
        alert.setContentText(errorDescription);

        alert.showAndWait();
    }

    /**
     * A text input dialog box appears. The resulting string is null if
     * the user clicks Cancel. The string can also be empty.
     */
    public Optional<String> showPropertiesInputDialog() {
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Properties");
        dialog.setHeaderText("Specify flow and capacity:\n"
                + "examples: \"8/10\" or \"10\" (short for 10/10)");
        dialog.setContentText("flow/capacity:");

        Optional<String> result = dialog.showAndWait();
        return result;
    }

    public void deleteSelected() {
        networkManager.deleteSelected();
        redraw();
    }
}
