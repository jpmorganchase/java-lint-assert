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

    //TODO::should packageName/className be part of ClassMetadata or ClssMetadata should be inlined?
    private String packageName;
    private String className;
    private ClassMetadata classMetadata;

    private boolean visible;

    private TestFrameworkStrategy testFramework;

    private LinkedHashSet<AnnotationMetadata> annotations;
    private List<MethodCallMetadata> methodCall;

    public MethodMetadata() {
        this.annotations = new LinkedHashSet<>();
        this.methodCall = new LinkedList<>();
        this.classMetadata = new ClassMetadata();
    }

    //TODO::it's getting harder to cleanly clone with growing number of fields. Consider marshaling
    public MethodMetadata(MethodMetadata source) {
        this.fileName = source.fileName;
        this.methodName = source.methodName;
        this.methodSignature = source.methodSignature;
        this.packageName = source.packageName;
        this.className = source.className;
        this.visible = source.visible;
        this.annotations = new LinkedHashSet<>(source.annotations);
        this.methodCall = new LinkedList<>(source.methodCall);
        this.classMetadata = new ClassMetadata(source.getClassMetadata());
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
        this.classMetadata = new ClassMetadata();
    }

    public void inClassAnnotatedWith(String annotation, boolean isAvailableForClientUse) {
        this.classMetadata.getAnnotations().add(new AnnotationMetadata(annotation));
        this.classMetadata.setVisible(isAvailableForClientUse);
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

    public void setClassName(String name) {
        this.className = name;
    }

    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
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

    public LinkedHashSet<AnnotationMetadata> getAnnotations() {
        return annotations;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void addMethodCall(MethodCallMetadata methodCallMetadata) {
        this.methodCall.add(methodCallMetadata);
    }

    public TestFrameworkStrategy getTestFramework() {
        return this.testFramework;
    }

    public void setTestFramework(TestFrameworkStrategy testFramework) {
        this.testFramework = testFramework;
    }

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