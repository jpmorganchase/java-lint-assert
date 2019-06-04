package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertClassVisitor extends ClassVisitor {

    static Logger log = LoggerFactory.getLogger(LintAssertClassVisitor.class);

    protected final int ASM_VERSION;

    public LintAssertClassVisitor(int version) {
        super(version);
        this.ASM_VERSION = version;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
        log.debug("descriptor=" + descriptor + ",visible=" + visible);
        return super.visitAnnotation(descriptor, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String descriptor, boolean visible) {
        log.debug("typeRef=" + typeRef + ", typePath=" + typePath + ", descriptor=" + descriptor + ",visible=" + visible);
        return super.visitTypeAnnotation(typeRef, typePath, descriptor, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        log.debug("visitField");
        return super.visitField(access,name,descriptor,signature,value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        log.debug("visitMethod");

        MethodVisitor mv = super.visitMethod(this.ASM_VERSION, name, desc, signature, exceptions);

        return new LintAssertMethodVisitor(this.ASM_VERSION, mv, name);
    }
}
