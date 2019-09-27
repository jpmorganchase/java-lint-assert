package org.lint.azzert.context;

import org.lint.azzert.TestFrameworkStrategy;
import org.lint.azzert.strategy.framework.NoOpStrategy;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MethodMetadata {

    private String fileName;
    private String methodName;
    private String methodSignature;
    private String packageName;
    private String className;
    private boolean visible;

    private TestFrameworkStrategy testFramework;

    private LinkedHashSet<AnnotationMetadata> annotations;
    private List<MethodCallMetadata> methodCall;

    public MethodMetadata() {
        this.annotations = new LinkedHashSet<>();
        this.methodCall = new LinkedList<>();
    }

    public MethodMetadata(MethodMetadata source) {
        this.fileName = source.fileName;
        this.methodName = source.methodName;
        this.methodSignature = source.methodSignature;
        this.packageName = source.packageName;
        this.className = source.className;
        this.visible = source.visible;
        this.annotations = new LinkedHashSet<>(source.annotations);
        this.methodCall = new LinkedList<>(source.methodCall);
        this.testFramework = source.testFramework;
    }

    public void resetMethodDetails() {
        this.methodName = "";
        this.methodSignature = "";
        this.annotations = new LinkedHashSet<>();
        this.methodCall = new LinkedList<>();
        this.visible = false;
        this.testFramework = new NoOpStrategy();
    }

    public void resetClassDetails() {
        this.fileName = "";
        this.packageName = "";
        this.className = "";
    }

    public String getMethodName() {
        return this.methodName;
    }

    public void setFileName(String name) { this.fileName = name; }

    public void setMethodName(String name) { this.methodName = name; }

    public void setMethodSignature(String name) { this.methodSignature = name; }

    public void setPackageName(String name) { this.packageName = name; }

    public void setClassName(String name) { this.className = name; }

    public void setVisible(boolean visible) { this.visible = visible; }

    public List<MethodCallMetadata> getMethodCalls() { return this.methodCall; }

    public String getFileName() {
        return this.fileName;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public LinkedHashSet<AnnotationMetadata> getAnnotations() { return annotations; }

    public boolean getVisible() { return this.visible; }

    public void addMethodCall(MethodCallMetadata methodCallMetadata) { this.methodCall.add(methodCallMetadata); }

    public TestFrameworkStrategy getTestFramework() { return this.testFramework; }

    public void setTestFramework(TestFrameworkStrategy testFramework){ this.testFramework = testFramework; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodMetadata that = (MethodMetadata) o;
        return methodName.equals(that.methodName) &&
                Objects.equals(methodSignature, that.methodSignature) &&
                Objects.equals(packageName, that.packageName) &&
                className.equals(that.className);
    }

    @Override
    public int hashCode() {
        return Objects.hash(methodName, methodSignature, packageName, className);
    }

    @Override
    public String toString() {
        return "MethodMetadata{" +
                "fileName='" + fileName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodSignature='" + methodSignature + '\'' +
                ", annotations=" + annotations +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", visible=" + visible +
                ", methodCall=" + methodCall +
                ", testFramework=" + testFramework +
                '}';
    }

}