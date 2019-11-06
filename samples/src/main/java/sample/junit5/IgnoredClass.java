package sample.junit5;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Disabled
public class IgnoredClass {

    @Test
    public void activeTest(){}

    @Test
    @Disabled
    public void ignoredTest(){}

    public void notTest(){}
}
