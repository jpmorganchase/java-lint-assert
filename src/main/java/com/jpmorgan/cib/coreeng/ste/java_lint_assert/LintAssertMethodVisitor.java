package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodVisitor extends MethodVisitor {

    //FIXME::move to props file, create a collection of such package names
    public static final String ORG_JUNIT_JUPITER_API_ASSERTIONS = "org/junit/jupiter/api/Assertions";
    public static final String ORG_JUNIT_JUPITER_API_TEST = "Lorg/junit/jupiter/api/Test;";
    private final LintAssertContext context;

    private static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);
    private int atLineNumber;

    public LintAssertMethodVisitor(LintAssertContext ctx) {
        super(ctx.getAsmVersion());
        this.context = ctx;
        atLineNumber = 0;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (ORG_JUNIT_JUPITER_API_TEST.equals(descriptor)) {
            AnnotationVisitor av = new LintAssertMethodAnnotationVisitor(this.context);
            context.with(descriptor, visible);
            return av;
        }else
            return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        if (ORG_JUNIT_JUPITER_API_ASSERTIONS.equals(owner)) {
            this.context.recordAssert(atLineNumber, name);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);

        this.atLineNumber = line;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        if (ORG_JUNIT_JUPITER_API_TEST.equals(context.getDescriptor())) {
//            log.debug(this.context.toString());
            context.doneProcessingMethod();
        }
    }
}
