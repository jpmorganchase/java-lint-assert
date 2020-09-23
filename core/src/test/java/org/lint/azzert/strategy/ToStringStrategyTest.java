package org.lint.azzert.strategy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.strategy.output.ToStringStrategy;

class ToStringStrategyTest {

    @Test
    void render() throws Exception {
        ToStringStrategy strategy = new ToStringStrategy(new ContextBuilder().build().getMethods());
        String output = strategy.render();
        System.out.println(output);
        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.contains("Package"));
    }
}