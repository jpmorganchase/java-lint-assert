package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertMethodVisitor extends MethodVisitor {

    static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);

    final String methodName;

    public LintAssertMethodVisitor(int version, MethodVisitor mv, String methodName) {
        super(version, mv);
        this.methodName = methodName;
        log.debug("version=" + version +", mv=" + mv + ", methodName=" + methodName);
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
