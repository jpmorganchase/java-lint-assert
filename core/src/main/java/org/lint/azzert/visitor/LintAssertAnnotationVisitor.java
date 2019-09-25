package org.lint.azzert.visitor;

import org.lint.azzert.context.AnnotationMetadata;
import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;

import java.util.Set;

public class LintAssertAnnotationVisitor extends AnnotationVisitor {

    private final Context ctx;

    public LintAssertAnnotationVisitor(Context context) {
        super(context.getAsmVersion());
        this.ctx = context;
    }

    @Override
    public void visit(String paramName, Object paramValue) {
        Set<AnnotationMetadata> annotations = ctx.getMethodInFlight().getAnnotations();
        AnnotationMetadata annotation = (AnnotationMetadata) annotations.toArray()[annotations.size() -1];
        annotation.addParameter(paramName, paramValue);
//        annotations.add(annotation);

        System.out.println(ctx.getMethodInFlight());
        super.visit(paramName, paramValue);
    }

}
