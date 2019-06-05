package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodVisitor extends MethodVisitor {

    static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);


    public LintAssertMethodVisitor(MethodVisitor mv, LintAssertContext context) {
        super(context.asmVersion, mv);
        log.debug("context=" + context);
    }

    @Override
    public AnnotationVisitor visitAnnotationDefault() {
        log.debug("visitAnnotationDefault");
        return super.visitAnnotationDefault();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        log.debug("visitAnnotation");
        return super.visitAnnotation(descriptor, visible);
    }
}
