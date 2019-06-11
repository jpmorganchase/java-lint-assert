package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class SampleTest {

    @Test
    void withoutAssert() {
        Function<String, String> toLowerCase = input -> input.toLowerCase();
        toLowerCase.apply("ZZZ");
    }

    @Test
    void withAssert() {
        Assertions.assertTrue(true);
        Assertions.assertArrayEquals(new int[]{1,2}, new int[]{} );
    }
}
