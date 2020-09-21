package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.command.OutputOnlyAssertlessMethods;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.ToStringStrategy;

import java.util.Set;

public class ConciseFormatIntegrationTest{

    @Test
    public void printOnlyAssertlessMethods() throws Exception {
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.junit5", false, true)).process();

        Assertions.assertTrue(methods.size() > 1);

        ToStringStrategy strategy = new ToStringStrategy(methods);
        final Set<MethodMetadata> decoratedMethods = strategy.decorate(new OutputOnlyAssertlessMethods());
        Assertions.assertEquals(1, decoratedMethods.size() );
    }
}
