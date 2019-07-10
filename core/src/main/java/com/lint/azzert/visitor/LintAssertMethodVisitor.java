package com.lint.azzert.visitor;

import com.lint.azzert.context.Context;
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
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        if (this.context.getSupportedTestFrameworks().contains(descriptor)) {
            AnnotationVisitor av = new LintAssertMethodAnnotationVisitor(this.context);
            context.with(descriptor, visible);
            return av;
        }else
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
        if (this.context.getSupportedTestFrameworks().contains(context.getDescriptor())) {
            context.resetCurrentMethodContext();
        }
    }
}
