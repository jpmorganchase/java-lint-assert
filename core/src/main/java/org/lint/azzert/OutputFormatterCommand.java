package org.lint.azzert;

import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public interface OutputFormatterCommand<T> {
    default T execute(Set<MethodMetadata> testMethods){return null;}
}
