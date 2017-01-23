package object;

import file.ImageManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import utility.AlertDialog;
import utility.Values;

import java.io.Serializable;
import java.util.Optional;

public class Splitter extends ComponentWithImage implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
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
            next.update(flowProperties.getFlow() * splitPercent / 100);
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

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(ImageManager.getImage(imageName),
                calculateCollisionBox().getX(),
                calculateCollisionBox().getY());
        drawHighlighting(gc);

        gc.setFill(Color.PURPLE);
        gc.fillText(splitPercent + " : " + (100 - splitPercent),
                position.getX(), position.getY() +
                (int) ImageManager.getImage(imageName).getHeight() / 2
                + Values.INFO_VERTICAL_OFFSET);
    }

    public Component getSecondNext() {
        return secondNext;
    }
}
