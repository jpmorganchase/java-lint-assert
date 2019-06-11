package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.AnnotationVisitor;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    final LintAssertContext context;

    public LintAssertMethodAnnotationVisitor(LintAssertContext context) {
        super(context.getAsmVersion());
        this.context = context;
    }

    @Override
    public void visit(String name, Object value) {
        super.visit(name, value);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
