package com.lint.azzert;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class TestLintPluginTest {

    static Logger log = LoggerFactory.getLogger(TestLintPluginTest.class);

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        lt.setPackageName("com.lint");
//        lt.setVerbose(true);

        String result = lt.lint();

//        System.out.println("result::: " + result);
        Assertions.assertTrue(result.contains("TestLintPluginTest.java"));
        Assertions.assertTrue(result.contains("lint"));

        //TODO::handle nested test classes:
        // Assertions.assertTrue(result.contains("dummy"));
    }

    class PlaceholderTest {
        @Test
        void dummy() {

        }
    }
}