package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LintAssertClassVisitor extends ClassVisitor {

    private final MethodMetadata currentState;
    private final Context ctx;

    private String inFlightClassAnnotation;

    public LintAssertClassVisitor(final Context ctx) {
        super(ctx.getAsmVersion());
        this.ctx = ctx;
        this.currentState = ctx.getMethodInFlight();
    }

    @Override
    public AnnotationVisitor visitAnnotation(String annotation, boolean visible) {
        this.inFlightClassAnnotation = annotation;
        return super.visitAnnotation(this.inFlightClassAnnotation, visible);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        super.visitMethod(this.ctx.getAsmVersion(), name, desc, signature, exceptions);
        this.currentState.setMethodName(name);
        this.currentState.setMethodSignature(signature);

        if (inFlightClassAnnotation != null) ctx.with(this.inFlightClassAnnotation, false);

        return new LintAssertMethodVisitor(ctx);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        int split = name.lastIndexOf('/');
        this.currentState.setPackageName(name.substring(0, split));
        this.currentState.setClassName(name.substring(split));
    }

    @Override
    public void visitSource(String source, String debug) {
        this.currentState.setFileName(source);
        super.visitSource(source, debug);
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
        ctx.resetCurrentClassContext();
    }
}
