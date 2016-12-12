package object;

import utility.Point;

public class Join extends Point{

    public Join(int x, int y) {
        super(x, y);
    }

    public Join(Point position) {
        this.x = position.getX();
        this.y = position.getY();
    }
}
