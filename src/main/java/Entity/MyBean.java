package Entity;

import Annotation.CreateOnTheFly;

public class MyBean {
    @CreateOnTheFly
    public MyDependency dependency;

    public MyBean() {
    }

    public MyBean(MyDependency dependency) {

        this.dependency = dependency;
    }

}
