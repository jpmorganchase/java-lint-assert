package org.lint.azzert.context;

import org.junit.jupiter.api.Test;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContextBuilderTest {

    @Test
    void build() throws Exception {
        Context ctx = new ContextBuilder().build();

        assertNotNull(ctx.getSupportedTestFrameworks());
        assertNotNull(ctx.getSupportedAssertApis());
        assertTrue(ctx.getSupportedTestFrameworks().size() > 0, "Expected at least one test_framework defined in application-properties.json");
        assertTrue(ctx.getSupportedAssertApis().size() > 0, "Expected at least one assert_api defined in application-properties.json");
    }

}
