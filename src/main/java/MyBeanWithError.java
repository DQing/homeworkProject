public class MyBeanWithError {
    public MyBeanWithError() {
        throw new ArithmeticException();
    }
}
