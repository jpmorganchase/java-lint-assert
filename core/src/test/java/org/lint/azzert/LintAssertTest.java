package org.lint.azzert;

import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.TestMethodContext;
import org.lint.azzert.strategy.ToStringStrategy;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


class LintAssertTest {

    @Test
    void azzert() throws Exception {

        final Set<TestMethodContext> methods = new ToStringAssertProcessor().process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(methods.size() > 0, "Expected to find at least one test method.");

        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();
        Assertions.assertEquals(1, countOccurences.apply("AssertJunit5Style.java", "withoutAssert"), "Failed to find method 'withoutAssert' annotated with @Test");
        Assertions.assertEquals(1, countOccurences.apply("AssertJunit5Style.java", "withAssert"), "Failed to find method 'withoutAssert' annotated with @Test");

        TestMethodContext withAssert = methods.stream().filter(
                f -> "withAssert".equals(f.getMethodName()) && "AssertJunit5Style.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(18, "assertTrue"), new Pair<>(19, "assertArrayEquals"))),
                "Failed to find assertTrue & assertArrayEquals in AssertJunit5Style::withAssert");

        final BiFunction<Set<TestMethodContext>,String, Integer> assertsInMethod = (methodContexts, str) ->
                methodContexts.stream().filter(f -> str.equals(f.getMethodName()))
                                .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size();

        Assertions.assertEquals(0, assertsInMethod.apply(methods, "withoutAssert"));
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts"));
        Assertions.assertEquals(4, assertsInMethod.apply(methods, "build"));
        Assertions.assertEquals(2, assertsInMethod.apply(methods, "withAssert"));
        Assertions.assertEquals(9, assertsInMethod.apply(methods, "azzert"));
    }

    @Test
    void azzertTestNG() throws Exception {

        final Set<TestMethodContext> methods = new ToStringAssertProcessor().process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(methods.size() > 0, "Expected to find at least one test method.");

        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();
        Assertions.assertEquals(1, countOccurences.apply("AssertTestNGStyle.java", "withoutAsserts"), "Failed to find method 'withoutAssert' annotated with @Test");
        Assertions.assertEquals(1, countOccurences.apply("AssertTestNGStyle.java", "withAsserts"), "Failed to find method 'withoutAssert' annotated with @Test");

        TestMethodContext withAssert = methods.stream().filter(
                f -> "withAsserts".equals(f.getMethodName()) && "AssertTestNGStyle.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(10, "assertTrue"))),
                "Failed to find assertTrue & assertArrayEquals in AssertTestNGStyle::withAsserts");

        TestMethodContext falseNegative = methods.stream().filter(
                f -> "falseNegative".equals(f.getMethodName()) && "AssertTestNGStyle.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(falseNegative.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(25, "assertTrue"))),
                "Failed to find assertTrue in AssertTestNGStyle::falseNegative");


        final BiFunction<Set<TestMethodContext>,String, Integer> assertsInMethod = (methodContexts, str) ->
                methodContexts.stream().filter(f -> str.equals(f.getMethodName()))
                        .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size();

        Assertions.assertEquals(0, assertsInMethod.apply(methods, "withoutAssert"));
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts"));
        Assertions.assertEquals(4, assertsInMethod.apply(methods, "build"));
        Assertions.assertEquals(2, assertsInMethod.apply(methods, "withAssert"));
        Assertions.assertEquals(9, assertsInMethod.apply(methods, "azzert"));
    }
}
