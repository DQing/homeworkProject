package Entity;

public class AutoCloseBean implements AutoCloseable {
    public boolean isClose;

    public AutoCloseBean() {
    }

    public AutoCloseBean(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    public void close() {
        isClose = true;
    }
}
