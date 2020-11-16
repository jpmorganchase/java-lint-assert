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
    //Is called for JUnit4's expected exception i.e. @Test(expected=NullPointerException.class)
    public void visit(String paramName, Object paramValue) {
        Set<AnnotationMetadata> annotations = context.getMethodInFlight().getAnnotations();
        ((AnnotationMetadata) annotations.toArray()[annotations.size() - 1]).addParameter(paramName, paramValue);

        super.visit(paramName, paramValue);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String name, String descriptor) {
        return super.visitAnnotation(name, descriptor);
    }

    @Override
    //Is called for testNG's expected exceptions  @Test(expectedExceptions = {NullPointerException.class})
    public AnnotationVisitor visitArray(String paramName) {
        Set<AnnotationMetadata> annotations = context.getMethodInFlight().getAnnotations();
        ((AnnotationMetadata) annotations.toArray()[annotations.size() - 1]).addParameter(paramName, null);

        return super.visitArray(paramName);
    }

}
