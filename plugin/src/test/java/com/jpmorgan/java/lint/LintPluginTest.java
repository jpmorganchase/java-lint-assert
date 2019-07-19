package com.jpmorgan.java.lint;

import com.jpmorgan.java.lint.azzert.context.TestMethodContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;

class LintPluginTest {

    static Logger log = LoggerFactory.getLogger(LintPluginTest.class);

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        lt.setPackageName("com.jpmorgan.java.lint");
        Set<TestMethodContext> result = lt.lintAssert();

//        System.out.println(new ConsoleOutputStrategy(result).render());

        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }
}