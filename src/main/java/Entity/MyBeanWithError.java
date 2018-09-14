package Entity;

public class MyBeanWithError {
    public MyBeanWithError() {
        throw new ArithmeticException();
    }
}
