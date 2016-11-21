package utility;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import object.NetworkManager;
import object.NetworkObject;
import object.Pump;

import java.awt.*;
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
        graphicsContext.setFill(Color.GREEN);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        graphicsContext.setFill(Color.PURPLE);
        graphicsContext.fillText("ello bois", 100, 200);

        networkManager.drawAllObjects(graphicsContext);
    }

    private void addEventHandlers() {
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, t -> mouseDragged(t));

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, t -> mousePressed(t));

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, t -> mouseReleased(t));
    }

    private void mousePressed(MouseEvent t) {
        dragOrigin = new Point((int) t.getX(), (int) t.getY());
        isClick = true;
    }

    private void mouseDragged(MouseEvent t) {
        int horizontalOffset = (int) (t.getX() - dragOrigin.getX());
        int verticalOffset = (int) (t.getY() - dragOrigin.getY());
        networkManager.translateAll(horizontalOffset, verticalOffset);
        dragOrigin.setLocation(t.getX(), t.getY());

        redraw();
        isClick = false;
    }

    private void mouseReleased(MouseEvent t) {
        if (isClick) {
            int cursorX = (int) t.getX();
            int cursorY = (int) t.getY();

            // try to select an object, if there is none - create one
            Optional<NetworkObject> selected = networkManager.getObject(cursorX, cursorY);
            NetworkObject currentObject = selected.orElse(new Pump(cursorX, cursorY));
            networkManager.add(currentObject);

            Optional<String> properties = showPropertiesInputDialog();
            try {
                currentObject.setFlow(readFlowValue(properties.orElse("10")));
                currentObject.setCapacity(readCapacityValue(properties.orElse("10")));
            } catch (NumberFormatException nfe) {
                showNumberFormatAlert();
            }
        }
        redraw();
    }

    private int readFlowValue(String string) throws NumberFormatException {
        if (!string.contains("/")) {
            return Integer.parseInt(string);
        }
        // TODO
        String[] numbers = string.split("/");
        if (numbers.length == 2) {

        }

        // string.chars().filter(Character::isDigit).forEach(println);
        return 0;
    }

    private int readCapacityValue(String string) throws NumberFormatException {
        if (!string.contains("/")) {
            return Integer.parseInt(string);
        }
        // TODO
        return 0;
    }

    /**
     * Shows an alert box saying that the expected numbers were not input properly.
     */
    private void showNumberFormatAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid input");
        alert.setContentText("The values were not numbers.\n" +
                "Default values are assigned.");

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
}
