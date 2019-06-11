package com.jpmorgan.cib.coreeng.ste.java_lint_assert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.LinkedList;

public class TestMethodContext {
    String name;
    String signature;
    String descriptor;
    boolean visible;

    final Collection<Pair<Integer, String>> assertMethodsAtLineNumbers;

    public TestMethodContext() {
        this.assertMethodsAtLineNumbers = new LinkedList<>();
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