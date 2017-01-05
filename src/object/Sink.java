package object;

public class Sink extends ComponentWithImage {

    public Sink() {
        super("sink64");
    }

    @Override
    public void setNext(Component nextComponent) {
        // intentionally left empty
    }

    /* Don't update the next component as this is a sink. */
    @Override
    public void showPropertiesDialog() {
        flowProperties.inputFlowPropertyValues();
    }

}
