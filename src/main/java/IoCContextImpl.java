import Annotation.CreateOnTheFly;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class IoCContextImpl<T> implements IoCContext {
    HashMap<Class,Class> classList = new HashMap<>();
    private List<Object> objectList = new ArrayList<>();
    boolean isCanRegister = false;

    List<Object> getObjectList() {
        return objectList;
    }

    @Override
    public void registerBean(Class<?> beanClazz) {
        if (isCanRegister) {
            throw new IllegalStateException();
        }
        registerBeanIllegalState(beanClazz);
        classList.put(beanClazz, beanClazz);
    }
    @Override
    public <T> void registerBean(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (isCanRegister) {
            throw new IllegalStateException();
        }
        withParameterIllegalState(resolveClazz,beanClazz);
        classList.put(resolveClazz,beanClazz);
    }

    @Override
    public <T> T getBean(Class<T> resolveClazz) throws InstantiationException, IllegalAccessException {
        isCanRegister = true;
        getBeanIllegalState(resolveClazz);
        if (classList.containsKey(resolveClazz)){
            return beanInstance(classList.get(resolveClazz));
        }
        return beanInstance(resolveClazz);
    }

    private <T> T beanInstance(Class bean) throws IllegalAccessException, InstantiationException {
        Stream<Field> totalDependency = Stream.concat(Arrays.stream(bean.getFields()),  Arrays.stream(bean.getSuperclass().getFields()));
          totalDependency.filter(field -> field.getAnnotation(CreateOnTheFly.class) != null)
                .map(field -> {
                    try {
                        if (classList.containsKey(field.getType())){
                            return bean.newInstance();
                        }else {
                            throw new IllegalStateException();
                        }
                    } catch (IllegalAccessException | InstantiationException e) {
                        throw new IllegalStateException();
                    }
                }).count();
        return (T) bean.newInstance();
    }

    private <T> void withParameterIllegalState(Class<? super T> resolveClazz, Class<T> beanClazz) {
        if (resolveClazz==null || beanClazz == null) {
            throw new IllegalArgumentException("beanClazz is mandatory");
        }
        superClassException(beanClazz);
        classList.forEach((key, value) -> classList.put(resolveClazz, beanClazz));
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

    private void getBeanIllegalState(Class<?> resolveClazz) {
        if (resolveClazz == null) {
            throw new IllegalArgumentException();
        }
        if (!classList.containsKey(resolveClazz) && !classList.containsValue(resolveClazz)) {
            throw new IllegalStateException();
        }
    }

    private void registerBeanIllegalState(Class<?> beanClazz) {
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
        if (classList.containsKey(beanClazz)) {
            return;
        }
    }

    @Override
    public void close() {
        classList.forEach((key,value)->{
            try {
                Object instance = value.newInstance();
                objectList.add(instance);
                Method close = value.getMethod("close");
                close.setAccessible(true);
                close.invoke(instance);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new IllegalStateException();
            }
        });
    }
}
