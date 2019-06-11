package com.jpmorgan.cib.coreeng.ste.java_lint_assert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.LinkedList;

public class LintAssertContext {

    private final int asmVersion;

    private String name;
    private String signature;
    private String descriptor;
    private boolean visible;
    private final Collection<Pair<Integer, String>> assertMethodsAtLineNumbers;

    public LintAssertContext(int asmVersion){
        this.asmVersion = asmVersion;
        this.assertMethodsAtLineNumbers = new LinkedList<>();
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        assertMethodsAtLineNumbers.add(new Pair<>(atLineNumber, assertMethodName));
    }

    public void with(String descriptor, boolean visible) {
        this.descriptor = descriptor;
        this.visible = visible;
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

    public void setName(String name) { this.name = name; }

    public void setSignature(String signature){ this.signature = signature; }

    public int getAsmVersion() { return this.asmVersion; }

    public String getDescriptor() { return this.descriptor;  }
}

