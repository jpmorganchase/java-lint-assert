package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.TestMethodContext;
import com.jpmorgan.cib.coreeng.ste.java_lint_assert.util.TestClassFinder;
import com.jpmorgan.cib.coreeng.ste.java_lint_assert.visitor.LintAssertClassVisitor;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

class LintAssertTest {

    @Test
    void _assert() throws IOException {
        final LintAssertContext ctx = new LintAssertContext(Opcodes.ASM7);
        final ClassVisitor classVisitor = new LintAssertClassVisitor(ctx);

        ArrayList<File> classFiles = TestClassFinder.getClasses("com.jpmorgan.cib.coreeng.ste.java_lint_assert");
        for (File classFile : classFiles) {
            final String classPath = TestClassFinder.buildClassFilePath(classFile.getPath());
//            System.out.println(classPath);
            InputStream inputStream = LintAssertTest.class.getResourceAsStream(classPath);
            ClassReader classReader = new ClassReader(inputStream);
            classReader.accept(classVisitor, 0);
        }

        ctx.getTestMethodsContext().forEach(System.out::println);

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
