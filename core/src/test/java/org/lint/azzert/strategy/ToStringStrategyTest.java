package org.lint.azzert.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.output.strategy.ToStringStrategy;

class ToStringStrategyTest {

    @Test
    void render() throws Exception {
        ToStringStrategy strategy = new ToStringStrategy(new ContextBuilder().build().getMethodContexts());
        String output = strategy.render();

        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.contains("Package"));
    }

}