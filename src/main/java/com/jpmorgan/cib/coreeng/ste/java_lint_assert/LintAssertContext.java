package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

public class LintAssertContext {

    final int asmVersion;

    LintAssertContext(int asmVersion){
        this.asmVersion = asmVersion;
    }

    TestMethodContext testMethodContext;

    void setMethodContext(int access, String name, String desc, String signature, String[] exceptions) {
        this.testMethodContext = new TestMethodContextBuilder().create(name, signature, exceptions);
    }

    @Override
    public String toString() {
        return "LintAssertContext{" +
                "asmVersion=" + asmVersion +
                ", testMethodContext=" + testMethodContext +
                '}';
    }

    public void with(String descriptor, boolean visible) {
        this.testMethodContext.descriptor = descriptor;
        testMethodContext.visible = visible;
    }

}
