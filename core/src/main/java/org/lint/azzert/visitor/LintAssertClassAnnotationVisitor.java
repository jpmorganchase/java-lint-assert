package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;

public class LintAssertClassAnnotationVisitor extends AnnotationVisitor {

    private final Context context;

    public LintAssertClassAnnotationVisitor(Context context) {
        super(context.getAsmVersion());
        this.context = context;
   }

    @Override
    public void visit(String paramName, Object paramValue) {
        context.getMethodInFlight().getClassMetadata().updateLastAddedAnnotation(paramName, paramValue);
        super.visit(paramName, paramValue);
    }
}
