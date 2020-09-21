package org.lint.azzert;

import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public interface OutputFormatterCommand {
    default void execute(Set<MethodMetadata> testMethods){}
}
