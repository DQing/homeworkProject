import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IoCContextTest {
    @Test
    void should_register_bean_correct() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(myBean.getClass(), MyBean.class);
    }

    @Test
    void should_throw_exception_if_null() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()-> context.registerBean(null),"beanClazz is mandatory");
    }

    @Test
    void should_throw_exception_if_not_can_be_instance() {
        IoCContextImpl<FunctionalInterface> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()-> context.registerBean(FunctionalInterface.class),MyInterface.class.getSimpleName()+" is abstract");
    }

    @Test
    void should_throw_exception_if_not_have_default_constroctor() {
        IoCContextImpl<WithNoDefaultConstructor> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()-> context.registerBean(WithNoDefaultConstructor.class),WithNoDefaultConstructor.class.getSimpleName()+" has no default constructor");
    }

    @Test
    void should_throw_exception_if_resolveClazz_is_null() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()-> context.getBean(null));
    }

    @Test
    void should_throw_exception_if_not_register() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalStateException.class,()-> context.getBean(MyBean.class));
    }

    @Test
    void should_not_register_after_getBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.getBean(MyBean.class);
        assertThrows(IllegalStateException.class,()-> context.registerBean(MyBean.class));
    }
}
