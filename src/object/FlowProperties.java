package object;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import utility.AlertDialog;
import utility.Values;

import java.util.Optional;

/**
 * A component to be used in objects which have flow and capacity.
 * Also responsible for showing input dialog and validating input.
 */
public class FlowProperties {

    private int flow;
    private int capacity;

    public FlowProperties() {
        flow = capacity = Values.DEFAULT_FLOW_INPUT;
    }

    /**
     * Show an input dialog and ask user for values. Control the case when input is null.
     * Set valid values for flow and capacity.
     */
    public void inputFlowPropertyValues() {
        Optional<String> properties = showPropertiesInputDialog();
        String input = properties.orElse("10");

        int inputFlow = Values.DEFAULT_FLOW_INPUT;
        int inputCapacity = inputFlow;
        try {
            inputFlow = extractTextInputData(input, 0);
            inputCapacity = extractTextInputData(input, 1);
        } catch (NumberFormatException | IndexOutOfBoundsException ex) {
            AlertDialog.showInvalidInputAlert("The values were not numbers.\n" +
                    "Default values are assigned.");
        } finally {
            setFlow(inputFlow);
            setCapacity(inputCapacity);
        }
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

    public void drawFlowCapacity(GraphicsContext gc, int x, int y) {
        gc.setFill(Color.PURPLE);
        gc.fillText(this.toString(), x, y);
    }

    @Override
    public String toString() {
        return flow + "/" + capacity;
    }
}
