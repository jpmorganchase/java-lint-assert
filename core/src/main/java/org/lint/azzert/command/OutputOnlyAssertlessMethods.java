package org.lint.azzert.command;

import org.lint.azzert.OutputFormatterCommand;
import org.lint.azzert.context.MethodMetadata;

import java.util.Set;

public class OutputOnlyAssertlessMethods implements OutputFormatterCommand {

    @Override
    public void execute(Set<MethodMetadata> testMethods) {
        testMethods.removeIf(m -> !m.getMethodCalls().isEmpty());
    }
}
