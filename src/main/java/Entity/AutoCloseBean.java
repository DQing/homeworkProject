package Entity;

public class AutoCloseBean implements AutoCloseable {
    public ClosableStateReference reference;

    public AutoCloseBean() {
    }
    public boolean isClosed() {
        return reference.isClosed();
    }

    public AutoCloseBean(ClosableStateReference reference) {
        this.reference = reference;
    }

    @Override
    public void close() {
        reference.close();
    }
}
