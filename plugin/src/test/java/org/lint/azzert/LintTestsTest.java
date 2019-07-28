package org.lint.azzert;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.TestMethodContext;
import org.lint.azzert.strategy.ToStringOutputStrategy;

import java.io.IOException;
import java.util.Set;

class LintTestsTest {

    @Test
    void lint() throws IOException, ParseException {
        LintTests lt = new LintTests();
        //lt.setPackageName("org.lint");
        lt.setVerbose(false);
        Set<TestMethodContext> result = lt.lintAssert();

        System.out.println(new ToStringOutputStrategy(result).render());
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }
}