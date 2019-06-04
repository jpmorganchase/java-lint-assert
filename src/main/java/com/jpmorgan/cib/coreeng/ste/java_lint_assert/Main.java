package com.jpmorgan.cib.coreeng.ste.java_lint_assert;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class Main {

     static Logger log = LoggerFactory.getLogger(Main.class);

     public static void main(String[] args) throws IOException{
          InputStream inputStream = Main.class.getResourceAsStream(
                  "/com/jpmorgan/cib/coreeng/ste/java_lint_assert/TestWithAsserts.class");

          final ClassVisitor classVisitor;
          classVisitor = new LintAssertClassVisitor(Opcodes.ASM7);

          ClassReader classReader = new ClassReader(inputStream);
          classReader.accept(classVisitor, 0);
     }
}
