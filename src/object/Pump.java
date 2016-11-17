package object;

import collision.CircleCollisionMask;
import file.ImageManager;

public class Pump extends NetworkObject {

    public Pump(int x, int y) {
        super(x, y, new CircleCollisionMask(32));
        image = ImageManager.getImage("pump");
    }
}
