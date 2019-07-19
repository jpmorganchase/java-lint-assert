package com.jpmorgan.java.lint;

import com.jpmorgan.java.lint.azzert.context.TestMethodContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

class TestLintPluginTest {

    static Logger log = LoggerFactory.getLogger(TestLintPluginTest.class);

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        lt.setPackageName("com.jpmorgan.java.lint");
        Set<TestMethodContext> result = lt.lintAssert();

        //FIXME::validate the set
//        Assertions.assertTrue(result.contains("TestLintPluginTest.java"));
//        Assertions.assertTrue(result.contains("lint"));

        //TODO::handle nested test classes:
        // Assertions.assertTrue(result.contains("dummy"));
    }

    class PlaceholderTest {
        @Test
        void dummy() {

        }
    }
}