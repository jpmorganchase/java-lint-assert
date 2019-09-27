package sample.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.function.Function;

public class TestNgStyle {

    @Test
    void withAsserts(){
        Assert.assertTrue(true);
    }

    @Test
    void withoutAsserts(){
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test(enabled = false)
    void iAmDisabled(){
        Assert.assertTrue(true);
    }

    void iAmNotATest1(){
        throw new UnsupportedOperationException();
    }

    @Deprecated
    void iAmNotATest2(){
        throw new UnsupportedOperationException();
    }
}
