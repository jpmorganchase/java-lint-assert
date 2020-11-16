package org.lint.azzert.context;

import org.javatuples.Pair;

import java.util.Collection;
import java.util.function.Function;

public class MethodMetadataCommand {

    final private MethodMetadata method;
    private final TestFrameworkType frameworkType;

    enum TestFrameworkType {
        JUNIT4("expected"), TESTNJ("expectedExceptions");

        final String elementException;
        private TestFrameworkType(String e){
            this.elementException = e;
        }
    }

    public MethodMetadataCommand(MethodMetadata method, TestFrameworkType frameworkType){
        this.method = method;
        this.frameworkType = frameworkType;
    }

    public boolean exceptionIsExpected() {
        final Function<MethodMetadata, Collection<Pair<String, String>>> fn = method -> method.getAnnotations().iterator().next().getParameters();
        return !this.method.getAnnotations().isEmpty() && !fn.apply(method).isEmpty() &&
                frameworkType.elementException.equals(fn.apply(method).iterator().next().getValue0());
    }

    public int getVerificationsCount() {
        return method.getMethodCalls().size() + (exceptionIsExpected() ? 1 : 0);
    }
}
