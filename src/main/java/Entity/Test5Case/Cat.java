package Entity.Test5Case;

import Annotation.CreateOnTheFly;
import Entity.MyDependency;

public class Cat extends Animal {
    @CreateOnTheFly
    public MyDependency myDependency;
}
