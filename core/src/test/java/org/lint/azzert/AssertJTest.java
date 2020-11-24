package org.lint.azzert;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.command.RemoveMethodsThatAreNotTestsCommand;
import org.lint.azzert.command.RemoveNonAssertCallsCommand;
import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class AssertJTest extends AbstractTest {

    @Test
    void assertMockitoVerify() throws Exception {

        //set up
        final Context context = TestUtil.buildContext("sample.assertj");
        new RemoveMethodsThatAreNotTestsCommand().execute(context);

        //execute
        new RemoveNonAssertCallsCommand().execute(context);
        Set<MethodMetadata> methods = context.getMethods();

        //verify
//        System.out.println(super.render(methods));
        Assertions.assertEquals(1, context.getMethods().size());
        Assertions.assertEquals(4, context.getMethods().iterator().next().getVerificationsCount());
    }
}
