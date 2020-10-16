package sample.junit4;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


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

    @Test(expected = NullPointerException.class)
    public void whenExceptionThrown_thenExpectationSatisfied() {
        String test = null;
        test.length();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Test
    public void throwsExceptionWithSpecificType() {
        thrown.expect(NullPointerException.class);
        throw new NullPointerException();
    }
}
