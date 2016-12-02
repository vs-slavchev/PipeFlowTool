package network;

// TODO: add pipe creation and setting up;

import object.*;
import utility.CursorManager.CursorType;

public class NetworkFactory {

    private static CursorType selected;

    public static Component createNetworkObject(int x, int y) {
        switch (selected) {
            case SPLITTER:
                return new Splitter(x, y);
            case MERGER:
                return new Merger(x, y);
            case SINK:
                return new Sink(x, y);
            default:
                return new Pump(x, y);
        }
    }

    public static void setSelected(CursorType type) {
        selected = type;
    }
}
