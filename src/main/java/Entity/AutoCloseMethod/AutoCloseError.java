package Entity.AutoCloseMethod;

public class AutoCloseError implements AutoCloseable {
    public boolean isClose;

    public AutoCloseError() {
    }

    public AutoCloseError(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    public void close() {
        isClose = true;
        throw new IllegalStateException();
    }
}
