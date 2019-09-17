package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.output.strategy.ToStringStrategy;

import java.util.Set;

class LintTestsTest {

    @Test
    void lintWithPackageFilter() throws Exception {
        LintTests lt = new LintTests();
        lt.setPackageName("org.lint");
        lt.setVerbose(false);
        Set<MethodMetadata> result = lt.lintAssert();

        System.out.println(new ToStringStrategy(result).render());
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }

    @Test
    void lint() throws Exception {
        LintTests lt = new LintTests();
        Set<MethodMetadata> result = lt.lintAssert();

        System.out.println(new ToStringStrategy(result).render());
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(0, result.size());
    }
}