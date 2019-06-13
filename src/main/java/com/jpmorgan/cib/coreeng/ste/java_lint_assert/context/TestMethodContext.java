package com.jpmorgan.cib.coreeng.ste.java_lint_assert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;

public class TestMethodContext {

    String name;
    String signature;
    String descriptor;
    boolean visible;

    final Collection<Pair<Integer, String>> assertMethodsAtLineNumbers;

    public TestMethodContext() {
        this.assertMethodsAtLineNumbers = new LinkedList<>();
    }

    public TestMethodContext(String name, String descriptor) {
        this();
        this.name = name;
        this.descriptor = descriptor;
    }

    public String getName() {
        return name;
    }

    public Collection<Pair<Integer, String>> getAssertMethodsAtLineNumbers() {
        return assertMethodsAtLineNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestMethodContext that = (TestMethodContext) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(descriptor, that.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, descriptor);
    }

    @Override
    public String toString() {
        return "TestMethodContext{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", descriptor='" + descriptor + '\'' +
                ", visible=" + visible +
                ", assertMethodsAtLineNumbers=" + assertMethodsAtLineNumbers +
                '}';
    }
}