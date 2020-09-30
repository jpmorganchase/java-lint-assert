package org.lint.azzert.context;

import org.lint.azzert.TestFrameworkStrategy;

import java.util.*;

public class Context {

    private final int asmVersion;
    private final Set<MethodMetadata> methods;
    private final TreeMap<String, TestFrameworkStrategy> testFrameworks;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        this.testFrameworks = new TreeMap<>();
        this.methods = new LinkedHashSet<>();
        this.methods.add(new MethodMetadata());
    }

    public void recordMethodCall(String owningClass, String assertMethodName, int atLineNumber) {
        MethodMetadata method = getMethodInFlight();
        method.addMethodCall(new MethodCallMetadata(owningClass, assertMethodName, atLineNumber));
    }

    public MethodMetadata getMethodInFlight() {
        return (MethodMetadata) methods.toArray()[methods.size() - 1];
    }

    public void resetCurrentMethodContext() {
        MethodMetadata method = getMethodInFlight();
        method.seedTestFramework(getSupportedTestFrameworks());

        MethodMetadata next = new MethodMetadata();
        this.methods.add(next);

        next.setFileName(method.getFileName());
        next.setPackageName(method.getPackageName());
        next.setClassName(method.getClassName());
        next.setClassMetadata(method.getClassMetadata());;
    }

    public void withAnnotation(String annotation, boolean isAvailableForClientUse) {
        MethodMetadata method = getMethodInFlight();
        method.getAnnotations().add(new AnnotationMetadata(annotation));
        method.setVisible(isAvailableForClientUse);
    }

    public void addSupportedTestFrameworks(Collection<TestFrameworkStrategy> fwks) {
        for (TestFrameworkStrategy strategy: new HashSet<>(fwks)){
            this.testFrameworks.put(strategy.getSupportedFramework(), strategy);
        }
    }

    public Map<String, TestFrameworkStrategy> getSupportedTestFrameworks() {
        return Collections.unmodifiableMap(this.testFrameworks);
    }

    public int getAsmVersion() {
        return this.asmVersion;
    }

    public Set<MethodMetadata> getMethods() {
        return methods;
    }

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", methodInFlight=" + getMethodInFlight() +
                ", methods=" + methods +
                ", testFrameworks=" + testFrameworks +
                '}';
    }

}

