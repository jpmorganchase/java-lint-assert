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
    //FIXME::refactor: {@see ClassMedatada}
    public void visit(String paramName, Object paramValue) {
        Set<AnnotationMetadata> annotations = context.getMethodInFlight().getAnnotations();
        AnnotationMetadata annotation = (AnnotationMetadata) annotations.toArray()[annotations.size() -1];
        annotation.addParameter(paramName, paramValue);

        super.visit(paramName, paramValue);
    }
}
