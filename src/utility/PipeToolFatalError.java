package utility;

import javafx.scene.control.Alert;

public class PipeToolFatalError {
    public static void show(String errorMessage) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Fatal Error:");
        alert.setContentText(errorMessage);

        alert.showAndWait();
        System.exit(0);
    }
}
