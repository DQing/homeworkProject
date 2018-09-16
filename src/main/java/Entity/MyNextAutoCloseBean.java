package Entity;

public class MyNextAutoCloseBean implements AutoCloseable {
    public boolean isClose;

    public MyNextAutoCloseBean() {
    }

    public MyNextAutoCloseBean(boolean isClose) {
        this.isClose = isClose;
    }

    @Override
    public void close() {
        isClose = true;
    }
}
