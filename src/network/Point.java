package network;

import java.io.Serializable;

public class Point implements Serializable {
    private static final long serialVersionUID = -2724135797181166853L;
    protected int x;
    protected int y;

    public Point() {
        x = y = 0;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setLocation(double x, double y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
