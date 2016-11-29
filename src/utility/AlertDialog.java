package utility;

import javafx.scene.control.Alert;

/** Makes showing alert and error messages easier. */
public class AlertDialog {

    public static void showFatalError(String errorMessage) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Fatal Error:");
        alert.setContentText(errorMessage);

        alert.showAndWait();
        System.exit(0);
    }

    /**
     * Shows an alert box saying that the expected numbers were not input properly.
     */
    public static void showInvalidInputAlert(String errorDescription) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Invalid input");
        alert.setContentText(errorDescription);

        alert.showAndWait();
    }
}
