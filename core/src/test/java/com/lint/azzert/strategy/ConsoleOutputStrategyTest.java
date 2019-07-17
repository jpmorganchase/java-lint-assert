package com.lint.azzert.strategy;

import com.lint.azzert.ContextBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ConsoleOutputStrategyTest {

    @Test
    void render() throws IOException, ParseException {
        ConsoleOutputStrategy strategy = new ConsoleOutputStrategy(new ContextBuilder().build().getTestMethodsContext());
        String output = strategy.render();

        Assertions.assertNotNull(output);
        Assertions.assertTrue(output.contains("Package"));
    }

}