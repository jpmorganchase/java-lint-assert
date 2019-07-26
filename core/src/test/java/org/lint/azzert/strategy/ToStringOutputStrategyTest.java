package org.lint.azzert.strategy;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.ContextBuilder;

import java.io.IOException;

class ToStringOutputStrategyTest {

    @Test
    void render() throws IOException, ParseException {
        ToStringOutputStrategy strategy = new ToStringOutputStrategy(new ContextBuilder().build().getMethodContexts());
        String output = strategy.render();

        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.contains("Package"));
    }

}