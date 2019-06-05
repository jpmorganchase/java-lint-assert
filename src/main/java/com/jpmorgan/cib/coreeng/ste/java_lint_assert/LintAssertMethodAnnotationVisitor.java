package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    static Logger log = LoggerFactory.getLogger(LintAssertMethodAnnotationVisitor.class);

    final LintAssertContext context;

    public LintAssertMethodAnnotationVisitor(LintAssertContext context) {
        super(context.asmVersion);
        this.context = context;
    }

    @Override
    public void visit(String name, Object value) {
        log.debug("visit");
        super.visit(name, value);
    }

    @Override
    public void visitEnum(String name, String descriptor, String value) {
        log.debug("visitEnum");
        super.visitEnum(name, descriptor, value);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        log.debug("visitAnnotation");
        return super.visitAnnotation(name, descriptor);
    }

    @Override
    public AnnotationVisitor visitArray(String name) {
        log.debug("visitArray");
        return super.visitArray(name);
    }

    @Override
    public void visitEnd() {
        log.debug("visitEnd");
        super.visitEnd();
    }
}
