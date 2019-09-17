package org.lint.azzert.context;

import org.javatuples.Pair;
import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.framework.strategy.NoOpStrategy;

import java.util.*;
import java.util.function.Function;

public class Context {

    private final int asmVersion;

    private final MethodMetadata methodInFlight;
    private final Set<MethodMetadata> methodContexts;
    private final TreeMap<String, TestFrameworkStrategy> testFrameworks;

    public Context(int asmVersion) {
        this.asmVersion = asmVersion;
        methodInFlight = new MethodMetadata();
        methodContexts = new HashSet<>();
        testFrameworks = new TreeMap<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        this.methodInFlight.addAssertMethodsAtLineNumbers(new Pair<>(atLineNumber, assertMethodName));
    }

    public void resetCurrentMethodContext() {
        MethodMetadata clone = new MethodMetadata(this.methodInFlight);
        this.methodContexts.add(clone);
        this.methodInFlight.resetMethodDetails();
    }

    public void resetCurrentClassContext() {
        this.methodInFlight.resetClassDetails();
    }

    public void with(String annotation, boolean isMethodVisible) {
        final Function<Set<String>, TestFrameworkStrategy> getTestFrameworkStrategy = (ants) -> {

            TestFrameworkStrategy strategy;
            for (String ann : ants) {
                strategy = this.testFrameworks.get(ann);
                if (strategy != null)
                    return strategy;
            }
            return new NoOpStrategy();
        };

        this.methodInFlight.getAnnotations().add(annotation);
        this.methodInFlight.setVisible(isMethodVisible);
        this.methodInFlight.setTestFramework(getTestFrameworkStrategy.apply(this.methodInFlight.getAnnotations()));
    }

    public void addSupportedTestFrameworks(Collection<TestFrameworkStrategy> fwks) {
        final Set<TestFrameworkStrategy> testFrameworks = new HashSet<>(fwks);

        for (TestFrameworkStrategy strategy: testFrameworks){
            this.testFrameworks.put(strategy.getSupportedFramework(), strategy);
        }
    }

    public Map<String, TestFrameworkStrategy> getSupportedTestFrameworks() { return Collections.unmodifiableMap(this.testFrameworks); }

    public int getAsmVersion() { return this.asmVersion; }

    public Set<MethodMetadata> getMethodContexts() { return methodContexts; }

    public MethodMetadata getMethodInFlight(){return this.methodInFlight;}

    @Override
    public String toString() {
        return "Context{" +
                "asmVersion=" + asmVersion +
                ", methodInFlight=" + methodInFlight +
                ", methodContexts=" + methodContexts +
                ", testFrameworks=" + testFrameworks +
                '}';
    }
}

