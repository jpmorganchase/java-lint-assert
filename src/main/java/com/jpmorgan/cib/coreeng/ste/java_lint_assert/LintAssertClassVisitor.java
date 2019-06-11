package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LintAssertClassVisitor extends ClassVisitor {

    protected final LintAssertContext ctx;

    public LintAssertClassVisitor(final LintAssertContext ctx) {
        super(ctx.asmVersion);
        this.ctx = ctx;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        super.visitMethod(this.ctx.asmVersion, name, desc, signature, exceptions);
        ctx.setMethodContext(access, name, desc, signature, exceptions);

        return new LintAssertMethodVisitor(ctx);
    }
}
