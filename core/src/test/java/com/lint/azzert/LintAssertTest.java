package com.lint.azzert;

import com.lint.azzert.context.Context;
import com.lint.azzert.context.TestMethodContext;
import com.lint.azzert.strategy.ConsoleOutputStrategy;
import com.lint.azzert.util.TestClassFinder;
import com.lint.azzert.visitor.LintAssertClassVisitor;
import org.javatuples.Pair;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


class LintAssertTest {

    @Test
    void azzert() throws IOException, ParseException {

        Context ctx = new ContextBuilder().build();

        final ClassVisitor classVisitor = new LintAssertClassVisitor(ctx);

        List<String> classes = TestClassFinder.getClasses(this.getClass().getPackage().getName());
        for (String classPath : classes) {
            ClassReader classReader = new ClassReader(classPath);
            classReader.accept(classVisitor, 0);
        }

        String output = new ConsoleOutputStrategy(ctx.getTestMethodsContext()).render();
        System.out.println(output);

        Assertions.assertTrue(ctx.getTestMethodsContext().size() > 0, "Expected to find at least one test method.");
        Assertions.assertTrue(
                ctx.getTestMethodsContext().contains(
                        new TestMethodContext("withoutAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withoutAssert' annotated with @Test"
        );
        Assertions.assertTrue(ctx.getTestMethodsContext().contains(
                new TestMethodContext("withAssert", "Lorg/junit/jupiter/api/Test;")),
                "Failed to find method 'withAssert' annotated with @Test");

        TestMethodContext withAssert = ctx.getTestMethodsContext().stream().filter(
                f -> "withAssert".equals(f.getMethodName()) && "AssertJunit5Style.java".equals(f.getFileName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(18, "assertTrue"), new Pair<>(19, "assertArrayEquals"))),
                "Failed to find 2 asserts in AssertJunit5Style::withAssert");

        Assertions.assertEquals(0, ctx.getTestMethodsContext().stream().filter(
                f -> "withoutAssert".equals(f.getMethodName()))
                .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size());
    }

}
