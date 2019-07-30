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

        Set<TestMethodContext> methods = new ToStringAssertProcessor().process();

        System.out.println(new ToStringStrategy(methods).render());

        Assertions.assertTrue(methods.size() > 0, "Expected to find at least one test method.");
        Assertions.assertTrue(
                methods.contains(
                        new TestMethodContext("withoutAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withoutAssert' annotated with @Test"
        );
        Assertions.assertTrue(methods.contains(
                new TestMethodContext("withAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withAssert' annotated with @Test");

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

    //TODO::handle methods in nested classes
    class InnerClass {
        @Test
        void dummy() {

        }
    }

    //TODO::handle @Ignore and @Disabled methods
    @org.junit.Ignore
    @org.junit.jupiter.api.Disabled
    void ignoredOrdisabled(){

    }

    //TODO::handle @Ignore and @Disabled at the class level
    @org.junit.Ignore
    @org.junit.jupiter.api.Disabled
    class Disabled {
        @Test
        void dummy() {

        }
    }

}
