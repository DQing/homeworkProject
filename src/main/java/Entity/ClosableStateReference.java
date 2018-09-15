package Entity;

public class ClosableStateReference {
    public boolean isClosed;

    public ClosableStateReference(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void close() {
        isClosed = true;
    }
}
