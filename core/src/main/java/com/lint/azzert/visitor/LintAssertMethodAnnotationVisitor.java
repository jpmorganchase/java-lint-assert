package com.lint.azzert.visitor;

import com.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    public LintAssertMethodAnnotationVisitor(Context context) {
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
