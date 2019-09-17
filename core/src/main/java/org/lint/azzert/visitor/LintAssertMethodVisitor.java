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
        context.with(annotation, isMethodVisible);
        return super.visitAnnotation(annotation, isMethodVisible);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        this.context.recordAssert(atLineNumber, name);
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
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
