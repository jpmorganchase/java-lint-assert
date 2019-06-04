package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestWithAsserts {

    @Test
    void withoutAssert() {
    }

    @Test
    void withAssert() {
        Assertions.assertTrue(true);
    }
}
