package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodVisitor extends MethodVisitor {

    //FIXME::move to props file, create a collection of such package names
    public static final String PACKAGE_ORG_JUNIT_JUPITER_API_ASSERTIONS = "org/junit/jupiter/api/Assertions";

    private final LintAssertContext context;

    private static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);
    private int atLineNumber;

    public LintAssertMethodVisitor(LintAssertContext ctx) {
        super(ctx.asmVersion);
        this.context = ctx;
        atLineNumber = 0;
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        return super.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        super.visitAnnotation(descriptor, visible);
        context.with(descriptor, visible);

        return new LintAssertMethodAnnotationVisitor(this.context);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        if (PACKAGE_ORG_JUNIT_JUPITER_API_ASSERTIONS.equals(owner)) {
            log.debug(this.context.toString());
            this.context.testMethodContext.recordAssert(atLineNumber, name);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
            log.debug("testMethodContext=" +  this.context.testMethodContext);
        }
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);

        this.atLineNumber = line;
    }
}
