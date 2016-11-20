package utility;

import file.ImageManager;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import object.NetworkManager;
import object.Pump;

import java.awt.*;

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
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                t -> {
                    int horizontalOffset = (int) (t.getX() - dragOrigin.getX());
                    int verticalOffset = (int) (t.getY() - dragOrigin.getY());
                    networkManager.translateAll(horizontalOffset, verticalOffset);
                    dragOrigin.setLocation(t.getX(), t.getY());

                    redraw();
                    isClick = false;
                });

        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED,
                t -> {
                    dragOrigin = new Point((int) t.getX(), (int) t.getY());
                    isClick = true;
                });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED,
                t -> {
                    if (isClick) {
                        networkManager.add(new Pump((int) t.getX(), (int) t.getY()));
                        redraw();
                    }
                });
    }

    /*public String askForInput() {
        return (String)JOptionPane.showInputDialog(
                this,
                "Specify flow and capacity:\n"
                        + "examples: \"8/10\" or \"10\" (short for 10/10)",
                "Properties", // window title
                JOptionPane.QUESTION_MESSAGE,
                UIManager.getIcon("FileChooser.detailsViewIcon"),
                null,
                "10"); // default text
    }*/

    public NetworkManager getNetworkManager() {
        return networkManager;
    }
}
