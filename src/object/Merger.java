package object;

import javafx.scene.canvas.GraphicsContext;

public class Merger extends ComponentWithImage {

    public Merger() {
        super("merger64");
    }

    @Override
    public void showPropertiesDialog() {
        // intentionally left empty
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, position.getX(), position.getY());
        drawHighlighting(gc);
    }
}
