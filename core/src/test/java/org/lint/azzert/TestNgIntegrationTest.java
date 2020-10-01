package org.lint.azzert;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;

import java.util.Set;

public class TestNgIntegrationTest extends LintAssertTest {

    @Test
    void assertTestNg() throws Exception{
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.testng", false, true, "ALL")).process();

        String content = super.render(methods);
        System.out.println(content);

        Assertions.assertFalse(findMethod.apply(methods, "iAmTestWithAssert").isEmpty(), "Failed to find method 'iAmTestwithAssert' annotated with @Test");
        Assertions.assertFalse(findMethod.apply(methods, "iAmTestwithoutAssert").isEmpty(), "Failed to find method 'iAmTestwithoutAssert' annotated with @Test");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATest").isEmpty(), "Methods without @Test annotation should not be included");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATestButDeprecatedMethod").isEmpty(), "Methods without @Test annotation should not be included");

        //the 'withoutAsserts' should contain no asserts
        Assertions.assertTrue(findMethod.apply(methods, "iAmTestWithoutAssert").get(0).getMethodCalls().isEmpty(), "There are *no* asserts in 'iAmTestWithoutAssert' method");

        //'withAssert' has 1 assert method
        Assertions.assertTrue(assertsInMethod.apply(methods, "iAmTestWithAssert").removeIf(m -> m.getAtLineNumber() == 12));

        //Ignored class' methods must be excluded
        Assertions.assertEquals(2, methods.size(), "Expected to find exactly 2 testNG style tests.");
    }
}
