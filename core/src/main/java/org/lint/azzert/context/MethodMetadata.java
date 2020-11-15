package org.lint.azzert.context;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.strategy.framework.JUnit4Strategy;
import org.lint.azzert.strategy.framework.NoOpStrategy;
import org.lint.azzert.strategy.framework.TestNgStrategy;

import java.util.*;
import java.util.function.Function;

public class MethodMetadata {

    private String fileName;
    private String methodName;
    private String methodSignature;
    private String packageName;
    private String className;
    private ClassMetadata classMetadata;
    private boolean visible;
    private TestFrameworkStrategy testFramework;
    private LinkedHashSet<AnnotationMetadata> annotations;
    private List<MethodCallMetadata> methodCall;

    MethodMetadata() {
        this.annotations = new LinkedHashSet<>();
        this.methodCall = new LinkedList<>();
        this.methodName = "";
        this.methodSignature = "";
        this.testFramework = new NoOpStrategy();

        resetClassDetails();
    }

    public void resetClassDetails() {
        this.fileName = "";
        this.packageName = "";
        this.className = "";
        this.classMetadata = new ClassMetadata();
    }

    void addMethodCall(MethodCallMetadata methodCallMetadata) {
        this.methodCall.add(methodCallMetadata);
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setMethodName(String name) {
        this.methodName = name;
    }

    public void setMethodSignature(String name) {
        this.methodSignature = name;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(String name) {
        this.className = name;
    }

    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    public void setClassMetadata(ClassMetadata metadata) {
        this.classMetadata = new ClassMetadata(metadata);
    }

    public List<MethodCallMetadata> getMethodCalls() {
        return this.methodCall;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String name) {
        this.packageName = name;
    }

    public void inClassAnnotatedWith(String annotation) {
        this.classMetadata.getAnnotations().add(new AnnotationMetadata(annotation));
    }

    public Set<AnnotationMetadata> getAnnotations() {
        return annotations;
    }

    public boolean getVisible() {
        return this.visible;
    }

    void setVisible(boolean visible) {
        this.visible = visible;
    }

    public TestFrameworkStrategy getTestFramework() {
        return this.testFramework;
    }

    public int getVerificationsCount() {
        if (this.testFramework.getClass() == JUnit4Strategy.class)
            return new MethodMetadataCommand(this, MethodMetadataCommand.TestFrameworkType.JUNIT4).getVerificationsCount();
        else if (this.testFramework.getClass() == TestNgStrategy.class)
            return new MethodMetadataCommand(this, MethodMetadataCommand.TestFrameworkType.TESTNJ).getVerificationsCount();
        return getMethodCalls().size();
    }

    public void seedTestFramework(Map<String, TestFrameworkStrategy> supportedTestFrameworks) {
        final Function<Set<AnnotationMetadata>, TestFrameworkStrategy> getTestFrameworkStrategy = ants -> {
            TestFrameworkStrategy strategy;
            for (AnnotationMetadata ann : ants) {
                strategy = supportedTestFrameworks.get(ann.getAnnotationName());
                if (strategy != null)
                    return strategy;
            }
            return new NoOpStrategy();
        };
        this.testFramework = getTestFrameworkStrategy.apply(this.getAnnotations());
    }


    @Override
    public String toString() {
        return "MethodMetadata{" +
                "fileName='" + fileName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodSignature='" + methodSignature + '\'' +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", classMetadata=" + classMetadata +
                ", visible=" + visible +
                ", testFramework=" + testFramework +
                ", annotations=" + annotations +
                ", methodCall=" + methodCall +
                '}';
    }
}