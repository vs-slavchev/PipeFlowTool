package tests;

import collision.CircleCollisionMask;
import objects.NetworkObject;

public class CircularNetworkObject extends NetworkObject {

    public CircularNetworkObject(int x, int y) {
        super(x, y, new CircleCollisionMask(20));
    }
}
