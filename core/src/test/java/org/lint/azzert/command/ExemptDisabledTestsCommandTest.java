package org.lint.azzert.command;

import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.lint.azzert.context.MethodMetadata;
import org.lint.azzert.output.strategy.ToStringStrategy;
import org.lint.azzert.processor.ToStringAssertProcessor;

import java.util.Set;
import java.util.function.Function;

public class ExemptDisabledTestsCommandTest {

    @Test
    void test() throws Exception{
        Set<MethodMetadata> methods = new ToStringAssertProcessor(
                null, Pair.with(ExemptDisabledTestsCommandTest.class.getPackage().getName(), false)
        ).process();

        System.out.println(new ToStringStrategy(methods).render());
        methods.forEach(System.out::println);

        final Function<String, Long> assertsInMethod = str -> methods.stream().filter(m-> m.getMethodName().equals(str)
                && m.getFileName().contains("ExemptDisabledTestsCommandTest")).count();

        Assertions.assertEquals(0, assertsInMethod.apply("youWouldThinkIAmATestButIAmNot"), "Should've excluded 'youWouldThinkIAmATestButIAmNot' annotated *only* with @Disabled");
        Assertions.assertEquals(0, assertsInMethod.apply("iAmDisabled"), "Should've excluded 'iAmDisabled' annotated with @Disabled");
        Assertions.assertEquals(1, assertsInMethod.apply("test"), "Should've included 'test' annotated with @Test");
    }
}
