package com.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class AssertJunit5Style {

    @Test
    void withoutAssert() {
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test
    void withAssert() {
        Assertions.assertTrue(true);
        Assertions.assertArrayEquals(new int[]{1,2}, new int[]{1,2} );
    }
}
