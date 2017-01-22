package object;

import javafx.scene.control.TextInputDialog;
import utility.AlertDialog;

import java.util.Optional;

public class Splitter extends ComponentWithImage {

    private Component secondNext;
    private int splitPercent = 50;


    public Splitter() {
        super("splitter64");
    }

    @Override
    public void showPropertiesDialog() {
        TextInputDialog dialog = new TextInputDialog("50");
        dialog.setTitle("Properties");
        dialog.setHeaderText("Specify the percent of the first output:");
        dialog.setContentText("flow percent:");

        Optional<String> result = dialog.showAndWait();
        String input = result.orElse("50");

        int percentValue = 0;
        try {
            percentValue = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            AlertDialog.showInvalidInputAlert("The value was not a number.\n" +
                    "Default value is assigned.");
        } finally {
            splitPercent = Math.min(Math.max(0, percentValue), 100);
            performSplitting();
        }
    }

    @Override
    public void update(double previousFlow) {
        flowProperties.setFlow(previousFlow);
        performSplitting();
    }

    private void performSplitting() {
        if (next != null && secondNext != null) {
            next.update(flowProperties.getFlow() * splitPercent/100);
            secondNext.update(flowProperties.getFlow() * (100 - splitPercent) / 100);
        }
    }

    @Override
    public void setNext(Component nextComponent) {
        if (this != nextComponent && !(nextComponent instanceof Pump)) {
            if (next == null) {
                next = nextComponent;
            } else if (secondNext == null) {
                secondNext = nextComponent;
            }
        }
        // nulling on purpose
        if (nextComponent == null) {
            next = secondNext = null;
        }
    }
}
