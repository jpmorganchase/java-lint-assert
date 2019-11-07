package org.lint.azzert.visitor;

import org.lint.azzert.context.Context;
import org.lint.azzert.context.MethodMetadata;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LintAssertClassVisitor extends ClassVisitor {

    private final MethodMetadata currentState;
    private final Context context;

    public LintAssertClassVisitor(final Context ctx) {
        super(ctx.getAsmVersion());
        this.context = ctx;
        this.currentState = ctx.getMethodInFlight();
    }


    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String annotation, boolean isClassVisible) {
        super.visitAnnotation(annotation, isClassVisible);
        context.getMethodInFlight().inClassAnnotatedWith(annotation, isClassVisible);
        return new LintAssertClassAnnotationVisitor(context);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        super.visitMethod(this.context.getAsmVersion(), name, desc, signature, exceptions);
        this.currentState.setMethodName(name);
        this.currentState.setMethodSignature(signature);

        return new LintAssertMethodVisitor(context);
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
        context.resetCurrentClassContext();
        super.visitEnd();
    }
}
