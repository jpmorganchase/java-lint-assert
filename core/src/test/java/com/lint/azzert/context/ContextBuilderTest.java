package com.lint.azzert.context;

import com.lint.azzert.ContextBuilder;
import org.junit.jupiter.api.Test;

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
