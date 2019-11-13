package sample.junit4;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class IgnoredClass {

    @Test
    public void activeTest(){}

    @Test
    @Ignore
    public void ignoredTest(){}

    public void notTest(){}
}
