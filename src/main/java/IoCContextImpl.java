import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IoCContextImpl<T> implements IoCContext {
    List<Class<?>> classList = new ArrayList<>();
    HashMap<String,String> baseClass = new HashMap<>();
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
        withParameterIllegalState(resolveClazz,beanClazz);
        classList.add(resolveClazz);
        classList.add(beanClazz);
    }

    private <T> void withParameterIllegalState(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (resolveClazz==null||beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        baseClassException(resolveClazz);
        superClassException(beanClazz);
        baseClass.forEach((key,value) -> {
            if (key.equals(resolveClazz.getSimpleName())){
                baseClass.put(resolveClazz.getSimpleName(), beanClazz.getSimpleName());
            }
        });
    }

    private <T> void superClassException(Class<? super T> beanClazz) {
        try {
            beanClazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(beanClazz.getName() + " is abstract");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(beanClazz.getName() + " has no default constructor");
        }
    }

    private <T> void baseClassException(Class<T> resolveClazz) {
        try {
            resolveClazz.newInstance();
        } catch (InstantiationException e) {
            throw new IllegalArgumentException(resolveClazz.getName() + " is abstract");
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(resolveClazz.getName() + " has no default constructor");
        }
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
