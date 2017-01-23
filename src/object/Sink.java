package object;

public class Sink extends ComponentWithImage {

    public Sink() {
        super("sink64");
    }

    /* Don't set the next component as this is the end of the line. */
    @Override
    public void setNext(Component nextComponent) {
        // intentionally left empty
    }

    @Override
    public void showPropertiesDialog() {
        flowProperties.showOnlyCapacityInputDialog();
    }

}
