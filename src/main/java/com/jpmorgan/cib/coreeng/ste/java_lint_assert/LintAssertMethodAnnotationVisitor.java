package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import org.objectweb.asm.AnnotationVisitor;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    private final LintAssertContext context;

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
