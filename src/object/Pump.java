package object;

import file.ImageManager;

public class Pump extends NetworkObject {

    public Pump(int x, int y) {
        super(x, y);
        image = ImageManager.getImage("pump64");
    }
}
