package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

public class LintAssertClassVisitor extends ClassVisitor {

    private final LintAssertContext ctx;

    public LintAssertClassVisitor(final LintAssertContext ctx) {
        super(ctx.getAsmVersion());
        this.ctx = ctx;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        super.visitMethod(this.ctx.getAsmVersion(), name, desc, signature, exceptions);
        this.ctx.setName(name);
        this.ctx.setSignature(signature);

        return new LintAssertMethodVisitor(ctx);
    }
}
