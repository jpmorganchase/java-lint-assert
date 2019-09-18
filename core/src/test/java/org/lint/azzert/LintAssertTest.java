package org.lint.azzert;

import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodCallMetadata;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.processor.ToStringAssertProcessor;
import org.lint.azzert.strategy.output.ToStringStrategy;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


class LintAssertTest {

    //find a method in the result set
    final BiFunction<Set<MethodMetadata>, String, List<MethodMetadata>> findMethod = (mtds, name) -> mtds.stream().filter(
            f -> name.equalsIgnoreCase(f.getMethodName())).collect(Collectors.toList());

    //count asserts in a method
    final BiFunction<Set<MethodMetadata>,String, Collection<MethodCallMetadata>> assertsInMethod = (mtds, name) ->
            findMethod.apply(mtds, name).get(0).getMethodCalls();

    @Test
    void assertJUnit5() throws Exception{

        final Set<MethodMetadata> methods = new ToStringAssertProcessor(null, Pair.with("org.lint.azzert.junit5", false)).process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertFalse(findMethod.apply(methods, "withAsserts").isEmpty(), "Failed to find method 'withAssert' annotated with @Test");
        Assertions.assertFalse(findMethod.apply(methods, "withoutAsserts").isEmpty(), "Failed to find method 'withoutAsserts' annotated with @Test");
        Assertions.assertTrue(findMethod.apply(methods, "iAmDisabled").isEmpty(), "Method 'iAmDisabled' should not be in the result set");
        Assertions.assertTrue(findMethod.apply(methods, "iAmNotATest").isEmpty(), "Method 'iAmNotATest' should not be in the result set");
        Assertions.assertEquals(2, methods.size(), "Expected to find at least one JUnit 5 test method.");

        //the 'withoutAsserts' should contain no asserts
        Assertions.assertTrue(findMethod.apply(methods, "withoutAsserts").get(0).getMethodCalls().isEmpty(), "There are *no* asserts in 'withoutAsserts' method");
        Assertions.assertEquals(2, findMethod.apply(methods, "withAsserts").get(0).getMethodCalls().size(), "There are 2 asserts in 'withAsserts' method");

        //'withAssert' has 2 assert methods on lines 19 and 20
        Assertions.assertTrue(assertsInMethod.apply(methods, "withAsserts").removeIf(m -> m.getAtLineNumber() == 19));
        Assertions.assertTrue(assertsInMethod.apply(methods, "withAsserts").removeIf(m -> m.getAtLineNumber() == 20));
    }

    @Test
    void assertJUnit4() throws Exception {

        final Set<MethodMetadata> methods = new ToStringAssertProcessor(
                null, Pair.with("org.lint.azzert.junit4", false)).process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(assertsInMethod.apply(methods, "withoutAsserts").isEmpty());
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts").size());
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "disabledTest"), "AssertJunit4Style::disabledTest should've been excluded");
        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "notATest"), "AssertJunit4Style::notATest should've been excluded");
        Assertions.assertTrue(methods.size() == 2, "Expected to find at least one JUnit 4 test method.");

    }

    int countMethodOccurrencesInFile(final Set<MethodMetadata> methods, String file, String method){
        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();

        return countOccurences.apply(file, method).intValue();
    }
}
