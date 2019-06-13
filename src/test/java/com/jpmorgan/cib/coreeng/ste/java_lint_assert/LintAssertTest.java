package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.TestMethodContext;
import com.jpmorgan.cib.coreeng.ste.java_lint_assert.visitor.LintAssertClassVisitor;
import org.javatuples.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

class LintAssertTest {

    @Test
    void _assert() throws IOException {
        final LintAssertContext ctx = new LintAssertContext(Opcodes.ASM7);

        InputStream inputStream = LintAssertTest.class.getResourceAsStream(
                "/com/jpmorgan/cib/coreeng/ste/java_lint_assert/SampleTest.class");

        final ClassVisitor classVisitor = new LintAssertClassVisitor(ctx);

        ClassReader classReader = new ClassReader(inputStream);
        classReader.accept(classVisitor, 0);

//          System.out.println(ctx.getTestMethodsContext());

        Assertions.assertEquals(2, ctx.getTestMethodsContext().size());
        Assertions.assertTrue(
                ctx.getTestMethodsContext().contains(
                        new TestMethodContext("withoutAssert", "Lorg/junit/jupiter/api/Test;")),
                "Expected to find method 'withoutAssert' annotated with @Test"
        );
        Assertions.assertTrue(ctx.getTestMethodsContext().contains(
                new TestMethodContext("withAssert", "Lorg/junit/jupiter/api/Test;")),
                "Expected to find method 'withAssert' annotated with @Test");

        TestMethodContext withAssert = ctx.getTestMethodsContext().stream().filter(
                f -> "withAssert".equals(f.getName())).collect(Collectors.toList()).get(0);

        Assertions.assertTrue(withAssert.getAssertMethodsAtLineNumbers().containsAll(
                Arrays.asList(new Pair<>(18, "assertTrue"), new Pair<>(19, "assertArrayEquals"))),
                "Expected to find 2 asserts in 'withAssert' method");
        Assertions.assertEquals(0, ctx.getTestMethodsContext().stream().filter(
                f -> "withoutAssert".equals(f.getName()))
                .collect(Collectors.toList()).get(0).getAssertMethodsAtLineNumbers().size());
    }
}
