package Entity;

import Annotation.CreateOnTheFly;
import Entity.Dependency.MyDependency;

public class MyBean {
    @CreateOnTheFly
    public MyDependency dependency;

    public MyBean() {
    }

    public MyBean(MyDependency dependency) {

        this.dependency = dependency;
    }

}
