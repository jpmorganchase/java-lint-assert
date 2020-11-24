package org.lint.azzert.command.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.AbstractTest;
import org.lint.azzert.TestUtil;
import org.lint.azzert.command.RemoveNonAssertCallsCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

class RemoveNonAssertCallsCommandTest extends AbstractTest {

    @Test
    void countInThrownExpectedException() throws Exception {
        Context context = TestUtil.buildContext("sample.junit4");
        new RemoveNonAssertCallsCommand().execute(context);

        Set<MethodMetadata> methods = context.getMethods();
        String content = super.render(methods);
        System.out.println(content);
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "throwsExceptionWithSpecificType").size());
    }
}