package org.lint.azzert.context;

import org.javatuples.Pair;

import java.util.Collection;

public class JUnit4MethodMetadataCommand{

    final private MethodMetadata method;

    public JUnit4MethodMetadataCommand(MethodMetadata method){
        this.method = method;
    }

    public boolean exceptionIsExpected() {
        final Collection<Pair<String, String>> params = this.method.getAnnotations().iterator().next().getParameters();
        return !this.method.getAnnotations().isEmpty() && !params.isEmpty() && "expected".equals(params.iterator().next().getValue0());
    }

    public int getVerificationsCount() {
        return method.getMethodCalls().size() + (exceptionIsExpected() ? 1 : 0);
    }
}
