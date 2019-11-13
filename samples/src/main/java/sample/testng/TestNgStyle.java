package sample.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.function.Function;

public class TestNgStyle {

    @Test
    void iAmTestWithAssert(){
        Assert.assertTrue(true);
    }

    @Test
    void iAmTestWithoutAssert(){
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test(enabled = false)
    void iAmDisabledTest(){
        Assert.assertTrue(true);
    }

    void iAmNotATest(){
        throw new UnsupportedOperationException();
    }

    @Deprecated
    void iAmNotATestButDeprecatedMethod(){
        throw new UnsupportedOperationException();
    }
}
