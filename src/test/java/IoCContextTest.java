import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IoCContextTest {
    @Test
    void should_register_bean_correct() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl();
        context.registerBean(MyBean.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(myBean.getClass(), MyBean.class);
    }

    @Test
    void should_throw_exception_if_null() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()->{
            context.registerBean(null);
        } );
    }

    @Test
    void should_throw_exception_if_not_can_be_instance() {
        IoCContextImpl<MyInterface> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()->{
            context.registerBean(MyInterface.class);
        });
    }

    @Test
    void should_throw_exception_if_not_have_default_constroctor() {
        IoCContextImpl<WithNoDefaultConstructor> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()->{
            context.registerBean(WithNoDefaultConstructor.class);
        });
    }

    @Test
    void should_throw_exception_if_resolveClazz_is_null() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,()->{
            context.getBean(null);
        });
    }

}
