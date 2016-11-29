package object;

import javafx.scene.control.TextInputDialog;
import utility.AlertDialog;
import utility.Values;

import java.util.Optional;

/* TODO: add draw method; call it in overridden draw for constrainable obj;
 * have it show status? */

/**
 * A component to be used in objects which have flow and capacity.
 * Also responsible for showing input dialog and validating input.
 */
public class FlowConstraints {

    private int flow;
    private int capacity;

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

    public void setFlow(final int flow) {
        this.flow = Math.max(flow, 0);
    }

    public void setCapacity(final int capacity) {
        this.capacity = Math.max(capacity, 0);
    }

    public int getFlow() {
        return flow;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Set valid values for the flow and capacity of a network object from an input string.
     * @param object
     * @param input
     */
    public void setObjectFlowAndCapacity(ConstrainedNetworkObject object, String input) {
        int flow = Values.DEFAULT_FLOW_INPUT;
        int capacity = flow;
        try {
            flow = extractTextInputData(input, 0);
            capacity = extractTextInputData(input, 1);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            AlertDialog.showInvalidInputAlert("The values were not numbers.\n" +
                    "Default values are assigned.");
        } finally {
            object.setFlowCapacity(flow, capacity);
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
}
