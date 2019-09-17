package org.lint.azzert.junit4;

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
    }

    public void notATest(){
    }

    @Test
    @Ignore
    public void disabledTest(){

    }
}
