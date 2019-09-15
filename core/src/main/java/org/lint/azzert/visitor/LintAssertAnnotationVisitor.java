package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;

public class LintAssertAnnotationVisitor extends AnnotationVisitor {

    private final Context ctx;

    public LintAssertAnnotationVisitor(final Context ctx) {
        super(ctx.getAsmVersion());
        this.ctx = ctx;
    }

    @Override
    public void visit(String name, Object value) {
        if (this.ctx.getSupportedParameters().contains(name)) {
            if (!Boolean.valueOf(value.toString())) {
                this.ctx.getCurrentMethodContext().setIgnored(Boolean.TRUE);
            }
        }
        super.visit(name, value);
    }

}
