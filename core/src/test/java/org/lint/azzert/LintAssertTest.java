package org.lint.azzert;

import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.output.strategy.ToStringStrategy;
import org.lint.azzert.processor.ToStringAssertProcessor;

import java.util.Arrays;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


class LintAssertTest {


    final BiFunction<Set<MethodMetadata>,String, Integer> assertsInMethod = (methodContexts, str) ->
            methodContexts.stream().filter(f -> str.equals(f.getMethodName()))
                    .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size();

    @Test
    void assertJUnit5() throws Exception{
        final Set<MethodMetadata> methods = new ToStringAssertProcessor(
                null, Pair.with("org.lint.azzert.junit5", false)).process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(methods.size() > 0, "Expected to find at least one JUnit 5 test method.");

        MethodMetadata withAssert = methods.stream().filter(
                f -> "withAssert".equals(f.getMethodName()) && "AssertJunit5Style.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(18, "assertTrue"), new Pair<>(19, "assertArrayEquals"))),
                "Failed to find assertTrue & assertArrayEquals in AssertJunit5Style::withAssert");

        //        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
//                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();
//        Assertions.assertEquals(1, countOccurences.apply("AssertJunit5Style.java", "withoutAssert"), "Failed to find method 'withoutAssert' annotated with @Test");
//        Assertions.assertEquals(1, countOccurences.apply("AssertJunit5Style.java", "withAssert"), "Failed to find method 'withoutAssert' annotated with @Test");
//

    }

    @Test
    void assertJUnit4() throws Exception {

        final Set<MethodMetadata> methods = new ToStringAssertProcessor(
                null, Pair.with("org.lint.azzert.junit4", false)).process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(methods.size() > 0, "Expected to find at least one JUnit 4 test method.");
        Assertions.assertEquals(0, assertsInMethod.apply(methods, "withoutAsserts"));
        Assertions.assertEquals(1, assertsInMethod.apply(methods, "withAsserts"));


        Assertions.assertEquals(0, countMethodOccurrencesInFile(methods,"AssertJunit4Style.java", "disabledTest"),
                "AssertJunit4Style::disabledTest should've been excluded");

//        Assertions.assertEquals(4, assertsInMethod.apply(methods, "build"));
//        Assertions.assertEquals(2, assertsInMethod.apply(methods, "withAssert"));
//        Assertions.assertEquals(9, assertsInMethod.apply(methods, "azzert"));
    }

    int countMethodOccurrencesInFile(final Set<MethodMetadata> methods, String file, String method){
        final BiFunction<String, String, Long> countOccurences = (fileName, methodName)
                -> methods.stream().filter(m -> m.getFileName().equals(fileName) && m.getMethodName().equals(methodName)).count();

        return countOccurences.apply(file, method).intValue();
    }
}
