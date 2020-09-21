package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;

import java.util.Set;

public class JUnit5IntegrationTest extends LintAssertTest{

    @Test
    void assertJUnit5() throws Exception{

        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.junit5", false, true)).process();

        String content = super.render(methods);
        System.out.println(content);

        Assertions.assertFalse(findMethod.apply(methods, "withAsserts").isEmpty(), "Failed to find method 'withAssert' annotated with @Test");
        Assertions.assertFalse(findMethod.apply(methods, "withoutAsserts").isEmpty(), "Failed to find method 'withoutAsserts' annotated with @Test");
        Assertions.assertTrue(findMethod.apply(methods, "iAmDisabled").isEmpty(), "Method 'iAmDisabled' should not be in the result set");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATest1").isEmpty(), "Method 'iAmNotATest' should not be in the result set");
        Assertions.assertEquals(2, methods.size(), "Expected to find exactly 2 JUnit 5 test methods.");

        //the 'withoutAsserts' should contain no asserts
        Assertions.assertTrue(findMethod.apply(methods, "withoutAsserts").get(0).getMethodCalls().isEmpty(), "There are *no* asserts in 'withoutAsserts' method");
        Assertions.assertEquals(2, findMethod.apply(methods, "withAsserts").get(0).getMethodCalls().size(), "There are 2 asserts in 'withAsserts' method");

        //'withAssert' has 2 assert methods on lines 19 and 20
        Assertions.assertTrue(assertsInMethod.apply(methods, "withAsserts").removeIf(m -> m.getAtLineNumber() == 19));
        Assertions.assertTrue(assertsInMethod.apply(methods, "withAsserts").removeIf(m -> m.getAtLineNumber() == 20));
    }

}
