package org.lint.azzert.command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.command.output.OutputOnlyAssertlessMethods;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;

import java.util.Set;

class RemoveTestMethodsWithAssertsCommandTest{

    @Test
    void testWithoutAssert(){}

    @Test
    void testWithAssert(){Assertions.assertTrue(true);}

    @Test
    void execute() throws Exception {
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("org.lint.azzert.command", false, false)).process();

        new OutputOnlyAssertlessMethods().execute(methods);

        // there should be 1 entry for 'testWithoutAssert'
        Assertions.assertEquals( 1, methods.size());
        Assertions.assertEquals(0, methods.iterator().next().getMethodCalls().size());
    }
}