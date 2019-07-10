package com.lint.azzert.util;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;

import java.io.IOException;
import java.util.List;

class TestClassFinderTest {

    @Test
    void getClasses() throws IOException {
        List<String> classes = TestClassFinder.getClasses("com.lint");
        for (String className : classes) {
            System.out.println(className);
            ClassReader classReader = new ClassReader(className);
        }
    }
}