package com.lint.azzert.util;

import com.lint.azzert.AssertJunit4Style;
import io.github.classgraph.ClassInfoList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestClassFinderTest {

    @Test
    void getClasses(){
        ClassInfoList cil = TestClassFinder.getClassInfoList("com.lint");

        Assertions.assertNotNull(cil.get(AssertJunit4Style.class.getName()));
        Assertions.assertNull(cil.get(TestClassFinderTest.class.getName())); //'this' is not loaded
    }
}