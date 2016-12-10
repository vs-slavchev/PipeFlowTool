package network;

// TODO: add pipe creation and setting up;

import object.*;
import utility.CursorManager.CursorType;

public class NetworkFactory {

    public static Component createNetworkObject(CursorType selected) {
        switch (selected) {
            case SPLITTER:
                return new Splitter();
            case MERGER:
                return new Merger();
            case SINK:
                return new Sink();
            default:
                return new Pump();
        }
    }
}
