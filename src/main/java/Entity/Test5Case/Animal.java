package Entity.Test5Case;

import Annotation.CreateOnTheFly;
import Entity.MyBaseDependency;

public class Animal {
    @CreateOnTheFly
    public MyBaseDependency myBaseDependency;
}
