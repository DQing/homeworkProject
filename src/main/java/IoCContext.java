public interface IoCContext extends AutoCloseable{
    void registerBean(Class<?> beanClazz) throws IllegalAccessException, InstantiationException;

    <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException;

    <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz);
}
