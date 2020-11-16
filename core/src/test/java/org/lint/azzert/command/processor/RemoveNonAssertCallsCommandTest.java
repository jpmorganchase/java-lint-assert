package org.lint.azzert.command.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.LintAssertTest;
import org.lint.azzert.command.FindTestMethodsCommand;
import org.lint.azzert.command.RemoveNonAssertCallsCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.ContextBuilder;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;

import java.util.Set;

class RemoveNonAssertCallsCommandTest extends LintAssertTest {

    @Test
    void countInThrownExpectedException() throws Exception {
        final Context context = new ContextBuilder().build();

        new FindTestMethodsCommand(
                null, new LintAssertBuildParameters("sample.junit4", false, true, "ALL"))
                    .withSuccessor(new RemoveNonAssertCallsCommand())
                        .execute(context);

        Set<MethodMetadata> methods = context.getMethods();
//        String content = super.render(methods);
//        System.out.println(content);
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "throwsExceptionWithSpecificType").size());
    }
}