package org.lint.azzert;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AssertTestNGStyle {

    @Test
    public void withAsserts() {
        Assert.assertTrue(true);
    }

    @Test
    public void withoutAsserts() {

    }

    @Test(enabled = false)
    public void ignoredTest() {
        Assert.assertTrue(true);
    }

    @Test(enabled = true)
    public void falseNegative() {
        Assert.assertTrue(true);
    }

    @Test(groups = {"group"})
    public void groupedTest() {
        Assert.assertTrue(true);
    }


}
