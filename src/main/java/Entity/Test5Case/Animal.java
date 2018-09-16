package Entity.Test5Case;

import Annotation.CreateOnTheFly;
import Entity.Dependency.MyBaseDependency;

public class Animal {
    @CreateOnTheFly
    public MyBaseDependency myBaseDependency;
}
