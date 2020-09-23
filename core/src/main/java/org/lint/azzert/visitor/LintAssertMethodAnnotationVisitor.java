package org.lint.azzert.visitor;

import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;

import java.util.Set;

public class LintAssertMethodAnnotationVisitor extends AnnotationVisitor {

    private final Context context;

    public LintAssertMethodAnnotationVisitor(Context context) {
        super(context.getAsmVersion());
        this.context = context;
    }

    @Override
    public void visit(String paramName, Object paramValue) {
        System.out.println("paramName=" + paramName +", paramValue=" + paramValue);
        Set<AnnotationMetadata> annotations = context.getMethodInFlight().getAnnotations();
        ((AnnotationMetadata) annotations.toArray()[annotations.size() - 1]).addParameter(paramName, paramValue);

        super.visit(paramName, paramValue);
    }
}
