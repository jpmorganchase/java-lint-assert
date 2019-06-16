package com.jpmorgan.cib.coreeng.ste.java_lint_assert.context;

import com.jpmorgan.cib.coreeng.ste.java_lint_assert.visitor.LintAssertMethodVisitor;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class LintAssertContext {

    private final int asmVersion;

    private Set<TestMethodContext> testMethodsContext;

    private  TestMethodContext testMethodContext;

    private static Logger log = LoggerFactory.getLogger(LintAssertMethodVisitor.class);

    public LintAssertContext(int asmVersion) {
        this.asmVersion = asmVersion;
        testMethodContext = new TestMethodContext();
        testMethodsContext = new HashSet<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        log.debug(this.testMethodContext.fileName + "==" + this.testMethodContext.methodName + ", assertMethodName=" + assertMethodName);

        this.testMethodContext.assertMethodsAtLineNumbers.add(new Pair<>(atLineNumber, assertMethodName));
    }

    public void resetCurrentMethodContext() {
        TestMethodContext clone = new TestMethodContext(this.testMethodContext);
        this.testMethodsContext.add(clone);
        this.testMethodContext.resetMethodDetails();
    }

    public void resetCurrentClassContext() {
        this.testMethodContext.resetClassDetails();
    }

    public void with(String descriptor, boolean visible) {
        this.testMethodContext.methodDescriptor = descriptor;
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

    public void setMethodName(String name) {
        this.testMethodContext.methodName = name;
    }

    public void setMethodSignature(String signature) {
        this.testMethodContext.methodSignature = signature;
    }

    public int getAsmVersion() {
        return this.asmVersion;
    }

    public String getDescriptor() {
        return this.testMethodContext.methodDescriptor;
    }

    public Set<TestMethodContext> getTestMethodsContext(){return testMethodsContext;}

    public void setFileName(String fileName){this.testMethodContext.fileName = fileName;}

}

