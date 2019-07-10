package com.lint.azzert.util;

import com.lint.azzert.AssertJunit4Style;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class TestClassFinderTest {

    @Test
    void getClasses(){
        List<String> classes = TestClassFinder.getClasses("com.lint");

        for (String className : classes) {
            System.out.println(className);
        }

        Assertions.assertTrue(classes.contains(AssertJunit4Style.class.getName()));
        Assertions.assertFalse(classes.contains(TestClassFinderTest.class.getName())); //'this' is not loaded

    }
}