package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

public class LintAssertMethodVisitor extends MethodVisitor {

    private final Context context;

    private int atLineNumber;

    public LintAssertMethodVisitor(Context ctx) {
        super(ctx.getAsmVersion());
        this.context = ctx;
        atLineNumber = 0;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String annotation, boolean isMethodVisible) {
        super.visitAnnotation(annotation, isMethodVisible);
        context.with(annotation, isMethodVisible);

        return new LintAssertAnnotationVisitor(context);
    }

    @Override
    public void visitMethodInsn(int opcode, String definingClass, String methodName, String descriptor, boolean isInterface) {
        this.context.recordMethodCall(definingClass, methodName, atLineNumber);
        super.visitMethodInsn(opcode, definingClass, methodName, descriptor, isInterface);

    }

    @Override
    public void visitLineNumber(int line, Label start) {
        super.visitLineNumber(line, start);
        this.atLineNumber = line;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        context.resetCurrentMethodContext();
    }
}
