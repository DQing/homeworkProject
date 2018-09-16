package Entity.Test5Case;

import Annotation.CreateOnTheFly;
import Entity.Dependency.MyDependency;

public class Cat extends Animal {
    @CreateOnTheFly
    public MyDependency myDependency;
}
