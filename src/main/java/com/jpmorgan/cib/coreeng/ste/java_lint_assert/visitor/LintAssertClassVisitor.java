package com.jpmorgan.cib.coreeng.ste.java_lint_assert.visitor;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.context.LintAssertContext;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LintAssertClassVisitor extends ClassVisitor {

    private static Logger log = LoggerFactory.getLogger(LintAssertClassVisitor.class);

    private final LintAssertContext ctx;

    public LintAssertClassVisitor(final LintAssertContext ctx) {
        super(ctx.getAsmVersion());
        this.ctx = ctx;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        super.visitMethod(this.ctx.getAsmVersion(), name, desc, signature, exceptions);

        //log.debug("visitMethod= " + name);

        this.ctx.setMethodName(name);
        this.ctx.setMethodSignature(signature);

        return new LintAssertMethodVisitor(ctx);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
//        log.debug("name=" + name +", signature=" + signature + ", superName=" + superName);
        int split = name.lastIndexOf('/');
        this.ctx.setPackageName(name.substring(0, split));
        this.ctx.setClassName(name.substring(split));
    }

    @Override
    public void visitSource(String source, String debug) {
        //log.debug("visitSource=" + source);
        this.ctx.setFileName(source);
        super.visitSource(source, debug);
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
        ctx.resetCurrentClassContext();
    }
}
