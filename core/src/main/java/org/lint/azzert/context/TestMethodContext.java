package org.lint.azzert.context;

import org.javatuples.Pair;

import java.util.*;

public class TestMethodContext {

    private String fileName;
    private String methodName;
    private String methodSignature;
    private String packageName;
    private String className;
    private boolean visible;

    private Set<String> annotations;
    private Collection<Pair<Integer, String>> assertMethodsAtLineNumbers;

    public TestMethodContext() {
        this.annotations = new HashSet<>();
        this.assertMethodsAtLineNumbers = new LinkedList<>();
    }

    public TestMethodContext(TestMethodContext source) {
        this.fileName = source.fileName;
        this.methodName = source.methodName;
        this.methodSignature = source.methodSignature;
        this.packageName = source.packageName;
        this.className = source.className;
        this.visible = source.visible;
        this.annotations = new HashSet<>(source.annotations);
        this.assertMethodsAtLineNumbers = new LinkedList<>(source.assertMethodsAtLineNumbers);
    }

    public void resetMethodDetails() {
        this.methodName = "";
        this.methodSignature = "";
        this.annotations = new HashSet<>();
        this.assertMethodsAtLineNumbers  = new LinkedList<>();
        this.visible = false;
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

    public Collection<Pair<Integer, String>> getAssertMethodsAtLineNumbers() {
        return this.assertMethodsAtLineNumbers;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getPackageName(){
        return this.packageName;
    }

    public Set<String> getAnnotations() { return annotations; }


    public void addAssertMethodsAtLineNumbers(Pair<Integer, String> pair) { this.assertMethodsAtLineNumbers.add(pair); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestMethodContext that = (TestMethodContext) o;
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
        return "TestMethodContext{" +
                "fileName='" + fileName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", methodSignature='" + methodSignature + '\'' +
                ", annotations=" + annotations +
                ", packageName='" + packageName + '\'' +
                ", className='" + className + '\'' +
                ", visible=" + visible +
                ", assertMethodsAtLineNumbers=" + assertMethodsAtLineNumbers +
                '}';
    }

}