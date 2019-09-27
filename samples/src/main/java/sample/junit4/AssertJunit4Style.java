package sample.junit4;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class AssertJunit4Style {

    @Test
    public void withAsserts() {
        Assert.assertTrue(true);
    }

    @Test
    public void withoutAsserts() {
        throw new UnsupportedOperationException();
    }

    public void notATest(){
        throw new UnsupportedOperationException();
    }

    @Test
    @Ignore
    public void disabledTest(){
        throw new UnsupportedOperationException();
    }
}
