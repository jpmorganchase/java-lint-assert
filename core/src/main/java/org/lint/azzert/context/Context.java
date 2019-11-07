package org.lint.azzert.context;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.strategy.framework.NoOpStrategy;

import java.util.*;
import java.util.function.Function;

public class Context {

    private final int asmVersion;
    private final MethodMetadata methodInFlight;
    private final Set<MethodMetadata> methods;
    private final TreeMap<String, TestFrameworkStrategy> testFrameworks;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        methodInFlight = new MethodMetadata();
        methods = new LinkedHashSet<>();
        testFrameworks = new TreeMap<>();
    }

    public void recordMethodCall(String owningClass, String assertMethodName, int atLineNumber) {
        this.methodInFlight.addMethodCall(new MethodCallMetadata(owningClass, assertMethodName, atLineNumber));
    }

    public void resetCurrentMethodContext() {
        //FIXME::setting the framework should be done at the class level
        final Function<Set<AnnotationMetadata>, TestFrameworkStrategy> getTestFrameworkStrategy = ants -> {
            TestFrameworkStrategy strategy;
            for (AnnotationMetadata ann : ants) {
                strategy = this.testFrameworks.get(ann.getAnnotationName());
                if (strategy != null)
                    return strategy;
            }
            return new NoOpStrategy();
        };
        this.methodInFlight.setTestFramework(getTestFrameworkStrategy.apply(this.methodInFlight.getAnnotations()));

        MethodMetadata clone = new MethodMetadata(this.methodInFlight);
        this.methods.add(clone);
        this.methodInFlight.resetMethodDetails();
    }

    public void resetCurrentClassContext() {
        this.methodInFlight.resetClassDetails();
    }

    public void withAnnotation(String annotation, boolean isAvailableForClientUse) {
        this.methodInFlight.getAnnotations().add(new AnnotationMetadata(annotation));
        this.methodInFlight.setVisible(isAvailableForClientUse);
    }

    public void addSupportedTestFrameworks(Collection<TestFrameworkStrategy> fwks) {
        for (TestFrameworkStrategy strategy: new HashSet<>(fwks)){
            this.testFrameworks.put(strategy.getSupportedFramework(), strategy);
        }
    }

    public Map<String, TestFrameworkStrategy> getSupportedTestFrameworks() { return Collections.unmodifiableMap(this.testFrameworks); }

    public int getAsmVersion() { return this.asmVersion; }

    public Set<MethodMetadata> getMethods() { return methods; }

    public MethodMetadata getMethodInFlight(){return this.methodInFlight;}

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", methodInFlight=" + methodInFlight +
                ", methods=" + methods +
                ", testFrameworks=" + testFrameworks +
                '}';
    }

}

