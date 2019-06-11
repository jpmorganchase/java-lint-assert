package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.javatuples.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;


public class TestMethodContextBuilder{
    public String name;
    public String signature;
    public String[] exceptions;
    public String descriptor;
    public boolean visible;

    public TestMethodContextBuilder with(Consumer<TestMethodContextBuilder> builder){
        builder.accept(this);
        return this;
    }

    public TestMethodContext create(final String name, final String signature, String[] exceptions){
        return new TestMethodContext(name, signature, exceptions);
    }
}

class TestMethodContext {
    String name;
    String signature;
    String[] exceptions;
    String descriptor;
    boolean visible;
    final Collection<Pair> assertMethodsAtLineNumbers;

    TestMethodContext(String name, String signature, String[] exceptions) {
        this.name = name;
        this.signature = signature;
        this.exceptions = exceptions;
        this.assertMethodsAtLineNumbers = new LinkedList<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        assertMethodsAtLineNumbers.add(new Pair(atLineNumber, assertMethodName));
    }

    @Override
    public String toString() {
        return "TestMethodContext{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", exceptions=" + Arrays.toString(exceptions) +
                ", descriptor='" + descriptor + '\'' +
                ", visible=" + visible +
                ", assertMethodsAtLineNumbers=" + assertMethodsAtLineNumbers +
                '}';
    }
}
