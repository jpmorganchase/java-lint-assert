package sample.testng;

import org.testng.annotations.Test;

@Test(enabled = false)
public class IgnoredClass {

    @Test
    public void iAmImplicitlyEnabledTest(){}

    @Test(enabled = true)
    public void iAmExplicitlyEnabledTest(){}

    @Test(enabled = false)
    public void iAmDisabledTest(){}

    public void iAmNotATest(){}
}
