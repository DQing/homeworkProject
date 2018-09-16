import Entity.*;
import Entity.Test5Case.Animal;
import Entity.Test5Case.Cat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IoCContextExtendTest {
    @Test
    void should_get_student_when_get_person() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(Person.class, Student.class);
        Student student = (Student) context.getBean(Person.class);
        assertEquals(Student.class,student.getClass());
    }
    @Test
    void should_get_student_when_get_student() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<Student> context = new IoCContextImpl<>();
        context.registerBean(Person.class, Student.class);
        Student student = context.getBean(Student.class);
        assertEquals(Student.class,student.getClass());
    }
    @Test
    void should_get_student_when_get_interface() throws InstantiationException, IllegalAccessException {
        IoCContextImpl context = new IoCContextImpl();
        context.registerBean(Action.class, Student.class);
        Student student = (Student) context.getBean(Action.class);
        assertEquals(Student.class,student.getClass());
    }
    @Test
    void should_get_correct_object_when_have_interface() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<Student> context = new IoCContextImpl<>();
        context.registerBean(Action.class, Student.class);
        Student student = context.getBean(Student.class);
        assertEquals(Student.class,student.getClass());
    }

    @Test
    void should_throw_exception_when_input_null() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, null),"beanClazz is mandatory");
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(Person.class, null),"beanClazz is mandatory");
        assertThrows(IllegalArgumentException.class, () -> context.registerBean(null, Student.class),"beanClazz is mandatory");
    }

    @Test
    void should_override() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<Student> context = new IoCContextImpl<>();
        context.registerBean(Person.class, Student.class);
        context.registerBean(Person.class, Work.class);

        Work work = context.getBean(Work.class);
        assertEquals(Work.class,work.getClass());
    }

    @Test
    void should_throw_exception_if_not_can_be_instance() {
        IoCContextImpl context = new IoCContextImpl();
        assertThrows(IllegalArgumentException.class,() -> context.registerBean(FunctionalInterface.class,FunctionalInterface.class), FunctionalInterface.class.getSimpleName()+" is abstract");
    }

    @Test
    void should_throw_exception_if_not_have_default_constructor() {
        IoCContextImpl<WithNoDefaultConstructor> context = new IoCContextImpl<>();
        assertThrows(IllegalArgumentException.class,() -> context.registerBean(Person.class,WithNoDefaultConstructor.class),WithNoDefaultConstructor.class.getSimpleName()+" has no default constructor");
    }
    @Test
    void should_throw_exception_if_not_register() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        assertThrows(IllegalStateException.class,() -> context.getBean(MyBean.class));
    }

    @Test
    void should_not_register_after_getBean() throws InstantiationException, IllegalAccessException {
        IoCContextImpl<Student> context = new IoCContextImpl<>();
        context.registerBean(Person.class,Student.class);
        context.getBean(Person.class);
        assertThrows(IllegalStateException.class,() -> context.registerBean(Person.class,Student.class));
    }
    //4 test

    @Test
    void should_get_correct_bean() throws IllegalAccessException, InstantiationException {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        context.registerBean(MyDependency.class);
        MyBean myBean = context.getBean(MyBean.class);
        assertEquals(MyBean.class,myBean.getClass());
    }
    @Test
    void should_throw_exception_when_dependency_not_instance() {
        IoCContextImpl<MyBean> context = new IoCContextImpl<>();
        context.registerBean(MyBean.class);
        assertThrows(IllegalStateException.class, () -> context.getBean(MyBean.class));
    }

    // 5 test

    @Test
    void should_get_correct_bean_with_inherit() throws IllegalAccessException, InstantiationException {
        IoCContextImpl<Cat> context = new IoCContextImpl<>();
        context.registerBean(Animal.class);
        context.registerBean(Cat.class);
        context.registerBean(MyBaseDependency.class);
        context.registerBean(MyDependency.class);
        Cat cat = context.getBean(Cat.class);
        assertEquals(Cat.class,cat.getClass());
    }
    @Test
    void should_throw_exception_when_not_instance_inherit_dependency() {
        IoCContextImpl<Cat> context = new IoCContextImpl<>();
        context.registerBean(Animal.class);
        context.registerBean(Cat.class);
        context.registerBean(MyDependency.class);
        assertThrows(IllegalStateException.class, () -> context.getBean(Cat.class));
    }
    @Test
    void should_throw_exception_when_not_instance_bean_dependency() {
        IoCContextImpl<Cat> context = new IoCContextImpl<>();
        context.registerBean(Animal.class);
        context.registerBean(Cat.class);
        context.registerBean(MyBaseDependency.class);
        assertThrows(IllegalStateException.class, () -> context.getBean(Cat.class));
    }
    @Test
    void should_throw_exception_when_not_instance_dependency() {
        IoCContextImpl<Cat> context = new IoCContextImpl<>();
        context.registerBean(Animal.class);
        context.registerBean(Cat.class);
        assertThrows(IllegalStateException.class, () -> context.getBean(Cat.class));
    }
    //6 test

    @Test
    void should_close_bean_when_IcoContext_close() {
        IoCContextImpl<AutoCloseBean> context = new IoCContextImpl<>();
        context.registerBean(AutoCloseBean.class);
        context.close();
        context.getObjectList().forEach(instance -> {
            try {
                assertTrue((Boolean) AutoCloseBean.class.getField("isClose").get(instance));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }
}
