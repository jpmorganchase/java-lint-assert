package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.ToStringStrategy;

import java.util.Set;

public class ConciseFormatIntegrationTest{

    @Test
    public void printOnlyAssertlessMethods() throws Exception {
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.junit5", false, true, "ASSERTLESS_ONLY")).process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertEquals(1, methods.size() );
    }
}
