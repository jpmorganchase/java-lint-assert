package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.util.Collections;

public class LintAssertMethodVisitor extends MethodVisitor {

    private final Context context;

    private int atLineNumber;

    public LintAssertMethodVisitor(Context ctx) {
        super(ctx.getAsmVersion());
        this.context = ctx;
        atLineNumber = 0;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (this.context.getAcceptableMethodAnnotations().contains(descriptor)){
            context.with(descriptor, visible);
            return new LintAssertAnnotationVisitor(context);
        }
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        if (this.context.getSupportedAssertApis().contains(owner)) {
            this.context.recordAssert(atLineNumber, name);
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
        this.atLineNumber = line;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();

        if ( ! Collections.disjoint(this.context.getAcceptableMethodAnnotations(), context.getCurrentMethodContext().getAnnotations())) {
            context.resetCurrentMethodContext();
        }
    }

    @Override
    public AnnotationVisitor visitParameterAnnotation(int parameter, String descriptor, boolean visible) {
        return super.visitParameterAnnotation(parameter, descriptor, visible);
    }
}
