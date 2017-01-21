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

    // TODO: uncomment
    /*@Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image,
                calculateCollisionBox().getX(),
                calculateCollisionBox().getY());
        drawHighlighting(gc);
    }*/
}
