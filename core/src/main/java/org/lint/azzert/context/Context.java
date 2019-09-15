package org.lint.azzert.context;

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
    private final Set<String> exemptApis;
    private final Set<String> supportedParameters;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        currentMethodContext = new TestMethodContext();
        methodContexts = new HashSet<>();
        assertApis = new HashSet<>();
        testFrameworks = new HashSet<>();
        exemptApis = new HashSet<>();
        supportedParameters = new HashSet<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        this.currentMethodContext.addAssertMethodsAtLineNumbers(new Pair<>(atLineNumber, assertMethodName));
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
        this.currentMethodContext.getAnnotations().add(descriptor);
        this.currentMethodContext.setVisible(visible);
    }


    public Collection<?> getAcceptableMethodAnnotations() {
        Set<String> acceptableMethodAnnotations = new HashSet<>(this.testFrameworks);
        acceptableMethodAnnotations.addAll(exemptApis);

        return acceptableMethodAnnotations;
    }

    public void addSupportedAssertApis(Collection<String> assertApis) { this.assertApis.addAll(assertApis); }

    public void addSupportedTestFrameworks(Collection<String> testFrameworks) { this.testFrameworks.addAll(testFrameworks); }

    public void addSupportedExemptApis(Collection<String> exempts) {this.exemptApis.addAll(exempts);}

    public Set<String> getSupportedAssertApis() { return Collections.unmodifiableSet(this.assertApis); }

    public Set<String> getSupportedTestFrameworks() { return Collections.unmodifiableSet(this.testFrameworks); }

    public Set<String> getSupportedExemptApis(){return Collections.unmodifiableSet(this.exemptApis);}

    public int getAsmVersion() { return this.asmVersion; }

    public Set<TestMethodContext> getMethodContexts() { return methodContexts; }

    public TestMethodContext getCurrentMethodContext(){return this.currentMethodContext;}

    public void addSupportedParameters(Collection<String> parameters){this.supportedParameters.addAll(parameters);}

    public Set<String> getSupportedParameters(){return this.supportedParameters;}

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", methodContexts=" + methodContexts +
                ", currentMethodContext=" + currentMethodContext +
                '}';
    }

}

