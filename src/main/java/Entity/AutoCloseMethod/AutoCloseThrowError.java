package Entity.AutoCloseMethod;

public class AutoCloseThrowError implements AutoCloseable {

    public boolean isClose;

    public AutoCloseThrowError() {
    }

    public AutoCloseThrowError(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    public void close() {
        isClose = true;
        throw new IllegalArgumentException();
    }
}
