package com.jpmorgan.cib.coreeng.ste.java_lint_assert;

import org.javatuples.Pair;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public class LintAssertContext {

    private final int asmVersion;

    private String name;
    private String signature;
    private String[] exceptions;
    private String descriptor;
    private boolean visible;
    private final Collection<Pair> assertMethodsAtLineNumbers;

    LintAssertContext(int asmVersion){
        this.asmVersion = asmVersion;
        this.assertMethodsAtLineNumbers = new LinkedList<>();
    }

    LintAssertContext(String name, String signature, String[] exceptions) {
        this(Opcodes.ASM7);
        this.name = name;
        this.signature = signature;
        this.exceptions = exceptions;
    }

    public void recordAssert(int atLineNumber, String assertMethodName) {
        assertMethodsAtLineNumbers.add(new Pair(atLineNumber, assertMethodName));
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
                ", exceptions=" + Arrays.toString(exceptions) +
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

class LintAssertContextBuilder{
    public String name;
    public String signature;
    public String[] exceptions;
    public String descriptor;
    public boolean visible;


    public LintAssertContextBuilder with(Consumer<LintAssertContextBuilder> builder){
        builder.accept(this);
        return this;
    }

    public LintAssertContext create(final String name, final String signature, String[] exceptions){
        return new LintAssertContext(name, signature, exceptions);
    }
}
