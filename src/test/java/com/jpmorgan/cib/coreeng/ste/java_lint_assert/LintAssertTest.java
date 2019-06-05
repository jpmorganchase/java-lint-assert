package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;

class LintAssertTest {

     @Test
     void _assert() throws IOException{
          InputStream inputStream = LintAssertTest.class.getResourceAsStream(
                  "/com/jpmorgan/cib/coreeng/ste/java_lint_assert/TestWithAsserts.class");

          final ClassVisitor classVisitor;
          classVisitor = new LintAssertClassVisitor(Opcodes.ASM7);

          ClassReader classReader = new ClassReader(inputStream);
          classReader.accept(classVisitor, 0);
     }
}
