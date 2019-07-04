package com.lint.azzert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Context {

    private final int asmVersion;

    private Set<TestMethodContext> testMethodsContext;

    private TestMethodContext testMethodContext;

    private final Set<String> assertApis;
    private final Set<String> testFrameworks;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        testMethodContext = new TestMethodContext();
        testMethodsContext = new HashSet<>();
        assertApis = new HashSet<>();
        testFrameworks = new HashSet<>();
    }

    public void addSupportedAssertApis(Collection<String> assertApis) {

        this.assertApis.addAll(assertApis);
    }

    public void addSupportedTestFrameworks(Collection<String> testFrameworks) {
        this.testFrameworks.addAll(testFrameworks);
    }

    public Set<String> getSupportedAssertApis() {
        return this.assertApis;
    }

    public Set<String> getSupportedTestFrameworks() {
        return this.testFrameworks;
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
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

    public Set<TestMethodContext> getTestMethodsContext() {
        return Collections.unmodifiableSet(testMethodsContext);
    }

    public void setFileName(String fileName) {
        this.testMethodContext.fileName = fileName;
    }

    public void setPackageName(String packageName) {
        this.testMethodContext.packageName = packageName;
    }

    public void setClassName(String className) {
        this.testMethodContext.className = className;
    }

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", testMethodsContext=" + testMethodsContext +
                ", testMethodContext=" + testMethodContext +
                '}';
    }

}

