package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;
import org.lint.azzert.strategy.output.PrintMode;

import java.util.Set;


class JUnit4IntegrationTest extends LintAssertTest {

    @Test
    void assertJUnit4() throws Exception {

        final Set<MethodMetadata> methods = new LintAssertProcessor(
                null, new LintAssertBuildParameters("sample.junit4", false, true, PrintMode.ALL.name())).process();

        String content = super.render(methods);
        System.out.println(content);

        // verify that whenExceptionThrown_thenExpectationSatisfied is annotated with @Test(expected=some_exception)
        MethodMetadata method = methods.stream().filter(m -> m.getFileName().equals("AssertJunit4Style.java") && m.getMethodName().equals("whenExceptionThrown_thenExpectationSatisfied")).findAny().get();
        Assertions.assertEquals(1, method.getVerificationsCount());

        Assertions.assertTrue(assertsInMethod.apply(methods, "withoutAsserts").isEmpty());
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts").size());
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "disabledTest"), "AssertJunit4Style::disabledTest should've been excluded");
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "notATest"), "AssertJunit4Style::notATest should've been excluded");
        Assertions.assertEquals(4, methods.size() ,"Expected to find exactly 4 JUnit4 test methods.");

    }

    @Test
    void printMethodsWithoutAsserts() throws Exception{
        final Set<MethodMetadata> methods = new LintAssertProcessor(null,
                new LintAssertBuildParameters("sample.junit4", false, true, PrintMode.ASSERTLESS_ONLY.name())).process();
        String content = super.render(methods);
        System.out.println(content);
        Assertions.assertEquals(1, methods.size());
    }
}
