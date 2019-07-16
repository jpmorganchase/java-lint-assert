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

    String ls = ConsoleOutputStrategy.LINE_SEPARATOR;
    String header = "|-----------------------|----------------------------|------------------------|-----------------|" + ls +
            "|        Package        |       Test file name       |    Test method name    |    # asserts    |"  + ls +
            "|-----------------------|----------------------------|------------------------|-----------------|"  + ls;


    @Test
    void lint() throws IOException, ParseException {

        LintTests lt = new LintTests();
        lt.setPackageName("com.lint");
        String result = lt.lint();
//        log.info(result);
        Assertions.assertTrue(result.startsWith(header));
        Assertions.assertEquals(result,
                header + "|    com/lint/azzert    |    PlaceholderTest.java    |         dummy          |        0        |"  + ls);
    }

    class PlaceholderTest {
        @Test
        void dummy() {

        }
    }
}