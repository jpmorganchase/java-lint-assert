package com.jpmorgan.java.lint.azzert;

import com.jpmorgan.java.lint.azzert.context.Context;
import com.jpmorgan.java.lint.azzert.context.ContextBuilder;
import com.jpmorgan.java.lint.azzert.context.TestMethodContext;
import com.jpmorgan.java.lint.azzert.strategy.ConsoleOutputStrategy;
import com.jpmorgan.java.lint.azzert.util.TestClassFinder;
import com.jpmorgan.java.lint.azzert.visitor.LintAssertClassVisitor;
import org.javatuples.Pair;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


class LintAssertTest {

    @Test
    void azzert() throws IOException, ParseException {

        Context ctx = new ContextBuilder().build();

        final ClassVisitor classVisitor = new LintAssertClassVisitor(ctx);

        List<URL> classes = new TestClassFinder().getClasses(this.getClass().getPackage().getName());
        for (URL url : classes) {
            ClassReader classReader = new ClassReader(url.openStream());
            classReader.accept(classVisitor, 0);
        }

        System.out.println(new ConsoleOutputStrategy(ctx.getMethodContexts()).render());

        Assertions.assertTrue(ctx.getMethodContexts().size() > 0, "Expected to find at least one test method.");
        Assertions.assertTrue(
                ctx.getMethodContexts().contains(
                        new TestMethodContext("withoutAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withoutAssert' annotated with @Test"
        );
        Assertions.assertTrue(ctx.getMethodContexts().contains(
                new TestMethodContext("withAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withAssert' annotated with @Test");

        TestMethodContext withAssert = ctx.getMethodContexts().stream().filter(
                f -> "withAssert".equals(f.getMethodName()) && "AssertJunit5Style.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(18, "assertTrue"), new Pair<>(19, "assertArrayEquals"))),
                "Failed to find assertTrue & assertArrayEquals in AssertJunit5Style::withAssert");

        final BiFunction<Context,String, Integer> assertsInMethod = (ctx1, str) -> ctx1.getMethodContexts().stream().filter(
                f -> str.equals(f.getMethodName()))
                .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size();

        Assertions.assertEquals(0, assertsInMethod.apply(ctx, "withoutAssert"));
        Assertions.assertEquals(1, assertsInMethod.apply(ctx, "withAsserts"));
        Assertions.assertEquals(4, assertsInMethod.apply(ctx, "build"));
        Assertions.assertEquals(2, assertsInMethod.apply(ctx, "withAssert"));
        Assertions.assertEquals(9, assertsInMethod.apply(ctx, "azzert"));
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
