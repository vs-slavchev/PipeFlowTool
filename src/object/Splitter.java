package object;

import javafx.scene.canvas.GraphicsContext;

public class Splitter extends ComponentWithImage {

    public Splitter() {
        super("splitter64");
    }

    @Override
    public void showPropertiesDialog() {
        // TODO: show slider for percent
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, collisionBox.getX(), collisionBox.getY());
        drawHighlighting(gc);
    }
}
