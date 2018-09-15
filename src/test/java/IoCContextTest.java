import Entity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IoCContextTest {
    @Test
    void should_throw_exception_if_null() {
        IoCContextImpl<?> context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class,() -> context.registerBean(null),"beanClazz is mandatory");
    }

    @Test
    void should_throw_exception_if_not_can_be_instance() {
        IoCContextImpl<FunctionalInterface> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,() -> context.registerBean(FunctionalInterface.class), MyInterface.class.getSimpleName()+" is abstract");
    }

    @Test
    void should_throw_exception_if_not_have_default_constructor() {
        IoCContextImpl<WithNoDefaultConstructor> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,() -> context.registerBean(WithNoDefaultConstructor.class),WithNoDefaultConstructor.class.getSimpleName()+" has no default constructor");
    }

    @Test
    void should_throw_exception_if_resolveClazz_is_null() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class,() -> context.getBean(null));
    }

    @Test
    void should_throw_exception_if_not_register() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalStateException.class,() -> context.getBean(MyBean.class));
    }

    @Test
    void should_not_register_after_getBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.getBean(MyBean.class);
        assertThrows(IllegalStateException.class,() -> context.registerBean(MyBean.class));
    }
    @Test
    void should_throw_exception_if_getBean() {
        IoCContextImpl<MyBeanWithError> context = new IoCContextImpl<>();
        assertThrows(ArithmeticException.class, ()->context.registerBean(MyBeanWithError.class));
    }
    @Test
    void should_register_bean_correct() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(myBean.getClass(), MyBean.class);
    }

    @Test
    void should_register_2_get_1() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.registerBean(Student.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(myBean.getClass(), MyBean.class);
    }
    @Test
    void should_register_2_get_2() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.registerBean(Student.class);
        MyBean myBean = context.getBean(MyBean.class);
        Student student = context.getBean(Student.class);
        assertEquals(myBean.getClass(), MyBean.class);
        assertEquals(Student.class, student.getClass());
    }

     @Test
    void should_register_2_get_anyone() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<Student> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.registerBean(Student.class);
        Student student = context.getBean(Student.class);
        assertEquals(Student.class, student.getClass());
    }
}
