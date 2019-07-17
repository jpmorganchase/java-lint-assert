package com.jpmorgan.cib.coreeng.ste.java_lint_assert.visitor;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import org.objectweb.asm.AnnotationVisitor;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    public LintAssertMethodAnnotationVisitor(LintAssertContext context) {
        super(context.getAsmVersion());
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
