package com.jpmorgan.java.lint.azzert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Context {

    private final int asmVersion;

    private TestMethodContext currentMethodContext;
    private Set<TestMethodContext> methodContexts;
    private final Set<String> assertApis;
    private final Set<String> testFrameworks;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        currentMethodContext = new TestMethodContext();
        methodContexts = new HashSet<>();
        assertApis = new HashSet<>();
        testFrameworks = new HashSet<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        this.currentMethodContext.assertMethodsAtLineNumbers.add(new Pair<>(atLineNumber, assertMethodName));
    }

    public void resetCurrentMethodContext() {
        TestMethodContext clone = new TestMethodContext(this.currentMethodContext);
        this.methodContexts.add(clone);
        this.currentMethodContext.resetMethodDetails();
    }

    public void resetCurrentClassContext() {
        this.currentMethodContext.resetClassDetails();
    }

    public void with(String descriptor, boolean visible) {
        this.currentMethodContext.methodDescriptor = descriptor;
        this.currentMethodContext.visible = visible;
    }

    public void addSupportedAssertApis(Collection<String> assertApis) { this.assertApis.addAll(assertApis); }

    public void addSupportedTestFrameworks(Collection<String> testFrameworks) { this.testFrameworks.addAll(testFrameworks); }

    public Set<String> getSupportedAssertApis() {
        return this.assertApis;
    }

    public Set<String> getSupportedTestFrameworks() {
        return this.testFrameworks;
    }

    public void setMethodName(String name) {
        this.currentMethodContext.methodName = name;
    }

    public void setMethodSignature(String signature) {
        this.currentMethodContext.methodSignature = signature;
    }

    public int getAsmVersion() {
        return this.asmVersion;
    }

    public String getDescriptor() {
        return this.currentMethodContext.methodDescriptor;
    }

    public Set<TestMethodContext> getMethodContexts() { return Collections.unmodifiableSet(methodContexts); }

    public void setFileName(String fileName) {
        this.currentMethodContext.fileName = fileName;
    }

    public void setPackageName(String packageName) {
        this.currentMethodContext.packageName = packageName;
    }

    public void setClassName(String className) {
        this.currentMethodContext.className = className;
    }

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", methodContexts=" + methodContexts +
                ", currentMethodContext=" + currentMethodContext +
                '}';
    }

}

