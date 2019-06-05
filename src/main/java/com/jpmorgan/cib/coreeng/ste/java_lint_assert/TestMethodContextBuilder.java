package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import java.util.Arrays;
import java.util.function.Consumer;


public class TestMethodContextBuilder{
    public int access;
    public String name;
    public String desc;
    public String signature;
    public String[] exceptions;
    public String descriptor;
    public boolean visible;

    public TestMethodContextBuilder with(Consumer<TestMethodContextBuilder> builder){
        builder.accept(this);
        return this;
    }

    public TestMethodContext create(int access, final String name, final String desc, final String signature, String[] exceptions){
        return new TestMethodContext(access, name, desc, signature, exceptions);
    }
}

class TestMethodContext {
    int access;
    String name;
    String desc;
    String signature;
    String[] exceptions;
    String descriptor;
    boolean visible;

    TestMethodContext(int access, String name, String desc, String signature, String[] exceptions) {
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    @Override
    public String toString() {
        return "TestMethodContext{" +
                "access=" + access +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", signature='" + signature + '\'' +
                ", exceptions=" + Arrays.toString(exceptions) +
                ", descriptor='" + descriptor + '\'' +
                ", visible=" + visible +
                '}';
    }
}
