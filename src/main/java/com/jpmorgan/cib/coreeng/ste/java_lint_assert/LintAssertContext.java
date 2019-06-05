package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

public class LintAssertContext {

    final int asmVersion;

    LintAssertContext(int asmVersion){ this.asmVersion = asmVersion;}

    TestMethodContext testMethodContext;

    void setMethodContext(int access, String name, String desc, String signature, String[] exceptions) {
        this.testMethodContext = new TestMethodContextBuilder().create(access, name, desc, signature, exceptions);
    }
}
