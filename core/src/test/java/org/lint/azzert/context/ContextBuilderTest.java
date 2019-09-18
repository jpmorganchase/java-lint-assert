package org.lint.azzert.context;

import org.junit.jupiter.api.Test;
import org.lint.azzert.strategy.framework.JUnit4Strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContextBuilderTest {

    @Test
    void build() throws Exception {
        Context ctx = new ContextBuilder().build();

        assertNotNull(ctx.getSupportedTestFrameworks());
        assertTrue(ctx.getSupportedTestFrameworks().size() > 0, "Expected at least one test_framework defined in application-properties.json");
        assertTrue(ctx.getSupportedTestFrameworks().containsKey(new JUnit4Strategy().getSupportedFramework()), "object of type JUnit4Strategy should've been loaded");
    }

}
