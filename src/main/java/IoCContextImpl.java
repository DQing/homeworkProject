import java.util.ArrayList;
import java.util.List;

public class IoCContextImpl<T> implements IoCContext {
    List<Class<?>> classList = new ArrayList<>();
    boolean isCanRegister = false;

    @Override
    public void registerBean(Class<?> beanClazz) {
        if (isCanRegister) {
            throw new IllegalStateException();
        }
        illegalState(beanClazz);
        classList.add(beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws IllegalAccessException, InstantiationException{
        isCanRegister = true;
        getBeanIllegalState(resolveClazz);
        return resolveClazz.newInstance();
    }

    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (isCanRegister) {
            throw new IllegalStateException();
        }
        illegalState(resolveClazz);
        illegalState(beanClazz);
        classList.add(resolveClazz);
        classList.add(beanClazz);
    }

    private void getBeanIllegalState(Class<?> resolveClazz) {
        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!classList.contains(resolveClazz)) {
            throw new IllegalStateException();
        }
    }

    private void illegalState(Class<?> beanClazz) {
        if (beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        try {
            beanClazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(beanClazz.getName() + " is abstract");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(beanClazz.getName() + " has no default constructor");
        }
        if (classList.contains(beanClazz)) {
            return;
        }
    }
}
