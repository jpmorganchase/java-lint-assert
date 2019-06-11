package com.jpmorgan.cib.coreeng.ste.java_lint_assert.context;

import org.javatuples.Pair;

import java.util.HashSet;
import java.util.Set;

public class LintAssertContext {

    private final int asmVersion;

    private final Set<TestMethodContext> testMethodsContext;

    private  TestMethodContext testMethodContext;

    public LintAssertContext(int asmVersion) {
        this.asmVersion = asmVersion;
        testMethodContext = new TestMethodContext();
        testMethodsContext = new HashSet<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        this.testMethodContext.assertMethodsAtLineNumbers.add(new Pair<>(atLineNumber, assertMethodName));
    }

    public void doneProcessingMethod() {
        this.testMethodsContext.add(this.testMethodContext);
        this.testMethodContext = new TestMethodContext();
    }

    public void with(String descriptor, boolean visible) {
        this.testMethodContext.descriptor = descriptor;
        this.testMethodContext.visible = visible;
    }

    @Override
    public String toString() {
        return "LintAssertContext{" +
                "asmVersion=" + asmVersion +
                ", testMethodsContext=" + testMethodsContext +
                ", testMethodContext=" + testMethodContext +
                '}';
    }

    public void setName(String name) {
        this.testMethodContext.name = name;
    }

    public void setSignature(String signature) {
        this.testMethodContext.signature = signature;
    }

    public int getAsmVersion() {
        return this.asmVersion;
    }

    public String getDescriptor() {
        return this.testMethodContext.descriptor;
    }

    public Set<TestMethodContext> getTestMethodsContext(){return testMethodsContext;}
}

