package org.lint.azzert;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.PrintMode;

import java.util.Set;

public class TestNgIntegrationTest extends LintAssertTest {

    @Test
    void assertTestNg() throws Exception{
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.testng", false, true, PrintMode.ALL.name())).process();

        String content = super.render(methods);
        System.out.println(content);

        Assertions.assertFalse(findMethod.apply(methods, "iAmTestWithAssert").isEmpty(), "Failed to find method 'iAmTestwithAssert' annotated with @Test");
        Assertions.assertFalse(findMethod.apply(methods, "iAmTestwithoutAssert").isEmpty(), "Failed to find method 'iAmTestwithoutAssert' annotated with @Test");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATest").isEmpty(), "Methods without @Test annotation should not be included");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATestButDeprecatedMethod").isEmpty(), "Methods without @Test annotation should not be included");

        // verify that iAmTestThatExpectsException is annotated with @Test(expectedExceptions = {})
        MethodMetadata method = methods.stream().filter(m -> m.getFileName().equals("TestNgStyle.java") && m.getMethodName().equals("iAmTestThatExpectsException")).findAny().get();
        Assertions.assertEquals(1, method.getVerificationsCount());

        Assertions.assertEquals(1, assertsInMethod.apply(methods, "iAmTestWithAssert").size());
        Assertions.assertEquals(0, assertsInMethod.apply(methods, "iAmTestWithoutAssert").size());

        //Ignored class' methods must be excluded
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"TestNgStyle.java", "iAmNotATest"),
                "AssertJunit4Style::notATest should've been excluded");

    }

    @Test
    void printMethodsWithoutAsserts() throws Exception{
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.testng", false, true, PrintMode.ASSERTLESS_ONLY.name())).process();

//        String content = super.render(methods);
//        System.out.println(content);
        Assertions.assertEquals(1, methods.size());
    }
}
