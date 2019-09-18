package org.lint.azzert.junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class AssertJunit5Style {

    @Test
    void withoutAsserts() {
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test
    void withAsserts() {
        Assertions.assertTrue(true);
        Assertions.assertArrayEquals(new int[]{1,2}, new int[]{1,2} );
    }

    @Test
    @Disabled
    void iAmDisabled(){}

    void iAmNotATest(){}
}
