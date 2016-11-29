package network;

// TODO: add pipe creation and setting up;

import object.*;

public class NetworkFactory {

    public enum NetworkObjectType {PUMP, SPLITTER, MERGER, SINK}

    private static NetworkObjectType selected = NetworkObjectType.PUMP;

    public static NetworkObject createNetworkObject(int x, int y) {
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

    public static void setSelected(NetworkObjectType type) {
        selected = type;
    }
}
