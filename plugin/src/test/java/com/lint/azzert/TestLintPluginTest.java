package com.lint.azzert;

import com.lint.azzert.strategy.ConsoleOutputStrategy;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class TestLintPluginTest {

    static Logger log = LoggerFactory.getLogger(TestLintPluginTest.class);

    String header = "|---------------|----------------------|------------------------|-----------------|" + ConsoleOutputStrategy.LINE_SEPARATOR +
            "|    Package    |    Test file name    |    Test method name    |    # asserts    |" + ConsoleOutputStrategy.LINE_SEPARATOR +
            "|---------------|----------------------|------------------------|-----------------|" + ConsoleOutputStrategy.LINE_SEPARATOR;

    @Test
    void lint() throws IOException, ParseException {

        LintTests lt = new LintTests();
        lt.setPackageName("com.lint");
        String result = lt.lint();

        Assertions.assertTrue(result.startsWith(header));
//        log.info(result);
    }
}