package object;

public class Splitter extends NetworkObject {

    public Splitter(int x, int y) {
        super(x, y, "splitter64");
    }

    @Override
    public void showPropertiesDialog() {
        // TODO: show slider for percent
    }
}