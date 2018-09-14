package Entity;

import Annotation.CreateOnTheFly;

public class MyBean {
    @CreateOnTheFly
    private MyDependency dependency;

    public MyBean() {

    }

}
