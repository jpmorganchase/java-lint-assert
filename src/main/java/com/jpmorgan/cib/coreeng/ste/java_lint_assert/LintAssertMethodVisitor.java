package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodVisitor extends MethodVisitor {

    static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);
    private final LintAssertContext context;

    public LintAssertMethodVisitor(LintAssertContext ctx) {
        super(ctx.asmVersion);

        this.context = ctx;

    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        log.debug("visitAnnotationDefault");
        return super.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {

        super.visitAnnotation(descriptor, visible);
        context.with(descriptor, visible);

        log.debug("context=" + this.context + "descriptor=" + descriptor);
        return new LintAssertMethodAnnotationVisitor(this.context);
    }
}
