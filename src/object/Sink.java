package object;

import javafx.scene.control.TextInputDialog;
import utility.AlertDialog;

import java.io.Serializable;
import java.util.Optional;

public class Sink extends ComponentWithImage implements Serializable{

    public Sink() {
        super("sink64");
    }

    @Override
    public void setNext(Component nextComponent) {
        // intentionally left empty
    }

    /* Don't update the next component as this is a sink. */
    @Override
    public void showPropertiesDialog() {
        TextInputDialog dialog = new TextInputDialog("10");
        dialog.setTitle("Properties");
        dialog.setHeaderText("Specify the capacity:");
        dialog.setContentText("capacity:");

        Optional<String> result = dialog.showAndWait();
        String input = result.orElse("10");

        int capacityValue = 0;
        try {
            capacityValue = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            AlertDialog.showInvalidInputAlert("The value was not a number.\n" +
                    "Default value is assigned.");
        } finally {
            flowProperties.setCapacity(capacityValue);
        }
    }

}
