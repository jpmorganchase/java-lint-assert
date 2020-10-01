package org.lint.azzert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.LintAssertBuildParameters;
import org.lint.azzert.processor.LintAssertProcessor;

import java.util.Set;
import java.util.function.BiFunction;


class JUnit4IntegrationTest extends LintAssertTest {

    @Test
    void assertJUnit4() throws Exception {

        final Set<MethodMetadata> methods = new LintAssertProcessor(
                null, new LintAssertBuildParameters("sample.junit4", false, true, "ALL")).process();

        String content = super.render(methods);
        System.out.println(content);

        Assertions.assertTrue(assertsInMethod.apply(methods, "withoutAsserts").isEmpty());
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts").size());
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "disabledTest"), "AssertJunit4Style::disabledTest should've been excluded");
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "notATest"), "AssertJunit4Style::notATest should've been excluded");
        Assertions.assertEquals(4, methods.size() ,"Expected to find exactly 4 JUnit4 test methods.");

    }

    int countMethodOccurrencesInFile(final Set<MethodMetadata> methods, String file, String method){
        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();

        return countOccurences.apply(file, method).intValue();
    }
}
