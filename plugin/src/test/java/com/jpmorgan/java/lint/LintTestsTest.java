package com.jpmorgan.java.lint;

import com.jpmorgan.java.lint.azzert.context.TestMethodContext;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

class LintTestsTest {

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        lt.setPackageName("com.jpmorgan.java.lint");
        lt.setVerbose(false);
        Set<TestMethodContext> result = lt.lintAssert();

//        System.out.println(new ConsoleOutputStrategy(result).render());
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }
}