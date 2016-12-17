package network;

import javafx.scene.canvas.GraphicsContext;

public class Join {

    private Point location;

    public Join(Point location) {
        this.location = location;
    }

    public void drawJoin(GraphicsContext gc) {

    }

    public int getX() {
        return location.getX();
    }

    public int getY() {
        return location.getY();
    }

    public void translate(int dx, int dy) {
        location.translate(dx, dy);
    }
}
