package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import java.util.function.Consumer;

public class TestMethodContext {
    int access;
    String name;
    String desc;
    String signature;
    String[] exceptions;

    public TestMethodContext(int access, String name, String desc, String signature, String[] exceptions) {
        this.access = access;
        this.name = name;
        this.desc = desc;
        this.signature = signature;
        this.exceptions = exceptions;
    }
}

class TestMethodContextBuilder{
    public int access;
    public String name;
    public String desc;
    public String signature;
    public String[] exceptions;

    public TestMethodContextBuilder with(Consumer<TestMethodContextBuilder> builder){
        builder.accept(this);
        return this;
    }

    public TestMethodContext create(int access, final String name, final String desc, final String signature, String[] exceptions){
        return new TestMethodContext(access, name, desc, signature, exceptions);
    }
}
